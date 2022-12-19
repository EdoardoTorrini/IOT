package iot.ids.client;

import com.google.gson.Gson;
import iot.ids.model.DeviceModel;
import iot.ids.persistence.DeviceManager;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
import java.util.HashMap;

public class DeviceCoapClient extends CoapClient {
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/device";
    private Gson gson;
    private Request request = null;

    public DeviceCoapClient() {
        super(COAP_ENDPOINT);
        gson = new Gson();
    }

    public HashMap<String, DeviceModel> getDeviceList() throws ConnectorException, IOException {
        request = new Request(CoAP.Code.GET);
        request.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        request.setConfirmable(true);

        CoapResponse coapResponse = this.advanced(request);
        DeviceManager deviceManager = gson.fromJson(
                coapResponse.getResponseText(), DeviceManager.class
        );

        return deviceManager.getDeviceMap();
    }

}
