package org.usfirst.frc3620.logger;

import java.util.Timer;
import java.util.TimerTask;

abstract public class TimedDataLoggerBase extends DataLoggerBase implements ITimedDataLogger {
    double intervalInSeconds = 0.1;

    Timer timer = null;

    @Override
    public void start() {
        schedule();
    }

    private void schedule() {
        if (timer != null) {
            logger.info("Cancelling {}", timer);
            timer.cancel();
        }

        timer = new Timer();
        long interval = Math.max(1, Math.round(intervalInSeconds * 1000));
        logger.info("New timer {}, interval = {}ms", timer, interval);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runCycle();
            }
        }, 0, interval);
    }

    @Override
    public void setInterval(double s) {
        double oldInterval = intervalInSeconds;
        intervalInSeconds = s;
        if (s != oldInterval) {
            if (timer != null) {
                schedule();
            }
        }
    }
}
