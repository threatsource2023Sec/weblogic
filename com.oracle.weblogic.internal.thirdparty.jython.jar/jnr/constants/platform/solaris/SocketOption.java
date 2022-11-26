package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum SocketOption implements Constant {
   SO_DEBUG(1L),
   SO_ACCEPTCONN(2L),
   SO_REUSEADDR(4L),
   SO_KEEPALIVE(8L),
   SO_DONTROUTE(16L),
   SO_BROADCAST(32L),
   SO_USELOOPBACK(64L),
   SO_LINGER(128L),
   SO_OOBINLINE(256L),
   SO_REUSEPORT(4110L),
   SO_TIMESTAMP(4115L),
   SO_SNDBUF(4097L),
   SO_RCVBUF(4098L),
   SO_SNDLOWAT(4099L),
   SO_RCVLOWAT(4100L),
   SO_SNDTIMEO(4101L),
   SO_RCVTIMEO(4102L),
   SO_ERROR(4103L),
   SO_TYPE(4104L),
   SO_ATTACH_FILTER(1073741825L),
   SO_DETACH_FILTER(1073741826L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 1073741826L;

   private SocketOption(long value) {
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
