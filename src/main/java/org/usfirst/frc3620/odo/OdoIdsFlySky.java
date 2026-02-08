package org.usfirst.frc3620.odo;

public class OdoIdsFlySky {

  // the following was a reasonable alternative to doing the enum. 
  // it's not as concise, so I went the other way.
  // public final static OdoAxisId LEFT_X = new OdoAxisId(0);

  public static enum AxisId implements IOdoAxisId {
    LEFT_X(0),
    LEFT_Y(1),
    RIGHT_Y(2), // Z Axis in driver station
    RIGHT_X(3), // X Rotate in driver station
    SWH(4),
    SWE(5),
    ;

    int axisNumber;

    AxisId(int axisNumber) {
      this.axisNumber = axisNumber;
    }

    @Override
    public int getAxisNumber() {
      return axisNumber;
    }

  }

  public static enum ButtonId implements IOdoButtonId {
    SWA(1),
    SWC(3),
    SWD(4),
    SWF(2),
    ;

    int buttonNumber;

    ButtonId(int buttonNumber) {
      this.buttonNumber = buttonNumber;
    }

    @Override
    public int getButtonNumber() {
      return buttonNumber;
    }
  }
}
