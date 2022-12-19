package iot.ids.resource;

import com.google.gson.Gson;
import iot.ids.model.PeopleCounterModel;
import iot.utils.CoreInterfaces;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeopleCounterResource extends CoapResource {

    private static final String OBJECT_TITLE = "PeopleCounterSmartObject";
    private static final Logger logger = LoggerFactory.getLogger(PeopleCounterResource.class);
    private Gson gson;
    private PeopleCounterModel peopleCounterModel;

    public PeopleCounterResource(String name) {
        super(name);

        this.init();
    }
    private void init() {
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.peopleCounterModel = new PeopleCounterModel();

        setObservable(true);
        setObserveType(CoAP.Type.CON);

        getAttributes().setAttribute("rt", "iot.ids.people_counter");
        getAttributes().setAttribute("if", CoreInterfaces.CORE_S.getValue());
        getAttributes().setAttribute("ct", Integer.toString(MediaTypeRegistry.APPLICATION_JSON));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {

            if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {
                exchange.respond(CoAP.ResponseCode.CONTENT, gson.toJson(this.peopleCounterModel), MediaTypeRegistry.APPLICATION_JSON);
                logger.warn("[ JSON PAYLOAD ]: (\n{}\n)", this.peopleCounterModel.toString());
            }
            else {
                logger.warn("[ TEXT PLAIN PAYLOAD ]: (\n{}\n)", this.peopleCounterModel.toString());
                exchange.respond(CoAP.ResponseCode.CONTENT, String.valueOf(this.peopleCounterModel.toString()), MediaTypeRegistry.TEXT_PLAIN);
            }

        } catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
