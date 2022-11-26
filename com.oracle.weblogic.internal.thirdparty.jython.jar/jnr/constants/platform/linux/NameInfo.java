package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum NameInfo implements Constant {
   NI_MAXHOST(1025),
   NI_MAXSERV(32),
   NI_NOFQDN(4),
   NI_NUMERICHOST(1),
   NI_NAMEREQD(8),
   NI_NUMERICSERV(2),
   NI_DGRAM(16);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 1025L;

   private NameInfo(int value) {
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
