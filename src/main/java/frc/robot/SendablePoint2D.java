package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

public class SendablePoint2D implements Sendable {
    double x = 0;
    double y = 0;

    public SendablePoint2D(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        SendableRegistry.addLW(this, "foo");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Point2D");
        builder.addDoubleProperty("x", this::getX, this::setX);
        builder.addDoubleProperty("y", this::getY, this::setY);
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
