package iot.configuration;

public class MqttConfigurationParameters {
    public static String BROKER_ADDRESS = "155.185.4.4";
    public static int BROKER_PORT = 7883;
    public static final String MQTT_USERNAME = "287357@studenti.unimore.it";
    public static final String MQTT_PASSWORD = "fdetnesikodaldcz";
    public static final String MQTT_BASIC_TOPIC = String.format("/iot/user/%s", MQTT_USERNAME );

    public static final String TOPIC_ALARM = "alarm";
    public static final String TOPIC_BIOMETRIC = "biometric";
    public static final String TOPIC_ENVIRONMENT = "environment";
    public static final String TOPIC_LIGHT = "light";
    public static final String TOPIC_PEOPLE = "people";
    public static final String TOPIC_PEOPLE_SIM = "sim/people";
    public static final String TOPIC_DOOR = "door";
    public static final String TOPIC_CONDITIONER = "conditioner";
}
