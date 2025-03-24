package org.usfirst.frc3620;

import java.util.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.util.struct.Struct;

/** Add your docs here. */
public class NTStructs {
  @SuppressWarnings("rawtypes")
  static Map<String, StructPublisher> publishers = new TreeMap<>();

  static NetworkTableInstance nt = NetworkTableInstance.getDefault();

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static void publish(String topic, Object o, Struct s) {
    StructPublisher publisher = publishers.computeIfAbsent(topic, (unused_lambda_argument) -> nt.getStructTopic(topic, s).publish());
    if (publisher != null) {
      publisher.set(o);
    }
  }

  public static void publish(String topic, Pose2d o) {
    publish(topic, o, Pose2d.struct);
  }

  public static void publish(String topic, Pose3d o) {
    publish(topic, o, Pose3d.struct);
  }

}
