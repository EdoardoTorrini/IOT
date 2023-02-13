package iot.data_center.client;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest {

    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);

    public static void main(String[] args) {
        String resource = "coap://localhost:5683/";

        CoapClient client = new CoapClient(resource);

        Request request = Request.newGet().setURI(resource).setObserve();
        request.setConfirmable(true);

        CoapObserveRelation relation = client.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                logger.info("Notification Response Pretty Print: \n{}", Utils.prettyPrint(response));
                logger.info("NOTIFICATION Body: " + content);
            }

            public void onError() {
                logger.error("OBSERVING FAILED");
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("CANCELLATION.....");
        relation.proactiveCancel();
    }
}
