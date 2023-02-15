package iot.coap.resource.actuator;

import com.google.gson.Gson;
import iot.configuration.MqttConfigurationParameters;
import iot.model.actuator.SmartDoorModel;
import iot.mqtt.persistance.GenericManager;
import iot.utils.CoreInterfaces;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

public class SmartDoorResource extends CoapResource {
    private static final String OBJECT_TITLE = "SmartDoorLockResource";
    private GenericManager<SmartDoorModel> genericManager;
    private static final Logger logger = LoggerFactory.getLogger(SmartDoorResource.class);
    private Gson gson;

    public SmartDoorResource(String name) throws MqttException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        super(name);

        this.init();
    }

    private void init() throws MqttException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        // launch the thread for reading the data from the SIM service
        this.genericManager = new GenericManager<>(MqttConfigurationParameters.TOPIC_DOOR, SmartDoorModel.class);
        this.genericManager.start();

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().setAttribute("rf", "iot.dr.environmental");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
        getAttributes().setObservable();

        Timer timer = new Timer();
        timer.schedule(new SmartDoorResource.UpdateTask(), 0, 10000);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            // notify all observers
            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String payload = gson.toJson(this.genericManager.getObj());
            exchange.respond(CoAP.ResponseCode.CONTENT, payload, exchange.getRequestOptions().getAccept());
            logger.warn("[ PAYLOAD ]: {}", payload);
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
