package iot.configuration;

public class MqttConfigurationParameters {
    public static String BROKER_ADDRESS = "155.185.4.4";
    public static int BROKER_PORT = 7883;
    public static final String MQTT_USERNAME = "*";
    public static final String MQTT_PASSWORD = "*";
    public static final String MQTT_BASIC_TOPIC = String.format("/iot/user/%s", MQTT_USERNAME );

    public static final String TOPIC_ALARM = "alarm";
    public static final String TOPIC_BIOMETRIC = "biometric";
    public static final String TOPIC_BIOMETRIC_SIM = "sim/biometric";
    public static final String TOPIC_ENVIRONMENT_SIM = "sim/environment";
    public static final String TOPIC_ENVIRONMENT = "environment";
    public static final String TOPIC_LIGHT = "light";
    public static final String TOPIC_PEOPLE = "people";
    public static final String TOPIC_PEOPLE_SIM = "sim/people";
    public static final String TOPIC_DOOR = "door";
    public static final String TOPIC_DOOR_SIM = "sim/door";
    public static final String TOPIC_CONDITIONER = "conditioner";

    public static final String URI_LOG = "http://127.0.0.1:8081/log/";
}
