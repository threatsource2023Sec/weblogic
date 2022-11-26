package com.bea.common.security.storeservice.util;

import com.bea.common.logger.spi.LoggerSpi;
import org.apache.openjpa.lib.log.AbstractLog;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactory;

public class LogFactoryImpl implements LogFactory {
   private String key;

   public void setKey(String key) {
      this.key = key;
   }

   public Log getLog(String channel) {
      final LoggerSpi logger = StoreServiceDelegate.getLogger(this.key);
      return new AbstractLog() {
         protected boolean isEnabled(short level) {
            switch (level) {
               case 1:
                  return logger.isDebugEnabled();
               default:
                  return true;
            }
         }

         protected void log(short level, String message, Throwable t) {
            if (t != null) {
               switch (level) {
                  case 1:
                     logger.debug(message, t);
                  case 2:
                  default:
                     break;
                  case 3:
                     logger.info(message, t);
                     break;
                  case 4:
                     logger.warn(message, t);
                     break;
                  case 5:
                     logger.error(message, t);
                     break;
                  case 6:
                     logger.severe(message, t);
               }
            } else {
               switch (level) {
                  case 1:
                     logger.debug(message);
                  case 2:
                  default:
                     break;
                  case 3:
                     logger.info(message);
                     break;
                  case 4:
                     logger.warn(message);
                     break;
                  case 5:
                     logger.error(message);
                     break;
                  case 6:
                     logger.severe(message);
               }
            }

         }
      };
   }
}
