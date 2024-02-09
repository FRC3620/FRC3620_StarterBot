import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

import org.junit.Test;
import org.apache.logging.log4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.FRC3620Level;

public class LoggingTest {
    @Test
    public void test() {
        printMemoryStatus("start");
        Logger logger = EventLogging.getLogger(LoggingTest.class, FRC3620Level.INFO);
        printMemoryStatus("after getLogger");
        System.out.println ("debug enabled = " + logger.isDebugEnabled());
        logger.error("error");
        printMemoryStatus("after first logging");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
        printMemoryStatus("done logging - doing gc");
        Runtime.getRuntime().gc();
        printMemoryStatus("end");

        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }

    static public void printMemoryStatus (String label) {
        StringBuilder sb = new StringBuilder("Memory: ");
        sb.append(label);
        long used=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        sb.append("; used=");
        sb.append(used);

        for(GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            sb.append("; gc." + gc.getName() + ".count=");
            sb.append(gc.getCollectionCount());
            sb.append("; gc." + gc.getName() + ".time=");
            sb.append(gc.getCollectionTime());
        }

        System.out.println (sb.toString());

        
      }
}
