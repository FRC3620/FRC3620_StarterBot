package org.usfirst.frc3620.logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.RobotController;

public class LoggingMaster {
    private static Date _timestamp = null;

    private static File _logDirectory = null;
    
    // http://javarevisited.blogspot.com/2014/05/double-checked-locking-on-singleton-in-java.html
    public static Date getTimestamp() {
        if (_timestamp == null) { // do a quick check (no overhead from
                                        // synchonized)
            synchronized (LoggingMaster.class) {
                if (_timestamp == null) { // Double checked
                    if (RobotController.isSystemTimeValid()) {
                        _timestamp = new Date();
                        String logMessage = String.format(
                                "timestamp for logs is %s\n",convertTimestampToString(_timestamp));
                        System.out.println(logMessage);
                    }
                }
            }
        }
        return _timestamp;
    }

    public static String convertTimestampToString(Date ts) {
        SimpleDateFormat formatName = new SimpleDateFormat(
                "yyyyMMdd-HHmmss");
        return formatName.format(ts);
    }
    
    public static File getLoggingDirectory() {
        if (_logDirectory == null) { // quick check
            synchronized (LoggingMaster.class) {
                if (_logDirectory == null) {
                    // Set dataLogger and Time information
                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                    _logDirectory = new File(DataLogManager.getLogDir());
                    String logMessage = String.format("Log directory is %s\n",
                            _logDirectory);
                    System.out.print(logMessage); // NOPMD
                }
            }
        }
        return _logDirectory;
    }

}