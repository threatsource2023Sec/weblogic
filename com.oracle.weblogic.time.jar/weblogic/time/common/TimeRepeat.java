package weblogic.time.common;

import java.util.Date;
import weblogic.common.T3MiscLogger;

/** @deprecated */
@Deprecated
public class TimeRepeat implements Schedulable {
   private long intervalMillis = 0L;
   private long lasttime = 0L;
   private long nexttime = 0L;
   private long firstTime = 0L;

   public TimeRepeat() {
   }

   /** @deprecated */
   @Deprecated
   public TimeRepeat(int intervalMillis) {
      if (intervalMillis < 0) {
         throw new IllegalArgumentException("Argument cannot be negative(or rolled over ) " + intervalMillis);
      } else {
         this.intervalMillis = (long)intervalMillis;
      }
   }

   /** @deprecated */
   @Deprecated
   public TimeRepeat(long intervalMillis) {
      this.intervalMillis = intervalMillis;
   }

   /** @deprecated */
   @Deprecated
   public long lastTime() {
      return this.lasttime;
   }

   /** @deprecated */
   @Deprecated
   public long schedule(long currentTimeMillis) {
      this.lasttime = currentTimeMillis;
      if (this.nexttime == 0L && this.firstTime != 0L) {
         if (this.firstTime < currentTimeMillis) {
            try {
               T3MiscLogger.logPastTime((new Date(this.firstTime)).toString());
            } catch (Exception var4) {
            }
         }

         this.nexttime = this.firstTime;
         return this.firstTime;
      } else {
         if (this.nexttime == 0L) {
            this.nexttime = currentTimeMillis;
         }

         do {
            this.nexttime += this.intervalMillis;
         } while(this.nexttime < currentTimeMillis);

         return this.nexttime;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setFirstScheduleTime(long firstScheduleTimeInMillis) {
      this.firstTime = firstScheduleTimeInMillis;
   }

   /** @deprecated */
   @Deprecated
   public long getFirstScheduleTime() {
      return this.firstTime;
   }
}
