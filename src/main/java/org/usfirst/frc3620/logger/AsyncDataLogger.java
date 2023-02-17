package org.usfirst.frc3620.logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class AsyncDataLogger<T extends AsyncDataLoggerDatum> extends SubsystemBase {
  ArrayBlockingQueue<AsyncDataLoggerMessage> queue;
  int droppedMessages = 0;
  Logger logger = EventLogging.getLogger(getClass(), EventLogging.Level.INFO);

  public AsyncDataLogger(String filename, int capacity) {
    queue = new ArrayBlockingQueue<>(capacity);

    String filename_with_timestamp = filename + "_" + LoggingMaster.convertTimestampToString(new Date());

    File file = new File(LoggingMaster.getLoggingDirectory(), filename_with_timestamp);
    
    Thread t = new Thread(() -> { writerThread(file); });
    t.setName("AsyncDataLogger:" + filename);
    t.setDaemon(true);
    t.start();
  }

  void do_queue(AsyncDataLoggerMessage m) {
    if (queue.remainingCapacity() > 0) {
      queue.add(m);
    } else {
      droppedMessages++;
    }
  }

  public void send(T o) {
    do_queue(new AsyncDataLoggerMessage(MessageType.DATA, o));
  }

  public void flush() {
    do_queue(new AsyncDataLoggerMessage(MessageType.FLUSH, null));
  }

  public void close() {
    do_queue(new AsyncDataLoggerMessage(MessageType.CLOSE, null));
  }

  enum MessageType { DATA, FLUSH, CLOSE }

  class AsyncDataLoggerMessage {
    MessageType messageType;
    T payload;

    AsyncDataLoggerMessage(MessageType messageType, T payload) {
      this.messageType = messageType;
      this.payload = payload;
    }
  }

  void writerThread(File file) {
    try {
      logger.info ("opening {}", file.getCanonicalPath());
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      boolean open = true;
      while (true) {
        AsyncDataLoggerMessage m = queue.take();
        if (open) {
          MessageType mt = m.messageType;
          switch (mt) {
            case FLUSH:
              bos.flush();
              break;
            case CLOSE:
              bos.close();
              open = false;
              break;
            case DATA:
              T payload = m.payload;
              if (payload != null) {
                bos.write(payload.getAsyncDataLoggerBytes());
              }
              break;
          }
        }
      }
    } catch (IOException | InterruptedException ex) {
      throw new RuntimeException("writer thread choked", ex);
    }
  }
}
