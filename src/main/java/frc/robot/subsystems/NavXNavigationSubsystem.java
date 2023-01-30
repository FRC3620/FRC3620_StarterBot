package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.kauailabs.navx.frc.AHRS;

public class NavXNavigationSubsystem extends SubsystemBase implements INavigationSubsystem {
  public AHRS ahrs = new AHRS(edu.wpi.first.wpilibj.SPI.Port.kMXP);

  double headingOffset = 0;

  /** Creates a new NavXNavigationSubsystem. */
  public NavXNavigationSubsystem() {
    SmartDashboard.putNumber("navx.offset", 0);
  }

  @Override
  public void periodic() {
		SmartDashboard.putNumber("navx.heading_corrected", getCorrectedHeading());
		SmartDashboard.putNumber("navx.heading_raw", getRawHeading());
  }

  @Override
  public void reset() {
    ahrs.reset();
  }

  @Override
  public double getRawHeading() {
    //returns raw degrees, can accumulate past 360 or -360 degrees 
    double angle = ahrs.getAngle();
    return angle;
  }

  @Override
  public double getCorrectedHeading() {
		//returns angle in the range of -180 degrees to 180 degrees with 0 being the front
		double angle = ahrs.getAngle() + headingOffset;    //adding in offset based on initial robot position for auto

		angle = angle % 360;
		
		if (angle > 180){
			angle = -360 + angle;
		}
		if (angle < -180){
			angle = 360 + angle;
		}
		
		return angle;
  }

  @Override
  public void setHeadingOffset(double headingOffset) {
    this.headingOffset = headingOffset;
		SmartDashboard.putNumber("navx.offset", headingOffset);
  }

  @Override
  public double getHeadingOffset() {
    return headingOffset;
  }
}
