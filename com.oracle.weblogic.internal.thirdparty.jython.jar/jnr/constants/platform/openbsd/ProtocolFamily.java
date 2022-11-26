package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum ProtocolFamily implements Constant {
   PF_UNSPEC(0),
   PF_LOCAL(1),
   PF_UNIX(1),
   PF_INET(2),
   PF_IMPLINK(3),
   PF_PUP(4),
   PF_CHAOS(5),
   PF_NS(6),
   PF_ISO(7),
   PF_OSI(7),
   PF_ECMA(8),
   PF_DATAKIT(9),
   PF_CCITT(10),
   PF_SNA(11),
   PF_DECnet(12),
   PF_DLI(13),
   PF_LAT(14),
   PF_HYLINK(15),
   PF_APPLETALK(16),
   PF_ROUTE(17),
   PF_LINK(18),
   PF_XTP(19),
   PF_COIP(20),
   PF_CNT(21),
   PF_SIP(29),
   PF_IPX(23),
   PF_RTIP(22),
   PF_PIP(25),
   PF_ISDN(26),
   PF_KEY(30),
   PF_INET6(24),
   PF_NATM(27),
   PF_MAX(33);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 33L;

   private ProtocolFamily(int value) {
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
