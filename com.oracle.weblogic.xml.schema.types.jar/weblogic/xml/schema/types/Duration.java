package weblogic.xml.schema.types;

import java.math.BigInteger;

public class Duration {
   private int signum = 1;
   private BigInteger years;
   private BigInteger months;
   private BigInteger days;
   private BigInteger hours;
   private BigInteger minutes;
   private BigInteger seconds;
   private BigInteger fraction;

   public Duration() {
   }

   public Duration(int signum, BigInteger years, BigInteger months, BigInteger days, BigInteger hours, BigInteger minutes, BigInteger seconds, BigInteger fraction) {
      this.signum = signum;
      this.years = years;
      this.months = months;
      this.days = days;
      this.hours = hours;
      this.minutes = minutes;
      this.seconds = seconds;
      this.fraction = fraction;
   }

   public Duration(Duration d) {
      this.signum = d.signum;
      this.years = d.years;
      this.months = d.months;
      this.days = d.days;
      this.hours = d.hours;
      this.minutes = d.minutes;
      this.seconds = d.seconds;
      this.fraction = d.fraction;
   }

   public void setSignum(int s) {
      if (s != -1 && s != 1) {
         throw new IllegalArgumentException();
      } else {
         this.signum = s;
      }
   }

   public void setYears(BigInteger y) {
      if (y != null && y.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.years = y;
      }
   }

   public void setMonths(BigInteger m) {
      if (m != null && m.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.months = m;
      }
   }

   public void setDays(BigInteger d) {
      if (d != null && d.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.days = d;
      }
   }

   public void setHours(BigInteger h) {
      if (h != null && h.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.hours = h;
      }
   }

   public void setMinutes(BigInteger m) {
      if (m != null && m.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.minutes = m;
      }
   }

   public void setSeconds(BigInteger s) {
      if (s != null && s.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.seconds = s;
      }
   }

   public void setSecondFraction(BigInteger f) {
      if (f != null && f.signum() == -1) {
         throw new IllegalArgumentException();
      } else {
         this.fraction = f;
      }
   }

   public BigInteger getYears() {
      return this.years;
   }

   public BigInteger getMonths() {
      return this.months;
   }

   public BigInteger getDays() {
      return this.days;
   }

   public BigInteger getHours() {
      return this.hours;
   }

   public BigInteger getMinutes() {
      return this.minutes;
   }

   public BigInteger getSeconds() {
      return this.seconds;
   }

   public BigInteger getSecondFraction() {
      return this.fraction;
   }

   public int getSignum() {
      return this.signum;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof Duration) {
         Duration d = (Duration)o;
         if (this.years == null && d.getYears() == null || this.years != null && this.years.equals(d.getYears())) {
            if (this.months == null && d.getMonths() == null || this.months != null && this.months.equals(d.getMonths())) {
               if (this.days == null && d.getDays() == null || this.days != null && this.days.equals(d.getDays())) {
                  if (this.hours == null && d.getHours() == null || this.hours != null && this.hours.equals(d.getHours())) {
                     if ((this.minutes != null || d.getMinutes() != null) && (this.minutes == null || !this.minutes.equals(d.getMinutes()))) {
                        return false;
                     } else if (this.seconds == null && d.getSeconds() == null || this.seconds != null && this.seconds.equals(d.getSeconds())) {
                        return this.fraction == null && d.getSecondFraction() == null || this.fraction != null && this.fraction.equals(d.getSecondFraction());
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hash = 0;
      if (this.years != null) {
         hash ^= this.years.hashCode();
      }

      if (this.months != null) {
         hash ^= this.months.hashCode();
      }

      if (this.days != null) {
         hash ^= this.days.hashCode();
      }

      if (this.hours != null) {
         hash ^= this.days.hashCode();
      }

      if (this.minutes != null) {
         hash ^= this.minutes.hashCode();
      }

      if (this.seconds != null) {
         hash ^= this.seconds.hashCode();
      }

      if (this.fraction != null) {
         hash ^= this.fraction.hashCode();
      }

      return hash * this.signum;
   }

   public String toString() {
      return "Duration{signum=" + this.signum + " years=" + this.years + " months=" + this.months + " days=" + this.days + " hours=" + this.hours + " minutes=" + this.minutes + " seconds=" + this.seconds + " fraction=" + this.fraction + "}";
   }
}
