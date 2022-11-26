package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MAX_SACK(1L),
   TCP_MSS(2L),
   TCP_MINMSS(3L),
   TCP_MINMSSOVERLOAD(4L),
   TCP_MAXWIN(5L),
   TCP_MAX_WINSHIFT(6L),
   TCP_MAXBURST(7L),
   TCP_MAXHLEN(8L),
   TCP_MAXOLEN(9L),
   TCP_NODELAY(10L),
   TCP_MAXSEG(11L),
   TCP_NOPUSH(12L),
   TCP_NOOPT(13L),
   TCP_KEEPALIVE(14L),
   TCP_NSTATES(15L),
   TCP_RETRANSHZ(16L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 16L;

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
