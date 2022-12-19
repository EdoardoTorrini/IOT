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
        getAttributes().setAttribute("if", CoreInterfaces.CORE_RP.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {

            String sDeviceId = exchange.getQueryParameter("id");
            String sPayload = gson.toJson(this.deviceManager.getDeviceMap());
            if (this.deviceManager.getDeviceMap().containsKey(sDeviceId))
                sPayload = gson.toJson(this.deviceManager.getDeviceFromId(sDeviceId));

            exchange.respond(CoAP.ResponseCode.CONTENT, sPayload, exchange.getRequestOptions().getAccept());
            logger.warn("[ PAYLOAD ]: (\n{}\n)", sPayload);


        } catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
