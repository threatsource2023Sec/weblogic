package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum RLIM implements Constant {
   RLIM_NLIMITS(9L),
   RLIM_INFINITY(Long.MAX_VALUE),
   RLIM_SAVED_MAX(Long.MAX_VALUE),
   RLIM_SAVED_CUR(Long.MAX_VALUE);

   private final long value;
   public static final long MIN_VALUE = 9L;
   public static final long MAX_VALUE = Long.MAX_VALUE;

   private RLIM(long value) {
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
