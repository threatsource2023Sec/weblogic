package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum Access implements Constant {
   F_OK(0L),
   X_OK(1L),
   W_OK(2L),
   R_OK(4L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 4L;

   private Access(long value) {
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
