package org.usfirst.frc3620.misc;

import static org.usfirst.frc3620.misc.ControllerId.*;

import edu.wpi.first.wpilibj.GenericHID;

public class FlySkyConstants {
  public final static AxisId AXIS_LEFT_X = new AxisId(0);
  public final static AxisId AXIS_LEFT_Y = new AxisId(1);
  public final static AxisId AXIS_RIGHT_Y = new AxisId(2); // Z Axis in driver station
  public final static AxisId AXIS_RIGHT_X = new AxisId(3); // X Rotate in driver station
  public final static AxisId AXIS_SWH = new AxisId(4);
  public final static AxisId AXIS_SWE = new AxisId(5);

  public final static ButtonId BUTTON_SWA = new ButtonId(1);
  public final static ButtonId BUTTON_SWC = new ButtonId(3);
  public final static ButtonId BUTTON_SWD = new ButtonId(4);

  public static boolean isAFlySky(GenericHID hid) {
    return isAFlySky(hid.getName());
  }

  public static boolean isAFlySky(String s) {
    return s.startsWith("FlySky");
  }
}