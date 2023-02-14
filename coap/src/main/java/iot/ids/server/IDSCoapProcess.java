package iot.ids.server;

import iot.ids.resource.EnvironmentalResource;
import iot.ids.resource.SmartDoorResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class IDSCoapProcess extends CoapServer {
    private static final Logger logger = LoggerFactory.getLogger(IDSCoapProcess.class);

    public IDSCoapProcess() throws MqttException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        super();

        this.add(new EnvironmentalResource("environmental"));
        this.add(new SmartDoorResource("door"));
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
