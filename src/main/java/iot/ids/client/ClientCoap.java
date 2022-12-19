package iot.ids.client;

import com.google.gson.Gson;
import iot.ids.model.DeviceModel;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCoap {
    private static final Logger logger = LoggerFactory.getLogger(ClientCoap.class);
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/device";

    public static final Request getDeviceList() {
        Request request = new Request(CoAP.Code.GET);
        request.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        request.getOptions().setUriQuery("id=light.switch.01");

        request.setConfirmable(true);
        return request;
    }

    public static Request updateDeviceList() {
        Gson gson = new Gson();

        Request request = new Request(CoAP.Code.PUT);
        request.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        request.getOptions().setUriQuery("id=light.switch.02");

        DeviceModel update = new DeviceModel("light.switch.02", "room01", "0.2", "PHILIPS");
        request.setPayload(gson.toJson(update));

        request.setConfirmable(true);
        return request;
    }

    public static void main(String[] args) {
        CoapClient coapClient = new CoapClient(COAP_ENDPOINT);

        Request request = updateDeviceList();

        logger.info("[ REQUEST ]: {}", coapClient.getURI());
        CoapResponse coapResponse = null;

        try {
            coapResponse = coapClient.advanced(request);

            String sText = coapResponse.getResponseText();
            logger.info("[ STATUS CODE ]: {}", coapResponse.getCode());
            logger.info("[ PAYLOAD ]: {}", sText);

        } catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
        }
    }
}
