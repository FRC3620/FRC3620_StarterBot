package org.usfirst.frc3620;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.tinylog.TaggedLogger;
import org.usfirst.frc3620.logger.LoggingMaster;

public class FakeDS {
  TaggedLogger logger = LoggingMaster.getLogger(getClass());

  private Thread m_thread;

  RobotMode requestedMode = RobotMode.DISABLED;

  private void generateEnabledDsPacket(byte[] data, short sendCount) {
    data[0] = (byte) (sendCount >> 8);
    data[1] = (byte) sendCount;
    data[2] = 0x01; // general data tag
    switch (requestedMode) {
      case TELEOP:
        data[3] = 0x04;
        break;
    
      case AUTONOMOUS:
        data[3] = 0x06;
        break;
    
      case TEST:
        data[3] = 0x05;
        break;
    
      default:
        data[3] = 0x0; // disabled
    }
    data[4] = 0x10; // normal data request
    data[5] = 0x00; // red 1 station
  }

  @SuppressWarnings("MissingJavadocMethod")
  public void start() {
    if (m_thread != null) {
      throw new IllegalStateException("tried tp start another DS thread with one already running");
    }
    m_thread =
        new Thread(
            () -> {
              logger.info("starting fakeDS");
              DatagramSocket socket;
              try {
                socket = new DatagramSocket();
              } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return;
              }
              InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 1110);
              byte[] sendData = new byte[6];
              DatagramPacket packet = new DatagramPacket(sendData, 0, 6, addr);
              short sendCount = 0;
              int initCount = 0;
              while (!Thread.currentThread().isInterrupted()) {
                try {
                  Thread.sleep(20);
                  generateEnabledDsPacket(sendData, sendCount++);
                  // ~50 disabled packets are required to make the robot actually enable
                  // 1 is definitely not enough.
                  if (initCount < 50) {
                    initCount++;
                    sendData[3] = 0;
                  }
                  packet.setData(sendData);
                  socket.send(packet);
                } catch (InterruptedException ex) {
                  Thread.currentThread().interrupt();
                } catch (IOException ex) {
                  // TODO Auto-generated catch block
                  ex.printStackTrace();
                }
              }
              logger.info("stopped fakeDS");
              socket.close();
            });
    // Because of the test setup in Java, this thread will not be stopped
    // So it must be a daemon thread
    m_thread.setDaemon(true);
    m_thread.start();
  }

  @SuppressWarnings("MissingJavadocMethod")
  public void stop() {
    if (m_thread == null) {
      return;
    }
    logger.info("stopping fakeDS");
    m_thread.interrupt();
    try {
      m_thread.join(1000);
    } catch (InterruptedException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }
    m_thread = null;
  }

  public void setMode (RobotMode mode) {
    requestedMode = mode;
  }
}