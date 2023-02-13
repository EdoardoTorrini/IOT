from model import Base


class BiometricSensor(Base):

    def __init__(self, code="", token=""):
        
        super(BiometricSensor, self).__init__(code)
        self.sToken = token

    def __repr__(self):

        return f"[ BIOMETRIC SENSOR ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ TOKEN ]: {self.sToken}" \
               f"[ TIME ]: {self.data}"
    
    