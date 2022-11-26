package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum RLIM implements Constant {
   RLIM_NLIMITS(11),
   RLIM_INFINITY(-1);

   private final int value;
   public static final long MIN_VALUE = 11L;
   public static final long MAX_VALUE = -1L;

   private RLIM(int value) {
      this.value = value;
   }

   public final int value() {
      return this.value;
   }

   public final int intValue() {
      return this.value;
   }

   public final long longValue() {
      return (long)this.value;
   }

   public final boolean defined() {
      return true;
   }
}
