package iot.model.actuator;

import iot.model.BaseModel;

public class ConditionerModel extends BaseModel {

    public static final int OFF = 0;
    public static final int LOW = 1;
    public static final int MEDIUM = 2;
    public static final int HIGH = 3;

    private boolean dehumidifier = false;
    private int airConditioningLevel = OFF;

    public ConditionerModel() { super(""); }
    
    public ConditionerModel(String id, boolean onDehumidifier, int airConditioningLevel) {
        super(id);

        this.dehumidifier = onDehumidifier;
        this.airConditioningLevel = airConditioningLevel;
    }

    public ConditionerModel(String id, long data, boolean onDehumidifier, int airConditioningLevel) {
        super(id, data);

        this.dehumidifier = onDehumidifier;
        this.airConditioningLevel = airConditioningLevel;
    }

    public void setDehumidifie(boolean onDehumidifier) { this.dehumidifier = onDehumidifier; }
    public boolean getDehumidifie() { return this.dehumidifier; }

    public void setLevelAirConditioning(int level) { this.airConditioningLevel = level; }
    public int getLevelAirConditioning() { return this.airConditioningLevel; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ CONDITIONER MODEL ] -> [ ID ]: %s, [ is ON ]"
        );

        return sRet;
    }
    
}
