from sqlalchemy import Column, String, Integer, DateTime, ForeignKey, text
from models import Session, Base, Engine

from models import LogType, CTable
from datetime import datetime


class Log(Base, CTable):

    __tablename__ = "log"

    code = Column(Integer, primary_key=True)
    category = Column(Integer) #, ForeignKey("log_type.code"))
    description = Column(String)
    data = Column(DateTime)

    def __init__(self, code=0, category=LogType.DEBUG, description="", data=None):

        self.code = code
        self.category = category
        self.description = description
        self.data = datetime.now()

        self.__session__ = Session(Engine().engine).session
        self.object = self.__session__.query(Log, LogType.description).join(LogType, Log.category == LogType.code)

