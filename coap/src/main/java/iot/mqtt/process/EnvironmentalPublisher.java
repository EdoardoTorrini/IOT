package iot.mqtt.process;

import iot.model.sensor.EnvironmentalModel;
import iot.mqtt.ThreadManager;
import iot.mqtt.persistance.GenericManager;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class EnvironmentalPublisher extends ThreadManager {
    private final GenericManager<EnvironmentalModel> genericManager;
    private boolean bStop = false;

    public EnvironmentalPublisher(String topic, GenericManager<EnvironmentalModel> genericManager) throws MqttException {
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

                    logger.info("[ MESSAGE ]: {}, [ TOPIC ]: {}", payload, this.topic);
                } else
                    logger.error("[ TOPIC ]: {}, [ PAYLOAD ]: {}, [ is CONNECT ]: {}", this.topic, payload, this.client.isConnected());

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

    public GenericManager<EnvironmentalModel> getGenericManager() { return genericManager; }

    public void setStop(boolean bStop) { this.bStop = bStop; }
}
