package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class ResetOdometryCommand extends InstantCommand {
  public ResetOdometryCommand() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Called once when the command executes
  @Override
  public void initialize() {
    RobotContainer.odometrySubsystem.resetPosition(DriverStation.getAlliance(), new Translation2d());
  }
}
