from http.client import NO_CONTENT, BAD_REQUEST, INTERNAL_SERVER_ERROR
from configuration.mqtt_config_param import MQTTConfParam
from process import BiometricThread

from flask import request
from flask_classful import FlaskView
from server import TOPIC

class BiometricView(FlaskView):

    __topic__ = MQTTConfParam.TOPIC_BIOMETRIC_SIM

    def post(self):

        try: 
            data = request.get_json(force=True)
            id = data["id"]

            if isinstance(TOPIC[self.__topic__], BiometricThread):
                TOPIC[self.__topic__].set_id_to_send(id)
            
            return {}, NO_CONTENT
        
        except KeyError as eErr:
            print(f"[ TYPE ERROR ]: {eErr}")
            return {"message": "bad request -> id:[token_for_biometric]"}, BAD_REQUEST
    
        except Exception as eErr:
            print(f"[ INTERNAL SERVER ERROR ]: {eErr}")
            return {"message": "internal server error"}, INTERNAL_SERVER_ERROR