from http.client import NO_CONTENT, BAD_REQUEST, INTERNAL_SERVER_ERROR
from configuration.mqtt_config_param import MQTTConfParam
from process import Publisher

from flask import request
from flask_classful import FlaskView
from server import TOPIC


class SmartDoorView(FlaskView):

    __topic__ = MQTTConfParam.TOPIC_DOOR_SIM

    def post(self):

        try:
            data = request.get_json(force=True)

            match data.get("type"):

                case "accelleration":
                    PATH = "value/smart_door/accelleration.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case "forcing":
                    PATH = "value/smart_door/forcing.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case "random_data":
                    PATH = "value/smart_door/random_data.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case _:
                    return {
                        "message": "type it could be",
                        "type": [
                            "accelleration",
                            "forcing",
                            "random_data"
                        ]
                    }, BAD_REQUEST

            return {}, NO_CONTENT

        except KeyError as eErr:
            print(f"[ TYPE ERROR ]: {eErr}")
            return {"message": "bad request -> type:[forcing, accelleration, random_data]"}, BAD_REQUEST

        except Exception as eErr:
            print(f"[ INTERNAL SERVER ERROR ]: {eErr}")
            return {"message": "internal server error"}, INTERNAL_SERVER_ERROR
