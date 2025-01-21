package org.usfirst.frc3620.motors;

public enum MotorWatcherMetric {
    TEMPERATURE("temperature");

    final String name;

    MotorWatcherMetric(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }
}
