package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum WaitFlags implements Constant {
   WNOHANG(1L),
   WUNTRACED(2L),
   WSTOPPED(8L),
   WEXITED(4L),
   WCONTINUED(16L),
   WNOWAIT(32L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 32L;

   private WaitFlags(long value) {
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
