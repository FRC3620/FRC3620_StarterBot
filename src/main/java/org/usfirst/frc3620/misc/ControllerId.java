package org.usfirst.frc3620.misc;

public class ControllerId {
  int id;

  public ControllerId(int _id) {
    this.id = _id;
  }

  public int getId() {
    return id;
  }

  public static class AxisId extends ControllerId {
    public AxisId(int _id) {
      super(_id);
    }
  }

  public static class ButtonId extends ControllerId {
    public ButtonId(int _id) {
      super(_id);
    }
  }
}
