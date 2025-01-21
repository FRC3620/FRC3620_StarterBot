// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.usfirst.frc3620;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DataLogManager;
import org.apache.logging.log4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.FRC3620Level;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.util.sendable.SendableRegistry.CallbackData;

/** Add your docs here. */
@SuppressWarnings("unused")
public class Utilities {
  static Logger logger = EventLogging.getLogger(Utilities.class, FRC3620Level.INFO);

  /**
   * This method makes sure the angle difference calculated falls between -180
   * degrees and 180 degrees
   * 
   * @param angle angle to be normalized (degrees)
   * @return normalized angle (-180..180)
   */
  public static double normalizeAngle(double angle) {
    angle = angle % 360;

    if (angle > 180) {
      angle = -360 + angle;
    }
    if (angle <= -180) {
      angle = 360 + angle;
    }

    if (angle == -0) {
      angle = 0;
    }

    return angle;
  }

  public static void dumpSendables(String label, String subsystemName) {
    logger.info("Dumping Sendables: {}", label);
    SendableRegistry.foreachLiveWindow(0, new Callback(subsystemName));
  }

  static class Callback implements Consumer<SendableRegistry.CallbackData> {
    String subsystemName;

    Callback(String s) {
      this.subsystemName = s;
    }

    @Override
    public void accept(CallbackData t) {
      if (subsystemName == null || subsystemName.equals(t.subsystem)) {
        logger.info("- {} -> {}", t.subsystem, t.name);
      }
    }
  }

  public static class SlidingWindowStats {
    LinkedList<Double> values = new LinkedList<>();

    int maxSize;

    int size;

    double mean;
    double stdDev;

    int flyers;

    public SlidingWindowStats(int maxSize) {
      this.maxSize = maxSize;
      clear();
    }

    public void clear() {
      values.clear();
      size = 0;
      mean = 0.0;
      stdDev = 0.0;
      flyers = 0;
    }

    public void addValue(double v) {
      if (size == maxSize) {
        values.removeFirst();
        size--;
      }
      values.addLast(v);
      size++;

      updateStats();
    }

    void updateStats() {
      double sum = 0;
      for (var v : values) {
        sum += v;
      }
      mean = sum / values.size();

      sum = 0;
      for (var v : values) {
        sum += (v - mean) * (v - mean);
      }
      stdDev = sum / values.size();

      flyers = 0;
      for (var v : values) {
        if (Math.abs(v - mean) > (3 * stdDev)) {
          flyers++;
        }
      }
    }

    public int getSize() {
      return size;
    }

    public double getMean() {
      return mean;
    }

    public double getStdDev() {
      return stdDev;
    }

    public int getFlyers() {
      return flyers;
    }

    @Override
    public String toString() {
      return "SlidingWindowStats [size=" + size + ", mean=" + mean
          + ", stdDev=" + stdDev + ", flyers=" + flyers + "]";
    }

  }

  public static double sum (List<Double> l) {
    double rv = 0;
    for (double v : l) rv += v;
    return rv;
  }

  public static void addDataLogForNT (String prefix) {
    String s = "/" + removeLeadingAndTrailingSlashes(prefix);
    int handle = NetworkTableInstance.getDefault().startEntryDataLog(DataLogManager.getLog(), s, s);
    logger.info ("Data log for {} = {}", prefix, handle);
  }

  public static String removeLeadingAndTrailingSlashes (String s) {
    String rv = s.replaceFirst("/+$", "");
    rv = rv.replaceFirst("^/+", "");
    return rv;
  }

}
