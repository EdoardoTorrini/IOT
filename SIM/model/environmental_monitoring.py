from model import Base


class EnvironmentalMonitoring(Base):

    def __init__(self, code="", temperature=0.0, humidity=0.0, uv_index=0.0, smoke_level=0.0):

        super(EnvironmentalMonitoring, self).__init__(code)
        self.temperature = temperature
        self.humidity = humidity
        self.uvIndex = uv_index
        self.smokeLevel = smoke_level

    def __repr__(self):

        return f"[ ENVIRONMENTAL MONITORING ] -> " \
               f"[ ID ]: {self.sID}" \
               f"[ TEMPERATURE ]: {self.temperature}" \
               f"[ HUMIDITY ]: {self.humidity}" \
               f"[ UVINDEX ]: {self.uvIndex}" \
               f"[ SMOKE LEVEL ]: {self.smokeLevel}" \
               f"[ TIME ]: {self.data}"
