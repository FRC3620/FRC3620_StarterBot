package org.usfirst.frc3620.logger.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "roborio", category = StrLookup.CATEGORY)
public class RoboRIOLookup implements StrLookup {
    public RoboRIOLookup() {
        System.out.println ("RoboRIOLookup instantiated");
    }
    @Override
    public String lookup(String key) {
        if (key.equalsIgnoreCase("logdir")) {
            return "x/";
        }
        return null;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return lookup(key);
    }
}
