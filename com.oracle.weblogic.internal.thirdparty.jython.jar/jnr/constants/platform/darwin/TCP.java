package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MAX_SACK(3L),
   TCP_MSS(512L),
   TCP_MINMSS(216L),
   TCP_MINMSSOVERLOAD(1000L),
   TCP_MAXWIN(65535L),
   TCP_MAX_WINSHIFT(14L),
   TCP_MAXBURST(4L),
   TCP_MAXHLEN(60L),
   TCP_MAXOLEN(40L),
   TCP_NODELAY(1L),
   TCP_MAXSEG(2L),
   TCP_NOPUSH(4L),
   TCP_NOOPT(8L),
   TCP_KEEPALIVE(16L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 65535L;

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
