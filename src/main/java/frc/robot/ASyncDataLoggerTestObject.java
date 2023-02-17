package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc3620.logger.AsyncDataLoggerDatum;

public class ASyncDataLoggerTestObject implements AsyncDataLoggerDatum {
  double t;
  int i;
  public ASyncDataLoggerTestObject(int i) {
    this.t = Timer.getFPGATimestamp();
    this.i = i;
  }

  @Override
  public byte[] getAsyncDataLoggerBytes() {
    String s = Thread.currentThread().getName() + ": " + t + "," + i + "\n";
    return s.getBytes();
  }
}
