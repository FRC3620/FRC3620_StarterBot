import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.usfirst.frc3620.misc.SwerveParameters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Odometry;
import frc.robot.subsystems.INavigationSubsystem;

/**
 * Add your docs here.
 */
public class OdometryTests implements INavigationSubsystem {

    double currentHeading = 0;

    SwerveParameters swerveParameters = new SwerveParameters(1, 1);

    @Test
    public void testHeadings() {
        Odometry o = new Odometry(this, Alliance.Blue, swerveParameters, new SwerveModulePosition[0]);

        Rotation2d r;

        // pointed at blue drivers
        r = o.getOdometryHeading(Alliance.Red);
        System.out.println (r);
        Assertions.assertEquals(Math.PI, r.getRadians(), 0.01);

        // pointed at red drivers
        r = o.getOdometryHeading(Alliance.Blue);
        System.out.println (r);
        Assertions.assertEquals(0, r.getRadians(), 0.01);

        currentHeading = -90; // pointed at scoring table
        r = o.getOdometryHeading(Alliance.Red);
        System.out.println (r);
        Assertions.assertEquals(-Math.PI/2, r.getRadians(), 0.01);

        currentHeading = 90; // pointed at scoring table
        r = o.getOdometryHeading(Alliance.Blue);
        System.out.println (r);
        Assertions.assertEquals(-Math.PI/2, r.getRadians(), 0.01);
    }

    @Test
    public void testResetAndMovePosition() {
        currentHeading = 0;
        Pose2d pose2d;
        SwerveModulePosition p = new SwerveModulePosition(0, new Rotation2d(0));
        SwerveModulePosition[] sp = new SwerveModulePosition[] {p, p, p, p};
        Alliance alliance = Alliance.Blue;

        Odometry o = new Odometry(this, alliance, swerveParameters, sp);
        pose2d = o.getPoseMeters();
        System.out.println ("initial  = " + pose2d);

        o.resetPosition(alliance, sp, new Translation2d(0, 0));
        pose2d = o.getPoseMeters();
        System.out.println ("reset    = " + pose2d);

        p.distanceMeters = 1.0;
        pose2d = o.update(alliance, sp);
        System.out.println ("move 1,0 = " + pose2d);

        p.distanceMeters = 0.0;
        pose2d = o.update(alliance, sp);
        System.out.println ("move 0,0 = " + pose2d);

        p.angle = new Rotation2d(Math.PI / 2); // turned to left 90 degrees
        p.distanceMeters = 1.0;
        pose2d = o.update(alliance, sp);
        System.out.println ("move 0,1 = " + pose2d);

        p.angle = new Rotation2d(-Math.PI / 4); // turned to right 45 degress
        p.distanceMeters = 1.0 + Math.sqrt(2.0);
        pose2d = o.update(alliance, sp);
        System.out.println ("move 1,0 = " + pose2d);
    }

    
    @Override
    public double getRawHeading() {
        return currentHeading;
    }
}