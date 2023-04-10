package iot.data_center.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iot.data_center.disaster_recovery.DisasterRecovery;
import iot.data_center.intrusion_detection_system.IntrusionDetectionSystem;
import iot.http.services.AppServer;
import iot.data_center.persistance.MessageGUIManager;

public class DataCenterServer extends Thread {

    private DisasterRecovery disasterRecovery;
    private IntrusionDetectionSystem intrusionDetectionSystem;
    private MessageGUIManager msgManager;
    private AppServer httpServer;
    private static final Logger logger = LoggerFactory.getLogger(DataCenterServer.class);

    public DataCenterServer() throws Exception 
    {
        this.msgManager = new MessageGUIManager();

        // start the thread for the disaster recovery system
        this.disasterRecovery = new DisasterRecovery(this.msgManager);
        this.disasterRecovery.start();

        // start the thread for the intrusion detection system
        this.intrusionDetectionSystem = new IntrusionDetectionSystem(this.msgManager);
        this.intrusionDetectionSystem.start();

        // start the http server for the react app
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
