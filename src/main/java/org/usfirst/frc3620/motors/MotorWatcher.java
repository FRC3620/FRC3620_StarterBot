package org.usfirst.frc3620.motors;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import org.usfirst.frc3620.NTPublisher;
import org.usfirst.frc3620.Utilities;

import java.util.*;

public class MotorWatcher {

  static class MotorWatcherInfo {
    MotorWatcherFetcher fetcher;
    EnumSet<MotorWatcherMetric> metrics;
    String name;
    MotorWatcherValueContainer values;
  }

  public MotorWatcher(String name) {
    this.watcherName = Utilities.removeLeadingAndTrailingSlashes(name);
  }

  String watcherName;

  List<MotorWatcherInfo> info = new ArrayList<>();

  public void addMotor(String name, Object o, EnumSet<MotorWatcherMetric> _metrics) {
    MotorWatcherFetcher f = null;
    if (o instanceof TalonFX) {
      f = new TalonFetcher((TalonFX) o);
    } else if (o instanceof SparkMax) {
      f = new SparkMAXFetcher((SparkMax) o);
    }
    if (f != null) {
      MotorWatcherInfo mwi = new MotorWatcherInfo();
      mwi.metrics = _metrics;
      mwi.fetcher = f;
      mwi.name = watcherName + "/" + Utilities.removeLeadingAndTrailingSlashes(name);
      mwi.values = new MotorWatcherValueContainer();
      info.add(mwi);
    }
  }

  public void collect(boolean publish) {
    for (var mwi : info) {
      mwi.fetcher.collect(mwi.values, mwi.metrics);
    }

    if (publish) {
      for (var mwi : info) {
        for (var metric : mwi.metrics) {
          Double value = null;
          if (metric == MotorWatcherMetric.TEMPERATURE) {
            value = mwi.values.getTemperature();
          }
          if (value != null) {
            NTPublisher.putNumber(mwi.name + "/" + metric.getName(), value);
          }
        }
      }
    }
  }
}
