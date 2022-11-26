package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum Shutdown implements Constant {
   SHUT_RD(0L),
   SHUT_WR(1L),
   SHUT_RDWR(2L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 4L;

   private Shutdown(long value) {
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
