package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class DayTimeDurationAttribute extends AttributeValue {
   private static final long HOURS_PER_DAY = 24L;
   private static final long MINUTES_PER_HOUR = 60L;
   private static final long SECONDS_PER_MINUTE = 60L;
   private static final long MILLIS_PER_SECOND = 1000L;
   private static final long NANOS_PER_MILLI = 1000000L;
   private static final long NANOS_PER_SECOND = 1000000000L;
   private static final int DAY_ALLOWED = 1;
   private static final int HOUR_ALLOWED = 2;
   private static final int MINUTE_ALLOWED = 4;
   private static final int SECOND_ALLOWED = 8;
   private boolean isDurationNegative;
   private int day;
   private int hour;
   private int minute;
   private int second;
   private long nanoseconds;
   private Long value;

   public DayTimeDurationAttribute(int hour, int minute, int second) throws InvalidAttributeException {
      this(0, hour, minute, second, 0L);
   }

   public DayTimeDurationAttribute(int day, int hour, int minute, int second) throws InvalidAttributeException {
      this(day, hour, minute, second, 0L);
   }

   public DayTimeDurationAttribute(int day, int hour, int minute, int second, long nanoseconds) throws InvalidAttributeException {
      this.isDurationNegative = day < 0;
      this.day = Math.abs(day);
      if (hour < 0) {
         this.isDurationNegative = true;
         if (day != 0) {
            throw new InvalidAttributeException("Hour cannot be negative if day is non-zero value");
         }
      }

      this.hour = Math.abs(hour);
      if (minute < 0) {
         this.isDurationNegative = true;
         if (hour != 0 || day != 0) {
            throw new InvalidAttributeException("Minute cannot be negative if either hour or day are non-zero values");
         }
      }

      this.minute = Math.abs(minute);
      if (second < 0) {
         this.isDurationNegative = true;
         if (hour != 0 || day != 0 || minute != 0) {
            throw new InvalidAttributeException("Second cannot be negative if either hour, day, or minute are non-zero values");
         }
      }

      this.second = Math.abs(second);
      if (nanoseconds < 0L) {
         this.isDurationNegative = true;
         if (hour != 0 || day != 0 || minute != 0 || second != 0) {
            throw new InvalidAttributeException("Nanoseconds cannot be negative if either hour, day, minute, or second are non-zero values");
         }
      }

      this.nanoseconds = Math.abs(nanoseconds);
      this.canonicalize();
   }

   public DayTimeDurationAttribute(String value) throws InvalidAttributeException {
      this.day = 0;
      this.hour = 0;
      this.minute = 0;
      this.second = 0;
      this.nanoseconds = 0L;
      int idx = 0;
      this.checkLength(idx, 1, value);
      this.isDurationNegative = value.charAt(idx) == '-';
      if (this.isDurationNegative) {
         ++idx;
      }

      this.checkLength(idx, 1, value);
      if (value.charAt(idx++) != 'P') {
         this.throwInvalidAttributeException();
      }

      this.checkLength(idx, 1, value);
      boolean parsedT = value.charAt(idx) == 'T';
      byte numberGuard;
      if (parsedT) {
         ++idx;
         numberGuard = 14;
      } else {
         numberGuard = 1;
      }

      do {
         int endOfNumber;
         for(endOfNumber = idx; endOfNumber < value.length() && Character.isDigit(value.charAt(endOfNumber)); ++endOfNumber) {
         }

         if (endOfNumber == idx || endOfNumber == value.length()) {
            this.throwInvalidAttributeException();
         }

         int number = Integer.parseInt(value.substring(idx, endOfNumber));
         if (number < 0) {
            this.throwInvalidAttributeException();
         }

         idx = endOfNumber;
         this.checkLength(endOfNumber, 1, value);
         char token = value.charAt(endOfNumber);
         switch (token) {
            case '.':
               if ((numberGuard & 8) == 0) {
                  this.throwInvalidAttributeException();
               }

               idx = endOfNumber + 1;

               for(endOfNumber = idx; endOfNumber < value.length() && Character.isDigit(value.charAt(endOfNumber)); ++endOfNumber) {
               }

               if (endOfNumber == idx || endOfNumber == value.length()) {
                  this.throwInvalidAttributeException();
               }

               String nanoString;
               for(nanoString = value.substring(idx, endOfNumber); nanoString.length() < 9; nanoString = nanoString + "0") {
               }

               if (nanoString.length() > 9) {
                  nanoString = nanoString.substring(0, 9);
               }

               this.nanoseconds = Long.parseLong(nanoString);
               idx = endOfNumber;
               this.checkLength(endOfNumber, 1, value);
               if (value.charAt(endOfNumber) != 'S') {
                  this.throwInvalidAttributeException();
               }
            case 'S':
               if ((numberGuard & 8) == 0) {
                  this.throwInvalidAttributeException();
               }

               this.second = number;
               ++idx;
               if (idx < value.length()) {
                  this.throwInvalidAttributeException();
               }
               break;
            case 'D':
               if ((numberGuard & 1) == 0) {
                  this.throwInvalidAttributeException();
               }

               this.day = number;
               idx = endOfNumber + 1;
               if (idx < value.length()) {
                  if (value.charAt(idx++) != 'T') {
                     this.throwInvalidAttributeException();
                  }

                  numberGuard = 14;
               }
               break;
            case 'H':
               if ((numberGuard & 2) == 0) {
                  this.throwInvalidAttributeException();
               }

               this.hour = number;
               idx = endOfNumber + 1;
               numberGuard = 12;
               break;
            case 'M':
               if ((numberGuard & 4) == 0) {
                  this.throwInvalidAttributeException();
               }

               this.minute = number;
               idx = endOfNumber + 1;
               numberGuard = 8;
               break;
            default:
               this.throwInvalidAttributeException();
         }
      } while(idx < value.length());

      if (this.day == 0 && this.hour == 0 && this.minute == 0 && this.second == 0 && this.nanoseconds == 0L) {
         throw new InvalidAttributeException("At least one of day, hour, minute and second must be non-zero.");
      } else {
         this.canonicalize();
      }
   }

   private void checkLength(int idx, int length, String value) throws InvalidAttributeException {
      if (idx + (length - 1) >= value.length()) {
         this.throwInvalidAttributeException();
      }

   }

   private void throwInvalidAttributeException() throws InvalidAttributeException {
      throw new InvalidAttributeException("dayTimeDuration value does not conform to: [-]?P(\\p{Nd}D(T(\\p{Nd}+(H(\\p{Nd}+(M(\\p{Nd}+(\\.\\p{Nd}*)?S|\\.\\p{Nd}+S)?|(\\.\\p{Nd}*)?S)|(\\.\\p{Nd}*)?S)?|M(\\p{Nd}+(\\.\\p{Nd}*)?S|\\.\\p{Nd}+S)?|(\\.\\p{Nd}*)?S)|\\.\\p{Nd}+S))?|T(\\p{Nd}+(H(\\p{Nd}+(M(\\p{Nd}+(\\.\\p{Nd}*)?S|\\.\\p{Nd}+S)?|(\\.\\p{Nd}*)?S)|(\\.\\p{Nd}*)?S)?|M(\\p{Nd}+(\\.\\p{Nd}*)?S|\\.\\p{Nd}+S)?|(\\.\\p{Nd}*)?S)|\\.\\p{Nd}+S))");
   }

   public Type getType() {
      return Type.DAY_TIME_DURATION;
   }

   public String getValue() {
      return this.toString();
   }

   public Long getDuration() {
      return this.value;
   }

   public int getSeconds() {
      return (int)(this.value / 1000000000L);
   }

   public long getNanoseconds() {
      return this.value % 1000000000L;
   }

   public int compareTo(DayTimeDurationAttribute other) {
      return this.value.compareTo(other.value);
   }

   private void canonicalize() {
      if (this.nanoseconds > 1000000000L) {
         this.second = (int)((long)this.second + this.nanoseconds / 1000000000L);
         this.nanoseconds %= 1000000000L;
      }

      if ((long)this.second >= 60L) {
         this.minute = (int)((long)this.minute + (long)this.second / 60L);
         this.second = (int)((long)this.second % 60L);
      }

      if ((long)this.minute >= 60L) {
         this.hour = (int)((long)this.hour + (long)this.minute / 60L);
         this.minute = (int)((long)this.minute % 60L);
      }

      if ((long)this.hour >= 24L) {
         this.day = (int)((long)this.day + (long)this.hour / 24L);
         this.hour = (int)((long)this.hour % 24L);
      }

      this.value = new Long(((((long)this.day * 24L + (long)this.hour) * 60L + (long)this.minute) * 60L + (long)this.second) * 1000000000L + this.nanoseconds);
      this.value = new Long(this.isDurationNegative ? -1L * this.value : this.value);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.isDurationNegative) {
         sb.append('-');
      }

      sb.append('P');
      boolean isWritten = false;
      if (this.day != 0) {
         sb.append(String.valueOf(this.day));
         sb.append('D');
         isWritten = true;
      }

      boolean isTWritten = false;
      if (this.hour != 0) {
         if (!isTWritten) {
            sb.append('T');
            isTWritten = true;
         }

         sb.append(String.valueOf(this.hour));
         sb.append('H');
         isWritten = true;
      }

      if (this.minute != 0) {
         if (!isTWritten) {
            sb.append('T');
            isTWritten = true;
         }

         sb.append(String.valueOf(this.minute));
         sb.append('M');
         isWritten = true;
      }

      if (!isWritten || this.second != 0 || this.nanoseconds != 0L) {
         if (!isTWritten) {
            sb.append('T');
         }

         sb.append(String.valueOf(this.second));
         if (this.nanoseconds != 0L) {
            String nanoString = String.valueOf(this.nanoseconds);

            int lastIndex;
            for(lastIndex = nanoString.length() - 1; lastIndex >= 0 && nanoString.charAt(lastIndex) == '0'; --lastIndex) {
            }

            sb.append('.');
            sb.append(nanoString.subSequence(0, lastIndex + 1));
         }

         sb.append('S');
      }

      return sb.toString();
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DayTimeDurationAttribute)) {
         return false;
      } else {
         DayTimeDurationAttribute other = (DayTimeDurationAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.intValue();
   }

   public boolean add(DayTimeDurationAttribute o) {
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

         public DayTimeDurationAttribute next() {
            this.nextNotCalled = false;
            return DayTimeDurationAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
