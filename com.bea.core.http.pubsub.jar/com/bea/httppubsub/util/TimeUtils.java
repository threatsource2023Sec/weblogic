package com.bea.httppubsub.util;

import java.util.TimeZone;
import weblogic.utils.string.SimpleCachingDateFormat;

public final class TimeUtils {
   private static final SimpleCachingDateFormat dateFormat = new SimpleCachingDateFormat("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("GMT"));

   private TimeUtils() {
   }

   public static String getTime(long time) {
      return dateFormat.getDate(time);
   }

   public static String getCurrentTime() {
      return getTime(System.currentTimeMillis());
   }
}
