package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum RLIMIT implements Constant {
   RLIMIT_AS(9),
   RLIMIT_CORE(4),
   RLIMIT_CPU(0),
   RLIMIT_DATA(2),
   RLIMIT_FSIZE(1),
   RLIMIT_LOCKS(10),
   RLIMIT_MEMLOCK(8),
   RLIMIT_MSGQUEUE(12),
   RLIMIT_NICE(13),
   RLIMIT_NLIMITS(15),
   RLIMIT_NOFILE(7),
   RLIMIT_NPROC(6),
   RLIMIT_OFILE(7),
   RLIMIT_RSS(5),
   RLIMIT_RTPRIO(14),
   RLIMIT_SIGPENDING(11),
   RLIMIT_STACK(3);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 15L;

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
