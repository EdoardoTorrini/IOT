package iot.coap.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;

import com.google.gson.Gson;

import iot.coap.persistance.actuator.ConditionerManager;
import iot.http.ClientHTTP;
import iot.model.actuator.ConditionerModel;
import iot.utils.CoreInterfaces;
import iot.model.Log;

public class ConditionerResource extends CoapResource {
    
    private static final String OBJECT_TITLE = "ConditionerActuatorResource";
    private ConditionerManager conditionerManager;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConditionerResource.class);
    private Gson gson;

    public ConditionerResource(String name) {
        super(name);
        this.conditionerManager = new ConditionerManager();
        this.init();
    }

    private void init() {
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();

        setObservable(false);
        setObserveType(CoAP.Type.CON);

        getAttributes().setAttribute("rf", "iot.conditioner.actuator");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_A.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String payload = gson.toJson(this.conditionerManager.getConditionerModel());
            exchange.respond(CoAP.ResponseCode.CONTENT, payload, exchange.getRequestOptions().getAccept());
            logger.warn("[ CONDITIONER RESOURCE ]: [ PAYLOAD ]: {}", payload);
        }
        catch (Exception eErr) {
            logger.error("[ CONDITIONER RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        try {
            int nLevel = this.conditionerManager.getConditionerModel().getLevelAirConditioning();
            int nNewLevel = (nLevel + 1) % (ConditionerModel.HIGH + 1);
            this.conditionerManager.getConditionerModel().setLevelAirConditioning(nNewLevel);

            logger.warn("[ CONDITIONER RESOURCE ] -> [ new LEVEL ]: {}", nNewLevel);
            exchange.respond(CoAP.ResponseCode.CHANGED);
        }
        catch (Exception eErr) {
            logger.error("[ CONDITIONER RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        try {
            String payload = new String(exchange.getRequestPayload());
            ConditionerModel cm = this.gson.fromJson(payload, ConditionerModel.class);

            if (cm != null) {
                
                if (cm.getDehumidifie() != this.conditionerManager.getConditionerModel().getDehumidifie()) {
                    this.conditionerManager.getConditionerModel().setDehumidifie(cm.getDehumidifie());
                    logger.info("[ CONDITIONER RESOURCE ] -> change [ is ON DEHUMIDIFIER ]: {}", cm.getDehumidifie());
                }

                if (cm.getLevelAirConditioning() != this.conditionerManager.getConditionerModel().getLevelAirConditioning()) {
                    this.conditionerManager.getConditionerModel().setLevelAirConditioning(cm.getLevelAirConditioning());
                    logger.info("[ CONDITIONER RESOURCE ] -> change [ LEVEL AIR CONDITIONING ]: {}", cm.getLevelAirConditioning());
                }
                
                ClientHTTP client = new ClientHTTP(
                    new Log(
                        Log.WARNING, 
                        String.format(
                            "[ CONDITIONER RESOURCE ] -> [ DEHUMIDIFIER ]: %b, [ LEVEL CONDITIONING ]: %d", 
                            cm.getDehumidifie(), cm.getLevelAirConditioning()
                        )
                    )
                );
                client.addLog();
                exchange.respond(CoAP.ResponseCode.CHANGED, new String(this.gson.toJson(this.conditionerManager.getConditionerModel())), exchange.getRequestOptions().getAccept());
            }
            else
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);

        }
        catch (Exception eErr) {
            logger.error("[ CONDITIONER RESOURCE ] -> [ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

}
