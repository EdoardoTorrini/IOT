package iot.coap.persistance.actuator;

import iot.model.actuator.SwitchModel;

public class AlarmManager {

    private SwitchModel alarmModel;

    public AlarmManager() {
        // manage the simulation
        this.alarmModel = new SwitchModel("alarm-switch-model.01", false);
    }

    public SwitchModel getSwitchModel() { return this.alarmModel; }
}
