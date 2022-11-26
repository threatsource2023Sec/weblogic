package com.bea.common.security.xacml.attr;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class YearMonthDurationAttribute extends AttributeValue {
   private static final int MONTHS_PER_YEAR = 12;
   private boolean isDurationNegative;
   private int year;
   private int month;
   private Integer value;

   public YearMonthDurationAttribute(int year, int month) throws InvalidAttributeException {
      this.isDurationNegative = year < 0;
      this.year = Math.abs(year);
      if (month < 0) {
         this.isDurationNegative = true;
         if (year != 0) {
            throw new InvalidAttributeException(ApiLogger.getNegativeMonthNonZeroYear());
         }
      }

      this.month = Math.abs(month);
      if (year == 0 && month == 0) {
         throw new InvalidAttributeException(ApiLogger.getMonthOrYearBothZero());
      } else {
         this.value = new Integer(year * 12 + month);
         this.value = new Integer(this.isDurationNegative ? -1 * this.value : this.value);
      }
   }

   public YearMonthDurationAttribute(String value) throws InvalidAttributeException {
      this.year = 0;
      this.month = 0;
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

      int endOfNumber;
      for(endOfNumber = idx; endOfNumber < value.length() && Character.isDigit(value.charAt(endOfNumber)); ++endOfNumber) {
      }

      if (endOfNumber == idx || endOfNumber == value.length()) {
         this.throwInvalidAttributeException();
      }

      int number = Integer.parseInt(value.substring(idx, endOfNumber));
      idx = endOfNumber;
      this.checkLength(endOfNumber, 1, value);
      char token = value.charAt(endOfNumber);
      switch (token) {
         case 'Y':
            this.year = number;
            idx = endOfNumber + 1;
            if (idx >= value.length()) {
               break;
            }

            for(endOfNumber = idx; endOfNumber < value.length() && Character.isDigit(value.charAt(endOfNumber)); ++endOfNumber) {
            }

            if (endOfNumber == idx || endOfNumber == value.length()) {
               this.throwInvalidAttributeException();
            }

            number = Integer.parseInt(value.substring(idx, endOfNumber));
            idx = endOfNumber;
            this.checkLength(endOfNumber, 1, value);
            if (value.charAt(endOfNumber) != 'M') {
               this.throwInvalidAttributeException();
            }
         case 'M':
            this.month = number;
            ++idx;
            if (idx != value.length()) {
               this.throwInvalidAttributeException();
            }
            break;
         default:
            this.throwInvalidAttributeException();
      }

      if (this.year == 0 && this.month == 0) {
         throw new InvalidAttributeException(ApiLogger.getMonthOrYearBothZero());
      } else {
         if (this.month >= 12) {
            this.year += this.month / 12;
            this.month %= 12;
         }

         this.value = new Integer(this.year * 12 + this.month);
         this.value = new Integer(this.isDurationNegative ? -1 * this.value : this.value);
      }
   }

   private void checkLength(int idx, int length, String value) throws InvalidAttributeException {
      if (idx + (length - 1) >= value.length()) {
         this.throwInvalidAttributeException();
      }

   }

   private void throwInvalidAttributeException() throws InvalidAttributeException {
      throw new InvalidAttributeException("yearMonthDuration value does not conform to: [-]?P\\p{Nd}+(Y(\\p{Nd}+M)?|M)");
   }

   public Type getType() {
      return Type.YEAR_MONTH_DURATION;
   }

   public String getValue() {
      return this.toString();
   }

   public Integer getDuration() {
      return this.value;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.isDurationNegative) {
         sb.append('-');
      }

      sb.append('P');
      if (this.year != 0) {
         sb.append(String.valueOf(this.year));
         sb.append('Y');
      }

      if (this.month != 0) {
         sb.append(String.valueOf(this.month));
         sb.append('M');
      }

      return sb.toString();
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.toString());
   }

   public int compareTo(YearMonthDurationAttribute other) {
      return this.value.compareTo(other.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof YearMonthDurationAttribute)) {
         return false;
      } else {
         YearMonthDurationAttribute other = (YearMonthDurationAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value;
   }

   public boolean add(YearMonthDurationAttribute o) {
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

         public YearMonthDurationAttribute next() {
            this.nextNotCalled = false;
            return YearMonthDurationAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
