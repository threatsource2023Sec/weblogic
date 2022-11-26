package com.bea.adaptive.harvester.utils.date;

import java.util.Date;

public abstract class DateUtils {
   public static final long NANOS_PER_MILLI = 1000000L;
   public static final long NANOS_PER_SEC = 1000000000L;

   public static String nanoDateToString(long nanoTime) {
      return nanoDateToString(nanoTime, false);
   }

   public static String nanoDateToString(long nanoTime, boolean inclRawValue) {
      long secTime = nanoTime / 1000000000L;
      long milliTime = nanoTime / 1000000L;
      long nanos = nanoTime - secTime * 1000000000L;
      String baseTimeStr = "" + new Date(milliTime);
      String ret = baseTimeStr.substring(0, 19) + ":" + nanos + baseTimeStr.substring(19);
      if (inclRawValue) {
         ret = ret + " [" + nanoTime + "]";
      }

      return ret;
   }

   public static long getNanoTimestamp() {
      return System.currentTimeMillis() * 1000000L;
   }

   public static String getNanoTimestampAsString() {
      return nanoDateToString(getNanoTimestamp());
   }
}
