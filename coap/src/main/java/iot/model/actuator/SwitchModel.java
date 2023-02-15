package iot.model.actuator;

import iot.model.BaseModel;

public class SwitchModel extends BaseModel {

    private boolean on;

    public SwitchModel() { super(""); }

    public SwitchModel(String id, boolean on) {
        super(id);
        this.on = on;
    }

    public SwitchModel(String id, double data, boolean on) {
        super(id, data);
        this.on = on;
    }

    public boolean isOn() { return on; }

    public void setOn(boolean on) { this.on = on; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ SWITCH MODEL ] -> [ ID ]: %s, [ is ON ]: %b, [ DATA ]: %f",
            this.id, this.on, this.data
        );
        return sRet;
    }
}
