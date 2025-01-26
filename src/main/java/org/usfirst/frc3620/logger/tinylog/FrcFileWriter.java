/*
 * Copyright 2016 Martin Winandy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.usfirst.frc3620.logger.tinylog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;
import org.tinylog.writers.raw.ByteArrayWriter;
import org.usfirst.frc3620.logger.LoggingMaster;

import edu.wpi.first.wpilibj.DataLogManager;

/**
 * Writer for outputting log entries to a log file. Already existing files can
 * be continued and the output can be
 * buffered for improving performance.
 */
public final class FrcFileWriter extends AbstractFormatPatternWriter {

  private Charset charset;
  private ByteArrayWriter writer;

  /**
   * @throws IOException
   *                                  Log file cannot be opened for write access
   * @throws IllegalArgumentException
   *                                  Log file is not defined in configuration
   */
  public FrcFileWriter() throws IOException {
    this(Collections.<String, String>emptyMap());
  }

  /**
   * @param properties
   *                   Configuration for writer
   *
   * @throws IllegalArgumentException
   *                                  Log file is not defined in configuration
   */
  public FrcFileWriter(final Map<String, String> properties) throws IOException {
    super(properties);
  }

  @Override
  public void write(final LogEntry logEntry) throws IOException {
    if (writer == null) {
      Date timestamp = LoggingMaster.getTimestamp();
      if (timestamp != null) {
        File logdir = LoggingMaster.getLoggingDirectory();
        if (logdir != null) {
          File f = new File(logdir, LoggingMaster.convertTimestampToString(timestamp) + ".log");
          String fileName = f.getAbsolutePath();
          boolean append = getBooleanValue("append");
          boolean buffered = getBooleanValue("buffered");
          boolean writingThread = getBooleanValue("writingthread");

          charset = getCharset();
          writer = createByteArrayWriter(fileName, append, buffered, !writingThread, false, charset);
        }
      }
    }

    if (writer != null) {
      byte[] data = render(logEntry).getBytes(charset);
      writer.write(data, 0, data.length);
    }
  }

  @Override
  public void flush() throws IOException {
    if (writer != null)
      writer.flush();
  }

  @Override
  public void close() throws IOException {
    if (writer != null)
      writer.close();
  }

}
