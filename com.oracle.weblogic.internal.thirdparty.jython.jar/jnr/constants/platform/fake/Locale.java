package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum Locale implements Constant {
   LC_CTYPE(0L),
   LC_NUMERIC(1L),
   LC_TIME(2L),
   LC_COLLATE(3L),
   LC_MONETARY(4L),
   LC_MESSAGES(5L),
   LC_ALL(6L),
   LC_PAPER(7L),
   LC_NAME(8L),
   LC_ADDRESS(9L),
   LC_TELEPHONE(10L),
   LC_MEASUREMENT(11L),
   LC_IDENTIFICATION(12L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 12L;

   private Locale(long value) {
      this.value = value;
   }

   public final int intValue() {
      return (int)this.value;
   }

   public final long longValue() {
      return this.value;
   }

   public final boolean defined() {
      return true;
   }
}
