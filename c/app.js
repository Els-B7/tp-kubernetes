const http = require('http');
const fs = require('fs');

const file = (process.env.DATA_DIR || '/data') + '/visites.txt';
try { fs.mkdirSync(process.env.DATA_DIR || '/data', { recursive: true }); } catch {}

http.createServer((req, res) => {
  if (req.url === '/healthz') return res.end('ok');

  try {
    let nb = fs.existsSync(file) ? parseInt(fs.readFileSync(file)) || 0 : 0;
    nb++;
    fs.writeFileSync(file, String(nb));
    res.end(`C [visites=${nb}]`);
  } catch {
    res.statusCode = 500;
    res.end('erreur C\n');
  }
}).listen(8080);
