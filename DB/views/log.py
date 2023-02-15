from http.client import OK, CREATED, BAD_REQUEST, INTERNAL_SERVER_ERROR

from flask import request
from flask_classful import FlaskView

from models import Log
import json


class LogAPI(FlaskView):

    def get(self):
        response = []

        try:
            logs = Log().object.all()
            for log in logs:
                response.append(
                    {
                        "code": log[0].code,
                        "category": log[1],
                        "description": log[0].description,
                        "data": log[0].data.strftime('%m/%d/%Y %H:%M')
                    }
                )

            return json.dumps(response), OK

        except Exception as err:
            print(f"[ INTERNAL SERVER ERROR ]: {err}")
            return {"message": "internal server error"}, INTERNAL_SERVER_ERROR

    def post(self):

        try:
            data = request.get_json(force=True)
            logs = Log().object.all()

            # gestione per l'autoincremento dovuto ad un mal funzionamento di SQLite
            data["code"] = logs[-1].code + 1 if len(logs) > 0 else 0

            log = Log(**data)
            log.insert()

            response = {
                "message": "Corrected create new log",
                "device": {
                    "code": log.code,
                    "category": log.category,
                    "description": log.description,
                    "data": log.data
                }
            }
            return response, CREATED

        except TypeError as err:
            print(f"[ TYPE ERROR ]: {err}")
            return {"message": "parsing structure", "error": str(err)}, BAD_REQUEST

        except Exception as err:
            print(f"[ INTERNAL SERVER ERROR ]: {err}")
            return {"messge": "internal server error"}, INTERNAL_SERVER_ERROR
