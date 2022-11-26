package jnr.posix;

final class JavaTimes implements Times {
   private static final long startTime = System.currentTimeMillis();
   static final long HZ = 1000L;

   public long utime() {
      return Math.max(System.currentTimeMillis() - startTime, 1L);
   }

   public long stime() {
      return 0L;
   }

   public long cutime() {
      return 0L;
   }

   public long cstime() {
      return 0L;
   }
}
