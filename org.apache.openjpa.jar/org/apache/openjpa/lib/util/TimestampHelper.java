package org.apache.openjpa.lib.util;

import java.sql.Timestamp;

public class TimestampHelper {
   protected static final long MilliMuliplier = 1000L;
   protected static final long MicroMuliplier = 1000000L;
   protected static final long NanoMuliplier = 1000000000L;
   private static long sec0;
   private static long nano0;

   public static Timestamp getNanoPrecisionTimestamp() {
      long nano_delta = nano0 + System.nanoTime();
      long sec1 = sec0 + nano_delta / 1000000000L;
      long nano1 = nano_delta % 1000000000L;
      Timestamp rtnTs = new Timestamp(sec1 * 1000L);
      rtnTs.setNanos((int)nano1);
      return rtnTs;
   }

   static {
      long curTime = System.currentTimeMillis();
      sec0 = curTime / 1000L;
      nano0 = curTime % 1000L * 1000000L - System.nanoTime();
   }
}
