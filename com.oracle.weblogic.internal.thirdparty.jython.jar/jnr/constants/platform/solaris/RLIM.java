package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum RLIM implements Constant {
   RLIM_NLIMITS(7L),
   RLIM_INFINITY(4294967293L),
   RLIM_SAVED_MAX(4294967294L),
   RLIM_SAVED_CUR(4294967295L);

   private final long value;
   public static final long MIN_VALUE = 7L;
   public static final long MAX_VALUE = 4294967295L;

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
