package org.usfirst.frc3620.motors;

public class MotorWatcherValueContainer implements MotorWatcherValues {
  Double temperature;

  @Override
  public Double getTemperature() {
    return temperature;
  }

  public Double get(MotorWatcherMetric metric) {
    if (metric == MotorWatcherMetric.TEMPERATURE) {
      return getTemperature();
    }
    return null;
  }
}
