package org.usfirst.frc3620.logger;

import org.littletonrobotics.junction.Logger;

public class AdvantageKitTimedDataLogger extends TimedDataLoggerBase {

    @Override
    public void addMetadata(String s, Object metadata) {
        Logger.recordMetadata(s, metadata.toString());
    }

    @Override
    void saveValue(int i, String name, Object value) {
        // Logger.recordOutput(name, value);
    }

    @Override
    void saveErrorValue(int i, String name, Exception e) {
        Logger.recordOutput(name, e.toString());
    }
}
