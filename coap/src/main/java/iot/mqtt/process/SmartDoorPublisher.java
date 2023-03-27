package iot.mqtt.process;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import iot.model.actuator.SmartDoorModel;
import iot.mqtt.ThreadManager;
import iot.mqtt.persistance.GenericManager;

public class SmartDoorPublisher extends ThreadManager {
    private final GenericManager<SmartDoorModel> genericManager;
    private boolean bStop = true;

    public SmartDoorPublisher(String topic, GenericManager<SmartDoorModel> genericManager) throws MqttException {
        super(topic);
        this.genericManager = genericManager;
    }

    @Override
    public void run() {
        try {
            while (!this.bStop) {
                String payload = this.gson.toJson(this.genericManager.getObj());

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
    
    public GenericManager<SmartDoorModel> getGenericManager() { return this.genericManager; }

    public void setStop(boolean bStop) { this.bStop = bStop; }
}
