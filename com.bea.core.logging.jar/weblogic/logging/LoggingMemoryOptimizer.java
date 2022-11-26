package weblogic.logging;

import java.util.ResourceBundle;

public class LoggingMemoryOptimizer {
   public static void clearCache() {
      ResourceBundle.clearCache();
   }
}
