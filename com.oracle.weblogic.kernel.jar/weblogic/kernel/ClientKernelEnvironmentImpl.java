package weblogic.kernel;

public class ClientKernelEnvironmentImpl extends KernelEnvironment {
   public void addExecuteQueueRuntime(ExecuteThreadManager etm) {
   }

   public boolean isInitializeFromSystemPropertiesAllowed(String prefix) {
      return true;
   }
}
