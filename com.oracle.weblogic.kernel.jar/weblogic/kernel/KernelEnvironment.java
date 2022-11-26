package weblogic.kernel;

public abstract class KernelEnvironment {
   private static KernelEnvironment singleton;

   public static KernelEnvironment getKernelEnvironment() {
      if (singleton == null) {
         try {
            singleton = (KernelEnvironment)Class.forName("weblogic.kernel.WLSKernelEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (KernelEnvironment)Class.forName("weblogic.kernel.ClientKernelEnvironmentImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   public static void setKernelEnvironment(KernelEnvironment helper) {
      singleton = helper;
   }

   public abstract void addExecuteQueueRuntime(ExecuteThreadManager var1);

   public abstract boolean isInitializeFromSystemPropertiesAllowed(String var1);
}
