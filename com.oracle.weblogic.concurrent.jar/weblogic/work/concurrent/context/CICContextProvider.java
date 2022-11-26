package weblogic.work.concurrent.context;

import java.security.AccessController;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class CICContextProvider implements ContextProvider {
   private static final long serialVersionUID = 2072548635903593294L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   private static final CICContextProvider instance = new CICContextProvider();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected CICContextProvider() {
   }

   public static CICContextProvider getInstance() {
      return instance;
   }

   public ContextHandle save(Map executionProperties) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext invocation = manager.getCurrentComponentInvocationContext();
      ComponentInvocationContext finalCIC = manager.createComponentInvocationContext(invocation.getApplicationId(), invocation.getModuleName(), invocation.getComponentName());
      return new InvocationContextHandle(finalCIC, (ManagedInvocationContext)null);
   }

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof InvocationContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip setupBasicContext: " + contextHandle);
         }

         return null;
      } else {
         InvocationContextHandle handle = (InvocationContextHandle)contextHandle;
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(KERNEL_ID).setCurrentComponentInvocationContext(handle.getInvocation());
         return new InvocationContextHandle((ComponentInvocationContext)null, mic);
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (!(contextHandle instanceof InvocationContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip resetBasicContext: " + contextHandle);
         }

      } else {
         ManagedInvocationContext mic = ((InvocationContextHandle)contextHandle).managedInvocation;
         mic.close();
      }
   }

   public String getContextType() {
      return "internal";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public static class InvocationContextHandle implements ContextHandle {
      private static final long serialVersionUID = -613057579013822643L;
      private final ComponentInvocationContext invocation;
      private final transient ManagedInvocationContext managedInvocation;

      public InvocationContextHandle(ComponentInvocationContext invocation, ManagedInvocationContext managedInvocation) {
         this.managedInvocation = managedInvocation;
         this.invocation = invocation;
      }

      public ComponentInvocationContext getInvocation() {
         return this.invocation;
      }

      public static ComponentInvocationContext extractCIC(ContextHandle handle) {
         return !(handle instanceof InvocationContextHandle) ? null : ((InvocationContextHandle)handle).getInvocation();
      }
   }
}
