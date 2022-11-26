package org.opensaml;

public interface DebugLogger {
   boolean isDebugEnabled();

   void debug(String var1);

   void debug(String var1, Exception var2);
}
