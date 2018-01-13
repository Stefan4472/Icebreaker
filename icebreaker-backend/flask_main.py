__author__ = 'cj'
# A placeholder Flash implementation. Doesn't do anything right now.

from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

