from model import Base


class BiometricSensor(Base):

    def __init__(self, code="", token=""):
        
        super(BiometricSensor, self).__init__(code)
        self.token = token

    def __repr__(self):

        return f"[ BIOMETRIC SENSOR ] -> " \
               f"[ ID ]: {self.id}" \
               f"[ TOKEN ]: {self.token}" \
               f"[ TIME ]: {self.data}"
    
    