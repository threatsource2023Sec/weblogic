package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum SocketOption implements Constant {
   SO_DEBUG(1),
   SO_ACCEPTCONN(2),
   SO_REUSEADDR(4),
   SO_KEEPALIVE(8),
   SO_DONTROUTE(16),
   SO_BROADCAST(32),
   SO_USELOOPBACK(64),
   SO_LINGER(128),
   SO_OOBINLINE(256),
   SO_REUSEPORT(512),
   SO_SNDBUF(4097),
   SO_RCVBUF(4098),
   SO_SNDLOWAT(4099),
   SO_RCVLOWAT(4100),
   SO_SNDTIMEO(4101),
   SO_RCVTIMEO(4102),
   SO_ERROR(4103),
   SO_TYPE(4104);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 4104L;

   private SocketOption(int value) {
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
