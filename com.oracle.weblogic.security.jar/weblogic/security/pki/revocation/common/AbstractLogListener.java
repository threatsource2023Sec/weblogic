package weblogic.security.pki.revocation.common;

import java.text.MessageFormat;
import java.util.logging.Level;

public abstract class AbstractLogListener implements LogListener {
   protected AbstractLogListener() {
   }

   public void log(Level level, String msg, Object... params) {
      this.log(level, (Throwable)null, msg, params);
   }

   public abstract void log(Level var1, Throwable var2, String var3, Object... var4);

   protected String formatMessage(String msg, Object... params) {
      String formattedMsg;
      if (null != msg) {
         formattedMsg = MessageFormat.format(msg, params);
      } else {
         formattedMsg = "";
      }

      return formattedMsg;
   }
}
