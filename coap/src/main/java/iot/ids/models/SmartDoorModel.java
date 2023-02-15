package iot.ids.models;

import iot.ids.models.base.Base;

public class SmartDoorModel extends Base {

    // when the door is lock it couldn't be open, instead when the door is unlocked it could be open or not
    private boolean lock;
    private boolean open;
    private double acceleration;

    public SmartDoorModel() { super(""); }

    public SmartDoorModel(String id, boolean lock, boolean open, double acceleration) {
        super(id);
        this.lock = lock;
        this.open = open;
        this.acceleration = acceleration;
    }

    public SmartDoorModel(String id, double data, boolean lock, boolean open, double acceleration) {
        super(id, data);
        this.lock = lock;
        this.open = open;
        this.acceleration = acceleration;
    }

    public boolean isLock() { return lock; }

    public void setLock(boolean lock) { this.lock = lock; }

    public boolean isOpen() { return open; }

    public void setOpen(boolean open) { this.open = open; }

    public double getAcceleration() { return acceleration; }

    public void setAcceleration(double acceleration) { this.acceleration = acceleration; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ SMART DOOR ] -> [ ID ]: %s, [ is LOCK ]: %b, [ is OPEN ]: %b, [ ACCELERATION ]: %f, [ TIME ]: %f",
            this.id, this.lock, this.open, this.acceleration, this.data
        );
        return sRet;
    }
}
