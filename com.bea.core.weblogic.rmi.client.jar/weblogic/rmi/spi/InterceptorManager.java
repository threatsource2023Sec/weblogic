package weblogic.rmi.spi;

public class InterceptorManager {
   private static final InterceptorManager singleton = new InterceptorManager();
   private Interceptor transactionInterceptor;

   private InterceptorManager() {
   }

   public static InterceptorManager getManager() {
      return singleton;
   }

   public void setTransactionInterceptor(Interceptor i) {
      this.transactionInterceptor = i;
   }

   public Interceptor getTransactionInterceptor() {
      return this.transactionInterceptor;
   }
}
