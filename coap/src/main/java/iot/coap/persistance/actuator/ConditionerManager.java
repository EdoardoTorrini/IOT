package iot.coap.persistance.actuator;

import iot.model.actuator.ConditionerModel;

public class ConditionerManager {

    // gestione con un unico dispositivo

    private ConditionerModel conditionerModel;

    public ConditionerManager() {
        // manage the simulation
        this.conditionerModel = new ConditionerModel("conditioner-model.01", false, ConditionerModel.OFF);
    }

    public ConditionerModel getConditionerModel() { return this.conditionerModel; }
    
}
