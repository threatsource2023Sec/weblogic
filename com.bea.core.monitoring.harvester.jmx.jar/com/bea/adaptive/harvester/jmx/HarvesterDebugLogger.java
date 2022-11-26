package com.bea.adaptive.harvester.jmx;

public interface HarvesterDebugLogger {
   void dbgC(Object var1);

   void dbgC(Object var1, Throwable var2);

   void dbgR(Object var1);

   void dbgR(Object var1, Throwable var2);

   void dbgH(Object var1);

   void dbgH(Object var1, Throwable var2);

   void dbgT(Object var1);

   void dbgT(Object var1, Throwable var2);

   boolean isDebugCEnabled();

   boolean isDebugREnabled();

   boolean isDebugHEnabled();

   boolean isDebugTEnabled();
}
