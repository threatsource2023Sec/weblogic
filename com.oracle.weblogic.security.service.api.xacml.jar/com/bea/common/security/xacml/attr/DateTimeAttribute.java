package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimeZone;

public class DateTimeAttribute extends AttributeValue {
   private static final long MINUTES_PER_HOUR = 60L;
   private static final long SECONDS_PER_MINUTE = 60L;
   private static final long MILLIS_PER_SECOND = 1000L;
   private static final long NANOS_PER_MILLI = 1000000L;
   private static final long NANOS_PER_SECOND = 1000000000L;
   private static final long MILLIS_PER_MINUTE = 60000L;
   private static final long MILLIS_PER_HOUR = 3600000L;
   private Calendar value;
   private boolean isTimeZoneExplicit;
   private boolean isHour24;
   private long nanoseconds;

   public DateTimeAttribute(Calendar value) {
      this(value, 0L);
   }

   public DateTimeAttribute(Calendar value, long nanoseconds) {
      this(value, nanoseconds, true);
   }

   public DateTimeAttribute(Calendar value, long nanoseconds, boolean isTimeZoneExplicit) {
      this.value = (Calendar)value.clone();
      this.nanoseconds = this.combineNanoseconds(this.value, nanoseconds);
      this.isTimeZoneExplicit = isTimeZoneExplicit;
      this.isHour24 = false;
   }

   public DateTimeAttribute(String value) throws InvalidAttributeException {
      this(value, false);
   }

   public DateTimeAttribute(String value, boolean lenient) throws InvalidAttributeException {
      int idx = 0;
      this.checkLength(idx, 1, value);
      if (value.charAt(idx) == '+' && lenient) {
         ++idx;
      }

      boolean yearNegative = value.charAt(idx) == '-';
      if (yearNegative) {
         ++idx;
      }

      int nextDash = value.indexOf(45, idx);
      if (nextDash < idx + 4) {
         this.throwInvalidAttributeException();
      }

      if (nextDash > idx + 4 && value.charAt(idx) == '0') {
         this.throwInvalidAttributeException();
      }

      int i;
      char next;
      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      int year = Integer.parseInt(value.substring(idx, nextDash));
      if (yearNegative) {
         year = -1 * year;
      }

      this.checkLength(nextDash, 1, value);
      idx = nextDash + 1;
      nextDash = value.indexOf(45, idx);
      if (!lenient && nextDash != idx + 2) {
         this.throwInvalidAttributeException();
      }

      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      int month = Integer.parseInt(value.substring(idx, nextDash));
      if (month < 1 || month > 12) {
         this.throwInvalidAttributeException();
      }

      this.checkLength(nextDash, 1, value);
      idx = nextDash + 1;
      nextDash = value.indexOf(84, idx);
      if (!lenient && nextDash != idx + 2) {
         this.throwInvalidAttributeException();
      }

      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      int day = Integer.parseInt(value.substring(idx, nextDash));
      this.checkLength(nextDash, 1, value);
      idx = nextDash + 1;
      nextDash = value.indexOf(58, idx);
      if (!lenient && nextDash != idx + 2) {
         this.throwInvalidAttributeException();
      }

      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      int hour = Integer.parseInt(value.substring(idx, nextDash));
      this.isHour24 = hour == 24;
      if (hour < 0 || hour > 24) {
         this.throwInvalidAttributeException();
      }

      this.checkLength(nextDash, 1, value);
      idx = nextDash + 1;
      nextDash = value.indexOf(58, idx);
      if (!lenient && nextDash != idx + 2) {
         this.throwInvalidAttributeException();
      }

      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      int minute = Integer.parseInt(value.substring(idx, nextDash));
      if (minute < 0 || minute > 59) {
         this.throwInvalidAttributeException();
      }

      if (this.isHour24 && minute != 0) {
         this.throwInvalidAttributeException();
      }

      this.checkLength(nextDash, lenient ? 2 : 3, value);
      idx = nextDash + 1;
      nextDash = idx + 2;

      for(i = idx; i < nextDash; ++i) {
         if (lenient && i >= value.length()) {
            --nextDash;
         } else {
            next = value.charAt(i);
            if (!Character.isDigit(next)) {
               if (lenient && i + 1 == nextDash) {
                  --nextDash;
               } else {
                  this.throwInvalidAttributeException();
               }
            }
         }
      }

      if (nextDash < value.length() && Character.isDigit(value.charAt(nextDash))) {
         this.throwInvalidAttributeException();
      }

      int second = Integer.parseInt(value.substring(idx, nextDash));
      if (second < 0 || second > 59) {
         this.throwInvalidAttributeException();
      }

      if (this.isHour24 && second != 0) {
         this.throwInvalidAttributeException();
      }

      this.nanoseconds = 0L;
      if (nextDash < value.length()) {
         char next = value.charAt(nextDash);
         if (next == '.') {
            this.checkLength(nextDash, 1, value);
            idx = nextDash + 1;

            while(idx < value.length() && Character.isDigit(value.charAt(idx++))) {
            }

            String nanoString;
            for(nanoString = value.substring(nextDash + 1, idx - 1); nanoString.length() < 9; nanoString = nanoString + "0") {
            }

            if (nanoString.length() > 9) {
               nanoString = nanoString.substring(0, 9);
            }

            this.nanoseconds = Long.parseLong(nanoString);
            if (this.isHour24 && this.nanoseconds != 0L) {
               this.throwInvalidAttributeException();
            }

            nextDash = idx - 1;
         }
      }

      this.isTimeZoneExplicit = false;
      TimeZone tz = null;
      if (nextDash < value.length()) {
         next = value.charAt(nextDash);
         switch (next) {
            case '+':
            case '-':
               this.checkLength(nextDash, 6, value);
               if (!Character.isDigit(value.charAt(nextDash + 1)) || !Character.isDigit(value.charAt(nextDash + 2)) || value.charAt(nextDash + 3) != ':' || !Character.isDigit(value.charAt(nextDash + 4)) || !Character.isDigit(value.charAt(nextDash + 5))) {
                  this.throwInvalidAttributeException();
               }

               int tzHour = Integer.parseInt(value.substring(nextDash + 1, nextDash + 3));
               if (tzHour <= 0 || tzHour > 14) {
                  this.throwInvalidAttributeException();
               }

               int tzMinute = Integer.parseInt(value.substring(nextDash + 4, nextDash + 6));
               if (tzMinute < 0 || tzMinute > 59 || tzHour == 14 && tzMinute != 0) {
                  this.throwInvalidAttributeException();
               }

               tz = TimeZone.getTimeZone("GMT" + value.substring(nextDash, nextDash + 6));
               nextDash += 6;
               break;
            case 'Z':
               tz = TimeZone.getTimeZone("GMT");
               ++nextDash;
               break;
            default:
               this.throwInvalidAttributeException();
         }

         this.isTimeZoneExplicit = true;
         if (nextDash < value.length()) {
            this.throwInvalidAttributeException();
         }
      }

      --month;
      Calendar cal = tz != null ? Calendar.getInstance(tz) : Calendar.getInstance();
      cal.set(1, year);
      if (cal.getActualMinimum(2) > month || cal.getActualMaximum(2) < month) {
         this.throwInvalidAttributeException();
      }

      cal.set(2, month);
      if (cal.getActualMinimum(5) > day || cal.getActualMaximum(5) < day) {
         this.throwInvalidAttributeException();
      }

      cal.set(5, day);
      cal.set(11, hour);
      cal.set(12, minute);
      cal.set(13, second);
      cal.set(14, 0);
      this.value = cal;
   }

   private void checkLength(int idx, int length, String value) throws InvalidAttributeException {
      if (idx + (length - 1) >= value.length()) {
         this.throwInvalidAttributeException();
      }

   }

   private void throwInvalidAttributeException() throws InvalidAttributeException {
      throw new InvalidAttributeException("dateTime value does not conform to: '-'? yyyy '-' mm '-' dd 'T' hh ':' mm ':' ss ('.' s+)? (zzzzzz)?");
   }

   private long combineNanoseconds(Calendar value, long nanoseconds) {
      int millis = value.get(14);
      if (millis == 0) {
         return this.canonicalizeNanoseconds(value, nanoseconds);
      } else {
         value.set(14, 0);
         nanoseconds += (long)millis * 1000000L;
         return this.canonicalizeNanoseconds(value, nanoseconds);
      }
   }

   private long canonicalizeNanoseconds(Calendar value, long nanoseconds) {
      int secs;
      if (nanoseconds >= 1000000000L) {
         secs = (int)(nanoseconds % 1000000000L);
         value.add(13, secs);
         nanoseconds /= 1000000000L;
      } else if (nanoseconds < 0L) {
         secs = (int)(nanoseconds / 1000000000L - 1L);
         value.add(13, secs);
         nanoseconds = nanoseconds * -1L / 1000000000L;
      }

      return nanoseconds;
   }

   public Type getType() {
      return Type.DATE_TIME;
   }

   public Calendar getValue() {
      return this.value;
   }

   public long getNanoseconds() {
      return this.nanoseconds;
   }

   public int compareTo(DateTimeAttribute other) {
      long t = this.value.getTimeInMillis();
      long ot = other.value.getTimeInMillis();
      int res = t == ot ? 0 : (t < ot ? -1 : 1);
      if (res == 0) {
         if (this.nanoseconds == other.nanoseconds) {
            return 0;
         } else {
            return this.nanoseconds < other.nanoseconds ? -1 : 1;
         }
      } else {
         return res;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      Calendar c = (Calendar)this.value.clone();
      if (this.isHour24) {
         c.roll(5, false);
      }

      String yearString = String.valueOf(c.get(1));
      int ysLength = yearString.length();

      while(ysLength++ < 4) {
         sb.append('0');
      }

      sb.append(yearString);
      sb.append('-');
      String monthString = String.valueOf(c.get(2) + 1);
      if (monthString.length() < 2) {
         sb.append('0');
      }

      sb.append(monthString);
      sb.append('-');
      String dayString = String.valueOf(c.get(5));
      if (dayString.length() < 2) {
         sb.append('0');
      }

      sb.append(dayString);
      sb.append('T');
      String zoneHoursStr;
      String zoneMinutesStr;
      if (this.isHour24) {
         sb.append("24:00:00");
      } else {
         String hourString = String.valueOf(c.get(11));
         if (hourString.length() < 2) {
            sb.append('0');
         }

         sb.append(hourString);
         sb.append(':');
         String minuteString = String.valueOf(c.get(12));
         if (minuteString.length() < 2) {
            sb.append('0');
         }

         sb.append(minuteString);
         sb.append(':');
         zoneHoursStr = String.valueOf(c.get(13));
         if (zoneHoursStr.length() < 2) {
            sb.append('0');
         }

         sb.append(zoneHoursStr);
         if (this.nanoseconds != 0L) {
            zoneMinutesStr = String.valueOf(this.nanoseconds);

            int lastIndex;
            for(lastIndex = zoneMinutesStr.length() - 1; lastIndex >= 0 && zoneMinutesStr.charAt(lastIndex) == '0'; --lastIndex) {
            }

            sb.append('.');
            sb.append(zoneMinutesStr.subSequence(0, lastIndex + 1));
         }
      }

      if (this.isTimeZoneExplicit) {
         int offset = c.get(15);
         if (offset == 0) {
            sb.append('Z');
         } else {
            if (offset < 0) {
               sb.append('-');
               offset = Math.abs(offset);
            } else {
               sb.append('+');
            }

            int zoneHours = offset / 3600000;
            zoneHoursStr = String.valueOf(zoneHours);
            if (zoneHoursStr.length() < 2) {
               sb.append('0');
            }

            sb.append(zoneHoursStr);
            sb.append(':');
            offset = Math.abs(offset);
            offset = (int)((long)offset - (long)zoneHours * 3600000L);
            zoneMinutesStr = String.valueOf(offset / '\uea60');
            if (zoneMinutesStr.length() < 2) {
               sb.append('0');
            }

            sb.append(zoneMinutesStr);
         }
      }

      return sb.toString();
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DateTimeAttribute)) {
         return false;
      } else {
         DateTimeAttribute other = (DateTimeAttribute)o;
         long t = this.value.getTimeInMillis();
         long ot = other.value.getTimeInMillis();
         return t == ot && this.nanoseconds == other.nanoseconds;
      }
   }

   public int internalHashCode() {
      return this.value.getTime().hashCode();
   }

   public boolean add(DateTimeAttribute o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return new Iterator() {
         boolean nextNotCalled = true;

         public boolean hasNext() {
            return this.nextNotCalled;
         }

         public DateTimeAttribute next() {
            this.nextNotCalled = false;
            return DateTimeAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
