package iot.mqtt.persistance;

import iot.mqtt.ThreadManager;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericManager<TYPE> extends ThreadManager {

    protected TYPE obj;
    protected Constructor<TYPE> constructor;
    protected Class<?> classOfT;

    public GenericManager(String topic, Class<?> classOfT)
            throws MqttException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        super(topic);

        this.constructor = (Constructor<TYPE>) classOfT.getConstructor();
        this.obj = constructor.newInstance();
        this.classOfT = classOfT;
    }

    @Override
    public void run() {
        if (this.client != null) {
            try {
                this.client.subscribe(this.topic, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                        String sPayload = new String(mqttMessage.getPayload());
                        String sLog = String.format("[ TOPIC ]: %s [ MESSAGE ]: %s", topic, sPayload);
                        logger.info(sLog);
                        GenericManager.this.obj = (TYPE) gson.fromJson(sPayload, GenericManager.this.classOfT);
                    }
                });
            }
            catch (MqttException eErr) {
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

    public TYPE getObj() { return obj; }

    public void setObj(TYPE obj) { this.obj = obj; }
}
