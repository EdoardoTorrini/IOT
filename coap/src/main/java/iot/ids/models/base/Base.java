package iot.ids.models.base;

public class Base {
    protected String id;
    protected double data;

    public Base(String id) {
        this.id = id;
        this.data = System.currentTimeMillis();
    }

    public Base(String id, double data) {
        this.id = id;
        this.data = data;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public double getTime() { return data; }
}
