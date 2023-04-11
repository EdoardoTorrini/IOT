package iot.coap.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import iot.coap.persistance.actuator.LightManager;
import iot.http.ClientHTTP;
import iot.utils.CoreInterfaces;
import iot.model.Log;

public class LightResource extends CoapResource {

    private static final String OBJECT_TITLE = "LightAcutatorResource";
    private LightManager lightManager;

    private static final Logger logger = LoggerFactory.getLogger(LightResource.class);
    private Gson gson;

    public LightResource(String name) {
        super(name);

        this.init();
    }

    private void init() {
        
        getAttributes().setTitle(OBJECT_TITLE);
        this.lightManager = new LightManager();
        this.gson = new Gson();

        setObservable(false);
        setObserveType(CoAP.Type.CON);

        getAttributes().setAttribute("rf", "iot.light.actuator");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String payload = gson.toJson(this.lightManager.getSwitchModel());
            logger.warn("[ LIGHT RESOURCE ] -> [ PAYLOAD ]: {}", payload);
            exchange.respond(CoAP.ResponseCode.CONTENT, payload, exchange.getRequestOptions().getAccept());
        }
        catch (Exception eErr) {
            logger.error("[ LIGHT RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        try {
            boolean bStatus = this.lightManager.getSwitchModel().isOn();
            this.lightManager.getSwitchModel().setOn(!bStatus);

            logger.warn("[ LIGHT RESOURCE ] -> [ STATUS ]: {}", !bStatus);

            ClientHTTP client = new ClientHTTP(
                new Log(
                    Log.INFO, 
                    String.format(
                        "[ LIGHT RESOURCE ] -> [ ON ]: %b", 
                        this.lightManager.getSwitchModel().isOn()
                    )
                )
            );
            client.addLog();

            exchange.respond(CoAP.ResponseCode.CHANGED, new String(this.gson.toJson(this.lightManager.getSwitchModel())), exchange.getRequestOptions().getAccept());
        }
        catch (Exception eErr) {
            logger.error("[ LIGHT RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        logger.info("[ LIGHT RESOURCE ] -> [ MESSAGE]: Call a Method NOT IMPLEMENTED");
        exchange.respond(CoAP.ResponseCode.NOT_IMPLEMENTED);        
    }

}
