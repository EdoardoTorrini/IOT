package iot.ids.persistance;

import com.google.gson.Gson;
import iot.ids.configuration.MqttConfigurationParameters;
import iot.ids.models.EnvironmentalModel;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

public class EnvironmentalManager extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(EnvironmentalManager.class);
    protected EnvironmentalModel environmentalModel;
    private Gson gson;
    private IMqttClient client;
    private final String topic;

    public EnvironmentalManager() throws MqttException {
        this.topic = String.format(
                "%s/%s", MqttConfigurationParameters.MQTT_BASIC_TOPIC,
                MqttConfigurationParameters.TOPIC_ENVIRONMENT
        );

        this.init();
        this.gson = new Gson();
        this.environmentalModel = new EnvironmentalModel();
    }

    public void init() throws MqttException {
        String clientID = UUID.randomUUID().toString();
        MqttClientPersistence persistence = new MemoryPersistence();

        this.client = new MqttClient(
                String.format("tcp://%s:%d", MqttConfigurationParameters.BROKER_ADDRESS, MqttConfigurationParameters.BROKER_PORT),
                clientID, persistence
        );

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(MqttConfigurationParameters.MQTT_USERNAME);
        options.setPassword(MqttConfigurationParameters.MQTT_PASSWORD.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        this.client.connect(options);
    }

    @Override
    public void run() {

        if (this.client != null) {
            try {
                this.client.subscribe(this.topic, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                        byte[] bPayload = mqttMessage.getPayload();
                        String log = String.format(
                                "Message Recived [ TOPIC ]: %s, [ MESSAGE ]: %s",
                                topic, new String(bPayload)
                        );
                        logger.warn(log);

                        String data = new String(bPayload);
                        EnvironmentalModel obj = gson.fromJson(
                                gson.toJson(data),
                                EnvironmentalModel.class
                        );

                        int i = 0;
                    }
                });
            } catch (MqttException eErr) {
                String log = String.format(
                        "on MQTTConsumer Err: %s, Code: %d",
                        eErr.getMessage(), eErr.getReasonCode()
                );
                logger.error(log);
            }
        }

    }

    public EnvironmentalModel getEnvironmentalModel() { return this.environmentalModel; }
    public void setEnvironmentalModel(EnvironmentalModel environmentalModel) { this.environmentalModel = environmentalModel; }

    public static void main(String[] args) {
        try {
            EnvironmentalManager tmp = new EnvironmentalManager();
            tmp.start();
        }
        catch (Exception eErr) {
            String log = String.format(
                    "on MQTTConsumer Err: %s, Cause: %s",
                    eErr.getMessage(), eErr.getCause()
            );
            logger.error(log);
        }
    }
}
