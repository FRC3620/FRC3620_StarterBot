package frc.robot;

import org.usfirst.frc3620.misc.RobotParametersBase;
import org.usfirst.frc3620.misc.SwerveParameters;

/**
 * add members here as needed
 */
public class RobotParameters extends RobotParametersBase {
    SwerveParameters swerveParameters;

    public SwerveParameters getSwerveParameters() {
        return swerveParameters;
    }

    @Override
    public String toString() {
        return "RobotParameters [swerveParameters=" + swerveParameters + "]";
    }

}