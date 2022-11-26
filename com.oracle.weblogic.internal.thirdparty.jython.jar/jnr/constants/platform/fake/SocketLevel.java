package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum SocketLevel implements Constant {
   SOL_SOCKET(1L),
   SOL_IP(2L),
   SOL_TCP(3L),
   SOL_UDP(4L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 4L;

   private SocketLevel(long value) {
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
