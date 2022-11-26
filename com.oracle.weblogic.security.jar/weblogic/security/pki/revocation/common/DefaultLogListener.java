package weblogic.security.pki.revocation.common;

import java.util.logging.Level;
import java.util.logging.Logger;

class DefaultLogListener extends AbstractLogListener {
   private static final Logger LOGGER = Logger.getLogger(DefaultLogListener.class.getPackage().getName());

   private DefaultLogListener() {
   }

   public static DefaultLogListener getInstance() {
      return new DefaultLogListener();
   }

   public boolean isLoggable(Level level) {
      if (null == level) {
         throw new IllegalArgumentException("Non-null Level expected.");
      } else {
         return LOGGER.isLoggable(level);
      }
   }

   public void log(Level level, Throwable throwable, String msg, Object... params) {
      if (this.isLoggable(level)) {
         String formattedMsg = this.formatMessage(msg, params);
         if (null != throwable) {
            LOGGER.log(level, formattedMsg, throwable);
         } else {
            LOGGER.log(level, formattedMsg);
         }

      }
   }
}
