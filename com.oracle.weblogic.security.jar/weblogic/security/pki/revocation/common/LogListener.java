package weblogic.security.pki.revocation.common;

import java.util.logging.Level;

public interface LogListener {
   boolean isLoggable(Level var1);

   void log(Level var1, String var2, Object... var3);

   void log(Level var1, Throwable var2, String var3, Object... var4);
}
