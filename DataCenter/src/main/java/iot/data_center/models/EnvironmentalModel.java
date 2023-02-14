package iot.data_center.models;

import iot.data_center.models.base.Base;

public class EnvironmentalModel extends Base {
    private double temperature;
    private double humidity;
    private double uvIndex;
    private double smokeLevel;

    public EnvironmentalModel() { super(""); }

    public EnvironmentalModel(String id, double temperature, double humidity, double uvIndex, double smokeLevel) {
        super(id);
        this.temperature = temperature;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.smokeLevel = smokeLevel;
    }

    public double getTemperature() { return temperature; }

    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }

    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getUvIndex() { return uvIndex; }

    public void setUvIndex(double uvIndex) { this.uvIndex = uvIndex; }

    public double getSmokeLevel() { return smokeLevel; }

    public void setSmokeLevel(double smokeLevel) { this.smokeLevel = smokeLevel; }

    @Override
    public String toString() {
        String sRet = String.format(
                "[ ENVIRONMENTAL MODEL ] -> [ ID ]: %s, [ TEMPERATURE ]: %f, [ HUMIDITY ]: %f, [ UV INDEX ]: %f, [ SMOKE LEVEL ]: %f, [ TIME ]: %d",
                this.id, this.temperature, this.humidity, this.uvIndex, this.smokeLevel, this.data
        );
        return sRet;
    }
}
