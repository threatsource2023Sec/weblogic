package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum TCP implements Constant {
   TCP_MAX_SACK(3),
   TCP_MSS(512),
   TCP_MAXWIN(65535),
   TCP_MAX_WINSHIFT(14),
   TCP_MAXBURST(4),
   TCP_NODELAY(1),
   TCP_MAXSEG(2);

   private final int value;
   public static final long MIN_VALUE = 1L;
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
