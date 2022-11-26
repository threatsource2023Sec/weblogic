package com.bea.common.ldap;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
   public static String toString(Date val, boolean isVDETimestamp) {
      Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
      c.setTime(val);
      StringBuffer sb = new StringBuffer();
      String yearString = String.valueOf(c.get(1));
      int ysLength = yearString.length();

      while(ysLength++ < 4) {
         sb.append('0');
      }

      sb.append(yearString);
      String monthString = String.valueOf(c.get(2) + (isVDETimestamp ? 0 : 1));
      if (monthString.length() < 2) {
         sb.append('0');
      }

      sb.append(monthString);
      String dayString = String.valueOf(c.get(5));
      if (dayString.length() < 2) {
         sb.append('0');
      }

      sb.append(dayString);
      String hourString = String.valueOf(c.get(11));
      if (hourString.length() < 2) {
         sb.append('0');
      }

      sb.append(hourString);
      String minuteString = String.valueOf(c.get(12));
      if (minuteString.length() < 2) {
         sb.append('0');
      }

      sb.append(minuteString);
      if (!isVDETimestamp) {
         String secondString = String.valueOf(c.get(13));
         if (secondString.length() < 2) {
            sb.append('0');
         }

         sb.append(secondString);
         int milli = c.get(14);
         if (milli > 0) {
            String milliString = String.valueOf(milli);
            int msLength = milliString.length();

            while(msLength++ < 3) {
               sb.append('0');
            }

            if (milliString.length() > 3) {
               sb.append(milliString.substring(0, 3));
            } else {
               sb.append(milliString);
            }
         }
      }

      sb.append('Z');
      return sb.toString();
   }
}
