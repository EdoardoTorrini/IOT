package iot.ids.model;

public class PeopleCounterModel {

    private String sDeviceId;
    private int nPeopleIN;
    private int nPeopleOUT;
    private long lTime;

    public PeopleCounterModel() { }

    public PeopleCounterModel(String sDeviceId, int nPeopleIN, int nPeopleOUT) {
        new PeopleCounterModel(sDeviceId, nPeopleIN, nPeopleOUT, System.currentTimeMillis());
    }
    public PeopleCounterModel(String sDeviceId, int nPeopleIN, int nPeopleOUT, long lTime) {
        this.sDeviceId = sDeviceId;
        this.nPeopleIN = nPeopleIN;
        this.nPeopleOUT = nPeopleOUT;
        this.lTime = lTime;
    }

    public String getDeviceId() { return sDeviceId; }

    public void setDeviceId(String sDeviceId) { this.sDeviceId = sDeviceId; }

    public int getPeopleIN() { return nPeopleIN; }

    public void setPeopleIN(int nPeopleIN) { this.nPeopleIN = nPeopleIN; }

    public int getPeopleOUT() { return nPeopleOUT; }

    public void setPeopleOUT(int nPeopleOUT) { this.nPeopleOUT = nPeopleOUT; }

    public long getTime() { return lTime; }

    public void setTime(long lTime) { this.lTime = lTime; }

    @Override
    public String toString() {
        String sRet = String.format(
                "[ PEOPLE COUNTER MODEL ] - [ DEVICE ID ]: {}, [ PEOPLE IN ]: {}, [ PEOPLE OUT ]: {}, [ TIME ]: {}",
                this.sDeviceId, this.nPeopleIN, this.nPeopleOUT, this.lTime
        );

        return sRet;
    }
}
