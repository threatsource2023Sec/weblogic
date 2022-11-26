package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum Locale implements Constant {
   LC_CTYPE(2L),
   LC_NUMERIC(4L),
   LC_TIME(5L),
   LC_COLLATE(1L),
   LC_MONETARY(3L),
   LC_MESSAGES(6L),
   LC_ALL(0L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 6L;

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
