from process import Publisher
from configuration.mqtt_config_param import MQTTConfParam

from os import path

ENVIRONMENTAL, E_DATA = "value/environmental/standard.json", None
SMART_DOOR, SD_DATA = "value/smart_door/random_data.json", None
TOPIC = {}

if path.exists(ENVIRONMENTAL):
    with open(ENVIRONMENTAL, "r+") as f:
        E_DATA = f.read()

if path.exists(SMART_DOOR):
    with open(SMART_DOOR, "r+") as f:
        SD_DATA = f.read()

TOPIC[MQTTConfParam.TOPIC_ENVIRONMENT] = Publisher(MQTTConfParam.TOPIC_ENVIRONMENT, E_DATA)
TOPIC[MQTTConfParam.TOPIC_DOOR] = Publisher(MQTTConfParam.TOPIC_DOOR, SD_DATA)

for topic, obj in TOPIC.items():
    if isinstance(obj, Publisher):
        obj.start()

