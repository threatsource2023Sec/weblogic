package weblogic.work;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

public abstract class PartitionUtility {
   private static final PartitionUtility DEFAULT_PARTITION_UTILITY = new FixedContextPartitionUtility();
   private static PartitionUtility instance;
   static final int NUM_SLOWDOWN_LEVELS = 10;

   static void setInstance(PartitionUtility partitionUtility) {
      instance = partitionUtility;
   }

   public static ComponentInvocationContext getCurrentComponentInvocationContext() {
      return instance.doGetCurrentComponentInvocationContext();
   }

   public static String getCurrentPartitionId() {
      return instance.doGetCurrentPartitionId();
   }

   public static String getCurrentPartitionName(boolean returnNullForGlobal) {
      return instance.doGetCurrentPartitionName(returnNullForGlobal);
   }

   public static void runWorkUnderContext(Runnable work, ComponentInvocationContext context) throws ExecutionException {
      instance.doRunWorkUnderContext(work, context);
   }

   public static Object runWorkUnderContext(Callable work, ComponentInvocationContext context) throws ExecutionException {
      return instance.doRunWorkUnderContext(work, context);
   }

   public static Object runWorkUnderGlobalContext(Callable work) throws ExecutionException {
      return instance.doRunWorkUnderGlobalContext(work);
   }

   public static ComponentInvocationContext createComponentInvocationContext(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
      return instance.doCreateComponentInvocationContext(partitionName, applicationName, applicationVersion, moduleName, componentName);
   }

   public abstract ComponentInvocationContext doGetCurrentComponentInvocationContext();

   public abstract String doGetCurrentPartitionId();

   public abstract String doGetCurrentPartitionName(boolean var1);

   public abstract void doRunWorkUnderContext(Runnable var1, ComponentInvocationContext var2) throws ExecutionException;

   public abstract Object doRunWorkUnderContext(Callable var1, ComponentInvocationContext var2) throws ExecutionException;

   public abstract Object doRunWorkUnderGlobalContext(Callable var1) throws ExecutionException;

   public abstract ComponentInvocationContext doCreateComponentInvocationContext(String var1, String var2, String var3, String var4, String var5);

   static {
      instance = DEFAULT_PARTITION_UTILITY;
   }

   private static class FixedContextPartitionUtility extends PartitionUtility {
      private FixedContextPartitionUtility() {
      }

      public ComponentInvocationContext doGetCurrentComponentInvocationContext() {
         return ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
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
         this.checkSameContext(context);

         try {
            work.run();
         } catch (RuntimeException var4) {
            throw new ExecutionException(var4);
         }
      }

      public Object doRunWorkUnderContext(Callable work, ComponentInvocationContext context) throws ExecutionException {
         this.checkSameContext(context);

         try {
            return work.call();
         } catch (Exception var4) {
            throw new ExecutionException(var4);
         }
      }

      public Object doRunWorkUnderGlobalContext(Callable work) throws ExecutionException {
         this.checkGlobalContext();

         try {
            return work.call();
         } catch (Exception var3) {
            throw new ExecutionException(var3);
         }
      }

      public ComponentInvocationContext doCreateComponentInvocationContext(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
         return ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName, applicationName, applicationVersion, moduleName, componentName);
      }

      void checkSameContext(ComponentInvocationContext cic) throws ExecutionException {
         ComponentInvocationContext currentCIC = this.doGetCurrentComponentInvocationContext();
         if (cic != null && !cic.equals(currentCIC)) {
            throw new ExecutionException(new SecurityException("Not authorized to task under a different ComponentInvocationContext "));
         }
      }

      void checkGlobalContext() throws ExecutionException {
         ComponentInvocationContext currentCIC = this.doGetCurrentComponentInvocationContext();
         if (!currentCIC.isGlobalRuntime()) {
            throw new ExecutionException(new SecurityException("Not authorized to run task under the global domain"));
         }
      }

      // $FF: synthetic method
      FixedContextPartitionUtility(Object x0) {
         this();
      }
   }
}
