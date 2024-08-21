package org.usfirst.frc3620.logger;

public interface ITimedDataLogger extends IDataLogger {
    public void setInterval(double seconds);

    public void start();
}
