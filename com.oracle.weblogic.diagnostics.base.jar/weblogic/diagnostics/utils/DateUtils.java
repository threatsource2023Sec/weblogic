package weblogic.diagnostics.utils;

import java.util.Calendar;
import java.util.Date;
import weblogic.diagnostics.i18n.DiagnosticsBaseTextTextFormatter;

public abstract class DateUtils {
   public static final long NANOS_PER_MILLI = 1000000L;
   public static final long NANOS_PER_SEC = 1000000000L;
   private static final int MINUTES_PER_DAY = 1440;
   private static final int MINUTES_PER_HOUR = 60;

   public static String nanoDateToString(long nanoTime) {
      return nanoDateToString(nanoTime, false);
   }

   public static String nanoDateToString(long nanoTime, boolean addRawValue) {
      long secTime = nanoTime / 1000000000L;
      long milliTime = nanoTime / 1000000L;
      long nanos = nanoTime - secTime * 1000000000L;
      String baseTimeStr = "" + new Date(milliTime);
      String ret = baseTimeStr.substring(0, 19) + ":" + nanos + baseTimeStr.substring(19);
      if (addRawValue) {
         ret = ret + " [" + nanoTime + "]";
      }

      return ret;
   }

   public static long getTimestamp(String interval, boolean add) throws NumberFormatException {
      Calendar cal = Calendar.getInstance();
      int factor = add ? 1 : -1;
      if (interval != null && !interval.isEmpty()) {
         String[] tokens = interval.split(":");
         String days = tokens.length >= 1 ? tokens[0] : "";
         String hours = tokens.length >= 2 ? tokens[1] : "";
         String minutes = tokens.length >= 3 ? tokens[2] : "";
         int numOfDays = days != null && !days.isEmpty() ? Integer.parseInt(days) : 0;
         int numOfHours = hours != null && !hours.isEmpty() ? Integer.parseInt(hours) : 0;
         int numOfMinutes = minutes != null && !minutes.isEmpty() ? Integer.parseInt(minutes) : 0;
         if (numOfDays < 0) {
            throw new IllegalArgumentException(DiagnosticsBaseTextTextFormatter.getInstance().getNegativeAgeValueInvalid());
         }

         cal.add(12, 1440 * numOfDays * factor);
         if (numOfHours < 0) {
            throw new IllegalArgumentException(DiagnosticsBaseTextTextFormatter.getInstance().getNegativeAgeValueInvalid());
         }

         cal.add(12, 60 * numOfHours * factor);
         if (numOfMinutes < 0) {
            throw new IllegalArgumentException(DiagnosticsBaseTextTextFormatter.getInstance().getNegativeAgeValueInvalid());
         }

         cal.add(12, numOfMinutes * factor);
      }

      return cal.getTimeInMillis();
   }

   public static long[] getTimestampRange(String last) {
      long toTimestamp = System.currentTimeMillis();
      int days = 0;
      int hours = 0;
      int minutes = 0;
      String[] tokens = last.split(" ");
      String[] var7 = tokens;
      int var8 = tokens.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String token = var7[var9];
         if (token != null && token.length() != 0) {
            char lastChar = token.charAt(token.length() - 1);
            String number = token.substring(0, token.length() - 1);
            int value = false;

            int value;
            try {
               value = Integer.parseInt(number);
            } catch (NumberFormatException var15) {
               throw new IllegalArgumentException(DiagnosticsBaseTextTextFormatter.getInstance().getInvalidLastParam(last), var15);
            }

            if (value > 0) {
               value = -1 * value;
            }

            switch (lastChar) {
               case 'D':
               case 'd':
                  days = value;
                  break;
               case 'H':
               case 'h':
                  hours = value;
                  break;
               case 'M':
               case 'm':
                  minutes = value;
            }
         }
      }

      if (days == 0 && hours == 0 && minutes == 0) {
         throw new IllegalArgumentException(DiagnosticsBaseTextTextFormatter.getInstance().getInvalidLastParam(last));
      } else {
         Calendar cal = Calendar.getInstance();
         cal.setTimeInMillis(toTimestamp);
         cal.add(5, days);
         cal.add(11, hours);
         cal.add(12, minutes);
         long fromTimestamp = cal.getTimeInMillis();
         return new long[]{fromTimestamp, toTimestamp};
      }
   }
}
