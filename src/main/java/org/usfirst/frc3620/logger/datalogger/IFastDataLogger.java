package org.usfirst.frc3620.logger.datalogger;

public interface IFastDataLogger extends IDataLogger {
    public void setMaxLength(Double seconds);

    public void done();

    public boolean isDone();

    public void update();
}
