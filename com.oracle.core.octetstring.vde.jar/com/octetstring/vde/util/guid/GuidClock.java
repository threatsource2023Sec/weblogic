package com.octetstring.vde.util.guid;

final class GuidClock {
   private static final long EPOCH_OFFSET = 12219292800000L;
   private static final long UUID_TIME_UNITS_IN_A_MILLISECOND = 10000L;
   private static final long MAX_TIMESTAMPS_PER_SYSTEM_CLOCK_TICK = 10000L;
   private static final int SYSTEM_CLOCK_RESOLUTION_IN_MILLISECONDS = 1;
   private long lastSystemTime;
   private long secondaryClock;
   private static GuidClock clock = new GuidClock();

   static GuidClock getInstance() {
      return clock;
   }

   synchronized long getTime() {
      long currentSystemTime = System.currentTimeMillis();
      if (currentSystemTime == this.lastSystemTime) {
         try {
            this.incrementSecondaryClock();
         } catch (ClockOverrunException var6) {
            while(currentSystemTime == this.lastSystemTime) {
               try {
                  Thread.currentThread();
                  Thread.sleep(1L);
                  currentSystemTime = System.currentTimeMillis();
               } catch (InterruptedException var5) {
                  currentSystemTime = System.currentTimeMillis();
               }
            }
         }
      } else {
         this.lastSystemTime = currentSystemTime;
         if (this.secondaryClockSet()) {
            this.resetSecondaryClock();
         }
      }

      long currentUTCTime = (currentSystemTime + 12219292800000L) * 10000L;
      return currentUTCTime + this.secondaryClock;
   }

   private final void resetSecondaryClock() {
      this.secondaryClock = 0L;
   }

   private final boolean secondaryClockSet() {
      return this.secondaryClock != 0L;
   }

   private void incrementSecondaryClock() throws ClockOverrunException {
      if (this.secondaryClock++ >= 10000L) {
         throw new ClockOverrunException();
      }
   }

   static class ClockOverrunException extends Exception {
   }
}
