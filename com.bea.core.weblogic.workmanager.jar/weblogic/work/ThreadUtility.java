package weblogic.work;

public abstract class ThreadUtility {
   private static final ThreadUtility DEFAULT_THREAD_UTIILTY = new DefaultThreadUtility();
   private static ThreadUtility instance;

   public static ExecuteThread getCurrentThreadAsExecuteThread() {
      return instance.doGetCurrentThreadAsExecuteThread();
   }

   protected abstract ExecuteThread doGetCurrentThreadAsExecuteThread();

   static {
      instance = DEFAULT_THREAD_UTIILTY;
   }

   private static class DefaultThreadUtility extends ThreadUtility {
      private DefaultThreadUtility() {
      }

      protected ExecuteThread doGetCurrentThreadAsExecuteThread() {
         return this.asExecuteThread(Thread.currentThread());
      }

      private ExecuteThread asExecuteThread(Thread thread) {
         return thread instanceof ExecuteThread ? (ExecuteThread)thread : null;
      }

      // $FF: synthetic method
      DefaultThreadUtility(Object x0) {
         this();
      }
   }
}
