package frc.robot.subsystems;

public interface INavigationSubsystem {
    public void reset();
    public double getRawHeading();
    public double getCorrectedHeading();
    public void setHeadingOffset(double headingOffset);
    public double getHeadingOffset();
}
