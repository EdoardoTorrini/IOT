from threading import Thread
import paho.mqtt.client as mqtt

from configuration.mqtt_config_param import MQTTConfParam
from model.biometric import BiometricSensor

import time


class BiometricThread(Thread):

    def __init__(self, duty_cycle=300):

        super().__init__()
        
        self.duty_cycle = duty_cycle
        self.sTopic = "{}/{}".format(MQTTConfParam.MQTT_BASE_TOPIC, MQTTConfParam.TOPIC_BIOMETRIC_SIM)

        self.bStop = False
        self.id_to_send = None
        self.init_client()

    def init_client(self):

        self.client = mqtt.Client(f"python-{self.sTopic}")
        self.client.username_pw_set(MQTTConfParam.MQTT_USERNAME, MQTTConfParam.MQTT_PWD)
        self.client.connect(MQTTConfParam.BROKER_ADDRESS, MQTTConfParam.BROKER_PORT)
        self.client.loop_start()

    def set_id_to_send(self, id: str):
        self.id_to_send = id

    def run(self) -> None:
        
        while not self.bStop:

            if self.id_to_send is not None:

                msg = BiometricSensor("biometric.sensor.01", self.id_to_send).get_json()
                self.id_to_send = None
                self.publish(msg)

                time.sleep(70)

                msg = BiometricSensor("biometric.sensor.01", "").get_json()
                self.publish(msg)

            # time.sleep(self.duty_cycle)

    def publish(self, msg):

        try:

            self.client.publish(self.sTopic, msg, 0, False)
            print(f"[ DATA PUBLISHED ] -> [ TOPIC ]: {self.sTopic}, [ PAYLOAD ]: {msg}")

        except Exception as err:
            print(f"[ MESSAGE ]: {err}, [ TYPE ]: {type(err)}")
    
    def set_stop(self):
        self.bStop = True