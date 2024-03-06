package org.usfirst.frc3620.misc;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static org.usfirst.frc3620.misc.ControllerId.*;

public class ChameleonController {
  public enum ControllerType {
    A, B
  }

  GenericHID hid;

  ControllerType currentControllerType = ControllerType.A;

  public ChameleonController(int port) {
    this.hid = new GenericHID(port);
  }

  public void setCurrentControllerType(ControllerType controllerType) {
    this.currentControllerType = controllerType;
  }

  public ControllerType getCurrentControllerType() {
    return currentControllerType;
  }

  public String getControllerName() {
    return hid.getName();
  }

  public double getRawAxis(AxisId aAxisId, AxisId bAxisId) {
    return hid.getRawAxis(currentControllerType == ControllerType.A ? aAxisId.getId() : bAxisId.getId());
  }

  public Trigger button(ButtonId aButtonId, ButtonId bButtonId) {
    return new Trigger(new ABBooleanProvider(aButtonId, bButtonId));
  }

  class ABBooleanProvider implements BooleanSupplier {
    ButtonId aButtonId, bButtonId;

    ABBooleanProvider(ButtonId aButtonId, ButtonId bButtonId) {
      this.aButtonId = aButtonId;
      this.bButtonId = bButtonId;
    }

    @Override
    public boolean getAsBoolean() {
      return hid.getRawButton(currentControllerType == ControllerType.A ? aButtonId.getId() : bButtonId.getId());
    }
  }

  public Trigger button(AxisId aAxisId, AxisId bAxisId) {
    return analogButton(aAxisId, bAxisId)
  }

  public Trigger analogButton(AxisId aAxisId, AxisId bAxisId) {
    return new Trigger(new ABAnalogButtonProvider(aAxisId, 0.2, bAxisId, 0.2));
  }

  public Trigger analogButton(AxisId aAxisId, double aThreshold, AxisId bAxisId, double bThreshold) {
    return new Trigger(new ABAnalogButtonProvider(aAxisId, aThreshold, bAxisId, bThreshold));
  }
    
  class ABAnalogButtonProvider implements BooleanSupplier {
    AxisId aAxisId, bAxisId;
    double aThreshold, bThreshold;

    ABAnalogButtonProvider(AxisId _aAxisId, double _aThreshold, AxisId _bAxisId, double _bThreshold) {
      this.aAxisId = _aAxisId;
      this.aThreshold = _aTh\]reshold;
      this.bAxisId = _bAxisId;
      this.bThreshold = _bThreshold;
    }

    @Override
    public boolean getAsBoolean() {
      double v = getRawAxis(aAxisId, bAxisId);
      double threshold = currentControllerType == ControllerType.A ? aThreshold : bThreshold;
      if (threshold >= 0.0) {
        return v > threshold;
      } else {
        return v < threshold;
      }
    }
  }
}
