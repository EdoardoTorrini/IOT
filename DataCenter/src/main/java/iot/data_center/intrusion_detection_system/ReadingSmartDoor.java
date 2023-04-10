package iot.data_center.intrusion_detection_system;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.paho.client.mqttv3.MqttException;

import iot.configuration.MqttConfigurationParameters;
import iot.data_center.models.actuator.SmartDoorModel;
import iot.data_center.models.sensor.BiometricModel;
import iot.data_center.models.sensor.PCounterModel;
import iot.mqtt.persistance.GenericManager;

public class ReadingSmartDoor extends Thread {

    private SmartDoorModel smartDoorModel;
    private BiometricModel biometricModel;
    private PCounterModel pCounterModel;

    private GenericManager<SmartDoorModel> managerSmartDoor;
    private GenericManager<BiometricModel> managerBiometric;
    private GenericManager<PCounterModel> managerPCounter;

    private boolean bStop = false;
    private boolean bLightOn = false;

    public ReadingSmartDoor() throws MqttException, InvocationTargetException,
        NoSuchMethodException, InstantiationException, IllegalAccessException
    {

        this.managerSmartDoor = new GenericManager<>(MqttConfigurationParameters.TOPIC_DOOR, SmartDoorModel.class);
        this.managerSmartDoor.start();

        this.managerBiometric = new GenericManager<>(MqttConfigurationParameters.TOPIC_BIOMETRIC, BiometricModel.class);
        this.managerBiometric.start();

        this.managerPCounter = new GenericManager<>(MqttConfigurationParameters.TOPIC_PEOPLE, PCounterModel.class);
        this.managerPCounter.start();

        this.smartDoorModel = new SmartDoorModel();
        this.biometricModel = new BiometricModel();
        this.pCounterModel = new PCounterModel();

    }

    @Override
    public void run() {
        while (!this.bStop)
        {

            this.smartDoorModel = this.managerSmartDoor.getObj();
            this.biometricModel = this.managerBiometric.getObj();
            this.pCounterModel = this.managerPCounter.getObj();

            if (this.pCounterModel.getPeopleIn() > 0)
                this.bLightOn = true;
            else
                this.bLightOn = false;

        }
    }

    public SmartDoorModel getSmartDoorModel() { return this.smartDoorModel; }
    public BiometricModel getBiometicModel() { return this.biometricModel; }
    public PCounterModel getPCounterModel() { return this.pCounterModel; }

    public boolean getLightOn() { return this.bLightOn; }
    
    public void setStop() { this.bStop = true; }
}
