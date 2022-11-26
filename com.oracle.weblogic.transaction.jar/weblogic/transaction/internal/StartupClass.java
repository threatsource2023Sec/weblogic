package weblogic.transaction.internal;

public class StartupClass {
   static int mainCallCount;
   public static Runnable startRunnable;

   public StartupClass() {
   }

   public static synchronized int getMainCallCount() {
      return mainCallCount;
   }

   static synchronized void increateMainCallCount() {
      ++mainCallCount;
   }

   private static void setRunnable(Runnable toMe) {
      startRunnable = toMe;
   }

   public StartupClass(Runnable startRunnable) {
      setRunnable(startRunnable);
   }

   public static void main(String[] args) {
      TransactionRecoveryService.initialize();
      if (startRunnable != null && mainCallCount == 0) {
         startRunnable.run();
      }

      increateMainCallCount();
   }
}
