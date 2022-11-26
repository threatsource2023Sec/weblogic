package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MSS(536L),
   TCP_NODELAY(1L),
   TCP_MAXSEG(2L),
   TCP_KEEPALIVE(8L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 536L;

   private TCP(long value) {
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
