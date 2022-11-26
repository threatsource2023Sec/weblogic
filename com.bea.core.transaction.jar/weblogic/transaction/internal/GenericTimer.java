package weblogic.transaction.internal;

final class GenericTimer implements Runnable {
   private static int instanceCounter = 1;
   private static int m_timeoutInterval = 5000;

   public GenericTimer() throws Exception {
      Thread timerThread = new Thread(this, "weblogic.transaction.TxTimer: '" + instanceCounter++ + "'");
      timerThread.setDaemon(true);
      timerThread.start();
   }

   public static void setTimeoutInterval(int timeout) {
      m_timeoutInterval = timeout;
   }

   public static int getTimeoutInterval() {
      return m_timeoutInterval;
   }

   public void run() {
      while(true) {
         try {
            synchronized(this) {
               this.wait((long)m_timeoutInterval);
            }

            TransactionManagerImpl.getTransactionManager().wakeUp();
         } catch (InterruptedException var4) {
         }
      }
   }
}
