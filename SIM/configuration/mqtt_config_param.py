class MQTTConfParam(object):

    BROKER_ADDRESS = "155.185.4.4"
    BROKER_PORT = 7883
    MQTT_USERNAME = "287357@studenti.unimore.it"
    MQTT_PWD = "fdetnesikodaldcz"
    MQTT_BASE_TOPIC = f"/iot/user/{MQTT_USERNAME}"

    TOPIC_ALARM = "alarm"
    TOPIC_BIOMETRIC_SIM = "sim/biometric"
    TOPIC_ENVIRONMENT_SIM = "sim/environment"
    TOPIC_LIGHT = "light"
    TOPIC_PEOPLE = "people"
    TOPIC_PEOPLE_SIM = "sim/people"
    TOPIC_DOOR_SIM = "sim/door"
