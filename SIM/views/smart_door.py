from http.client import NO_CONTENT, BAD_REQUEST, INTERNAL_SERVER_ERROR

from flask import request
from flask_classful import FlaskView


class SmartDoorView(FlaskView):

    def post(self):

        try:
            data = request.get_json(force=True)

            match data.get("type"):

                case "accelleration":
                    pass

                case "forcing":
                    pass

                case "random_data":
                    pass

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
