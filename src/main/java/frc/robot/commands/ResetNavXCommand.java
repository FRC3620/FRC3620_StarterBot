package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class ResetNavXCommand extends InstantCommand {
  public ResetNavXCommand() {
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
    RobotContainer.navigationSubsystem.setHeadingOffset(0);
    RobotContainer.navigationSubsystem.reset();
    RobotContainer.driveSubsystem.setTargetHeading(RobotContainer.navigationSubsystem.getCorrectedHeading());
  }
}
