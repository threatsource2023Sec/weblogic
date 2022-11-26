package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum NameInfo implements Constant {
   NI_MAXHOST(1L),
   NI_MAXSERV(2L),
   NI_NOFQDN(3L),
   NI_NUMERICHOST(4L),
   NI_NAMEREQD(5L),
   NI_NUMERICSERV(6L),
   NI_DGRAM(7L),
   NI_WITHSCOPEID(8L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 8L;

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
