package weblogic.apache.org.apache.log.output.io;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;
import weblogic.apache.org.apache.log.output.AbstractOutputTarget;

public class WriterTarget extends AbstractOutputTarget {
   /** @deprecated */
   protected Writer m_output;

   public WriterTarget(Writer writer, Formatter formatter) {
      super(formatter);
      if (null != writer) {
         this.setWriter(writer);
         this.open();
      }

   }

   protected synchronized void setWriter(Writer writer) {
      if (null == writer) {
         throw new NullPointerException("writer property must not be null");
      } else {
         this.m_output = writer;
      }
   }

   protected void write(String data) {
      try {
         this.m_output.write(data);
         this.m_output.flush();
      } catch (IOException var3) {
         this.getErrorHandler().error("Caught an IOException", var3, (LogEvent)null);
      }

   }

   public synchronized void close() {
      super.close();
      this.shutdownWriter();
   }

   protected synchronized void shutdownWriter() {
      Writer writer = this.m_output;
      this.m_output = null;

      try {
         if (null != writer) {
            writer.close();
         }
      } catch (IOException var3) {
         this.getErrorHandler().error("Error closing Writer", var3, (LogEvent)null);
      }

   }
}
