package org.usfirst.frc3620.motors;

import com.revrobotics.spark.SparkMax;

class SparkMAXFetcher extends MotorWatcherFetcher {
  SparkMax sparkMax;

  SparkMAXFetcher(SparkMax _sparkMAX) {
    super();
    sparkMax = _sparkMAX;
  }

  @Override
  public Double getTemperature() {
    return sparkMax.getMotorTemperature();
  }
}
