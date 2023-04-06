package iot.data_center.disaster_recovery;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iot.configuration.MqttConfigurationParameters;
import iot.data_center.models.actuator.ConditionerModel;
import iot.data_center.models.sensor.EnvironmentalModel;
import iot.mqtt.persistance.GenericManager;

public class ReadingEnvironmetal extends Thread {
    
    private final static Logger logger = LoggerFactory.getLogger(ReadingEnvironmetal.class);
    
    private EnvironmentalModel environmentalModel;
    private GenericManager<EnvironmentalModel> managerEnv;
    
    private boolean bDehumidifier = false;
    private boolean bFlooding = false;
    private boolean bFire = false;
    private boolean bNormal = false;
    private int nLevel = ConditionerModel.OFF;

    private boolean bStop = false;

    public ReadingEnvironmetal() throws MqttException, InvocationTargetException,
    NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        this.managerEnv = new GenericManager<>(MqttConfigurationParameters.TOPIC_ENVIRONMENT, EnvironmentalModel.class);
        this.environmentalModel = new EnvironmentalModel();
    }

    @Override
    public void run() {
        while (!this.bStop)
        {
            this.environmentalModel = this.managerEnv.getObj();
            logger.info("[ MESSAGE RECEIVED ], {}", this.environmentalModel.toString());
            
            // check if the humidity is too high
            if (
                (this.environmentalModel.getHumidity() >= 60) &&  
                (this.environmentalModel.getHumidity() <= 80)
            )
                this.bDehumidifier = true;
            else
                this.bDehumidifier = false;
            
            // check if the level of humidity is critical
            if (this.environmentalModel.getHumidity() > 80)
                this.bFlooding = true;
            else
                this.bFlooding = false;

            // check the UvIndex and SmokeLevel 
            if (
                (this.environmentalModel.getUvIndex() > 1) &&
                (this.environmentalModel.getSmokeLevel() > 1)
            )
                this.bFire = true;
            else
                this.bFire = false;
            
            // check the temperature
            if (
                this.environmentalModel.getTemperature() >= 25 &&
                this.environmentalModel.getTemperature() <= 40
            ) {
                if (this.environmentalModel.getTemperature() <= 30)
                    this.nLevel = ConditionerModel.LOW;
                else if (this.environmentalModel.getTemperature() <= 35)
                    this.nLevel = ConditionerModel.MEDIUM;
                else
                    this.nLevel = ConditionerModel.HIGH;
            }
            else
                this.nLevel = ConditionerModel.OFF;
            
            // check if nothig is high for deactive everything 
            if ((!this.bDehumidifier) && (!this.bFlooding) && (!this.bFire) && (this.nLevel < 0))
                this.bNormal = true;
            else
                this.bNormal = false;
            
        }
    }

    public boolean isNormal() { return this.bNormal; }
    public boolean isDehumidifier() { return this.bDehumidifier; }
    public boolean isFire() { return this.bFire; }
    public boolean isFlooding() { return this.bFlooding; }

    public int getLevel() { return this.nLevel; }
    public void setStop() { this.bStop = true; }
}
