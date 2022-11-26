package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum AddressFamily implements Constant {
   AF_UNSPEC(0L),
   AF_LOCAL(1L),
   AF_UNIX(1L),
   AF_INET(2L),
   AF_IMPLINK(3L),
   AF_PUP(4L),
   AF_CHAOS(5L),
   AF_NS(6L),
   AF_OSI(19L),
   AF_ECMA(8L),
   AF_DATAKIT(9L),
   AF_CCITT(10L),
   AF_SNA(11L),
   AF_DECnet(12L),
   AF_DLI(13L),
   AF_LAT(14L),
   AF_HYLINK(15L),
   AF_APPLETALK(16L),
   AF_ROUTE(24L),
   AF_LINK(25L),
   AF_IPX(23L),
   AF_INET6(26L),
   AF_MAX(33L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 33L;

   private AddressFamily(long value) {
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
