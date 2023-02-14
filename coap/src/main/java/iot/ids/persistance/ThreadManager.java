package iot.ids.persistance;

import com.google.gson.Gson;
import iot.ids.configuration.MqttConfigurationParameters;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ThreadManager extends Thread {
    protected final static Logger logger = LoggerFactory.getLogger(ThreadManager.class);
    protected Gson gson;
    protected IMqttClient client;
    protected final String topic;

    public ThreadManager(String topic) throws MqttException {

        this.gson = new Gson();
        this.topic = String.format("%s/%s", MqttConfigurationParameters.MQTT_BASIC_TOPIC, topic);

        this.init();
    }

    private void init() throws MqttException {
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
}
