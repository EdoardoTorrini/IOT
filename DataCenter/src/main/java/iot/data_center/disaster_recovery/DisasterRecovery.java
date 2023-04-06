package iot.data_center.disaster_recovery;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.paho.client.mqttv3.MqttException;

public class DisasterRecovery extends Thread {

    private boolean DEHUMIDIFIER;
    private boolean FLOODING;
    private boolean FIRE;

    private int nLevel;

    private boolean bStop = false;

    private ReadingEnvironmetal readingEnvironmetal;

    public DisasterRecovery() throws MqttException, InvocationTargetException,
        NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        this.readingEnvironmetal = new ReadingEnvironmetal();
    }

    @Override
    public void run() {
        while (!this.bStop)
        {
            if (!this.readingEnvironmetal.isNormal()) {

                // check the humidity
                this.DEHUMIDIFIER = this.readingEnvironmetal.isDehumidifier();
                if (this.DEHUMIDIFIER)
                    continue;

                //check if there is a flood
                this.FLOODING = this.readingEnvironmetal.isFlooding();
                if (this.FLOODING)
                    continue;

                // check if there is a fire
                this.FIRE = this.readingEnvironmetal.isFire();
                if (this.FIRE)
                    continue;

                // check if there is an high temperature
                this.nLevel = this.readingEnvironmetal.getLevel();
                if (this.nLevel >= 0)
                    continue;

            }
        }
    }

    


    public void setStop() { this.bStop = true; }
}
