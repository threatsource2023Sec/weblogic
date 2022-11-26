package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum ProtocolFamily implements Constant {
   PF_UNSPEC(0L),
   PF_LOCAL(1L),
   PF_UNIX(1L),
   PF_INET(2L),
   PF_IMPLINK(3L),
   PF_PUP(4L),
   PF_CHAOS(5L),
   PF_NS(6L),
   PF_OSI(19L),
   PF_ECMA(8L),
   PF_DATAKIT(9L),
   PF_CCITT(10L),
   PF_SNA(11L),
   PF_DECnet(12L),
   PF_DLI(13L),
   PF_LAT(14L),
   PF_HYLINK(15L),
   PF_APPLETALK(16L),
   PF_ROUTE(24L),
   PF_LINK(25L),
   PF_IPX(23L),
   PF_KEY(27L),
   PF_INET6(26L),
   PF_MAX(33L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 33L;

   private ProtocolFamily(long value) {
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
