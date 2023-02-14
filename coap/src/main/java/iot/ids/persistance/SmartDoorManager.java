package iot.ids.persistance;

import iot.ids.configuration.MqttConfigurationParameters;
import iot.ids.models.EnvironmentalModel;
import iot.ids.models.SmartDoorModel;
import iot.ids.persistance.base.Base;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SmartDoorManager extends Base {
    private SmartDoorModel smartDoorModel = null;
    private SmartDoorModel smartDoorModelOld;

    public SmartDoorManager() throws MqttException {
        super(MqttConfigurationParameters.TOPIC_DOOR);
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

                            // need this part for the SIM
                            // -----------------------------------------------------------------------------------------
                            String data = new String(bPayload);
                            if (SmartDoorManager.this.smartDoorModel == null) {
                                SmartDoorManager.this.smartDoorModel = gson.fromJson(data, SmartDoorModel.class);
                                SmartDoorManager.this.smartDoorModelOld = SmartDoorManager.this.smartDoorModel;
                            }

                            if (SmartDoorManager.this.smartDoorModel != null) {
                                SmartDoorManager.this.smartDoorModel = gson.fromJson(data, SmartDoorModel.class);
                                SmartDoorManager.this.smartDoorModel.setLock(
                                        SmartDoorManager.this.smartDoorModelOld.isLock()
                                );
                                SmartDoorManager.this.smartDoorModel.setOpen(
                                        SmartDoorManager.this.smartDoorModelOld.isOpen()
                                );
                                SmartDoorManager.this.smartDoorModelOld = SmartDoorManager.this.smartDoorModel;
                            }
                            // -----------------------------------------------------------------------------------------

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

    public SmartDoorModel getSmartDoorModel() { return smartDoorModel; }

    public void setSmartDoorModel(SmartDoorModel smartDoorModel) { this.smartDoorModel = smartDoorModel; }
}
