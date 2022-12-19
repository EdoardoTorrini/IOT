package iot.utils.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private String sConnectionString;
    private static final transient Logger logger = LoggerFactory.getLogger(DBConnector.class);

    public DBConnector(String sConnectionString) {
        this.sConnectionString = String.format("jdbc:sqlite:%s", sConnectionString);
    }

    public void getConnectDB() {
        Connection dbConn = null;
        try {
            dbConn = DriverManager.getConnection(this.sConnectionString);
        } catch (SQLException sqlErr) {
            logger.error("[ SQL ERROR] -> [ CODE ]: {}, [ MESSAGE ]: {}", sqlErr.getErrorCode(), sqlErr.getMessage());
        }
    }
}
