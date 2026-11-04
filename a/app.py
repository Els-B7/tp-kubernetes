import os, requests
from flask import Flask

app = Flask(__name__)

@app.route("/healthz")
def health():
    return "ok"

@app.route("/")
def index():
    try:
        res = requests.get(os.environ["URL_B"])
        return f"A -> {res.text}\n"
    except:
        return "erreur A\n", 500
