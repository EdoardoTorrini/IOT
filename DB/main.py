from flask import Flask
from flask_cors import CORS
from views.log import LogAPI
from views.device import DeviceAPI

from models import Engine
from models import Base

app = Flask(__name__)
cors = CORS(app)

LogAPI.register(app, route_base="/log")
DeviceAPI.register(app, route_base="/device")

if __name__ == "__main__":

    try:
        Base.metadata.create_all(Engine().engine)
        app.run("0.0.0.0", 8080)

    except KeyboardInterrupt:
        SystemExit(0)
