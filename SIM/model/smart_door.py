from base import Base


class SmartDoor(Base):

    def __init__(self, sID="", bOpen=False, bLock=True, dAccelleration=0.0):

        super(SmartDoor, self).__init__(sID)
        self.bOpen = bOpen
        self.bLock = bLock
        self.dAccelleration = dAccelleration

    def __repr__(self):
        return f"[ SMART DOOR LOCK ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ is OPEN ]: {self.bOpen}" \
               f"[ is LOCK ]: {self.bLock}" \
               f"[ ACCELERATION ]: {self.dAccelleration}" \
               f"[ TIME ]: {self.data}"
