from model import Base


class SmartDoor(Base):

    def __init__(self, code="", open=False, lock=True, accelleration=0.0):

        super(SmartDoor, self).__init__(code)
        self.bOpen = open
        self.bLock = lock
        self.dAccelleration = accelleration

    def __repr__(self):
        return f"[ SMART DOOR LOCK ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ is OPEN ]: {self.bOpen}" \
               f"[ is LOCK ]: {self.bLock}" \
               f"[ ACCELERATION ]: {self.dAccelleration}" \
               f"[ TIME ]: {self.data}"
