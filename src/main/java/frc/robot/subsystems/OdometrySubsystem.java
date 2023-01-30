package frc.robot.subsystems;

import java.util.function.Supplier;

import org.usfirst.frc3620.misc.SwerveParameters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OdometrySubsystem extends SubsystemBase {

    SwerveDriveOdometry sdo;
    INavigationSubsystem navigationSubsystem;
    Supplier<SwerveModulePosition[]> modulePositionProvider;

    static public boolean putSwerveModulePositionsOnDashboard = false;

    public OdometrySubsystem (INavigationSubsystem ns, Alliance alliance, SwerveParameters swerveParameters, Supplier<SwerveModulePosition[]> modulePositionProvider) {
        this.navigationSubsystem = ns;
        this.modulePositionProvider = modulePositionProvider;

        double halfChassisWidthInMeters = Units.inchesToMeters(swerveParameters.getChassisWidth()) / 2.0;
        double halfChassisLengthInMeters = Units.inchesToMeters(swerveParameters.getChassisLength()) / 2.0;
        // +x is towards the front of the robot, +y is towards the left of the robot
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(+halfChassisLengthInMeters, +halfChassisWidthInMeters) // LF
            ,
            new Translation2d(+halfChassisLengthInMeters, -halfChassisWidthInMeters) // RF
            ,
            new Translation2d(-halfChassisLengthInMeters, +halfChassisWidthInMeters) // LB
            ,
            new Translation2d(-halfChassisLengthInMeters, -halfChassisWidthInMeters) // RB
        );

        sdo = new SwerveDriveOdometry(kinematics, getOdometryHeading(alliance), modulePositionProvider.get());
    }

    public void resetPosition (Alliance alliance, Translation2d currentPosition) {
        Rotation2d r2d = getOdometryHeading(alliance);
        Pose2d pose2d = new Pose2d(currentPosition, r2d);
        sdo.resetPosition(r2d, getPositions(), pose2d);
    }

    SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] sp = modulePositionProvider.get();
        if (putSwerveModulePositionsOnDashboard) {
            for (int i = 0; i < sp.length; i++) {
                SmartDashboard.putNumber("odometry.sp." + i + ".angle", sp[i].angle.getDegrees());
                SmartDashboard.putNumber("odometry.sp." + i + ".pos", sp[i].distanceMeters);
            }
        }
        return sp;
    }

    public Pose2d getPoseMeters() {
        return sdo.getPoseMeters();
    }

    public Pose2d update(Alliance alliance) {
        return sdo.update(getOdometryHeading(alliance), getPositions());
    }

    @Override
    public void periodic() {
        Pose2d whereIIs = update(DriverStation.getAlliance());
        SmartDashboard.putNumber("odometry.x", whereIIs.getX());
        SmartDashboard.putNumber("odometry.y", whereIIs.getY());
    }

    public Rotation2d getOdometryHeading(Alliance alliance) {
        // 0 is pointed at opponent's driver stations, increasing value is clockwise, in degrees
        double headingInDegrees = navigationSubsystem.getCorrectedHeading();
        // 0 is pointed at opponent's driver stations, increasing value is CCW, in radians
        double trigClassHeadingInRadians = -Units.degreesToRadians(headingInDegrees);
        if (alliance == Alliance.Red) {
            trigClassHeadingInRadians += Math.PI;
        }
        if (trigClassHeadingInRadians > Math.PI) trigClassHeadingInRadians -= (2 * Math.PI);
        // 0 is pointed at RED alliance driver station
        SmartDashboard.putNumber ("odometry.heading", Units.radiansToDegrees(trigClassHeadingInRadians));
        return new Rotation2d(trigClassHeadingInRadians);
    }
}
