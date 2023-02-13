from datetime import datetime
import json
from abc import abstractmethod


class Base:

    def __init__(self, sID=""):

        self.sID = sID
        self.data = datetime.now()

    def get_json(self):

        return json.dumps(self.__dict__)

    @abstractmethod
    def __repr__(self):
        pass