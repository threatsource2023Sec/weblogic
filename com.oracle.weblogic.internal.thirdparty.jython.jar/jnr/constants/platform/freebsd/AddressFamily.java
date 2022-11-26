package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum AddressFamily implements Constant {
   AF_UNSPEC(0),
   AF_LOCAL(1),
   AF_UNIX(1),
   AF_INET(2),
   AF_IMPLINK(3),
   AF_PUP(4),
   AF_CHAOS(5),
   AF_ISO(7),
   AF_OSI(7),
   AF_ECMA(8),
   AF_DATAKIT(9),
   AF_CCITT(10),
   AF_SNA(11),
   AF_DECnet(12),
   AF_DLI(13),
   AF_LAT(14),
   AF_HYLINK(15),
   AF_APPLETALK(16),
   AF_ROUTE(17),
   AF_LINK(18),
   pseudo_AF_XTP(19),
   AF_COIP(20),
   AF_CNT(21),
   pseudo_AF_RTIP(22),
   AF_IPX(23),
   AF_SIP(24),
   pseudo_AF_PIP(25),
   AF_ISDN(26),
   AF_E164(26),
   pseudo_AF_KEY(27),
   AF_INET6(28),
   AF_NATM(29),
   AF_NETBIOS(6),
   AF_ATM(30),
   pseudo_AF_HDRCMPLT(31),
   AF_NETGRAPH(32),
   AF_MAX(37);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 37L;

   private AddressFamily(int value) {
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
