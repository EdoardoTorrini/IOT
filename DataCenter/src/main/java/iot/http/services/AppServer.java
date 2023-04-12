package iot.http.services;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import iot.data_center.persistance.MessageGUIManager;
import iot.http.resources.MessageGUIResource;

public class AppServer extends Application<Configuration> {
    
    private MessageGUIManager msg;

    public AppServer(MessageGUIManager msg) { this.msg = msg; }

    @Override
    public void run(Configuration arg0, Environment environment) throws Exception {

        environment.jersey().register(
            new MessageGUIResource(this.msg)
        );
    }
}
