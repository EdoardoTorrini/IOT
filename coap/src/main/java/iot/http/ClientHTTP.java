package iot.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import com.google.gson.Gson;

import iot.configuration.MqttConfigurationParameters;
import iot.model.Log;

public class ClientHTTP {

    private Gson gson = new Gson();
    private Log log;

    public ClientHTTP(Log log) {
        this.log = log;
        this.gson = new Gson();
    }

    public void addLog() throws Exception {

        CloseableHttpClient client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        try {

            HttpPost request = new HttpPost(MqttConfigurationParameters.URI_LOG);
            StringEntity params = new StringEntity(this.gson.toJson(this.log).toString());

            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            client.execute(request);

        }
        catch(Exception eErr) { throw new Exception("", eErr); }
        finally { client.close(); }

    }
    
}
