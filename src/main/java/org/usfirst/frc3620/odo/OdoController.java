package org.usfirst.frc3620.odo;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.tinylog.TaggedLogger;
import org.usfirst.frc3620.logger.LoggingMaster;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OdoController {
  public enum ControllerType {
    A, B
  }

  Joystick joystick;

  TaggedLogger logger = LoggingMaster.getLogger(getClass());

  ControllerType currentControllerType = ControllerType.A;

  public OdoController(Joystick joystick) {
    this.joystick = joystick;
    setCurrentControllerType(ControllerType.A);
  }

  public void setCurrentControllerType(ControllerType controllerType) {
    logger.info ("setting controller to {}", controllerType);
    this.currentControllerType = controllerType;
  }

  public ControllerType getCurrentControllerType() {
    return currentControllerType;
  }

  public Trigger button(IOdoButtonId a_button_id, IOdoButtonId b_button_id) {
    BooleanSupplier a_supplier = () -> joystick.getRawButton(a_button_id.getButtonNumber());
    BooleanSupplier b_supplier = () -> joystick.getRawButton(b_button_id.getButtonNumber());
    return button(a_supplier, b_supplier);
  }

  public Trigger button(BooleanSupplier a_supplier, BooleanSupplier b_supplier) {
    return new Trigger(new ABBooleanProvider(a_supplier, b_supplier));
  }

  public class ABBooleanProvider implements BooleanSupplier {
    BooleanSupplier a_supplier, b_supplier;

    ABBooleanProvider(BooleanSupplier a_supplier, BooleanSupplier b_supplier) {
      this.a_supplier = a_supplier;
      this.b_supplier = b_supplier;
    }

    @Override
    public boolean getAsBoolean() {
      if (currentControllerType == ControllerType.A) {
        return a_supplier.getAsBoolean();
      } else {
        return b_supplier.getAsBoolean();
      }
    }
  }

  public double getAxis(IOdoAxisId a_axis_id, IOdoAxisId b_axis_id) {
    DoubleSupplier a_supplier = () -> joystick.getRawAxis(a_axis_id.getAxisNumber());
    DoubleSupplier b_supplier = () -> joystick.getRawAxis(b_axis_id.getAxisNumber());
    return getAxis(a_supplier, b_supplier);
  }

  public double getAxis(DoubleSupplier a, DoubleSupplier b) {
    if (currentControllerType == ControllerType.A) {
      return a.getAsDouble();
    } else {
      return b.getAsDouble();
    }
  }

  public class ABDoubleProvider implements DoubleSupplier {
    DoubleSupplier a_supplier, b_supplier;

    ABDoubleProvider(DoubleSupplier a_supplier, DoubleSupplier b_supplier) {
      this.a_supplier = a_supplier;
      this.b_supplier = b_supplier;
    }

    @Override
    public double getAsDouble() {
      if (currentControllerType == ControllerType.A) {
        return a_supplier.getAsDouble();
      } else {
        return b_supplier.getAsDouble();
      }
    }
  }

}
