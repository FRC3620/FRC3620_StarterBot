package org.usfirst.frc3620.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import edu.wpi.first.wpilibj.DriverStation;

@SuppressWarnings("unused")
public class EventLogging {

    // make some levels that correspond to the different SLF4J logging
    // methods. Have the mappings to the underlying j.u.l logging levels.
    public enum FRC3620Level {
        TRACE(org.apache.logging.log4j.Level.TRACE), //
        DEBUG(org.apache.logging.log4j.Level.DEBUG), //
        INFO(org.apache.logging.log4j.Level.INFO), //
        WARN(org.apache.logging.log4j.Level.WARN), //
        ERROR(org.apache.logging.log4j.Level.ERROR);

        final org.apache.logging.log4j.Level log4jLevel;

        FRC3620Level(org.apache.logging.log4j.Level _log4jLevel) {
            log4jLevel = _log4jLevel;
        }
    }

    /**
     * Get a log4j2 logger for a class.
     * 
     * @param clazz
     *            class for the logger
     */
    static public Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName(), null);
    }

    /**
     * Get a log4j2 logger for a class.
     * 
     * @param clazz
     *            class for the logger
     * @param level
     *            Level that we want to log at
     * @return
     */
    static public Logger getLogger(Class<?> clazz, FRC3620Level level) {
        return getLogger(clazz.getName(), level);
    }

    /**
     * Get a log4j2 logger for a class.
     * 
     * @param sClazz
     *            name for the logger
     * @return
     */
    static public Logger getLogger(String sClazz) {
        return getLogger(sClazz, null);
    }

    /**
     * Get a log4j2 logger for a class.
     * 
     * @param sClazz
     *            name for the logger
     * @param level
     *            Level that we want to log at
     * @return
     */
    static public Logger getLogger(String sClazz, FRC3620Level level) {
        Logger rv = LogManager.getLogger(sClazz);
        if (level != null) {
            setLoggerLevel(sClazz, level);
        }
        return rv;
    }

    static LoggerContext log4jLoggerContext = null;
    static Configuration log4jConfiguration = null;

    static LoggerConfig getLoggerConfig(String sClazz) {
        if (log4jConfiguration == null) {
            log4jLoggerContext = (LoggerContext) LogManager.getContext(false);
            log4jConfiguration = log4jLoggerContext.getConfiguration();
        }
        return log4jConfiguration.getLoggerConfig(sClazz);
    }

    public static void setLoggerLevel (String sClazz, FRC3620Level newLevel) {
            LoggerConfig loggerConfig = getLoggerConfig(sClazz); 
            org.apache.logging.log4j.Level currentLevel = loggerConfig.getLevel();
            if (!currentLevel.equals(newLevel.log4jLevel)) {
                loggerConfig.setLevel(newLevel.log4jLevel);
                log4jLoggerContext.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
            }
    }
    
    /**
     * Log command starts and stops
     * 
     * @param logger
     * 			  logger to log to.
     */
    public static void commandMessage (Logger logger) {
  	  Throwable t = new Throwable();
  	  StackTraceElement[] stackTraceElement = t.getStackTrace();
  	  logger.info("command {}", stackTraceElement[1].getMethodName());
    }

    
    /**
     * Write a warning message to the DriverStation.
     * 
     * @param message
     *            Message to log.
     */
    public static void writeWarningToDS(String message) {
        if (DriverStation.isDSAttached()) {
        	DriverStation.reportWarning(message, false);
        }
    }

    /**
     * Create a String representation of an Exception.
     * 
     * @param t
     * @return
     */
    public static String exceptionToString(Throwable t) {
        final StackTraceElement[] stackTrace = t.getStackTrace();
        final StringBuilder message = new StringBuilder(1000);
        final String separator = "===\n";
        final Throwable cause = t.getCause();

        message.append("Exception of type ").append(t.getClass().getName());
        message.append("\nMessage: ").append(t.getMessage()).append('\n');
        message.append(separator);
        message.append("   ").append(stackTrace[0]).append('\n');

        for (int i = 1; i < stackTrace.length; i++) {
            message.append(" \t").append(stackTrace[i]).append('\n');
        }

        if (cause != null) {
            final StackTraceElement[] causeTrace = cause.getStackTrace();
            message.append(" \t\tCaused by ")
                    .append(cause.getClass().getName());
            message.append("\n\t\tBecause: ")
                    .append(cause.getMessage());
            message.append("\n\t\t   ").append(causeTrace[0]);
            message.append("\n\t\t\t").append(causeTrace[2]);
            message.append("\n\t\t\t").append(causeTrace[3]);
        }

        return message.toString();
    }

    public static void setup() {
    }

}
