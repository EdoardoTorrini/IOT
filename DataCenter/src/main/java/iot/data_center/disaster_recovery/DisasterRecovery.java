package iot.data_center.disaster_recovery;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.eclipse.californium.elements.exception.ConnectorException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import iot.configuration.MqttConfigurationParameters;
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
    private static final Logger logger = LoggerFactory.getLogger(DisasterRecovery.class);
    private Gson gson;

    private ReadingEnvironmetal readingEnvironmetal;
    private MessageGUIManager msgManager;

    public DisasterRecovery(MessageGUIManager msgManager) throws MqttException, InvocationTargetException,
        NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        this.readingEnvironmetal = new ReadingEnvironmetal();
        this.readingEnvironmetal.start();

        this.gson = new Gson();

        this.msgManager = msgManager;
    }

    @Override
    public void run() {
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
                TimeUnit.SECONDS.sleep(23);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setConditioner(boolean dehumidifier, int level) {
        
        try {
            CoapClient client = new CoapClient(
                String.format(
                    "coap://127.0.0.1:5683/%s", 
                    MqttConfigurationParameters.TOPIC_CONDITIONER
                )
            );

            ConditionerModel conditionerModel = new ConditionerModel("", dehumidifier, level);

            Request request = Request.newPut().setURI(client.getURI()).setPayload(this.gson.toJson(conditionerModel));
            request.setConfirmable(true);

            // vediamo come risponde e nel caso se utilizzarlo
            CoapResponse response = client.advanced(request);
            
            this.msgManager.getMsgGUIModel().setConditioningObj(this.gson.fromJson(new String(response.getPayload()), ConditionerModel.class));
        }
        catch (ConnectorException | IOException eErr) {
            logger.error("[ DISASTER RECOVERY ] -> error on: [ set DEHUMIDIFIER and LEVEL AIR ]: {}", eErr.getMessage());
        }
    }

    private void changeStatusAlarm() {
        try {
            CoapClient client = new CoapClient(
                String.format(
                    "coap://127.0.0.1:5683/%s",
                    MqttConfigurationParameters.TOPIC_ALARM
                )
            );

            Request request = new Request(Code.POST);
            request.setConfirmable(true);

            // vediamo come va questo tipo di richiesta
            CoapResponse response = client.advanced(request);

            this.msgManager.getMsgGUIModel().setAlarmObj(this.gson.fromJson(new String(response.getPayload()), SwitchModel.class));
            this.bAlarm = !this.bAlarm;

        }
        catch (ConnectorException | IOException eErr) {
            logger.error("[ DISASTER RECOVERY ] -> error on: [ set ALARM {} ]: {}", !this.bAlarm, eErr.getMessage());
        }
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
            CoapClient client = new CoapClient(
                String.format(
                    "coap://127.0.0.1:5683/%s",
                    MqttConfigurationParameters.TOPIC_ALARM
                )
            );

            Request request = new Request(Code.GET);
            request.setConfirmable(true);

            // vediamo come va questo tipo di richiesta
            CoapResponse response = client.advanced(request);

            this.msgManager.getMsgGUIModel().setAlarmObj(this.gson.fromJson(new String(response.getPayload()), SwitchModel.class));

        }
        catch (ConnectorException | IOException eErr) {
            logger.error("[ DISASTER RECOVERY ] -> error on: [ get ALARM ]: {}", eErr.getMessage());
        }
    }

    public void setStop() { this.bStop = true; }
}
