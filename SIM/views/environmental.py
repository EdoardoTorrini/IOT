from http.client import NO_CONTENT, BAD_REQUEST, INTERNAL_SERVER_ERROR

from flask import request
from flask_classful import FlaskView


class EnvironmentView(FlaskView):

    def post(self):

        try:
            data = request.get_json(force=True)

            match data.get("type"):

                case "fire":
                    pass

                case "flooding":
                    pass

                case "standard":
                    pass

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
