from http.client import OK, CREATED, BAD_REQUEST, INTERNAL_SERVER_ERROR, NO_CONTENT

from flask import request
from flask_classful import FlaskView

from models import Device
import json


class DeviceAPI(FlaskView):

    def get(self):

        response = []

        try:
            devices = Device().object.all()
            for device in devices:
                response.append(
                    {
                        "code": device.code,
                        "room_id": device.room_id,
                        "soft_version": device.soft_version,
                        "manufacturer": device.manufacturer
                    }
                )

            return json.dumps(response), OK

        except Exception as err:
            return {"message": "internal server error"}, INTERNAL_SERVER_ERROR

    def post(self):

        try:
            data = request.get_json(force=True)
            device = Device(**data)
            device.insert()

            response = {
                "message": "Corrected create new device",
                "device": {
                    "code": device.code,
                    "room_id": device.room_id,
                    "soft_version": device.soft_version,
                    "manufacturer": device.manufacturer
                }
            }
            return response, CREATED

        except TypeError as err:
            print(f"[ TYPE ERROR ]: {err}")
            return {"message": "parsing structure", "error": str(err)}, BAD_REQUEST

        except KeyError as err:
            print(f"[ KEY ERROR ]: {err}")
            return {"message": "not unique code", "error": str(err)}, BAD_REQUEST

        except Exception as err:
            print(f"[ INTERNAL SERVER ERROR ]: {err}")
            return {"messge": "internal server error"}, INTERNAL_SERVER_ERROR

    def put(self):
        # TODO: non funziona l'UPDATE dei device
        try:
            data = request.get_json(force=True)
            device = Device().object.filter_by(code=data.get("code")).first()

            if isinstance(device, Device):

                device.room_id = data.get("room_id")
                device.soft_version = data.get("soft_version")
                device.manufacturer = data.get("manufacturer")

                device.update()

            else:
                pass

            return {}, NO_CONTENT

        except TypeError as err:
            print(f"[ TYPE ERROR ]: {err}")
            return {"message": "parsing structure", "error": str(err)}, BAD_REQUEST

        except Exception as err:
            print(f"[ INTERNAL SERVER ERROR ]: {err}")
            return {"messge": "internal server error"}, INTERNAL_SERVER_ERROR
