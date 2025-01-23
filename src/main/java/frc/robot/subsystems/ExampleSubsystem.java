// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.usfirst.frc3620.*;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc3620.motors.MotorWatcher;
import org.usfirst.frc3620.motors.MotorWatcherMetric;

import java.util.EnumSet;

public class ExampleSubsystem extends SubsystemBase {
  FakeMotor motor;
  SparkMaxSendable max;
  SparkMax max2;

  MotorWatcher watcher = new MotorWatcher("example");
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    motor = new FakeMotor(99);
    max = new SparkMaxSendable(11, MotorType.kBrushless);
    max2 = new SparkMax(12, MotorType.kBrushless);
    SparkMaxSendableWrapper maxW = new SparkMaxSendableWrapper(max2);
    addChild("max", max);
    addChild("max3", motor);
    addChild("zmax2", maxW);

    Utilities.addDataLogForNT("example");

    NTPublisher.putString("example/boo", "baz");

    watcher.addMotor("motor/max2", max2, EnumSet.allOf(MotorWatcherMetric.class));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    watcher.collect(true);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
