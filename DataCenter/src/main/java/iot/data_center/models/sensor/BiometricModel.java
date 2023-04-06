package iot.data_center.models.sensor;

import iot.data_center.models.Base;

public class BiometricModel extends Base {
    
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

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        String sRet = String.format(
            "[ BIOMETRIC MODEL ]-> [ ID ]: %s, [ TOKEN ]: %s, [ DATA ]: %d", 
            this.id, this.token, this.data
        );
        return sRet;
    }
    
}
