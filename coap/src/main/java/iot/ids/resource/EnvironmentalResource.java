package iot.ids.resource;

import com.google.gson.Gson;
import iot.ids.persistance.EnvironmentalManager;
import iot.utils.CoreInterfaces;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentalResource extends CoapResource {
    private static final String OBJECT_TITLE = "EnvironmentalMonitoringResorce";
    private EnvironmentalManager environmentalManager;
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentalResource.class);

    private Gson gson;

    public EnvironmentalResource(String name) throws MqttException {
        super(name);

        this.init();
    }

    private void init() throws MqttException {
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        // launch the thread for reading the data from the SIM service
        this.environmentalManager = new EnvironmentalManager();
        this.environmentalManager.start();

        setObservable(true);

        getAttributes().setAttribute("rf", "iot.dr.environmental");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String payload = gson.toJson(this.environmentalManager.getEnvironmentalModel());

            exchange.respond(CoAP.ResponseCode.CONTENT, payload, exchange.getRequestOptions().getAccept());
            logger.warn("[ PAYLOAD ]: {}", payload);
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
