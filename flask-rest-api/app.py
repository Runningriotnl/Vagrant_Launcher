#!bin/python
from flask import Flask, jsonify
from vagrantlauncher import *
import json
import logging
from logging.handlers import RotatingFileHandler

app = Flask(__name__)

@app.route('/')
def index_page():
    return "<html><h1>VAGRANT LAUNCHER</h1><p>Welcome to the serverside of your vagrant launcher</p></html>"

@app.route('/rest/api/1.0/boxes', methods=['GET'])
def get_boxes():
    v = VagrantManager()
    boxes = {}
    boxes['boxes'] = v.getListOfBoxes()
    return jsonify(boxes)

@app.route('/rest/api/1.0/boxes/<string:box_id>/up', methods=['GET'])
def up_box(box_id):
    v = VagrantManager()

    #this should be done through error/success http codes instead
    if v.boxExists(box_id):
        box =  v.getBox(box_id)
        v.upBox(box)
        return jsonify({"status":"box is now up"})
    else: 
        return jsonify({"status":"box does not exist"})

@app.route('/rest/api/1.0/boxes/<string:box_id>/provision', methods=['GET'])
def provision_box(box_id):
    v = VagrantManager()

    #this should be done through error/success http codes instead
    if v.boxExists(box_id):
        box =  v.getBox(box_id)
        v.provisionBox(box)
        return jsonify({"status":"box is now provisioned"})
    else: 
        return jsonify({"status":"box does not exist"})

@app.route('/rest/api/1.0/boxes/<string:box_id>/halt', methods=['GET'])
def halt_box(box_id):
    v = VagrantManager()

    #this should be done through error/success http codes instead
    if v.boxExists(box_id):
        box =  v.getBox(box_id)
        v.haltBox(box)
        return jsonify({"status":"box is now halted"})
    else: 
        return jsonify({"status":"box does not exist"})

@app.route('/rest/api/1.0/boxes/<string:box_id>/destroy', methods=['GET'])
def destroy_box(box_id):
    v = VagrantManager()

    #this should be done through error/success http codes instead
    if v.boxExists(box_id):
        box =  v.getBox(box_id)
        v.destroyBox(box)
        return jsonify({"status":"box is now destroyed"})
    else: 
        return jsonify({"status":"box does not exist"})

if __name__ == '__main__':
    handler = RotatingFileHandler('foo.log', maxBytes=10000, backupCount=1)
    handler.setLevel(logging.INFO)
    app.logger.addHandler(handler)
    app.run(debug=True)


