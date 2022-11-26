package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.debug.DebugLogger;

final class DebugSupport {
   private static DebugLogger dbg = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");
   private static DebugLogger dbgData = DebugLogger.getDebugLogger("DebugDiagnosticsHarvesterData");

   static DebugLogger getDebugLogger() {
      return dbg;
   }

   static DebugLogger getLowLevelDebugLogger() {
      return dbgData;
   }
}
