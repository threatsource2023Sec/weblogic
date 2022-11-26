package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimeZone;

public class DateAttribute extends AttributeValue {
   private static final long MINUTES_PER_HOUR = 60L;
   private static final long SECONDS_PER_MINUTE = 60L;
   private static final long MILLIS_PER_SECOND = 1000L;
   private static final long MILLIS_PER_MINUTE = 60000L;
   private static final long MILLIS_PER_HOUR = 3600000L;
   private Calendar value;
   private boolean isTimeZoneExplicit;

   public DateAttribute(Calendar value) {
      this(value, true);
   }

   public DateAttribute(Calendar value, boolean isTimeZoneExplicit) {
      this.value = (Calendar)value.clone();
      this.value.set(10, 0);
      this.value.set(12, 0);
      this.value.set(13, 0);
      this.value.set(14, 0);
      this.isTimeZoneExplicit = isTimeZoneExplicit;
   }

   public Type getType() {
      return Type.DATE;
   }

   public DateAttribute(String value) throws InvalidAttributeException {
      this(value, false);
   }

   public DateAttribute(String value, boolean lenient) throws InvalidAttributeException {
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
      if (nextDash != idx + 2) {
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

      this.checkLength(nextDash, 3, value);
      idx = nextDash + 1;
      nextDash = idx + 2;

      for(i = idx; i < nextDash; ++i) {
         next = value.charAt(i);
         if (!Character.isDigit(next)) {
            this.throwInvalidAttributeException();
         }
      }

      if (nextDash < value.length() && Character.isDigit(value.charAt(nextDash))) {
         this.throwInvalidAttributeException();
      }

      int day = Integer.parseInt(value.substring(idx, nextDash));
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
      cal.set(11, 0);
      cal.set(12, 0);
      cal.set(13, 0);
      cal.set(14, 0);
      this.value = cal;
   }

   private void checkLength(int idx, int length, String value) throws InvalidAttributeException {
      if (idx + (length - 1) >= value.length()) {
         this.throwInvalidAttributeException();
      }

   }

   private void throwInvalidAttributeException() throws InvalidAttributeException {
      throw new InvalidAttributeException("date value does not conform to: '-'? yyyy '-' mm '-' dd (zzzzzz)?");
   }

   public Calendar getValue() {
      return this.value;
   }

   public int compareTo(DateAttribute other) {
      long t = this.value.getTimeInMillis();
      long ot = other.value.getTimeInMillis();
      return t == ot ? 0 : (t < ot ? -1 : 1);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      String yearString = String.valueOf(this.value.get(1));
      int ysLength = yearString.length();

      while(ysLength++ < 4) {
         sb.append('0');
      }

      sb.append(yearString);
      sb.append('-');
      String monthString = String.valueOf(this.value.get(2) + 1);
      if (monthString.length() < 2) {
         sb.append('0');
      }

      sb.append(monthString);
      sb.append('-');
      String dayString = String.valueOf(this.value.get(5));
      if (dayString.length() < 2) {
         sb.append('0');
      }

      sb.append(dayString);
      if (this.isTimeZoneExplicit) {
         int offset = this.value.get(15);
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
            String zoneHoursStr = String.valueOf(zoneHours);
            if (zoneHoursStr.length() < 2) {
               sb.append('0');
            }

            sb.append(zoneHoursStr);
            sb.append(':');
            offset = Math.abs(offset);
            offset = (int)((long)offset - (long)zoneHours * 3600000L);
            String zoneMinutesStr = String.valueOf(offset / '\uea60');
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
      } else if (!(o instanceof DateAttribute)) {
         return false;
      } else {
         DateAttribute other = (DateAttribute)o;
         long t = this.value.getTimeInMillis();
         long ot = other.value.getTimeInMillis();
         return t == ot;
      }
   }

   public int internalHashCode() {
      return this.value.getTime().hashCode();
   }

   public boolean add(DateAttribute o) {
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

         public DateAttribute next() {
            this.nextNotCalled = false;
            return DateAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
