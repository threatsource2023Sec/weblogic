package org.apache.log.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log.Logger;
import org.apache.log.Priority;

public class LoggerOutputStream extends OutputStream {
   private final Logger m_logger;
   private final Priority m_priority;
   private final StringBuffer m_output = new StringBuffer();
   private boolean m_closed;

   public LoggerOutputStream(Logger logger, Priority priority) {
      this.m_logger = logger;
      this.m_priority = priority;
   }

   public void close() throws IOException {
      this.flush();
      super.close();
      this.m_closed = true;
   }

   public void write(int data) throws IOException {
      this.checkValid();
      this.m_output.append((char)data);
      if (10 == data) {
         this.flush();
      }

   }

   public synchronized void flush() throws IOException {
      this.checkValid();
      this.m_logger.log(this.m_priority, this.m_output.toString());
      this.m_output.setLength(0);
   }

   private void checkValid() throws IOException {
      if (this.m_closed) {
         throw new EOFException("OutputStreamLogger closed");
      }
   }
}
