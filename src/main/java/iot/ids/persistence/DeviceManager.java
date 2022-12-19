package iot.ids.persistence;

import com.google.gson.Gson;
import iot.ids.model.DeviceModel;

import java.util.HashMap;
import java.util.Map;

public class DeviceManager {

    private HashMap<String, DeviceModel> mDeviceMap = null;

    public DeviceManager() {
        this.mDeviceMap = new HashMap<>();
        this.init();
    }

    private void init() {

        // TODO: get the value from db

        DeviceModel dmPeopleCounter = new DeviceModel("people.counter.01", "room01", "0.1", "AMAZON");
        this.mDeviceMap.put(dmPeopleCounter.getDeviceId(), dmPeopleCounter);

        DeviceModel dmLightController = new DeviceModel("light.switch.01", "room01", "0.1", "PHILIPS");
        this.mDeviceMap.put(dmLightController.getDeviceId(), dmLightController);

        DeviceModel dmAlarmController = new DeviceModel("alarm.switch.01", "room01", "0.1", "SECURITY");
        this.mDeviceMap.put(dmAlarmController.getDeviceId(), dmAlarmController);

        DeviceModel dmTouchBiometric = new DeviceModel("biometric.login.01", "room01", "0.1", "SECURITY");
        this.mDeviceMap.put(dmTouchBiometric.getDeviceId(), dmTouchBiometric);

        DeviceModel dmDoorSmartLock = new DeviceModel("door.smart.lock.01", "room01", "0.1", "SECURITY");
        this.mDeviceMap.put(dmDoorSmartLock.getDeviceId(), dmDoorSmartLock);

        DeviceModel dmEnvironmentMonitoring = new DeviceModel("environment.monitoring.01", "room01", "0.1", "MONITORING");
        this.mDeviceMap.put(dmEnvironmentMonitoring.getDeviceId(), dmEnvironmentMonitoring);
    }

    public DeviceModel getDeviceFromId(String sId) {
        if (this.mDeviceMap.containsKey(sId))
            return this.mDeviceMap.get(sId);
        else
            throw new ArrayIndexOutOfBoundsException(String.format("%s not EXISTS", sId));
    }

    public HashMap<String, DeviceModel> getDeviceMap() { return this.mDeviceMap; }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return String.valueOf(gson.toJson(this));
    }
}
