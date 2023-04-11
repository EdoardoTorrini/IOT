package iot.mqtt.process;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import iot.coap.persistance.actuator.SmartDoorManager;
import iot.mqtt.ThreadManager;

public class SmartDoorPublisher extends ThreadManager {
    private SmartDoorManager smartDoorManager;
    private boolean bStop = false;

    public SmartDoorPublisher(String topic, SmartDoorManager genericManager) throws MqttException {
        super(topic);
        this.smartDoorManager = genericManager;
    }

    @Override
    public void run() {
        try {
            while (!this.bStop) {
                String payload = this.gson.toJson(this.smartDoorManager.getSDModel());

                if (this.client.isConnected() && !payload.isEmpty() && !this.topic.isEmpty()) {
                    MqttMessage msg = new MqttMessage(payload.getBytes());

                    msg.setQos(0);
                    msg.setRetained(false);
                    this.client.publish(this.topic, msg);

                    logger.info("[ SMART DOOR ] -> [ MESSAGE ]: {}, [ TOPIC ]: {}", payload, this.topic);
                }
                else
                    logger.error("[ TOPIC ]: {}, [ PAYLOAD ]: {}, [ is CONNECTED ]: {}", this.topic, payload, this.client.isConnected());
                
                Thread.sleep(10000);
            }
        }
        catch (MqttException eErr) {
            logger.error("[ MESSAGE ]: {}, [ CODE ]: {}", eErr.getMessage(), eErr.getReasonCode());
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
        }
    }
    
    public SmartDoorManager getGenericManager() { return this.smartDoorManager; }

    public void setStop() { this.bStop = true; }
}
