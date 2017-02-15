#!./bin/python3.6
from flask import Flask, jsonify
from vagrantlauncher import *
import json

app = Flask(__name__)

@app.route('/rest/api/1.0/boxes', methods=['GET'])
def get_boxes():
    v = VagrantManager()
    v.listBoxes()
    boxes = {} 
    for box in v.ListOfBoxes:

        boxes['boxes'] = []
        boxes['boxes'].append({'ID': box.ID, 'name':box.name, 'state': box.state, 'directory': box.directory, 'provider': box.provider})
    
    return jsonify(boxes)

@app.route('/rest/api/1.0/boxes/up', methods=['PUT'])
def up_box():
    test = ""

@app.route('/rest/api/1.0/boxes/halt', methods=['PUT'])
def halt_box():
    test = ""

@app.route('/rest/api/1.0/boxes/provision', methods=['PUT'])
def provision_box():
    test = ""

@app.route('/rest/api/1.0/boxes/destroy', methods=['PUT'])
def destroy_box():
    test = ""

    

if __name__ == '__main__':
    app.run(debug=True)


