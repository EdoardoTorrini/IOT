package iot.ids.resource;

import com.google.gson.Gson;
import iot.ids.model.DeviceModel;
import iot.ids.persistence.DeviceManager;
import iot.utils.CoreInterfaces;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DeviceResource extends CoapResource {
    private static final String OBJECT_TITLE = "DeviceInformationResource";
    private static final Logger logger = LoggerFactory.getLogger(DeviceResource.class);

    private Gson gson;
    private DeviceManager deviceManager;

    public DeviceResource(String name) {
        super(name);

        this.init();
    }
    private void init() {
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.deviceManager = new DeviceManager();

        getAttributes().setAttribute("rf", "iot.ids.device");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_P.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {

            String sPayload = "";
            String sDeviceId = exchange.getQueryParameter("id");

            if ((sDeviceId != null) && (this.deviceManager.getDeviceMap().containsKey(sDeviceId))) {
                sPayload = gson.toJson(this.deviceManager.getDeviceFromId(sDeviceId));
                logger.info("[ ID DEVICE ]: {}", sDeviceId);
            }
            else {
                sPayload = gson.toJson(this.deviceManager.getDeviceMap());
                logger.info("[ GET all the DEVICE ]");
            }

            exchange.respond(CoAP.ResponseCode.CONTENT, sPayload, exchange.getRequestOptions().getAccept());
            logger.warn("[ PAYLOAD ]: {}", sPayload);


        } catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        try {

            String sDeviceId = exchange.getQueryParameter("id");

            String sPayload = new String(exchange.getRequestPayload());
            DeviceModel updateDeviceModel = gson.fromJson(sPayload, DeviceModel.class);

            if ((sDeviceId == null) || (!sDeviceId.equals(updateDeviceModel.getDeviceId()))) {
                exchange.respond(CoAP.ResponseCode.CONFLICT, "Query-Uri [ id ] is different from DeviceId", MediaTypeRegistry.TEXT_PLAIN);
                logger.error("[ QUERY ID ]: {}, [ DEVICE ID ]: {}", sDeviceId, updateDeviceModel.getDeviceId());
            } else {
                if (this.deviceManager.getDeviceMap().containsKey(sDeviceId)) {
                    if (this.deviceManager.updateDeviceMapById(sDeviceId, updateDeviceModel) > 0) {

                        // UPDATE correctly the Device
                        logger.info("[ DEVICE ID ]: {} UPDATE", sDeviceId);
                        exchange.respond(CoAP.ResponseCode.CHANGED);
                    }
                    else {
                        logger.error("[ DEVICE ID ]: {} not UPDATE", sDeviceId);
                        exchange.respond(CoAP.ResponseCode.NOT_IMPLEMENTED);
                    }
                } else {
                    Set<String> aDeviceId = this.deviceManager.getDeviceMap().keySet();
                    exchange.respond(CoAP.ResponseCode.BAD_OPTION, gson.toJson(aDeviceId));
                    logger.warn("[ QUERY ID ]: {} REQUEST UPDATE", sDeviceId);
                }
            }
        } catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
