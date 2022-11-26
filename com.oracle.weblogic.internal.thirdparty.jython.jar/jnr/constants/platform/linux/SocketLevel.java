package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum SocketLevel implements Constant {
   SOL_SOCKET(1);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 1L;

   private SocketLevel(int value) {
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
