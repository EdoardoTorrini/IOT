package iot.coap.server;

import iot.coap.resource.SmartDoorResource;
import iot.configuration.MqttConfigurationParameters;
import iot.model.sensor.EnvironmentalModel;
import iot.model.sensor.PCounterModel;
import iot.mqtt.persistance.GenericManager;
import iot.mqtt.process.EnvironmentalPublisher;
import iot.mqtt.process.PCounterPublisher;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class IDSCoapProcess extends CoapServer {
    private static final Logger logger = LoggerFactory.getLogger(IDSCoapProcess.class);
    private EnvironmentalPublisher environmentalPublisher;
    private PCounterPublisher pCounterPublisher;
    private GenericManager<EnvironmentalModel> managerEnv;
    private GenericManager<PCounterModel> managerPCounter;

    public IDSCoapProcess() throws MqttException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        super();

        this.managerEnv = new GenericManager<>(MqttConfigurationParameters.TOPIC_ENVIRONMENT_SIM, EnvironmentalModel.class);
        this.managerEnv.start();

        this.environmentalPublisher = new EnvironmentalPublisher(MqttConfigurationParameters.TOPIC_ENVIRONMENT, this.managerEnv);
        this.environmentalPublisher.start();

        this.managerPCounter = new GenericManager<>(MqttConfigurationParameters.TOPIC_PEOPLE_SIM, PCounterModel.class);
        this.managerPCounter.start();

        this.pCounterPublisher = new PCounterPublisher(MqttConfigurationParameters.TOPIC_PEOPLE, this.managerPCounter);
        this.pCounterPublisher.start();

        this.add(new SmartDoorResource(MqttConfigurationParameters.TOPIC_DOOR));
    }

    public static void main(String[] args) {
        try {
            IDSCoapProcess server = new IDSCoapProcess();
            server.start();

            server.getRoot().getChildren().forEach(resource -> {
                String log = String.format(
                    "[ RESOURCE ]: %s -> URI: %s - OBS: %b",
                    resource.getName(), resource.getURI(), resource.isObservable()
                );
                logger.info(log);
            });
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
        }
    }
}
