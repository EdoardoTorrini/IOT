package iot.data_center.intrusion_detection_system;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

import iot.configuration.MqttConfigurationParameters;
import iot.data_center.client.GeneralClient;
import iot.data_center.models.actuator.SmartDoorModel;
import iot.data_center.models.actuator.SwitchModel;
import iot.data_center.persistance.MessageGUIManager;

public class IntrusionDetectionSystem extends Thread {

    private static final String ALARM_INTRUSION = "Alarm: IDENTIFIED INTRUSION";

    private boolean bStop = false;
    private boolean bAlarm = false;
    private boolean bLightOn = false;
    private boolean bStatusLock = false;

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
        this.refreshAlarmModel();
        this.refreshLightModel();

        while (!this.bStop)
        {
            this.msgManager.getMsgGUIModel().setBiometricObj(this.readingSmartDoor.getBiometicModel());
            this.msgManager.getMsgGUIModel().setPCounterObj(this.readingSmartDoor.getPCounterModel());
            this.msgManager.getMsgGUIModel().setSmartDoorObj(this.readingSmartDoor.getSmartDoorModel());

            if (this.bLightOn != this.readingSmartDoor.getLightOn())
                this.changeLightStatus();

            if (
                (!this.bAlarm && this.readingSmartDoor.getBruteForce()) ||
                (!this.bAlarm && this.readingSmartDoor.getLockForcing()) ||
                (this.bAlarm && !this.readingSmartDoor.getBruteForce() && !this.readingSmartDoor.getLockForcing())
            )
                this.changeStatusAlarm();


            if (this.readingSmartDoor.getDoorOk() ^ this.bStatusLock)
                this.unlockDoor();

            // delay for reading
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

    private void changeLightStatus() {
        try {

            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_LIGHT, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setLightObj(client.changeStatusObj());
            
            this.bLightOn = !this.bLightOn;
        }
        catch (Exception e) {}
    }

    private void refreshAlarmModel() {
        try {

            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_ALARM, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setAlarmObj(client.getIstance());

        }
        catch (Exception e) {}
    }

    public void refreshLightModel() {
        try {

            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_LIGHT, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setLightObj(client.getIstance());

        }
        catch (Exception e) {}
    }

    private void changeStatusAlarm() {
        try {

            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_ALARM, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setAlarmObj(client.changeStatusObj());

            this.bAlarm = !this.bAlarm;

            if (this.bAlarm) {
                if (!this.msgManager.getMsgGUIModel().getMessage().contains(ALARM_INTRUSION))
                    this.msgManager.getMsgGUIModel().addMessage(ALARM_INTRUSION);
            }
            else {
                if (this.msgManager.getMsgGUIModel().getMessage().contains(ALARM_INTRUSION))
                    this.msgManager.getMsgGUIModel().deleteMessage(ALARM_INTRUSION);
            }

        }
        catch(Exception e) {}
    }

    private void unlockDoor() {
        try {

            GeneralClient<SmartDoorModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_DOOR, SmartDoorModel.class);
            this.msgManager.getMsgGUIModel().setSmartDoorObj(client.changeStatusObj());

            this.bStatusLock = !this.bStatusLock;
        }
        catch(Exception e) {}
    }

    public void setStop() { this.bStop = true; }
}
