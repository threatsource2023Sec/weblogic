package weblogic.entitlement.rules;

public final class TimeOfDay implements Comparable {
   public int MS_PER_MINUTE = 60000;
   public int MS_PER_HOUR;
   public int MS_PER_DAY;
   private int time;

   public TimeOfDay(int time) {
      this.MS_PER_HOUR = 60 * this.MS_PER_MINUTE;
      this.MS_PER_DAY = 24 * this.MS_PER_HOUR;
      this.time = 0;
      if (time >= 0 && time < this.MS_PER_DAY) {
         this.time = time;
      } else {
         throw new IllegalArgumentException("Time of day must be >= 0 and < " + this.MS_PER_DAY);
      }
   }

   public TimeOfDay(int hours, int minutes, int seconds) {
      this.MS_PER_HOUR = 60 * this.MS_PER_MINUTE;
      this.MS_PER_DAY = 24 * this.MS_PER_HOUR;
      this.time = 0;
      if (hours >= 0 && hours <= 23) {
         if (minutes >= 0 && minutes <= 59) {
            if (seconds >= 0 && seconds <= 59) {
               this.time = hours * this.MS_PER_HOUR + minutes * this.MS_PER_MINUTE + seconds * 1000;
            } else {
               throw new IllegalArgumentException("Illegal seconds value " + seconds);
            }
         } else {
            throw new IllegalArgumentException("Illegal minutes value " + minutes);
         }
      } else {
         throw new IllegalArgumentException("Illegal hours value " + hours);
      }
   }

   public int getTime() {
      return this.time;
   }

   public int getHours() {
      return this.time / this.MS_PER_HOUR;
   }

   public int getMinutes() {
      return this.time % this.MS_PER_HOUR / this.MS_PER_MINUTE;
   }

   public int getSeconds() {
      return this.time % this.MS_PER_MINUTE / 1000;
   }

   public boolean equals(Object o) {
      return o == this || o instanceof TimeOfDay && this.time == ((TimeOfDay)o).getTime();
   }

   public int hashCode() {
      return this.time;
   }

   public int compareTo(Object o) {
      int time = ((TimeOfDay)o).getTime();
      return this.time < time ? -1 : (this.time > time ? 1 : 0);
   }
}
