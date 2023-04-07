package iot.data_center.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iot.data_center.disaster_recovery.DisasterRecovery;
import iot.data_center.persistance.MessageGUIManager;
import iot.http.services.AppServer;

public class DataCenterServer extends Thread {

    private DisasterRecovery disasterRecovery;
    private MessageGUIManager msgManager;
    private AppServer httpServer;
    private static final Logger logger = LoggerFactory.getLogger(DataCenterServer.class);

    public DataCenterServer() throws Exception 
    {
        this.msgManager = new MessageGUIManager();

        this.disasterRecovery = new DisasterRecovery(this.msgManager);
        this.disasterRecovery.start();

        this.httpServer = new AppServer(this.msgManager);
        this.httpServer.run(new String[]{"server", "configuration.yml"});
    }

    public static void main(String[] args) {
        try {
            DataCenterServer dataCenterServer = new DataCenterServer();
            dataCenterServer.start();
        }
        catch (Exception eErr) {
            logger.error("[ DATA CENTER SERVER ] -> [ MESSAGE ]: {}", eErr.getMessage());
            System.exit(1);
        }
    }
    
}
