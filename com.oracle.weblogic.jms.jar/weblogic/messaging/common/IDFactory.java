package weblogic.messaging.common;

public final class IDFactory {
   private static int seed = MessagingUtilities.getSeed();
   private static long timestamp = System.currentTimeMillis();
   private static int counter = 2147483646;
   private static Object lock = new Object();

   final void initId(IDImpl id) {
      int counterl;
      int seedl;
      long timestampl;
      synchronized(lock) {
         counterl = ++counter;
         if (counterl == Integer.MAX_VALUE) {
            timestamp = System.currentTimeMillis();
            counter = 1;
            counterl = 1;
         }

         seedl = seed;
         timestampl = timestamp;
      }

      id.init(timestampl, seedl, counterl);
   }
}
