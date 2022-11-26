package com.bea.staxb.buildtime;

public interface BuildtimeLogger {
   boolean isVerbose();

   void logInfo(String var1);

   void logVerbose(String var1);

   void logDebug(String var1);
}
