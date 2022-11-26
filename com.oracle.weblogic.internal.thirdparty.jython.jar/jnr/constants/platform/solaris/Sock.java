package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum Sock implements Constant {
   SOCK_STREAM(2L),
   SOCK_DGRAM(1L),
   SOCK_RAW(4L),
   SOCK_RDM(5L),
   SOCK_SEQPACKET(6L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 6L;

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
