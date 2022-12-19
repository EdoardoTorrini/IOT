package iot.ids.server;

import iot.ids.resource.DeviceResource;
import iot.ids.resource.PeopleCounterResource;
import org.eclipse.californium.core.CoapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDSCoapProcess extends CoapServer {
    private static final Logger logger = LoggerFactory.getLogger(IDSCoapProcess.class);

    public IDSCoapProcess() {
        super();

        this.add(new DeviceResource("device"));
        this.add(new PeopleCounterResource("people_count"));
    }

    public static void main(String[] args) {
        IDSCoapProcess idsCoapProcess = new IDSCoapProcess();
        idsCoapProcess.start();

        idsCoapProcess.getRoot().getChildren().forEach(resource -> {
            String sLog = String.format(
                    "[ RESOURCE ]: %s -> URI: %s - OBS: %b",
                    resource.getName(), resource.getURI(), resource.isObservable()
            );
            logger.info(sLog);
        });
    }
}
