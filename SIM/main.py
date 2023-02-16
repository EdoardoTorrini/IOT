from model import EnvironmentalMonitoring, SmartDoor, AlarmController
from model import BiometricSensor, LightController, PeopleCounter
from process import Publisher
from configuration.mqtt_config_param import MQTTConfParam

from os import path


def main():

    ENVIRONMENTAL, E_DATA = "value/environmental/standard.json", None
    SMART_DOOR, SD_DATA = "value/smart_door/random_data.json", None
    BIOMETRIC, B_DATA = "value/biometric_check.json", None
    PCOUNTER, PC_DATA = "value/people/data.json", None
    TOPIC = {}

    if path.exists(ENVIRONMENTAL):
        with open(ENVIRONMENTAL, "r+") as f:
            E_DATA = f.read()

    if path.exists(SMART_DOOR):
        with open(SMART_DOOR, "r+") as f:
            SD_DATA = f.read()

    if path.exists(BIOMETRIC):
        with open(BIOMETRIC, "r+") as f:
            B_DATA = f.read()

    if path.exists(PCOUNTER):
        with open(PCOUNTER, "r+") as f:
            PC_DATA = f.read()

    TOPIC[MQTTConfParam.TOPIC_ENVIRONMENT_SIM] = Publisher(MQTTConfParam.TOPIC_ENVIRONMENT_SIM, E_DATA, EnvironmentalMonitoring)
    TOPIC[MQTTConfParam.TOPIC_DOOR_SIM] = Publisher(MQTTConfParam.TOPIC_DOOR_SIM, SD_DATA, SmartDoor)
    TOPIC[MQTTConfParam.TOPIC_PEOPLE_SIM] = Publisher(MQTTConfParam.TOPIC_PEOPLE_SIM, PC_DATA, PeopleCounter)
    TOPIC[MQTTConfParam.TOPIC_BIOMETRIC_SIM] = Publisher(MQTTConfParam.TOPIC_BIOMETRIC_SIM, B_DATA, BiometricSensor, duty_cycle=300)

    for topic, obj in TOPIC.items():
        if isinstance(obj, Publisher):
            obj.start()


if __name__ == "__main__":
    main()
