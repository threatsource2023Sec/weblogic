package weblogic.logging;

import java.io.OutputStream;
import java.util.logging.ErrorManager;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.CommonLogMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.LogMBean;

public final class ConsoleHandler extends StreamHandler {
   /** @deprecated */
   @Deprecated
   public ConsoleHandler(KernelMBean akmb) {
      this(System.out, akmb.getLog());
   }

   /** @deprecated */
   @Deprecated
   public ConsoleHandler(LogMBean logConfig) {
      this(System.out, logConfig);
   }

   public ConsoleHandler(CommonLogMBean logConfig) {
      this(System.out, logConfig);
   }

   ConsoleHandler(OutputStream os, CommonLogMBean logConfig) {
      super(os, new SimpleFormatter());
      ErrorManager jdkErrorManager = this.getErrorManager();
      if (Kernel.isApplet()) {
         this.setErrorManager(jdkErrorManager);
      } else {
         this.setErrorManager(new WLErrorManager(this));
      }

      this.setLevel(WLLevel.getLevel(Severities.severityStringToNum(logConfig.getStdoutSeverity())));
   }

   public synchronized void publish(LogRecord rec) {
      super.publish(rec);
      super.flush();
   }

   public void close() {
      this.setLevel(Level.OFF);
      super.flush();
   }
}
