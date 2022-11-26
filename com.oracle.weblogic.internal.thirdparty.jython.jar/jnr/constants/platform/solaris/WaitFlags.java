package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum WaitFlags implements Constant {
   WNOHANG(64L),
   WUNTRACED(4L),
   WSTOPPED(4L),
   WEXITED(1L),
   WCONTINUED(8L),
   WNOWAIT(128L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 128L;

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
