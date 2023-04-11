package iot.coap.persistance.actuator;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.paho.client.mqttv3.MqttException;

import iot.configuration.MqttConfigurationParameters;
import iot.model.actuator.SmartDoorModel;
import iot.mqtt.persistance.GenericManager;

public class SmartDoorManager {

    private GenericManager<SmartDoorModel> genericManager;
    private SmartDoorModel smartDoorModel;

    private boolean bSimLock = true;

    public SmartDoorManager() throws NoSuchMethodException, InvocationTargetException, 
        InstantiationException, IllegalAccessException, MqttException 
    {

        this.genericManager = new GenericManager<>(MqttConfigurationParameters.TOPIC_DOOR_SIM, SmartDoorModel.class);
        this.genericManager.start();

        this.smartDoorModel = new SmartDoorModel();

    }
    
    public SmartDoorModel getSDModel() {

        this.smartDoorModel = this.genericManager.getObj();
        this.smartDoorModel.setLock(this.bSimLock);
        this.smartDoorModel.setOpen(!this.bSimLock);

        return this.smartDoorModel;
    }

    public void setSimLock(boolean bSimLock) { this.bSimLock = bSimLock; }

}
