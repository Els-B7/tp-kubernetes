import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.*;
import java.net.http.*;

public class Main {
    static void send(HttpExchange ex, int code, String txt) throws IOException {
        byte[] b = txt.getBytes();
        ex.sendResponseHeaders(code, b.length);
        ex.getResponseBody().write(b);
        ex.close();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HttpClient client = HttpClient.newHttpClient();

        server.createContext("/healthz", ex -> send(ex, 200, "ok"));
        server.createContext("/", ex -> {
            try {
                HttpRequest req = HttpRequest.newBuilder(URI.create(System.getenv("URL_C"))).build();
                String rep = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
                send(ex, 200, "B -> " + rep);
            } catch (Exception e) {
                send(ex, 500, "erreur B\n");
            }
        });

        server.start();
    }
}
