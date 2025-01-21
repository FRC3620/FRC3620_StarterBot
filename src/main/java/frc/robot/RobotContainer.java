package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.LogCommand;
import org.usfirst.frc3620.logger.EventLogging.FRC3620Level;
import org.usfirst.frc3620.CANDeviceFinder;
import org.usfirst.frc3620.CANDeviceType;
import org.usfirst.frc3620.RobotParametersContainer;
import org.usfirst.frc3620.XBoxConstants;

import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final static Logger logger = EventLogging.getLogger(RobotContainer.class, FRC3620Level.INFO);
  
  // need this
  public static CANDeviceFinder canDeviceFinder;
  public static RobotParameters robotParameters;

  Alert missingDevicesAlert = new Alert("Diagnostics", "", Alert.AlertType.kWarning);

  // hardware here...
  private static DigitalInput practiceBotJumper;

  public static PneumaticsModuleType pneumaticModuleType = null;

  // subsystems here
  private static ExampleSubsystem exampleSubsystem;

  // joysticks here....
  public static Joystick driverJoystick;
  public static Joystick operatorJoystick;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    canDeviceFinder = new CANDeviceFinder();

    robotParameters = RobotParametersContainer.getRobotParameters(RobotParameters.class);
    logger.info("got parameters for chassis '{}'", robotParameters.getName());

    practiceBotJumper = new DigitalInput(0);
    boolean iAmACompetitionRobot = amIACompBot();
    if (!iAmACompetitionRobot) {
      logger.warn("this is a test chassis, will try to deal with missing hardware!");
    }

    if (canDeviceFinder.isDevicePresent(CANDeviceType.REV_PH, 1, "REV PH") || iAmACompetitionRobot) {
      pneumaticModuleType = PneumaticsModuleType.REVPH;
    } else if (canDeviceFinder.isDevicePresent(CANDeviceType.CTRE_PCM, 0, "CTRE PCM")) {
      pneumaticModuleType = PneumaticsModuleType.CTREPCM;
    }

    makeSubsystems();

    if (!canDeviceFinder.getMissingDeviceSet().isEmpty()) {
      missingDevicesAlert.set(true);
      missingDevicesAlert.setText("Missing from CAN bus: " + canDeviceFinder.getMissingDeviceSet());
    }

    // Configure the button bindings
    configureButtonBindings();

    setupSmartDashboardCommands();

    setupAutonomousCommands();
  }

  private void makeSubsystems() {
    exampleSubsystem = new ExampleSubsystem();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    driverJoystick = new Joystick(0);
    operatorJoystick = new Joystick(1);

    new JoystickButton(driverJoystick, XBoxConstants.BUTTON_A)
      .onTrue(new LogCommand("'A' button hit"));

  }

  private void setupSmartDashboardCommands() {
    // SmartDashboard.putData(new xxxxCommand());
  }

  SendableChooser<Command> chooser = new SendableChooser<>();
  public void setupAutonomousCommands() {
    SmartDashboard.putData("Auto mode", chooser);

    chooser.addOption("Example Command", new ExampleCommand(exampleSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return new GoldenAutoCommand(driveSubsystem, shooterSubsystem, VisionSubsystem, intakeSubsystem);
    return chooser.getSelected();
  }

  /**
   * Determine if this robot is a competition robot.
   * <p><li>
   * <ul>It is if it's connected to an FMS.</ul>
   * <ul>It is if it is missing a grounding jumper on DigitalInput 0.</ul>
   * <ul>It is if the robot_parameters.json says so for this MAC address.</ul>
   * </li></p>
   *
   * @return true if this robot is a competition robot.
   */
  @SuppressWarnings({"unused", "RedundantIfStatement", "PointlessBooleanExpression"})
  public static boolean amIACompBot() {
    if (DriverStation.isFMSAttached()) {
      return true;
    }

    if(practiceBotJumper.get() == true){
      return true;
    }

    if (robotParameters.isCompetitionRobot()) {
      return true;
    }

    return false;
  }

  /**
   * Determine if we should make software objects, even if the device does 
   * not appear on the CAN bus.
   * <p><li>
   * <ul>We should if it's connected to an FMS.</ul>
   * <ul>We should if it is missing a grounding jumper on DigitalInput 0.</ul>
   * <ul>We should if the robot_parameters.json says so for this MAC address.</ul>
   * </li></p>
   *
   * @return true if we should make all software objects for CAN devices
   */
  @SuppressWarnings({"unused", "RedundantIfStatement"})
  public static boolean shouldMakeAllCANDevices() {
    if (DriverStation.isFMSAttached()) {
      return true;
    }

    //noinspection PointlessBooleanExpression
    if(practiceBotJumper.get() == true){
      return true;
    }

    if (robotParameters.shouldMakeAllCANDevices()) {
      return true;
    }

    return false;
  }

}