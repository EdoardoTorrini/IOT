from base import Base


class LightController(Base):

    def __init__(self, sID="", bOn=False):

        super(LightController, self).__init__(sID)
        self.bOn = bOn

    def __repr__(self):

        return f"[ LIGHT CONTROLLER ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ ON ]: {self.bOn}" \
               f"[ TIME ]: {self.data}"
