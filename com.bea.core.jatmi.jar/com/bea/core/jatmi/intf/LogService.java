package com.bea.core.jatmi.intf;

public interface LogService {
   void setTraceLevel(int var1);

   void doTrace(String var1);

   void doTrace(int var1, String var2);

   void doTrace(int var1, int var2, String var3);

   boolean isTraceEnabled(int var1);

   boolean isMixedTraceEnabled(int var1);
}
