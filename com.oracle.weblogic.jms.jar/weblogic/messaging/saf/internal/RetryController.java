package weblogic.messaging.saf.internal;

public final class RetryController {
   private final double multiplier;
   private final long base;
   private final long maximum;
   private long nextDelay;

   public RetryController(long base, long maximum, double multiplier) {
      this.base = base;
      this.maximum = maximum;
      this.multiplier = multiplier;
      this.nextDelay = base;
   }

   public synchronized long getNextRetry() {
      long save = this.nextDelay;
      this.nextDelay = (long)((double)this.nextDelay * this.multiplier);
      if (this.nextDelay > this.maximum) {
         this.nextDelay = this.maximum;
      }

      return save;
   }

   public synchronized void reset() {
      this.nextDelay = this.base;
   }
}
