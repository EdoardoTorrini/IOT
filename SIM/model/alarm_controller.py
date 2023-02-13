from model import Base


class AlarmController(Base):

    def __init__(self, code="", on=False):
    
        super(AlarmController, self).__init__(code)
        self.bOn = on

    def __repr__(self):

        return f"[ ALARM CONTROLER ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ ON ]: {self.bOn}" \
               f"[ TIME ]: {self.data}"
