package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum SocketOption implements Constant {
   SO_DEBUG(1),
   SO_ACCEPTCONN(30),
   SO_REUSEADDR(2),
   SO_KEEPALIVE(9),
   SO_DONTROUTE(5),
   SO_BROADCAST(6),
   SO_LINGER(13),
   SO_OOBINLINE(10),
   SO_TIMESTAMP(29),
   SO_SNDBUF(7),
   SO_RCVBUF(8),
   SO_SNDLOWAT(19),
   SO_RCVLOWAT(18),
   SO_SNDTIMEO(21),
   SO_RCVTIMEO(20),
   SO_ERROR(4),
   SO_TYPE(3),
   SO_ATTACH_FILTER(26),
   SO_BINDTODEVICE(25),
   SO_DETACH_FILTER(27),
   SO_NO_CHECK(11),
   SO_PASSCRED(16),
   SO_PEERCRED(17),
   SO_PEERNAME(28),
   SO_PRIORITY(12),
   SO_SECURITY_AUTHENTICATION(22),
   SO_SECURITY_ENCRYPTION_NETWORK(24),
   SO_SECURITY_ENCRYPTION_TRANSPORT(23);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 30L;

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
