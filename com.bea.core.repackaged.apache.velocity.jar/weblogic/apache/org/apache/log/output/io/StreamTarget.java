package weblogic.apache.org.apache.log.output.io;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;
import weblogic.apache.org.apache.log.output.AbstractOutputTarget;

public class StreamTarget extends AbstractOutputTarget {
   private OutputStream m_outputStream;

   public StreamTarget(OutputStream outputStream, Formatter formatter) {
      super(formatter);
      if (null != outputStream) {
         this.setOutputStream(outputStream);
         this.open();
      }

   }

   protected synchronized void setOutputStream(OutputStream outputStream) {
      if (null == outputStream) {
         throw new NullPointerException("outputStream property must not be null");
      } else {
         this.m_outputStream = outputStream;
      }
   }

   protected synchronized void write(String data) {
      OutputStream outputStream = this.m_outputStream;
      if (null == outputStream) {
         String message = "Attempted to write data '" + data + "' to Null OutputStream";
         this.getErrorHandler().error(message, (Throwable)null, (LogEvent)null);
      } else {
         try {
            outputStream.write(data.getBytes());
            outputStream.flush();
         } catch (IOException var5) {
            String message = "Error writing data '" + data + "' to OutputStream";
            this.getErrorHandler().error(message, var5, (LogEvent)null);
         }

      }
   }

   public synchronized void close() {
      super.close();
      this.shutdownStream();
   }

   protected synchronized void shutdownStream() {
      OutputStream outputStream = this.m_outputStream;
      this.m_outputStream = null;

      try {
         if (null != outputStream) {
            outputStream.close();
         }
      } catch (IOException var3) {
         this.getErrorHandler().error("Error closing OutputStream", var3, (LogEvent)null);
      }

   }
}
