package weblogic.work.concurrent.context;

import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class SubmittingCompStateCheckerProvider implements ContextProvider {
   private static final long serialVersionUID = -5744664588298078282L;
   private final String appId;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");

   public SubmittingCompStateCheckerProvider(String appId) {
      this.appId = appId;
   }

   public String getContextType() {
      return "internal";
   }

   public int getConcurrentObjectType() {
      return 1;
   }

   public ContextHandle save(Map executionProperties) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext invocation = manager.getCurrentComponentInvocationContext();
      if (invocation.getApplicationId() != null && !invocation.getApplicationId().equals(this.appId)) {
         ComponentInvocationContext submittingCIC = manager.createComponentInvocationContext(invocation.getApplicationId(), invocation.getModuleName(), invocation.getComponentName());
         return new CICContextProvider.InvocationContextHandle(submittingCIC, (ManagedInvocationContext)null);
      } else {
         return null;
      }
   }

   public ContextHandle setup(ContextHandle contextHandle) throws IllegalStateException {
      if (!(contextHandle instanceof CICContextProvider.InvocationContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip submitting component state check: " + contextHandle);
         }

         return null;
      } else {
         CICContextProvider.InvocationContextHandle handle = (CICContextProvider.InvocationContextHandle)contextHandle;
         String submittingApp = handle.getInvocation().getApplicationId();
         if (!ConcurrentManagedObjectCollection.isStarted(submittingApp)) {
            throw new IllegalStateException(ConcurrencyLogger.logSubmittingCompStateCheckerFailedLoggable(submittingApp).getMessage());
         } else {
            return null;
         }
      }
   }

   public void reset(ContextHandle contextHandle) {
   }
}
