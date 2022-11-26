package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum AddressFamily implements Constant {
   AF_UNSPEC(0),
   AF_LOCAL(1),
   AF_UNIX(1),
   AF_INET(2),
   AF_SNA(22),
   AF_DECnet(12),
   AF_APPLETALK(5),
   AF_ROUTE(16),
   AF_IPX(4),
   AF_INET6(10),
   AF_AX25(3),
   AF_MAX(34);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 34L;

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
