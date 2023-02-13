package iot.data_center.models.base;

public class Base {
    protected String id;
    protected long time;

    public Base(String id) {
        this.id = id;
        this.time = System.currentTimeMillis();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public long getTime() { return time; }
}
