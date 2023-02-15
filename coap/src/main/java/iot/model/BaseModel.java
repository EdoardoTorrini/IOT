package iot.model;

public class BaseModel {
    protected String id;
    protected double data;

    public BaseModel(String id) {
        this.id = id;
        this.data = System.currentTimeMillis();
    }

    public BaseModel(String id, double data) {
        this.id = id;
        this.data = data;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public double getTime() { return data; }
}
