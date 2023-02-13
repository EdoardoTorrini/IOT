package iot.ids.models.base;

public class Base {
    protected String id;
    protected long data;

    public Base(String id) {
        this.id = id;
        this.data = System.currentTimeMillis();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public long getTime() { return data; }
}
