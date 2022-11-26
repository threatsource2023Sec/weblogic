package jnr.constants.platform;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MAX_SACK,
   TCP_MSS,
   TCP_MINMSS,
   TCP_MINMSSOVERLOAD,
   TCP_MAXWIN,
   TCP_MAX_WINSHIFT,
   TCP_MAXBURST,
   TCP_MAXHLEN,
   TCP_MAXOLEN,
   TCP_NODELAY,
   TCP_MAXSEG,
   TCP_NOPUSH,
   TCP_NOOPT,
   TCP_KEEPALIVE,
   TCP_NSTATES,
   TCP_RETRANSHZ,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(TCP.class, 20000, 29999);

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

   public static TCP valueOf(long value) {
      return (TCP)resolver.valueOf(value);
   }
}
