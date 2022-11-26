package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum Sock implements Constant {
   SOCK_STREAM(1L),
   SOCK_DGRAM(2L),
   SOCK_RAW(3L),
   SOCK_RDM(4L),
   SOCK_SEQPACKET(5L),
   SOCK_MAXADDRLEN(255L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 255L;

   private Sock(long value) {
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
