package weblogic.work;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextChangeListener;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class LivePartitionUtility extends PartitionUtility {
   private ComponentInvocationContext globalContext;
   private final AuthenticatedSubject kernelId;

   public LivePartitionUtility(AuthenticatedSubject kernelId) {
      this.kernelId = kernelId;
      ComponentInvocationContextManager.getInstance(kernelId).addInvocationContextChangeListener(new ExecuteThreadContextChangeListenerImpl());
   }

   public ComponentInvocationContext doGetCurrentComponentInvocationContext() {
      return ComponentInvocationContextManager.getInstance(this.kernelId).getCurrentComponentInvocationContext();
   }

   public String doGetCurrentPartitionId() {
      ComponentInvocationContext componentInvocationContext = this.doGetCurrentComponentInvocationContext();
      return componentInvocationContext != null ? componentInvocationContext.getPartitionId() : null;
   }

   public String doGetCurrentPartitionName(boolean returnNullForGlobal) {
      ComponentInvocationContext componentInvocationContext = this.doGetCurrentComponentInvocationContext();
      if (componentInvocationContext != null) {
         return returnNullForGlobal && componentInvocationContext.isGlobalRuntime() ? null : componentInvocationContext.getPartitionName();
      } else {
         return null;
      }
   }

   public void doRunWorkUnderContext(Runnable work, ComponentInvocationContext context) throws ExecutionException {
      ComponentInvocationContextManager.runAs(this.kernelId, context, work);
   }

   public Object doRunWorkUnderContext(Callable work, ComponentInvocationContext context) throws ExecutionException {
      return ComponentInvocationContextManager.runAs(this.kernelId, context, work);
   }

   public Object doRunWorkUnderGlobalContext(Callable work) throws ExecutionException {
      if (this.globalContext == null) {
         this.globalContext = ComponentInvocationContextManager.getInstance(this.kernelId).createComponentInvocationContext("DOMAIN");
      }

      return ComponentInvocationContextManager.runAs(this.kernelId, this.globalContext, work);
   }

   public ComponentInvocationContext doCreateComponentInvocationContext(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
      return ComponentInvocationContextManager.getInstance(this.kernelId).createComponentInvocationContext(partitionName, applicationName, applicationVersion, moduleName, componentName);
   }

   static class ExecuteThreadContextChangeListenerImpl implements ComponentInvocationContextChangeListener {
      public void componentInvocationContextChanged(ComponentInvocationContext oldContext, ComponentInvocationContext newContext, boolean isPush) {
         Thread currentThread = Thread.currentThread();
         if (currentThread instanceof ExecuteThread) {
            ((ExecuteThread)currentThread).setCurrentCIC(newContext);
         }

      }
   }
}
