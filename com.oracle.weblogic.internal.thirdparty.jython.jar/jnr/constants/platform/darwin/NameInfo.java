package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum NameInfo implements Constant {
   NI_MAXHOST(1025L),
   NI_MAXSERV(32L),
   NI_NOFQDN(1L),
   NI_NUMERICHOST(2L),
   NI_NAMEREQD(4L),
   NI_NUMERICSERV(8L),
   NI_DGRAM(16L),
   NI_WITHSCOPEID(32L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 1025L;

   private NameInfo(long value) {
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
