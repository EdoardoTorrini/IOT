package iot.data_center.models;

import iot.data_center.models.base.Base;

public class PCounterModel extends Base {
    private int peopleIn;

    public PCounterModel() { super(""); }

    public PCounterModel(String id, int peopleIn) {
        super(id);
        this.peopleIn = peopleIn;
    }
    
    public PCounterModel(String id, double data, int peopleIn) {
        super(id, data);
        this.peopleIn = peopleIn;
    }

    public int getPeopleIn() { return peopleIn; }

    public void setPeopleIn(int peopleIn) { this.peopleIn = peopleIn; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ PEOPLE COUNTER MODEL ] -> [ ID ]: %s, [ NUM PEOPLE ]: %d, [ DATA ]: %d",
            this.id, this.peopleIn, this.data
        );
        return sRet;
    }
}
