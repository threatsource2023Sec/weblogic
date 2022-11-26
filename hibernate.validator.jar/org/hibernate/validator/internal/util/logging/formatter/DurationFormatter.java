package org.hibernate.validator.internal.util.logging.formatter;

import java.time.Duration;

public class DurationFormatter {
   private final String stringRepresentation;

   public DurationFormatter(Duration duration) {
      if (Duration.ZERO.equals(duration)) {
         this.stringRepresentation = "0";
      } else {
         long seconds = duration.getSeconds();
         long days = seconds / 86400L;
         long hours = seconds / 3600L % 24L;
         long minutes = seconds / 60L % 60L;
         int millis = duration.getNano() / 1000000;
         int nanos = duration.getNano() % 1000000;
         StringBuilder formattedDuration = new StringBuilder();
         this.appendTimeUnit(formattedDuration, days, "days", "day");
         this.appendTimeUnit(formattedDuration, hours, "hours", "hour");
         this.appendTimeUnit(formattedDuration, minutes, "minutes", "minute");
         this.appendTimeUnit(formattedDuration, seconds % 60L, "seconds", "second");
         this.appendTimeUnit(formattedDuration, (long)millis, "milliseconds", "millisecond");
         this.appendTimeUnit(formattedDuration, (long)nanos, "nanoseconds", "nanosecond");
         this.stringRepresentation = formattedDuration.toString();
      }

   }

   private void appendTimeUnit(StringBuilder sb, long number, String pluralLabel, String singularLabel) {
      if (number != 0L) {
         if (sb.length() > 0) {
            sb.append(" ");
         }

         sb.append(number).append(" ").append(number == 1L ? singularLabel : pluralLabel);
      }
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
