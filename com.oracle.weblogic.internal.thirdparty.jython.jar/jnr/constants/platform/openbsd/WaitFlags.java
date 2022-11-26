package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum WaitFlags implements Constant {
   WNOHANG(1),
   WUNTRACED(2),
   WSTOPPED(127),
   WCONTINUED(8);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 127L;

   private WaitFlags(int value) {
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
