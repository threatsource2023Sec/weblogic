package com.bea.core.repackaged.aspectj.bridge;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Version {
   public static final String DEVELOPMENT = "DEVELOPMENT";
   public static final long NOTIME = 0L;
   public static final String text = "1.8.9";
   public static final String time_text = "Monday Mar 14, 2016 at 21:18:16 GMT";
   private static long time = -1L;
   public static final String SIMPLE_DATE_FORMAT = "EEEE MMM d, yyyy 'at' HH:mm:ss z";

   public static long getTime() {
      if (time == -1L) {
         long foundTime = 0L;

         try {
            SimpleDateFormat format = new SimpleDateFormat("EEEE MMM d, yyyy 'at' HH:mm:ss z");
            ParsePosition pos = new ParsePosition(0);
            Date date = format.parse("Monday Mar 14, 2016 at 21:18:16 GMT", pos);
            if (date != null) {
               foundTime = date.getTime();
            }
         } catch (Throwable var5) {
         }

         time = foundTime;
      }

      return time;
   }

   public static void main(String[] args) {
      if (null != args && 0 < args.length && !"1.8.9".equals(args[0])) {
         System.err.println("version expected: \"" + args[0] + "\" actual=\"" + "1.8.9" + "\"");
      }

   }
}
