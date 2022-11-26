package weblogic.cluster.singleton;

public abstract class SingletonOperation {
   protected static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();

   public abstract void prepare(SingletonServiceInfo var1, Leasing var2) throws SingletonOperationException, LeasingException;

   public abstract void execute(SingletonServiceInfo var1);

   public abstract void failed(String var1, Leasing var2, Throwable var3) throws LeasingException;

   public static void p(Object o) {
      SingletonServicesDebugLogger.debug("SingletonServicesManagerService " + o.toString());
   }

   public abstract String getName();
}
