package weblogic.diagnostics.debug;

public class DebugLifecycle {
   public DebugLifecycle() {
      try {
         DebugProviderRegistration.registerDebugProvider(SimpleDebugProvider.getInstance(), DebugLogger.getDefaultDebugLoggerRepository());
      } catch (DebugProviderRegistrationException var2) {
         System.err.println("Error initializing SimpleDebugProvider");
         var2.printStackTrace();
      }

   }
}
