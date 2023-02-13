from base import Base


class EnvironmentalMonitoring(Base):

    def __init__(self, sID="", dTemp=0.0, dHumidity=0.0, dUVIndex=0.0, dSmokeLvl=0.0):

        super(EnvironmentalMonitoring, self).__init__(sID)
        self.dTemp = dTemp
        self.dHumidity = dHumidity
        self.dUVIndex = dUVIndex
        self.dSmokeLvl = dSmokeLvl

    def __repr__(self):

        return f"[ ENVIRONMENTAL MONITORING ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ TEMPERATURE ]: {self.dTemp}" \
               f"[ HUMIDITY ]: {self.dHumidity}" \
               f"[ UVINDEX ]: {self.dUVIndex}" \
               f"[ SMOKE LEVEL ]: {self.dSmokeLvl}" \
               f"[ TIME ]: {self.data}"
