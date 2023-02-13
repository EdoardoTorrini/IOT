from base import Base


class BiometricSensor(Base):

    def __init__(self, sID="", sToken=""):
        
        super(BiometricSensor, self).__init__(sID)
        self.sToken = sToken

    def __repr__(self):

        return f"[ BIOMETRIC SENSOR ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ TOKEN ]: {self.sToken}" \
               f"[ TIME ]: {self.data}"
    
    