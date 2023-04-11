from model import Base


class SmartDoor(Base):

    def __init__(self, code="", is_open=False, lock=True, acceleration=0.0):

        super(SmartDoor, self).__init__(code)
        self.open = is_open
        self.lock = lock
        self.acceleration = acceleration

    def __repr__(self):
        return f"[ SMART DOOR LOCK ] -> " \
               f"[ ID ]: {self.id}" \
               f"[ is OPEN ]: {self.open}" \
               f"[ is LOCK ]: {self.lock}" \
               f"[ ACCELERATION ]: {self.acceleration}" \
               f"[ TIME ]: {self.data}"
