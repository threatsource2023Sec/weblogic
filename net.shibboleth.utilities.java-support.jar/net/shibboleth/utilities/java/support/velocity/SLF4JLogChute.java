package net.shibboleth.utilities.java.support.velocity;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogChute implements LogChute {
   public static final String LOGCHUTE_SLF4J_NAME = "runtime.log.logsystem.slf4j.name";
   public static final String DEFAULT_LOG_NAME = "org.apache.velocity";
   private Logger log;

   public void init(RuntimeServices rs) throws Exception {
      String name = (String)rs.getProperty("runtime.log.logsystem.slf4j.name");
      if (name == null) {
         name = "org.apache.velocity";
      }

      this.log = LoggerFactory.getLogger(name);
      this.log(0, "SLF4JLogChute name is '" + name + "'");
   }

   public boolean isLevelEnabled(int level) {
      switch (level) {
         case -1:
            return this.log.isTraceEnabled();
         case 0:
            return this.log.isDebugEnabled();
         case 1:
            return this.log.isInfoEnabled();
         case 2:
            return this.log.isWarnEnabled();
         case 3:
            return this.log.isErrorEnabled();
         default:
            return true;
      }
   }

   public void log(int level, String message) {
      switch (level) {
         case -1:
            this.log.trace(message);
            break;
         case 0:
         default:
            this.log.debug(message);
            break;
         case 1:
            this.log.info(message);
            break;
         case 2:
            this.log.warn(message);
            break;
         case 3:
            this.log.error(message);
      }

   }

   public void log(int level, String message, Throwable t) {
      switch (level) {
         case -1:
            this.log.trace(message, t);
            break;
         case 0:
         default:
            this.log.debug(message, t);
            break;
         case 1:
            this.log.info(message, t);
            break;
         case 2:
            this.log.warn(message, t);
            break;
         case 3:
            this.log.error(message, t);
      }

   }
}
