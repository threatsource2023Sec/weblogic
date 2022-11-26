package serp.util;

public class Numbers {
   private static final Integer INT_NEGONE = new Integer(-1);
   private static final Long LONG_NEGONE = new Long(-1L);
   private static final Integer[] INTEGERS = new Integer[50];
   private static final Long[] LONGS = new Long[50];

   public static Integer valueOf(int n) {
      if (n == -1) {
         return INT_NEGONE;
      } else {
         return n >= 0 && n < INTEGERS.length ? INTEGERS[n] : new Integer(n);
      }
   }

   public static Long valueOf(long n) {
      if (n == -1L) {
         return LONG_NEGONE;
      } else {
         return n >= 0L && n < (long)LONGS.length ? LONGS[(int)n] : new Long(n);
      }
   }

   static {
      int i;
      for(i = 0; i < INTEGERS.length; ++i) {
         INTEGERS[i] = new Integer(i);
      }

      for(i = 0; i < LONGS.length; ++i) {
         LONGS[i] = new Long((long)i);
      }

   }
}
