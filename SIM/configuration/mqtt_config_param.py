class MQTTConfParam(object):

    BROKER_ADDRESS = "155.185.4.4"
    BROKER_PORT = 7883
    MQTT_USERNAME = "287357@studenti.unimore.it"
    MQTT_PWD = "fdetnesikodaldcz"
    MQTT_BASE_TOPIC = f"/iot/user/{MQTT_USERNAME}"

    TOPIC_ALARM = "alarm"
    TOPIC_BIOMETRIC = "biometric"
    TOPIC_ENVIRONMENT = "environment"
    TOPIC_LIGHT = "light"
    TOPIC_PEOPLE = "people"
    TOPIC_DOOR = "door"
