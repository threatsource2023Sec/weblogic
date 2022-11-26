package weblogic.xml.schema.types.util;

import java.math.BigInteger;
import weblogic.xml.schema.types.Duration;

public class XSDDurationDeserializer {
   private static final char YEAR = 'Y';
   private static final char MONTH = 'M';
   private static final char DAY = 'D';
   private static final char HOUR = 'H';
   private static final char MINUTE = 'M';
   private static final char SECOND = 'S';

   public static Duration getDuration(String content) {
      Duration d = new Duration();
      int index = false;
      int index;
      if (content.startsWith("-P")) {
         d.setSignum(-1);
         index = 2;
      } else {
         if (!content.startsWith("P")) {
            throw new IllegalArgumentException("Invalid duration format, 'P' is required.");
         }

         index = 1;
      }

      BigIntegerObjectHolder holder = new BigIntegerObjectHolder();
      index = setDate(d, content, index, holder);
      setTime(d, content, index, holder);
      return d;
   }

   private static int setDate(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      lastIndex = setYears(d, content, lastIndex, holder);
      lastIndex = setMonths(d, content, lastIndex, holder);
      lastIndex = setDays(d, content, lastIndex, holder);
      return lastIndex;
   }

   private static int setYears(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, 'Y');
      d.setYears(holder.getValue());
      return currentIndex;
   }

   private static int setMonths(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, 'M');
      d.setMonths(holder.getValue());
      return currentIndex;
   }

   private static int setDays(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, 'D');
      d.setDays(holder.getValue());
      return currentIndex;
   }

   private static int setTime(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int contentLength = content.length();
      if (contentLength <= lastIndex) {
         return lastIndex;
      } else if (content.charAt(lastIndex) != 'T') {
         throw new IllegalArgumentException("Invalid duration format, missing delimiter 'T'.");
      } else if (contentLength <= lastIndex + 1) {
         throw new IllegalArgumentException("Invalid duration format, contains 'T' but no time items.");
      } else {
         ++lastIndex;
         lastIndex = setHours(d, content, lastIndex, holder);
         lastIndex = setMinutes(d, content, lastIndex, holder);
         lastIndex = setSeconds(d, content, lastIndex, holder);
         return lastIndex;
      }
   }

   private static int setHours(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, 'H');
      d.setHours(holder.getValue());
      return currentIndex;
   }

   private static int setMinutes(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, 'M');
      d.setMinutes(holder.getValue());
      return currentIndex;
   }

   private static int setSeconds(Duration d, String content, int lastIndex, BigIntegerObjectHolder holder) {
      int currentIndex = setBigIntValue(holder, content, lastIndex, '.');
      BigInteger a = holder.getValue();
      currentIndex = setBigIntValue(holder, content, currentIndex, 'S');
      BigInteger b = holder.getValue();
      if (a != null) {
         if (b == null) {
            throw new IllegalArgumentException("Invalid format in seconds token.");
         }

         d.setSeconds(a);
         d.setSecondFraction(b);
      } else {
         d.setSeconds(b);
      }

      return currentIndex;
   }

   private static int setBigIntValue(BigIntegerObjectHolder holder, String content, int lastIndex, char delimiter) {
      int currentIndex = content.indexOf(delimiter, lastIndex);
      if (currentIndex != -1) {
         String token = content.substring(lastIndex, currentIndex);
         if (token.startsWith("-")) {
            throw new IllegalArgumentException("Invalid duration format, contains '-' at an unallowed position.");
         } else {
            BigInteger bi = new BigInteger(token);
            holder.setValue(bi);
            return currentIndex + 1;
         }
      } else {
         holder.setValue((BigInteger)null);
         return lastIndex;
      }
   }

   static class BigIntegerObjectHolder {
      private BigInteger value;

      public void setValue(BigInteger bi) {
         this.value = bi;
      }

      public BigInteger getValue() {
         return this.value;
      }
   }
}
