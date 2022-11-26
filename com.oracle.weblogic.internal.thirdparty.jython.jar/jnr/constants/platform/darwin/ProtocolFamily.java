package jnr.constants.platform.darwin;

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
   PF_ISO(7L),
   PF_OSI(7L),
   PF_ECMA(8L),
   PF_DATAKIT(9L),
   PF_CCITT(10L),
   PF_SNA(11L),
   PF_DECnet(12L),
   PF_DLI(13L),
   PF_LAT(14L),
   PF_HYLINK(15L),
   PF_APPLETALK(16L),
   PF_ROUTE(17L),
   PF_LINK(18L),
   PF_XTP(19L),
   PF_COIP(20L),
   PF_CNT(21L),
   PF_SIP(24L),
   PF_IPX(23L),
   PF_RTIP(22L),
   PF_PIP(25L),
   PF_NDRV(27L),
   PF_ISDN(28L),
   PF_KEY(29L),
   PF_INET6(30L),
   PF_NATM(31L),
   PF_SYSTEM(32L),
   PF_NETBIOS(33L),
   PF_PPP(34L),
   PF_MAX(38L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 38L;

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
