package iot.data_center.models;

import iot.data_center.models.base.Base;

public class SmartDoorModel extends Base {

    // when the door is lock it couldn't be open, instead when the door is unlocked it could be open or not
    private boolean lock;
    private boolean open;
    private double accelleration;

    public SmartDoorModel() { super(""); }

    public SmartDoorModel(String id, boolean lock, boolean open, double accelleration) {
        super(id);
        this.lock = lock;
        this.open = open;
        this.accelleration = accelleration;
    }

    public SmartDoorModel(String id, double data, boolean lock, boolean open, double accelleration) {
        super(id, data);
        this.lock = lock;
        this.open = open;
        this.accelleration = accelleration;
    }

    public boolean isLock() { return lock; }

    public void setLock(boolean lock) { this.lock = lock; }

    public boolean isOpen() { return open; }

    public void setOpen(boolean open) { this.open = open; }

    public double getAccelleration() { return accelleration; }

    public void setAccelleration(double accelleration) { this.accelleration = accelleration; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ SMART DOOR ] -> [ ID ]: %s, [ is LOCK ]: %b, [ is OPEN ]: %b, [ ACCELERATION ]: %f, [ TIME ]: %f",
            this.id, this.lock, this.open, this.accelleration, this.data
        );
        return sRet;
    }
}
