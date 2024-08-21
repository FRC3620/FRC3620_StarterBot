package frc.robot;

import org.littletonrobotics.junction.Logger;

import org.usfirst.frc3620.misc.CANDeviceFinder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotDataLogger extends SubsystemBase {
	Timer timer = new Timer();
	double intervalInSeconds = 0.1;

	PowerDistribution powerDistribution = null;

	public RobotDataLogger(CANDeviceFinder canDeviceFinder) {
		if (canDeviceFinder.isPowerDistributionPresent()) {
			powerDistribution = new PowerDistribution();
		}
		timer.reset();
		timer.start();
	}

	@Override
	public void periodic() {
		if (timer.advanceIfElapsed(intervalInSeconds)) {
			record();
		}
	}

	void record() {
		Logger.recordOutput("matchTime", DriverStation.getMatchTime());
		Logger.recordOutput("robotMode", Robot.getCurrentRobotMode().toString());
		Logger.recordOutput("robotModeInt", Robot.getCurrentRobotMode().ordinal());
		Logger.recordOutput("batteryVoltage", RobotController.getBatteryVoltage());

		if (powerDistribution != null) {
			Logger.recordOutput("pdp.totalCurrent", powerDistribution.getTotalCurrent());
			Logger.recordOutput("pdp.totalPower", powerDistribution.getTotalPower());
			Logger.recordOutput("pdp.totalEnergy", powerDistribution.getTotalEnergy());

		}
	}
}