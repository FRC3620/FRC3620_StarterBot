package org.usfirst.frc3620.logger.log4j2;

import java.util.Date;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.usfirst.frc3620.logger.LoggingMaster;

@Plugin(name = "roborio", category = StrLookup.CATEGORY)
public class RoboRIOLookup implements StrLookup {
    String logdir = null;

    public RoboRIOLookup() {
        System.out.println ("RoboRIOLookup instantiated");
    }

    @Override
    public String lookup(String key) {
        if ("logdir".equalsIgnoreCase(key)) {
            if (logdir == null) {
                logdir = LoggingMaster.getLoggingDirectory().getAbsolutePath();
                String logMessage = String.format("xx Log directory is %s\n", logdir);
                System.out.print(logMessage); // NOPMD
            }
            return logdir;
        } else if ("timestamp".equalsIgnoreCase(key)) {
            Date ts = LoggingMaster.getTimestamp();
            if (ts != null) {
                return LoggingMaster.convertTimestampToString(ts);
            }
        }
        return null;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return lookup(key);
    }
}
