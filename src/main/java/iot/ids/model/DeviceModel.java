package iot.ids.model;

public class DeviceModel {

    private String sDeviceId;
    private String sRoomId;
    private String sSoftwareVersion;
    private String sManufacturer;

    public DeviceModel() {}

    public DeviceModel(String sDeviceId, String sRoomId, String sSoftwareVersion, String sManufactorer) {
        this.sDeviceId = sDeviceId;
        this.sRoomId = sRoomId;
        this.sSoftwareVersion = sSoftwareVersion;
        this.sManufacturer = sManufactorer;
    }

    public String getDeviceId() { return sDeviceId; }

    public void setDeviceId(String sDeviceId) { this.sDeviceId = sDeviceId; }

    public String getRoomId() { return sRoomId; }

    public void setRoomId(String sRoomId) { this.sRoomId = sRoomId; }

    public String getSoftwareVersion() { return sSoftwareVersion; }

    public void setSoftwareVersion(String sSoftwareVersion) { this.sSoftwareVersion = sSoftwareVersion; }

    public String getManufacturer() { return sManufacturer; }

    public void setManufacturer(String sManufacturer) { this.sManufacturer = sManufacturer; }

    @Override
    public String toString() {
        String sRet = String.format(
                "[ DEVICE ] - [ DEVICE ID ]: {}, [ ROOM ]: {}, [ SOFTWARE VERSION ]: {}, [ MANUFACTURER ]: {}",
                this.sDeviceId, this.sRoomId, this.sSoftwareVersion, this.sManufacturer
        );

        return sRet;
    }
}
