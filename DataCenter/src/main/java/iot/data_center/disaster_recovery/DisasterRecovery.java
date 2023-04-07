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

            try {
                TimeUnit.MINUTES.sleep(1);
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
                    "coap:://localhost:5683/%s", 
                    MqttConfigurationParameters.TOPIC_CONDITIONER
                )
            );

            ConditionerModel conditionerModel = new ConditionerModel("", dehumidifier, level);

            Request request = new Request(Code.PUT);
            request.setConfirmable(true);
            request.setPayload(this.gson.toJson(conditionerModel));

            // vediamo come risponde e nel caso se utilizzarlo
            CoapResponse response = client.advanced(request);
            int i = 0;
        }
        catch (ConnectorException | IOException eErr) {
            logger.error("[ DISASTER RECOVERY ] -> error on: [ set DEHUMIDIFIER and LEVEL AIR ]: {}", eErr.getMessage());
        }
    }

    private void changeStatusAlarm() {
        try {
            CoapClient client = new CoapClient(
                String.format(
                    "coap://localhost:5683/%s",
                    MqttConfigurationParameters.TOPIC_ALARM
                )
            );

            Request request = new Request(Code.POST);
            request.setConfirmable(true);

            // vediamo come va questo tipo di richiesta
            CoapResponse response = client.advanced(request);
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

    public void setStop() { this.bStop = true; }
}
