package org.usfirst.frc3620;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.tinylog.TaggedLogger;
import org.usfirst.frc3620.logger.LoggingMaster;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LogProgressOfCompositeCommand extends InstantCommand {
  TaggedLogger logger = LoggingMaster.getLogger(getClass());

  public LogProgressOfCompositeCommand() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    List<StackWalker.StackFrame> stack = StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
        .walk(Stream::toList);
    for (StackFrame stackFrame : stack) {
      System.out.println(stackFrame);
      if (Command.class.isAssignableFrom(stackFrame.getClass())) {
        System.out.println("** Command! " + stackFrame.getClass().getCanonicalName());
      }
  }
}
