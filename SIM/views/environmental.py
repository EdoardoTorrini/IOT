from http.client import NO_CONTENT, BAD_REQUEST, INTERNAL_SERVER_ERROR, OK
from configuration.mqtt_config_param import MQTTConfParam
from process import Publisher

from flask import request
from flask_classful import FlaskView
from flask import make_response
from server import TOPIC


class EnvironmentView(FlaskView):

    __topic__ = MQTTConfParam.TOPIC_ENVIRONMENT_SIM

    def get(self):

        state = "standard"
        if isinstance(TOPIC[self.__topic__], Publisher):
            state = TOPIC[self.__topic__].sPath.split("/")[-1].split(".")[0]

        return make_response({ "type": state }, OK, { "Content-type": "application/json" })

    def post(self):

        try:
            data = request.get_json(force=True)

            match data.get("type"):

                case "fire":
                    PATH = "value/environmental/fire.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case "flooding":
                    PATH = "value/environmental/flooding.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case "standard":
                    PATH = "value/environmental/standard.json"
                    if isinstance(TOPIC[self.__topic__], Publisher):
                        TOPIC[self.__topic__].change_file(PATH)

                case _:
                    return {
                       "message": "type it could be",
                       "type": [
                           "fire",
                           "flooding",
                           "standard"
                       ]
                    }, BAD_REQUEST

            return {}, NO_CONTENT

        except KeyError as eErr:
            print(f"[ TYPE ERROR ]: {eErr}")
            return {"message": "bad request -> type:[fire, flooding, standard]"}, BAD_REQUEST

        except Exception as eErr:
            print(f"[ INTERNAL SERVER ERROR ]: {eErr}")
            return {"message": "internal server error"}, INTERNAL_SERVER_ERROR
