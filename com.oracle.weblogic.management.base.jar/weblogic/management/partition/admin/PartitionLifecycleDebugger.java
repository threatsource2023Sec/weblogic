package weblogic.management.partition.admin;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;

public class PartitionLifecycleDebugger implements PartitionManagerInterceptorAdapter.DebugLog {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugPartitionLifecycle");

   public static final boolean isDebugEnabled() {
      return debugLogger.isDebugEnabled();
   }

   public static void debug(String msg) {
      debugLogger.debug(msg);
   }

   public static PartitionLifecycleDebugger getInstance() {
      return PartitionLifecycleDebugger.SingletonHolder.INSTANCE;
   }

   public boolean isEnabled() {
      return isDebugEnabled();
   }

   public void write(String message) {
      debug(message);
   }

   private static final class SingletonHolder {
      private static final PartitionLifecycleDebugger INSTANCE = init();

      private static PartitionLifecycleDebugger init() {
         return new PartitionLifecycleDebugger();
      }
   }
}
