package iot.data_center.client;

import com.google.gson.Gson;

import iot.data_center.models.actuator.SmartDoorModel;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartDoorClient extends Thread {

    private static final String TOPIC = "coap://localhost:5683/door";
    private static final Logger logger = LoggerFactory.getLogger(SmartDoorClient.class);
    private SmartDoorModel smartDoorModel;
    private Gson gson;
    private CoapClient client = null;
    private Request request = null;

    public SmartDoorClient() {
        this.client = new CoapClient(TOPIC);
        this.request = Request.newGet().setURI(TOPIC).setObserve();
        this.request.setConfirmable(true);

        this.smartDoorModel = new SmartDoorModel();
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try {

            CoapObserveRelation relation = this.client.observe(request, new CoapHandler() {
                @Override
                public void onLoad(CoapResponse coapResponse) {
                    try {
                        SmartDoorClient.this.setSmartDoorModel(
                                new String(coapResponse.getPayload())
                        );
                        logger.info("[ MESSAGE ]: {}", new String(coapResponse.getPayload()));
                    } catch (Exception eErr) {
                        logger.error("[ MESSAGE ]: {}", eErr.getMessage());
                    }
                }

                @Override
                public void onError() {

                }
            });

            Thread.sleep(100000000);

        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
        }
    }

    public SmartDoorModel getSmartDoorModel() { return smartDoorModel; }

    public void setSmartDoorModel(SmartDoorModel smartDoorModel) { this.smartDoorModel = smartDoorModel; }
    public void setSmartDoorModel(String dati) throws Exception {
        try {
            this.smartDoorModel = gson.fromJson(dati, SmartDoorModel.class);
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            throw new Exception(eErr.getMessage());
        }
    }

    public static void main(String[] args) {
        SmartDoorClient smartDoorClient = new SmartDoorClient();
        smartDoorClient.start();
    }
}
