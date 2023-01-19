package frc.robot;

import org.usfirst.frc3620.logger.DataLogger;
import org.usfirst.frc3620.misc.CANDeviceFinder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;

public class RobotDataLogger {
	PowerDistribution powerDistribution = null;
	boolean logDriveMotorCurrent = true;

	public RobotDataLogger (DataLogger dataLogger, CANDeviceFinder canDeviceFinder) {

		dataLogger.addDataProvider("matchTime", () -> DataLogger.f2(DriverStation.getMatchTime()));
		dataLogger.addDataProvider("robotMode", () -> Robot.currentRobotMode.toString());
		dataLogger.addDataProvider("robotModeInt", () -> Robot.currentRobotMode.ordinal());
		dataLogger.addDataProvider("batteryVoltage", () -> DataLogger.f2(RobotController.getBatteryVoltage()));

		dataLogger.addDataProvider("nav.heading_raw", () -> DataLogger.f2(RobotContainer.navigationSubsystem.getRawHeading()));
		dataLogger.addDataProvider("nav.heading", () -> DataLogger.f2(RobotContainer.navigationSubsystem.getCorrectedHeading()));
		dataLogger.addDataProvider("nav.heading_offset", () -> DataLogger.f2(RobotContainer.navigationSubsystem.getHeadingOffset()));

		if (canDeviceFinder.isPowerDistributionPresent()) {
			powerDistribution = new PowerDistribution();
			dataLogger.addDataProvider("pdp.totalCurrent", () -> DataLogger.f2(powerDistribution.getTotalCurrent()));
			dataLogger.addDataProvider("pdp.totalPower", () -> DataLogger.f2(powerDistribution.getTotalPower()));
			dataLogger.addDataProvider("pdp.totalEnergy", () -> DataLogger.f2(powerDistribution.getTotalEnergy()));
		}

		if (RobotContainer.driveSubsystem.leftFrontDrive != null) {
			dataLogger.addDataProvider("drive.lf.azimuth.home_encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftFrontHomeEncoder.getVoltage()));
			dataLogger.addDataProvider("drive.lf.azimuth.encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftFrontAzimuthEncoder.getPosition()));
			dataLogger.addDataProvider("drive.rf.azimuth.home_encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightFrontHomeEncoder.getVoltage()));
			dataLogger.addDataProvider("drive.rf.azimuth.encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightFrontAzimuthEncoder.getPosition()));
			dataLogger.addDataProvider("drive.lb.azimuth.home_encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftBackHomeEncoder.getVoltage()));
			dataLogger.addDataProvider("drive.lb.azimuth.encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftBackAzimuthEncoder.getPosition()));
			dataLogger.addDataProvider("drive.rb.azimuth.home_encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightBackHomeEncoder.getVoltage()));
			dataLogger.addDataProvider("drive.rb.azimuth.encoder", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightBackAzimuthEncoder.getPosition()));

			if (logDriveMotorCurrent) {
				dataLogger.addDataProvider("drive.lf.drive.power", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftFrontDrive.getAppliedOutput()));
				dataLogger.addDataProvider("drive.rf.drive.power", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightFrontDrive.getAppliedOutput()));
				dataLogger.addDataProvider("drive.lb.drive.power", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftBackDrive.getAppliedOutput()));
				dataLogger.addDataProvider("drive.rb.drive.power", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightBackDrive.getAppliedOutput()));
				dataLogger.addDataProvider("drive.lf.drive.current", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftFrontDrive.getOutputCurrent()));
				dataLogger.addDataProvider("drive.rf.drive.current", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightFrontDrive.getOutputCurrent()));
				dataLogger.addDataProvider("drive.lb.drive.current", () -> DataLogger.f2(RobotContainer.driveSubsystem.leftBackDrive.getOutputCurrent()));
				dataLogger.addDataProvider("drive.rb.drive.current", () -> DataLogger.f2(RobotContainer.driveSubsystem.rightBackDrive.getOutputCurrent()));
			}
		}

	}
}