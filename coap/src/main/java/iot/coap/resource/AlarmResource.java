package iot.coap.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import iot.coap.persistance.actuator.AlarmManager;
import iot.utils.CoreInterfaces;

public class AlarmResource extends CoapResource {
    
    private static final String OBJECT_TITLE = "AlarmActuatorResource";
    private AlarmManager alarmManager;

    private static final Logger logger = LoggerFactory.getLogger(AlarmResource.class);
    private Gson gson;

    public AlarmResource(String name) {
        super(name);
        this.alarmManager = new AlarmManager();

        this.init();
    }

    private void init() {
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        setObservable(false);
        setObserveType(CoAP.Type.CON);
        
        getAttributes().setAttribute("rf", "iot.alarm.actuator");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String payload = gson.toJson(this.alarmManager.getSwitchModel());
            exchange.respond(CoAP.ResponseCode.CONTENT, payload, exchange.getRequestOptions().getAccept());
            logger.warn("[ ALARM RESOURCE ] -> [ PAYLOAD ]: {}", payload);
        }
        catch (Exception eErr) {
            logger.error("[ ALARM RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        try {
            // toggle the status of AlarmModel
            boolean bStatus = this.alarmManager.getSwitchModel().isOn();
            this.alarmManager.getSwitchModel().setOn(!bStatus);

            logger.warn("[ ALARM RESOURCE ] -> [ STATUS ]: {}", !bStatus);
            exchange.respond(CoAP.ResponseCode.CHANGED, new String(this.gson.toJson(this.alarmManager.getSwitchModel())), exchange.getRequestOptions().getAccept());
        }
        catch (Exception eErr) {
            logger.error("[ ALARM RESOURCE ] -> [ MESSAGE ]", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        logger.info("[ ALARM RESOURCE ] -> [ MESSAGE]: Call a Method NOT IMPLEMENTED");
        exchange.respond(CoAP.ResponseCode.NOT_IMPLEMENTED);
    }
}
