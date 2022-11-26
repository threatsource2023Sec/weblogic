package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum Sock implements Constant {
   SOCK_STREAM(1),
   SOCK_DGRAM(2),
   SOCK_RAW(3),
   SOCK_RDM(4),
   SOCK_SEQPACKET(5);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 5L;

   private Sock(int value) {
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
