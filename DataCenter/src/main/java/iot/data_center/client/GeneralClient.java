package iot.data_center.client;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GeneralClient<TYPE> {

    private static final String BASE_TOPIC = "coap://127.0.0.1:5683";
    private static final Logger logger = LoggerFactory.getLogger(GeneralClient.class);
    private Gson gson;

    private TYPE obj;
    private Constructor<TYPE> constructor;
    private Class<?> classOfT;
    private String topic;

    public GeneralClient(String topic, Class<?> classOfT) 
        throws NoSuchMethodException, SecurityException, InstantiationException, 
        IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        this.topic = String.format("%s/%s", BASE_TOPIC, topic);
        this.gson = new Gson();

        this.constructor = (Constructor<TYPE>) classOfT.getConstructor();
        this.obj = constructor.newInstance();
        this.classOfT = classOfT;
    }

    public TYPE getIstance() {
        try {
            
            CoapClient client = new CoapClient(this.topic);
            Request request = new Request(Code.GET);
            
            CoapResponse response = client.advanced(request);
            if (!response.isSuccess())
                throw new Exception(String.format("Error on request -> [ TOPIC ]: %s, [ STATUS CODE ]: %s", this.topic, response.getCode()));

            this.obj = (TYPE) this.gson.fromJson(new String(response.getPayload()), classOfT);

        }
        catch (ConnectorException | IOException eErr) {
            logger.error("[ CoAP ERROR ] -> error on: [ get {} ]: {}", this.topic, eErr.getMessage());
        }
        catch (Exception eErr) {
            logger.error("[ GENERAL ERROR ] -> error on: [ get {} ]: {}", this.topic, eErr.getMessage());
        }
        
        return this.obj;
    }
    
}
