package org.apache.velocity.runtime.log;

import java.io.File;
import org.apache.log.Hierarchy;
import org.apache.log.LogTarget;
import org.apache.log.Logger;
import org.apache.log.Priority;
import org.apache.log.output.io.FileTarget;
import org.apache.velocity.runtime.RuntimeServices;

public class AvalonLogSystem implements LogSystem {
   private Logger logger = null;
   private RuntimeServices rsvc = null;

   public void init(RuntimeServices rs) throws Exception {
      this.rsvc = rs;
      String loggerName = (String)this.rsvc.getProperty("runtime.log.logsystem.avalon.logger");
      if (loggerName != null) {
         this.logger = Hierarchy.getDefaultHierarchy().getLoggerFor(loggerName);
      } else {
         String logfile = (String)this.rsvc.getProperty("runtime.log");

         try {
            this.init(logfile);
            this.logVelocityMessage(0, "AvalonLogSystem initialized using logfile '" + logfile + "'");
         } catch (Exception var5) {
            System.out.println("PANIC : Error configuring AvalonLogSystem : " + var5);
            System.err.println("PANIC : Error configuring AvalonLogSystem : " + var5);
            throw new Exception("Unable to configure AvalonLogSystem : " + var5);
         }
      }

   }

   public void init(String logFile) throws Exception {
      FileTarget target = new FileTarget(new File(logFile), false, new VelocityFormatter("%{time} %{message}\\n%{throwable}"));
      this.logger = Hierarchy.getDefaultHierarchy().getLoggerFor(this.rsvc.toString());
      this.logger.setPriority(Priority.DEBUG);
      this.logger.setLogTargets(new LogTarget[]{target});
   }

   public void logVelocityMessage(int level, String message) {
      switch (level) {
         case 0:
            this.logger.debug(" [debug] " + message);
            break;
         case 1:
            this.logger.info("  [info] " + message);
            break;
         case 2:
            this.logger.warn("  [warn] " + message);
            break;
         case 3:
            this.logger.error(" [error] " + message);
            break;
         default:
            this.logger.info(message);
      }

   }
}
