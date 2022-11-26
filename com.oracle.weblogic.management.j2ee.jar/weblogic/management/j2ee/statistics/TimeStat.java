package weblogic.management.j2ee.statistics;

import java.io.Serializable;

public class TimeStat implements Serializable {
   private long startTime;
   private long lastSampleTime;

   public TimeStat(long startTime, long lastSampleTime) throws StatException {
      String exceptionString = "";
      if (startTime <= 0L) {
         exceptionString = exceptionString + "Start time is less than 0. Start time = " + startTime;
      }

      if (lastSampleTime <= 0L) {
         exceptionString = exceptionString + "\n Last sample time is less than 0. Last sample time = " + lastSampleTime;
      }

      if (exceptionString.length() > 0) {
         throw new StatException(exceptionString);
      } else {
         this.startTime = startTime;
         this.lastSampleTime = lastSampleTime;
      }
   }

   public synchronized void setLastSampleTime(long lastSampleTime) throws StatException {
      if (lastSampleTime <= 0L) {
         throw new StatException("Last sample time is less than 0. Last sample time = " + lastSampleTime);
      } else {
         this.lastSampleTime = lastSampleTime;
      }
   }

   public long getStartTime() {
      return this.startTime;
   }

   public synchronized long getLastSampleTime() {
      return this.lastSampleTime;
   }
}
