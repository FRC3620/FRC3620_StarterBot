package frc.robot.subsystems;

public interface INavigationSubsystem {
    public default void reset() { }

    public double getRawHeading();

    public default double getCorrectedHeading() {
        return getRawHeading() + getHeadingOffset();
    }

    public default void setHeadingOffset(double headingOffset) {
        throw new RuntimeException (new NoSuchMethodException());
    }

    public default double getHeadingOffset() {
        return 0;
    }
}
