package iot.coap.persistance.actuator;

import iot.model.actuator.SwitchModel;

public class LightManager {
    private SwitchModel alarmModel;

    public LightManager() {
        // manage the simulation
        this.alarmModel = new SwitchModel("light-switch-model.01", false);
    }

    public SwitchModel getSwitchModel() { return this.alarmModel; }
}
