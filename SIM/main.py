import json

from process import Publisher

from model import AlarmController, BiometricSensor, EnvironmentalMonitoring
from model import LightController, PeopleCounter, SmartDoor
from configuration.mqtt_config_param import MQTTConfParam

from os import path

PATH = "value/environmental/standard.json"
DATA, TOPIC = None, {}

if path.exists(PATH):
    with open(PATH, "r+") as f:
        DATA = f.read()

TOPIC[MQTTConfParam.TOPIC_ENVIRONMENT] = Publisher(MQTTConfParam.TOPIC_ENVIRONMENT, DATA)
for topic, obj in TOPIC.items():
    if isinstance(obj, Publisher):
        obj.start()

