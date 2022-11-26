package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum Shutdown implements Constant {
   SHUT_RD(0),
   SHUT_WR(1),
   SHUT_RDWR(2);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 2L;

   private Shutdown(int value) {
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
