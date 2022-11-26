package weblogic.apache.org.apache.log.output.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;

public class SafeFileTarget extends FileTarget {
   public SafeFileTarget(File file, boolean append, Formatter formatter) throws IOException {
      super(file, append, formatter);
      this.shutdownStream();
   }

   public synchronized void processEvent(LogEvent event) {
      if (!this.isOpen()) {
         this.getErrorHandler().error("Writing event to closed stream.", (Throwable)null, event);
      } else {
         try {
            FileOutputStream outputStream = new FileOutputStream(this.getFile().getPath(), true);
            this.setOutputStream(outputStream);
         } catch (Throwable var3) {
            this.getErrorHandler().error("Unable to open file to write log event.", var3, event);
            return;
         }

         super.processEvent(event);
         this.shutdownStream();
      }
   }
}
