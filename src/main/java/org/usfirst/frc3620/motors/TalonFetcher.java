package org.usfirst.frc3620.motors;

import com.ctre.phoenix6.hardware.TalonFX;

class TalonFetcher extends MotorWatcherFetcher {
  TalonFX talonFX;

  TalonFetcher(TalonFX _talonFX) {
    super();
    talonFX = _talonFX;
  }

  @Override
  public Double getTemperature() {
    return talonFX.getDeviceTemp().getValueAsDouble();
  }
}
