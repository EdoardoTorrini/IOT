from model import Base


class LightController(Base):

    def __init__(self, code="", on=False):

        super(LightController, self).__init__(code)
        self.bOn = on

    def __repr__(self):

        return f"[ LIGHT CONTROLLER ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ ON ]: {self.bOn}" \
               f"[ TIME ]: {self.data}"
