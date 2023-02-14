from threading import Thread
import paho.mqtt.client as mqtt

from configuration.mqtt_config_param import MQTTConfParam

import json, itertools, time


class Publisher(Thread):
    
    def __init__(self, sTopic, fData, obj=None, duty_cycle=10):

        super(Publisher, self).__init__()
        self.sTopic = sTopic
        self.aData = json.loads(fData)

        self.obj = obj
        self.duty_cycle = duty_cycle

        self.client = None
        self.jData = None

        self.bStop = False

    def run(self):

        try:

            self.client = mqtt.Client(f"python-{self.sTopic}")
            self.client.username_pw_set(MQTTConfParam.MQTT_USERNAME, MQTTConfParam.MQTT_PWD)
            self.client.connect(MQTTConfParam.BROKER_ADDRESS, MQTTConfParam.BROKER_PORT)
            self.client.loop_start()

            for value in itertools.cycle(self.aData):

                if self.obj is not None:

                    self.jData = self.obj(**value).get_json()
                    self.publish()

                else:
                    print(f"[ TOPIC ]: {self.sTopic} -> Check the configuration - self.obj is None")
                    break

                time.sleep(self.duty_cycle)

                if self.bStop:
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

    def set_stop(self):
        self.bStop = True
