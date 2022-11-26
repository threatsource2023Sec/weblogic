package weblogic.management.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimestampParser {
   private TimestampParser() {
   }

   public static final long toMillis(String time) {
      try {
         return parseString(time);
      } catch (IllegalArgumentException var6) {
         try {
            return Long.parseLong(time);
         } catch (NumberFormatException var5) {
            try {
               Date date = DateFormat.getInstance().parse(time);
               return date.getTime();
            } catch (ParseException var4) {
               throw var6;
            }
         }
      }
   }

   private static long parseString(String str) {
      String[] split = str.split("T");
      String date = split[0];
      String time;
      if (split.length < 2) {
         time = "";
      } else {
         time = split[1];
      }

      String[] datesplit = date.split("-");
      int cnt = time.indexOf("+");
      if (cnt == -1) {
         cnt = time.indexOf("-");
      }

      String timezone = null;
      if (cnt != -1) {
         timezone = time.substring(cnt);
         time = time.substring(0, cnt);
      }

      String year = datesplit[0];
      String month = datesplit.length >= 2 ? datesplit[1] : "1";
      String day = datesplit.length >= 3 ? datesplit[2] : "1";
      String[] individualTimes = time.split(":");
      String hour = individualTimes[0];
      String minute = individualTimes.length >= 2 ? individualTimes[1] : "0";
      String second = individualTimes.length >= 3 ? individualTimes[2] : "0";
      int milliseconds = 0;
      if (second.indexOf(".") > 0) {
         int indx = second.indexOf(".");
         String msec = second.substring(indx + 1);
         second = second.substring(0, indx);
         int fraction = Integer.parseInt(msec);
         milliseconds = fraction * 10;
      }

      Calendar calendar;
      if (timezone != null) {
         TimeZone ts = TimeZone.getTimeZone("GMT" + timezone);
         calendar = Calendar.getInstance(ts);
      } else {
         calendar = Calendar.getInstance();
      }

      calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
      calendar.set(14, 0);
      if (milliseconds > 0) {
         calendar.set(14, milliseconds);
      }

      return calendar.getTimeInMillis();
   }
}
