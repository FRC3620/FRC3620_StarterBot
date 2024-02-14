// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.usfirst.frc3620.misc.CANSparkMaxSendable;
import org.usfirst.frc3620.misc.CANSparkMaxSendableWrapper;
import org.usfirst.frc3620.misc.FakeMotor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
  FakeMotor motor;
  CANSparkMaxSendable max;
  CANSparkMax max2;
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    motor = new FakeMotor(99);
    max = new CANSparkMaxSendable(11, MotorType.kBrushless);
    max2 = new CANSparkMax(12, MotorType.kBrushless);
    CANSparkMaxSendableWrapper maxW = new CANSparkMaxSendableWrapper(max2);
    addChild("max", max);
    addChild("max3", motor);
    addChild("zmax2", maxW);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
