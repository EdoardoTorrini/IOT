package iot.ids.persistance;

import com.google.gson.Gson;
import iot.ids.configuration.MqttConfigurationParameters;
import iot.ids.models.EnvironmentalModel;
import iot.ids.persistance.base.Base;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class EnvironmentalManager extends Base {
    protected EnvironmentalModel environmentalModel;

    public EnvironmentalManager() throws MqttException {
        super(MqttConfigurationParameters.TOPIC_ENVIRONMENT);
    }

    @Override
    public void run() {

        if (this.client != null) {
            try {
                this.client.subscribe(this.topic, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage mqttMessage){
                        try {
                            byte[] bPayload = mqttMessage.getPayload();
                            String log = String.format(
                                    "Message Recived [ TOPIC ]: %s, [ MESSAGE ]: %s",
                                    topic, new String(bPayload)
                            );
                            logger.warn(log);

                            String data = new String(bPayload);

                            EnvironmentalManager.this.environmentalModel = gson.fromJson(data, EnvironmentalModel.class);

                            int i = 0;
                        }
                        catch (Exception eErr) {
                            logger.error("[ MESSAGE ]: {}", eErr.getMessage());
                        }
                    }
                });
            } catch (MqttException eErr) {
                String log = String.format(
                        "on MQTTConsumer Err: %s, Code: %d",
                        eErr.getMessage(), eErr.getReasonCode()
                );
                logger.error(log);
            } catch (Exception eErr) {
                String log = String.format("[ MESSAGE ]: %s", eErr.getMessage());
                logger.error(log);

            }
        }

    }

    public EnvironmentalModel getEnvironmentalModel() { return this.environmentalModel; }
    public void setEnvironmentalModel(EnvironmentalModel environmentalModel) { this.environmentalModel = environmentalModel; }

}
