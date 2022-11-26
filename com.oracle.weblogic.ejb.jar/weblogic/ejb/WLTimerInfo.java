package weblogic.ejb;

public final class WLTimerInfo {
   public static final int REMOVE_TIMER_ACTION = 1;
   public static final int DISABLE_TIMER_ACTION = 2;
   public static final int SKIP_TIMEOUT_ACTION = 3;
   private int maxRetryAttempts = -1;
   private int maxTimeouts = 0;
   private long retryDelay = 0L;
   private int timeoutFailureAction = 2;

   public void setMaxRetryAttempts(int retries) {
      if (retries < -1) {
         throw new IllegalArgumentException("" + retries);
      } else {
         this.maxRetryAttempts = retries;
      }
   }

   public int getMaxRetryAttempts() {
      return this.maxRetryAttempts;
   }

   public void setRetryDelay(long millis) {
      if (millis < 0L) {
         throw new IllegalArgumentException("" + millis);
      } else {
         this.retryDelay = millis;
      }
   }

   public long getRetryDelay() {
      return this.retryDelay;
   }

   public void setMaxTimeouts(int max) {
      if (max < 0) {
         throw new IllegalArgumentException("" + max);
      } else {
         this.maxTimeouts = max;
      }
   }

   public int getMaxTimeouts() {
      return this.maxTimeouts;
   }

   public void setTimeoutFailureAction(int action) {
      if (action != 1 && action != 2 && action != 3) {
         throw new IllegalArgumentException("" + action);
      } else {
         this.timeoutFailureAction = action;
      }
   }

   public int getTimeoutFailureAction() {
      return this.timeoutFailureAction;
   }
}
