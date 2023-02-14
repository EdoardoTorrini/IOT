from model import Base


class SmartDoor(Base):

    def __init__(self, code="", is_open=False, lock=True, accelleration=0.0):

        super(SmartDoor, self).__init__(code)
        self.open = is_open
        self.lock = lock
        self.accelleration = accelleration

    def __repr__(self):
        return f"[ SMART DOOR LOCK ] -> " \
               f"[ ID ]: {self.id}" \
               f"[ is OPEN ]: {self.open}" \
               f"[ is LOCK ]: {self.lock}" \
               f"[ ACCELERATION ]: {self.accelleration}" \
               f"[ TIME ]: {self.data}"
