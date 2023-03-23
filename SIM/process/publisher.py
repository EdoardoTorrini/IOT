from threading import Thread
import paho.mqtt.client as mqtt

from configuration.mqtt_config_param import MQTTConfParam

import json, time, os
from enum import Enum


class PubState(Enum):

    INIT = 0, 
    RUN = 1,
    CHANGE_FILE = 2
    ERROR = 3


class Publisher(Thread):
    
    def __init__(self, sTopic, sPath, obj=None, duty_cycle=10):

        super(Publisher, self).__init__()
        self.sTopic = sTopic
        self.sPath = sPath
        self.aData = None

        self.obj = obj
        self.duty_cycle = duty_cycle

        self.client = None
        self.jData = None

        self.read_data()
        self.nState = PubState.INIT.value
        self.bStop = False

    def read_data(self):

        if os.path.exists(self.sPath):
            with open(self.sPath, 'r+') as f:
                self.aData = json.loads(f.read())
        else:
            print(f"[ FILE ]: {self.sPath} not found")

    def change_file(self, sPath):

        self.sPath = sPath
        self.read_data()
        self.nState = PubState.CHANGE_FILE.value

    def run(self):

        try:

            while not self.bStop:

                match self.nState:

                    case PubState.INIT.value:

                        self.client = mqtt.Client(f"python-{self.sTopic}")
                        self.client.username_pw_set(MQTTConfParam.MQTT_USERNAME, MQTTConfParam.MQTT_PWD)
                        self.client.connect(MQTTConfParam.BROKER_ADDRESS, MQTTConfParam.BROKER_PORT)
                        self.client.loop_start()
                        self.nState = PubState.RUN.value

                    case PubState.RUN.value:

                        for value in self.aData:

                            if self.obj is not None:

                                self.jData = self.obj(**value).get_json()
                                self.publish()

                            else:
                                print(f"[ TOPIC ]: {self.sTopic} -> Check the configuration - self.obj is None")
                                break

                            time.sleep(self.duty_cycle)

                            if self.bStop or self.nState != PubState.RUN.value:
                                break

                    case PubState.CHANGE_FILE.value:

                        print(f"ENTER in CHANGE_FILE state")
                        self.nState = PubState.RUN.value

                    case PubState.ERROR.value:

                        print(f"[ CLOSE THREAD ] - [ TOPIC ]: {self.sTopic}")
                        self.set_stop()

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

    def set_stop(self):
        self.bStop = True
