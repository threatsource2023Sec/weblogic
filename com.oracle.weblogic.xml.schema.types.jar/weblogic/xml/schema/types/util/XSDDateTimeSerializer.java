package weblogic.xml.schema.types.util;

import java.util.Calendar;

public class XSDDateTimeSerializer {
   protected static final char MINUS = '-';
   protected static final char PLUS = '+';
   protected static final char COLON = ':';
   protected static final char PERIOD = '.';
   protected static final char ZERO = '0';
   protected static final char T = 'T';

   public static String getString(Calendar c) {
      StringBuffer buffer = new StringBuffer();
      setEra(c, buffer);
      setDate(c, buffer);
      buffer.append('T');
      setTime(c, buffer);
      setTimeZone(c, buffer);
      return buffer.toString();
   }

   protected static void setEra(Calendar c, StringBuffer buffer) {
      if (c.get(0) == 0) {
         buffer.append('-');
      }

   }

   protected static void setDate(Calendar c, StringBuffer buffer) {
      setYear(c, buffer);
      buffer.append('-');
      setMonth(c, buffer);
      buffer.append('-');
      setDay(c, buffer);
   }

   protected static void setYear(Calendar c, StringBuffer buffer) {
      int year = c.get(1);
      if (year < 10) {
         buffer.append("000");
      } else if (year < 100) {
         buffer.append("00");
      } else if (year < 1000) {
         buffer.append('0');
      }

      buffer.append(year);
   }

   protected static void setMonth(Calendar c, StringBuffer buffer) {
      int month = c.get(2) + 1;
      if (month < 10) {
         buffer.append('0');
      }

      buffer.append(month);
   }

   protected static void setDay(Calendar c, StringBuffer buffer) {
      int day = c.get(5);
      if (day < 10) {
         buffer.append('0');
      }

      buffer.append(day);
   }

   protected static void setTime(Calendar c, StringBuffer buffer) {
      int hours = c.get(11);
      if (hours < 10) {
         buffer.append('0');
      }

      buffer.append(hours);
      buffer.append(':');
      int minutes = c.get(12);
      if (minutes < 10) {
         buffer.append('0');
      }

      buffer.append(minutes);
      buffer.append(':');
      int seconds = c.get(13);
      if (seconds < 10) {
         buffer.append('0');
      }

      buffer.append(seconds);
      int millis = c.get(14);
      if (millis != 0) {
         buffer.append('.');
         if (millis < 100) {
            buffer.append('0');
         }

         if (millis < 10) {
            buffer.append('0');
         }

         buffer.append(millis);
      }

   }

   protected static void setTimeZone(Calendar c, StringBuffer buffer) {
      int tz = false;
      int tz;
      if (c.isSet(0) && c.isSet(1) && c.isSet(2) && c.isSet(5) && c.isSet(7) && c.isSet(14)) {
         tz = c.getTimeZone().getOffset(c.getTimeInMillis());
      } else {
         tz = c.getTimeZone().getRawOffset();
      }

      if (tz == 0) {
         buffer.append('Z');
      } else {
         boolean negative = false;
         if (tz < 0) {
            tz *= -1;
            negative = true;
         }

         int ms = tz % 1000;
         int s = (tz - ms) / 1000;
         int mm = s % 60;
         int m = (s - mm) / 60;
         int mh = m % 60;
         int h = (m - mh) / 60;
         if (negative) {
            buffer.append('-');
         } else {
            buffer.append('+');
         }

         if (h < 10) {
            buffer.append('0');
         }

         buffer.append(h);
         buffer.append(':');
         if (mh < 10) {
            buffer.append('0');
         }

         buffer.append(mh);
      }
   }
}
