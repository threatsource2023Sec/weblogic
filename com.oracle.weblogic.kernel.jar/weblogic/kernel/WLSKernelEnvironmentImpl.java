package weblogic.kernel;

public class WLSKernelEnvironmentImpl extends KernelEnvironment {
   public void addExecuteQueueRuntime(ExecuteThreadManager etm) {
      if (KernelStatus.isServer()) {
         ExecuteQueueRuntime.addExecuteQueueRuntime(etm);
      }

   }

   public boolean isInitializeFromSystemPropertiesAllowed(String prefix) {
      return true;
   }
}
