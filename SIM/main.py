from model import EnvironmentalMonitoring, SmartDoor, AlarmController
from model import BiometricSensor, LightController, PeopleCounter
from process import Publisher, BiometricThread
from configuration.mqtt_config_param import MQTTConfParam
from threading import Thread

from server import TOPIC
from flask import Flask
from views.environmental import EnvironmentView
from views.smart_door import SmartDoorView
from views.biometric import BiometricView

from os import path

sIp, nPort = "0.0.0.0", 8000
app = Flask(__name__)
EnvironmentView.register(app, route_base="/environment")
SmartDoorView.register(app, route_base="/smart_door")
BiometricView.register(app, route_base="/biometric")

ENVIRONMENTAL = "value/environmental/standard.json"
SMART_DOOR = "value/smart_door/random_data.json"
BIOMETRIC = "value/biometric_check.json"
PCOUNTER = "value/people/data.json"


TOPIC[MQTTConfParam.TOPIC_ENVIRONMENT_SIM] = Publisher(MQTTConfParam.TOPIC_ENVIRONMENT_SIM, ENVIRONMENTAL, EnvironmentalMonitoring)
TOPIC[MQTTConfParam.TOPIC_DOOR_SIM] = Publisher(MQTTConfParam.TOPIC_DOOR_SIM, SMART_DOOR, SmartDoor)
TOPIC[MQTTConfParam.TOPIC_PEOPLE_SIM] = Publisher(MQTTConfParam.TOPIC_PEOPLE_SIM, PCOUNTER, PeopleCounter)
# TOPIC[MQTTConfParam.TOPIC_BIOMETRIC_SIM] = Publisher(MQTTConfParam.TOPIC_BIOMETRIC_SIM, BIOMETRIC, BiometricSensor, duty_cycle=300)
TOPIC[MQTTConfParam.TOPIC_BIOMETRIC_SIM] = BiometricThread()

try:
    # starting simulation
    for topic, obj in TOPIC.items():
        if isinstance(obj, Thread):
            obj.start()

    # starting web server
    print(f"Starting Server on [ IP ]: {sIp} and [ PORT ]: {nPort}")
    app.run(sIp, nPort)
    

except KeyboardInterrupt:
    print(f"STOP Publisher")
    for topc, obj in TOPIC.items():
        if isinstance(obj, Thread):
            obj.set_stop()
            obj.join()

except Exception as eErr:
    print(f"[ FAIL Publisher ] -> [ ERROR ]: {eErr}")
    for topc, obj in TOPIC.items():
        if isinstance(obj, Thread):
            obj.set_stop()
            obj.join()

    