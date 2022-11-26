package weblogic.security.pki.revocation.wls;

import java.util.logging.Level;
import weblogic.security.pki.revocation.common.AbstractLogListener;
import weblogic.security.shared.LoggerWrapper;

class WlsLogListener extends AbstractLogListener {
   private static final LoggerWrapper LOGGER = LoggerWrapper.getInstance("DebugCertRevocCheck");

   private WlsLogListener() {
   }

   public static WlsLogListener getInstance() {
      return new WlsLogListener();
   }

   public boolean isLoggable(Level level) {
      return LOGGER.isDebugEnabled();
   }

   public void log(Level level, Throwable throwable, String msg, Object... params) {
      if (this.isLoggable(level)) {
         String formattedMsg = this.formatMessage(msg, params);
         if (null != throwable) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug(formattedMsg, throwable);
            }
         } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(formattedMsg);
         }

      }
   }
}
