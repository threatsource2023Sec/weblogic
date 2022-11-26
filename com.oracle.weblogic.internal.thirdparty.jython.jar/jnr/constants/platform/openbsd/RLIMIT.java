package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum RLIMIT implements Constant {
   RLIMIT_CORE(4),
   RLIMIT_CPU(0),
   RLIMIT_DATA(2),
   RLIMIT_FSIZE(1),
   RLIMIT_MEMLOCK(6),
   RLIMIT_NOFILE(8),
   RLIMIT_NPROC(7),
   RLIMIT_RSS(5),
   RLIMIT_STACK(3);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 8L;

   private RLIMIT(int value) {
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
