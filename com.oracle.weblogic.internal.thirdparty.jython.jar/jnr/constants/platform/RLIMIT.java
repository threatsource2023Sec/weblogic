package jnr.constants.platform;

import jnr.constants.Constant;

public enum RLIMIT implements Constant {
   RLIMIT_AS,
   RLIMIT_CORE,
   RLIMIT_CPU,
   RLIMIT_DATA,
   RLIMIT_FSIZE,
   RLIMIT_LOCKS,
   RLIMIT_MEMLOCK,
   RLIMIT_MSGQUEUE,
   RLIMIT_NICE,
   RLIMIT_NLIMITS,
   RLIMIT_NOFILE,
   RLIMIT_NPROC,
   RLIMIT_OFILE,
   RLIMIT_RSS,
   RLIMIT_RTPRIO,
   RLIMIT_RTTIME,
   RLIMIT_SIGPENDING,
   RLIMIT_STACK,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(RLIMIT.class, 20000, 29999);

   public final int intValue() {
      return (int)resolver.longValue(this);
   }

   public final long longValue() {
      return resolver.longValue(this);
   }

   public final String description() {
      return resolver.description(this);
   }

   public final boolean defined() {
      return resolver.defined(this);
   }

   public final String toString() {
      return this.description();
   }

   public static RLIMIT valueOf(long value) {
      return (RLIMIT)resolver.valueOf(value);
   }
}
