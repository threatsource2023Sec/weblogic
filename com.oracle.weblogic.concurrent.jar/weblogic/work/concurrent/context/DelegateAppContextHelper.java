package weblogic.work.concurrent.context;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.concurrent.ConcurrencyLogger;

public class DelegateAppContextHelper {
   private static volatile AppContextHelper delegate;
   private static final DebugLogger debugLogger;

   public static GenericClassLoader getOrCreatePartitionClassLoader(String partitionName) {
      return delegate.getOrCreatePartitionClassLoader(partitionName);
   }

   public static void setDelegate(AppContextHelper helper) {
      delegate = helper;
   }

   static AppContextHelper getDelegate() {
      return delegate;
   }

   static {
      delegate = DelegateAppContextHelper.DefaultAppContextHelper.instance;
      debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   }

   static enum DefaultAppContextHelper implements AppContextHelper {
      instance;

      public GenericClassLoader getOrCreatePartitionClassLoader(String partitionName) {
         this.logError();
         return null;
      }

      private void logError() {
         ConcurrencyLogger.logConcurrentNotInitialized(new Exception());
         if (DelegateAppContextHelper.debugLogger.isDebugEnabled()) {
            DelegateAppContextHelper.debugLogger.debug("Concurrent service not stated!", new Exception());
         }

      }
   }
}
