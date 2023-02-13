from base import Base


class AlarmController(Base):

    def __init__(self, sID="", bOn=False):
    
        super(AlarmController, self).__init__(sID)
        self.bOn = bOn

    def __repr__(self):

        return f"[ ALARM CONTROLER ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ ON ]: {self.bOn}" \
               f"[ TIME ]: {self.data}"
