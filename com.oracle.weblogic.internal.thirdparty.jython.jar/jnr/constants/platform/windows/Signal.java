package jnr.constants.platform.windows;

import jnr.constants.Constant;

public enum Signal implements Constant {
   SIGTERM(15),
   SIGSEGV(11),
   SIGABRT(22),
   SIGFPE(8),
   SIGILL(4),
   NSIG(23),
   SIGKILL(9),
   SIGINT(2);

   private final int value;
   public static final long MIN_VALUE = 2L;
   public static final long MAX_VALUE = 23L;

   private Signal(int value) {
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
