package iot.data_center.client;

import com.google.gson.Gson;

import iot.data_center.models.sensor.EnvironmentalModel;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentalClient extends Thread {

    private static final String TOPIC = "coap://localhost:5683/environmental";
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentalClient.class);
    private EnvironmentalModel environmentalModel;
    private Gson gson;
    private CoapClient client = null;
    private Request request = null;

    public EnvironmentalClient() {
        this.client = new CoapClient(TOPIC);
        this.request = Request.newGet().setURI(TOPIC).setObserve();
        this.request.setConfirmable(true);

        this.environmentalModel = new EnvironmentalModel();
        this.gson = new Gson();
    }

    @Override
    public void run() {

        try {

            CoapObserveRelation relation = this.client.observe(request, new CoapHandler() {
                @Override
                public void onLoad(CoapResponse coapResponse) {
                    try {
                        EnvironmentalClient.this.setEnvironmentalModel(
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

    public EnvironmentalModel getEnvironmentalModel() { return environmentalModel; }

    public void setEnvironmentalModel(EnvironmentalModel environmentalModel) {
        this.environmentalModel = environmentalModel;
    }

    public void setEnvironmentalModel(String dati) throws Exception {
        try {
            this.environmentalModel = gson.fromJson(dati, EnvironmentalModel.class);
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            throw new Exception(eErr.getMessage());
        }
    }

    public static void main(String[] args) {
        EnvironmentalClient environmentalClient = new EnvironmentalClient();
        environmentalClient.start();
    }
}
