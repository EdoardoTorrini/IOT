package iot.data_center.intrusion_detection_system;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iot.configuration.MqttConfigurationParameters;
import iot.data_center.client.GeneralClient;
import iot.data_center.models.actuator.SmartDoorModel;
import iot.data_center.persistance.MessageGUIManager;

public class IntrusionDetectionSystem extends Thread {

    private static final String ALARM_INTRUSION = "Alarm: IDENTIFIED INTRUSION";

    private static final Logger logger = LoggerFactory.getLogger(IntrusionDetectionSystem.class);
    private boolean bStop = false;

    private ReadingSmartDoor readingSmartDoor;
    private MessageGUIManager msgManager;
    
    public IntrusionDetectionSystem(MessageGUIManager msgManager) throws InvocationTargetException, 
        NoSuchMethodException, InstantiationException, IllegalAccessException, MqttException 
    {
        this.readingSmartDoor = new ReadingSmartDoor();
        this.readingSmartDoor.start();

        this.msgManager = msgManager;
    }
    
    @Override
    public void run() {

        this.refreshSmartDoorModel();

        while (!this.bStop)
        {
            this.msgManager.getMsgGUIModel().setBiometricObj(this.readingSmartDoor.getBiometicModel());
            this.msgManager.getMsgGUIModel().setPCounterObj(this.readingSmartDoor.getPCounterModel());

            try {
                TimeUnit.SECONDS.sleep(60);
            }
            catch(InterruptedException e) {}

        }
    }

    private void refreshSmartDoorModel() {
        try {
            GeneralClient<SmartDoorModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_DOOR, SmartDoorModel.class);
            this.msgManager.getMsgGUIModel().setSmartDoorObj(client.getIstance());
        }
        catch (Exception e) {}
    }

    public void setStop() { this.bStop = true; }
}
