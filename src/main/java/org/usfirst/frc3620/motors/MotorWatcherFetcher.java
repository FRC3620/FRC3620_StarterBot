package org.usfirst.frc3620.motors;

import java.util.*;

abstract class MotorWatcherFetcher implements MotorWatcherValues {

  void startCollection() {

  }

  void endCollection() {

  }

  void collect(MotorWatcherValueContainer container, EnumSet<MotorWatcherMetric> metrics) {
    startCollection();
    if (metrics.contains(MotorWatcherMetric.TEMPERATURE)) {
      container.temperature = this.getTemperature();
    }
    endCollection();
  }
}
