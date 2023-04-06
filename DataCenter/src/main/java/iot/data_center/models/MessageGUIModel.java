package iot.data_center.models;

import java.util.Vector;

import iot.data_center.models.actuator.ConditionerModel;
import iot.data_center.models.actuator.SmartDoorModel;
import iot.data_center.models.actuator.SwitchModel;
import iot.data_center.models.sensor.BiometricModel;
import iot.data_center.models.sensor.EnvironmentalModel;
import iot.data_center.models.sensor.PCounterModel;

public class MessageGUIModel {

    private ConditionerModel conditionerModel;
    private SmartDoorModel smartDoorModel;
    private SwitchModel lightModel;
    private SwitchModel alarmModel;
    
    private BiometricModel biometricModel;
    private EnvironmentalModel environmentalModel;
    private PCounterModel pCounterModel;

    private Vector<String> message;

    public MessageGUIModel() {}

    public MessageGUIModel(
        ConditionerModel conditionerModel, SmartDoorModel smartDoorModel, SwitchModel lightModel, SwitchModel alarmModel,
        BiometricModel biometricModel, EnvironmentalModel environmentModel, PCounterModel pCounterModel
    ) {
        this.conditionerModel = conditionerModel;
        this.smartDoorModel = smartDoorModel;
        this.lightModel = lightModel;
        this.alarmModel = alarmModel;

        this.biometricModel = biometricModel;
        this.environmentalModel = environmentModel;
        this.pCounterModel = pCounterModel;

        this.message = new Vector<String>();
    }

    public void setConditioningObj(ConditionerModel conditionerModel) { this.conditionerModel = conditionerModel; }
    public ConditionerModel getConditionerObj() { return conditionerModel; }

    public void setSmartDoorObj(SmartDoorModel smartDoorModel) { this.smartDoorModel = smartDoorModel; }
    public SmartDoorModel getSmartDoorObj() { return this.smartDoorModel; }

    public void setAlarmObj(SwitchModel switchModel) { this.alarmModel = switchModel; }
    public SwitchModel getAlarmObj() { return this.alarmModel; }

    public void setLightObj(SwitchModel switchModel) { this.lightModel = switchModel; }
    public SwitchModel getLightObj() { return this.lightModel; }

    public void setBiometricObj(BiometricModel biometricModel) { this.biometricModel = biometricModel; }
    public BiometricModel getBiometricModel() { return this.biometricModel; }

    public void setEnvironmentObj(EnvironmentalModel environmentModel) { this.environmentalModel = environmentModel; }
    public EnvironmentalModel gEnvironmentObj() { return this.environmentalModel; }

    public void setPCounterObj(PCounterModel pcounterModel) { this.pCounterModel = pcounterModel; }
    public PCounterModel getPCounterObj() { return this.pCounterModel; }

    public Vector<String> getMessage() { return this.message; }
    public int addMessage(String message) {
        this.message.add(message);
        return this.message.indexOf(message);
    }
    public boolean deleteMessage(String message) { return this.message.remove(message); }
    public String deleteMessage(int index) { return this.message.remove(index); }
}
