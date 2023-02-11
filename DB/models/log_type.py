from sqlalchemy import Column, String, Integer
from models import Session, Base, Engine


class LogType(Base):

    __tablename__ = "log_type"
    __session__ = Session(Engine().engine).session

    DEBUG = 0
    INFO = 1
    WARNING = 2
    ERROR = 3

    code = Column(Integer, primary_key=True)
    description = Column(String)

    def __init__(self):
        self.object = self.__session__.query(LogType)
