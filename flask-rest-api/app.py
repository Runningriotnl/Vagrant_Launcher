#!Scripts/python.exe
from flask import Flask, jsonify
from vagrantlauncher import *
import json

app = Flask(__name__)

@app.route('/')
def index_page():
    os = Commander().os
    v = VagrantManager()
    return "<html><h1>VAGRANT LAUNCHER</h1><p>Welcome to the serverside of your vagrant launcher</p></html>"

@app.route('/rest/api/1.0/boxes', methods=['GET'])
def get_boxes():
    v = VagrantManager()
    return jsonify(v.getListOfBoxes())

@app.route('/rest/api/1.0/boxes/<string:task_id>/up', methods=['GET'])
def up_box(task_id):
    return jsonify({"status":"has to be implemented"})

@app.route('/rest/api/1.0/boxes/<string:task_id>/halt', methods=['GET'])
def halt_box(task_id):
    return jsonify({"status":"has to be implemented"})

@app.route('/rest/api/1.0/boxes/<string:task_id>/provision', methods=['GET'])
def provision_box(task_id):
    return jsonify({"status":"has to be implemented"})

@app.route('/rest/api/1.0/boxes/<string:task_id>/destroy', methods=['GET'])
def destroy_box(task_id):
    return jsonify({"status":"has to be implemented"})

if __name__ == '__main__':
    app.run(debug=True)


