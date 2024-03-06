package org.usfirst.frc3620.misc;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static org.usfirst.frc3620.misc.ControllerId.*;

public class JoeController {
  GenericHID joystick;

  public JoeController(int port) {
    this.joystick = new GenericHID(port);
  }

  public double getRawAxis(AxisId axisId) {
    return joystick.getRawAxis(axisId.getId());
  }

  public Trigger button(ButtonId buttonId) {
    return new Trigger(() -> joystick.getRawButton(buttonId.getId()));
  }

  public Trigger analogButton(AxisId axisId) {
    return new Trigger(() -> analogTrigger(joystick.getRawAxis(axisId.getId()), 0.2));
  }

  public Trigger analogButton(AxisId axisId, double threshold) {
    return new Trigger(() -> analogTrigger(joystick.getRawAxis(axisId.getId()), threshold));
  }

  public boolean analogTrigger(double v, double threshold) {
    if (threshold >= 0.0) {
      return v > threshold;
    } else {
      return v < threshold;
    }
  }
}
