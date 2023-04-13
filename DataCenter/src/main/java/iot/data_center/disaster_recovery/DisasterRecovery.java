package iot.data_center.disaster_recovery;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

import iot.configuration.MqttConfigurationParameters;
import iot.data_center.client.GeneralClient;
import iot.data_center.models.actuator.ConditionerModel;
import iot.data_center.models.actuator.SwitchModel;
import iot.data_center.persistance.MessageGUIManager;

public class DisasterRecovery extends Thread {

    private static final String ALARM_FIRE = "Alarm: IDENTIFIED FIRE";
    private static final String ALARM_FLOODING = "Alarm: IDENTIFIED FLOODING";

    private boolean DEHUMIDIFIER;
    private boolean FLOODING;
    private boolean FIRE;

    private int nLevel;

    private boolean bAlarm = false;

    private boolean bStop = false;

    private ReadingEnvironmetal readingEnvironmetal;
    private MessageGUIManager msgManager;

    public DisasterRecovery(MessageGUIManager msgManager) throws MqttException, InvocationTargetException,
        NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        this.readingEnvironmetal = new ReadingEnvironmetal();
        this.readingEnvironmetal.start();

        this.msgManager = msgManager;
    }

    @Override
    public void run() {

        this.refreshAlarmModel();
        this.refreshConditionerModel();

        while (!this.bStop)
        {
            if (!this.readingEnvironmetal.isNormal()) {

                // check the humidity
                this.DEHUMIDIFIER = this.readingEnvironmetal.isDehumidifier();

                // check if there is an high temperature
                this.nLevel = this.readingEnvironmetal.getLevel();
                
                // set the dehumidifier and level of air conditioning
                this.setConditioner(DEHUMIDIFIER, nLevel);

                //check if there is a flood
                this.FLOODING = this.readingEnvironmetal.isFlooding();
                this.changeStatusOfMsg(this.FLOODING, DisasterRecovery.ALARM_FLOODING);

                // check if there is a fire
                this.FIRE = this.readingEnvironmetal.isFire();
                this.changeStatusOfMsg(this.FIRE, DisasterRecovery.ALARM_FIRE);

                // change the status of the alarm if necessary
                if ((!this.bAlarm && this.FIRE) || (!this.bAlarm && this.FLOODING) || (this.bAlarm && !this.FIRE && !this.FLOODING))
                    this.changeStatusAlarm();
            }

            this.msgManager.getMsgGUIModel().setEnvironmentObj(this.readingEnvironmetal.getEnvModel());
            this.refreshAlarmModel();

            try {
                TimeUnit.SECONDS.sleep(10);
            } 
            catch (InterruptedException e) {}
        }
    }

    private void setConditioner(boolean dehumidifier, int level) {

        try {

            ConditionerModel conditionerModel = new ConditionerModel("", dehumidifier, level);
            GeneralClient<ConditionerModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_CONDITIONER, ConditionerModel.class);            

            this.msgManager.getMsgGUIModel().setConditioningObj(client.putModel(conditionerModel));

        }
        catch(Exception eErr) {}
    }

    private void changeStatusAlarm() {

        try {

            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_ALARM, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setAlarmObj(client.changeStatusObj());
            
            this.bAlarm = !this.bAlarm;
        }
        catch (Exception eErr) {}
    }

    private void changeStatusOfMsg(boolean bAlarm, String msg) {
        if (bAlarm) {
            if (!this.msgManager.getMsgGUIModel().getMessage().contains(msg))
                this.msgManager.getMsgGUIModel().addMessage(msg);
        }
        else {
            if (this.msgManager.getMsgGUIModel().getMessage().contains(msg))
                this.msgManager.getMsgGUIModel().deleteMessage(msg);
        }
    }

    private void refreshAlarmModel() {
        try {
            
            GeneralClient<SwitchModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_ALARM, SwitchModel.class);
            this.msgManager.getMsgGUIModel().setAlarmObj(client.getIstance());

        }
        catch (Exception e) {}
    }

    private void refreshConditionerModel() {
        try {
            
            GeneralClient<ConditionerModel> client = new GeneralClient<>(MqttConfigurationParameters.TOPIC_CONDITIONER, ConditionerModel.class);
            this.msgManager.getMsgGUIModel().setConditioningObj(client.getIstance());

        }
        catch (Exception e) {}
    }

    public void setStop() { this.bStop = true; }
}
