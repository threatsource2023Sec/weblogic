package weblogic.diagnostics.debug;

import java.util.Properties;
import java.util.logging.Logger;

public class KernelDebugService {
   private static KernelDebugService singleton = null;

   public static KernelDebugService getKernelDebugService() {
      if (singleton == null) {
         singleton = new KernelDebugService();
      }

      return singleton;
   }

   KernelDebugService() {
   }

   public void initializeDebugLogging(Logger logger) {
      DebugLogger.getDefaultDebugLoggerRepository().setLogger(logger);
   }

   public void initializeDebugParameters(Properties props) {
      DebugLogger.getDefaultDebugLoggerRepository().setDebugLoggerParameters(props);
   }
}
