package iot.data_center.client;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest {

    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

    public static void main(String[] args) {
        String resource = "coap://localhost:5683/environmental";

        CoapClient client = new CoapClient(resource);

        Request request = Request.newGet().setURI(resource).setObserve();
        request.setConfirmable(true);

        CoapObserveRelation relation = client.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                logger.info("[ MESSAGE ]: {}", new String(response.getPayload()));
            }

            public void onError() {
                logger.error("OBSERVING FAILED");
            }
        });

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("CANCELLATION.....");
        relation.proactiveCancel();
    }
}
