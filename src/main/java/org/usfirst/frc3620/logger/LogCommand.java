package org.usfirst.frc3620.logger;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class LogCommand extends InstantCommand {
  static Logger defaultLogger = EventLogging.getLogger(LogCommand.class, Level.INFO);

  private Logger logger;

  private String msg;

  public LogCommand(String message) {
    this(message, null);
  }

  public LogCommand(String message, Logger constructorLogger) {
    logger = (constructorLogger == null) ? defaultLogger : constructorLogger;
    msg = message;
  }

  @Override
  public void initialize() {
    logger.info(msg);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
