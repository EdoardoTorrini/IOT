package iot.coap.server;

import iot.coap.persistance.actuator.SmartDoorManager;
import iot.coap.resource.AlarmResource;
import iot.coap.resource.ConditionerResource;
import iot.coap.resource.LightResource;
import iot.coap.resource.SmartDoorResource;
import iot.configuration.MqttConfigurationParameters;
import iot.model.sensor.BiometricModel;
import iot.model.sensor.EnvironmentalModel;
import iot.model.sensor.PCounterModel;
import iot.mqtt.persistance.GenericManager;
import iot.mqtt.process.BiometricPublisher;
import iot.mqtt.process.EnvironmentalPublisher;
import iot.mqtt.process.PCounterPublisher;
import iot.mqtt.process.SmartDoorPublisher;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class IDSCoapProcess extends CoapServer {

    private static final Logger logger = LoggerFactory.getLogger(IDSCoapProcess.class);

    /* Environmental Model Task */
    private GenericManager<EnvironmentalModel> managerEnv;
    private EnvironmentalPublisher environmentalPublisher;

    /* People Counter Model Task */
    private GenericManager<PCounterModel> managerPCounter;
    private PCounterPublisher pCounterPublisher;

    /* Smart Door Task */
    private SmartDoorPublisher smartDoorPublisher;
    private SmartDoorManager smartDoorManager;

    /* Biometric Model Task */
    private GenericManager<BiometricModel> managerBiometric;
    private BiometricPublisher biometricPublisher;

    public IDSCoapProcess() throws MqttException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException 
    {

        super();
        
        /* Environmental Model Task */
        this.managerEnv = new GenericManager<>(MqttConfigurationParameters.TOPIC_ENVIRONMENT_SIM, EnvironmentalModel.class);
        this.managerEnv.start();

        this.environmentalPublisher = new EnvironmentalPublisher(MqttConfigurationParameters.TOPIC_ENVIRONMENT, this.managerEnv);
        this.environmentalPublisher.start();
        
        /* People Counter Model Task */
        this.managerPCounter = new GenericManager<>(MqttConfigurationParameters.TOPIC_PEOPLE_SIM, PCounterModel.class);
        this.managerPCounter.start();

        this.pCounterPublisher = new PCounterPublisher(MqttConfigurationParameters.TOPIC_PEOPLE, this.managerPCounter);
        this.pCounterPublisher.start();

        /* Smart Door Model Task */
        this.smartDoorManager = new SmartDoorManager();
        this.smartDoorPublisher = new SmartDoorPublisher(MqttConfigurationParameters.TOPIC_DOOR, this.smartDoorManager);
        this.smartDoorPublisher.start();

        /* Biometric Model Task */     
        this.managerBiometric = new GenericManager<>(MqttConfigurationParameters.TOPIC_BIOMETRIC_SIM, BiometricModel.class);
        this.managerBiometric.start();

        this.biometricPublisher = new BiometricPublisher(MqttConfigurationParameters.TOPIC_BIOMETRIC, this.managerBiometric);
        this.biometricPublisher.start();


        this.add(new SmartDoorResource(MqttConfigurationParameters.TOPIC_DOOR, this.smartDoorManager));
        this.add(new AlarmResource(MqttConfigurationParameters.TOPIC_ALARM));
        this.add(new LightResource(MqttConfigurationParameters.TOPIC_LIGHT));
        this.add(new ConditionerResource(MqttConfigurationParameters.TOPIC_CONDITIONER));
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
