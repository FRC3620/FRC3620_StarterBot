package org.usfirst.frc3620;

import org.wpilib.driverstation.Alert;
import org.wpilib.driverstation.Alert.Level;

public class CompoundAlert {
  String name;

  final Alert infoAlert, warningAlert, errorAlert;

  public CompoundAlert(String groupname) {
    this(groupname, (String) null);
  }

  public CompoundAlert(String groupname, Class<?> clazz) {
    this(groupname, clazz.getSimpleName());
  }

  public CompoundAlert(String groupname, String name) {
    this.name = name;

    infoAlert = new Alert(groupname, "", Level.LOW);
    warningAlert = new Alert(groupname, "", Level.MEDIUM);
    errorAlert = new Alert(groupname, "", Level.HIGH);

    reset();
  }

  void reset() {
    infoAlert.set(false);
    warningAlert.set(false);
    errorAlert.set(false);

    infoAlert.setText("");
    warningAlert.setText("");
    errorAlert.setText("");
  }

  void setAlert(Alert a, String text) {
    reset();
    if (name != null) text = name + ": " + text;
    a.setText(text);
    a.set(true);
  }

  public void info(String text) {
    setAlert(infoAlert, text);
  }

  public void warning(String text) {
    setAlert(warningAlert, text);
  }

  public void error(String text) {
    setAlert(errorAlert, text);
  }

  public void none() {
    reset();
  }
}
