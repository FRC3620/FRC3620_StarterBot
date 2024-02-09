import org.junit.Test;
import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

public class LoggingTest {
    @Test
    public void test() {
        Logger logger = EventLogging.getLogger(LoggingTest.class, Level.INFO);
        System.out.println ("debug enabled = " + logger.isDebugEnabled());
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
