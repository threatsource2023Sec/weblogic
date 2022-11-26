package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MAX_SACK(4),
   TCP_MSS(512),
   TCP_MINMSS(216),
   TCP_MINMSSOVERLOAD(0),
   TCP_MAXWIN(65535),
   TCP_MAX_WINSHIFT(14),
   TCP_MAXBURST(4),
   TCP_MAXHLEN(60),
   TCP_MAXOLEN(40),
   TCP_NODELAY(1),
   TCP_MAXSEG(2),
   TCP_NOPUSH(4),
   TCP_NOOPT(8);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 65535L;

   private TCP(int value) {
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
