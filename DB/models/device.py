from sqlalchemy import Column, String, Integer
from models import Session, Base, Engine

from models import CTable


class Device(Base, CTable):

    __tablename__ = 'device'

    code = Column(String, primary_key=True)
    room_id = Column(Integer)
    soft_version = Column(String)
    manufacturer = Column(String)

    def __init__(self, code="", room_id="", soft_version="", manufacturer=""):

        self.code = code
        self.room_id = room_id
        self.soft_version = soft_version
        self.manufacturer = manufacturer

        self.__session__ = Session(Engine().engine).session
        self.object = self.__session__.query(Device)
