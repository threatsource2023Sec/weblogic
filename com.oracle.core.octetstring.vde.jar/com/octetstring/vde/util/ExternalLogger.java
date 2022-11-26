package com.octetstring.vde.util;

public interface ExternalLogger {
   void log(int var1, String var2, String var3);

   void printStackTrace(Throwable var1);

   void printStackTraceLog(Throwable var1);

   void printStackTraceConsole(Throwable var1);
}
