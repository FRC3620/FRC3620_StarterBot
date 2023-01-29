// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.opencv.core.Point;
import org.usfirst.frc3620.misc.SwerveParameters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.INavigationSubsystem;

/** Add your docs here. */
public class Odometry {

    SwerveDriveOdometry sdo;
    INavigationSubsystem navigationSubsystem;

    public Odometry (INavigationSubsystem ns, Alliance alliance, SwerveParameters swerveParameters, SwerveModulePosition[] sp) {
        this.navigationSubsystem = ns;

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

        sdo = new SwerveDriveOdometry(kinematics, getOdometryHeading(alliance), sp);
    }

    public void resetPosition (Alliance alliance, SwerveModulePosition[] sp, Translation2d currentPosition) {
        Rotation2d r2d = getOdometryHeading(alliance);
        Pose2d pose2d = new Pose2d(currentPosition, r2d);
        sdo.resetPosition(r2d, sp, pose2d);
    }

    public Pose2d getPoseMeters() {
        return sdo.getPoseMeters();
    }

    public Pose2d update(Alliance alliance, SwerveModulePosition[] sp) {
        return sdo.update(getOdometryHeading(alliance), sp);
    }

    public Rotation2d getOdometryHeading(Alliance alliance) {
        // 0 is pointed at opponent's driver stations, increasing value is clockwise, in degrees
        double headingInDegress = navigationSubsystem.getCorrectedHeading();
        // 0 is pointed at opponent's driver stations, increasing value is CCW, in radians
        double trigClassHeadingInRadians = -Units.degreesToRadians(headingInDegress);
        if (alliance == Alliance.Red) {
            trigClassHeadingInRadians += Math.PI;
        }
        if (trigClassHeadingInRadians > Math.PI) trigClassHeadingInRadians -= (2 * Math.PI);
        // 0 is pointed at RED alliance driver station
        return new Rotation2d(trigClassHeadingInRadians);
    }
}
