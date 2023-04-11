package iot.model;

public class Log {

    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    private int code;
    private int category;
    private String description;
    private long data;

    public Log(int category, String description) {
        this.code = 0;
        this.category = category;
        this.description = description;
    }
    
    public int getCode() { return this.code; }
    public int getCategory() { return this.category; }
    public String getDescription() { return this.description; }
    public long getTimestamp() { return this.data; }
}
