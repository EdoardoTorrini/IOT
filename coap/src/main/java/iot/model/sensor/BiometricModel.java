package iot.model.sensor;

import iot.model.BaseModel;

public class BiometricModel extends BaseModel {

    private String token;

    public BiometricModel() { super(""); }

    public BiometricModel(String id, String token) {
        super(id);
        this.token = token;
    }

    public BiometricModel(String id, double data, String token) {
        super(id, data);
        this.token = token;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ BIOMETRIC MODEL ] -> [ ID ]: %s, [ TOKEN ]: %s, [ DATA ]: %s",
            this.id, this.token, this.data
        );
        return sRet;
    }
}
