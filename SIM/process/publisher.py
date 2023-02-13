from threading import Thread
import paho.mqtt.client as mqtt

from model import SmartDoor, AlarmController, BiometricSensor
from model import LightController, PeopleCounter, EnvironmentalMonitoring

from configuration.mqtt_config_param import MQTTConfParam

import json
import itertools
import time


class Publisher(Thread):
    
    def __init__(self, sTopic, fData):

        super(Publisher, self).__init__()
        self.sTopic = sTopic
        self.aData = json.loads(fData)

        self.client = None
        self.jData = None

        self.bStop = False

    def run(self):

        try:

            sPubId = f"python-{self.sTopic}"
            self.client = mqtt.Client(sPubId)
            self.client.username_pw_set(MQTTConfParam.MQTT_USERNAME, MQTTConfParam.MQTT_PWD)
            self.client.connect(MQTTConfParam.BROKER_ADDRESS, MQTTConfParam.BROKER_PORT)
            self.client.loop_start()

            for value in itertools.cycle(self.aData):

                if self.check_model(value, EnvironmentalMonitoring):

                    self.jData = EnvironmentalMonitoring(**value).get_json()
                    self.publish()

                time.sleep(5)

                if self.bStop:
                    self.client.loop_stop()
                    break

        except Exception as err:
            print(f"[ MESSAGE ]: {err}, [ TYPE ]: {type(err)}")

    def publish(self):

        try:

            sTargetTopic = f"{MQTTConfParam.MQTT_BASE_TOPIC}/" \
                           f"{self.sTopic}"

            if self.jData is not None:
                self.client.publish(sTargetTopic, self.jData, 0, False)
                print(f"[ DATA PUBLISHED ] -> [ TOPIC ]: {sTargetTopic}, [ PAYLOAD ]: {self.jData}")

            else:
                raise TypeError("[ DATA ]: None - self.jData")

        except Exception as err:
            print(f"[ MESSAGE ]: {err}, [ TYPE ]: {type(err)}")

    def check_model(self, dData, model):
        bOk = False

        try:

            new = model(**dData)
            bOk = True

        except Exception as err:
            pass

        return bOk

    def set_stop(self):
        self.bStop = True
