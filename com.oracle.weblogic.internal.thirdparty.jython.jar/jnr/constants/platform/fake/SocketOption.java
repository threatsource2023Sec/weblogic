package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum SocketOption implements Constant {
   SO_DEBUG(1L),
   SO_ACCEPTCONN(2L),
   SO_REUSEADDR(3L),
   SO_KEEPALIVE(4L),
   SO_DONTROUTE(5L),
   SO_BROADCAST(6L),
   SO_USELOOPBACK(7L),
   SO_LINGER(8L),
   SO_OOBINLINE(9L),
   SO_REUSEPORT(10L),
   SO_TIMESTAMP(11L),
   SO_ACCEPTFILTER(12L),
   SO_DONTTRUNC(13L),
   SO_WANTMORE(14L),
   SO_WANTOOBFLAG(15L),
   SO_SNDBUF(16L),
   SO_RCVBUF(17L),
   SO_SNDLOWAT(18L),
   SO_RCVLOWAT(19L),
   SO_SNDTIMEO(20L),
   SO_RCVTIMEO(21L),
   SO_ERROR(22L),
   SO_TYPE(23L),
   SO_NREAD(24L),
   SO_NKE(25L),
   SO_NOSIGPIPE(26L),
   SO_NOADDRERR(27L),
   SO_NWRITE(28L),
   SO_REUSESHAREUID(29L),
   SO_LABEL(30L),
   SO_PEERLABEL(31L),
   SO_ATTACH_FILTER(32L),
   SO_BINDTODEVICE(33L),
   SO_DETACH_FILTER(34L),
   SO_NO_CHECK(35L),
   SO_PASSCRED(36L),
   SO_PEERCRED(37L),
   SO_PEERNAME(38L),
   SO_PRIORITY(39L),
   SO_SECURITY_AUTHENTICATION(40L),
   SO_SECURITY_ENCRYPTION_NETWORK(41L),
   SO_SECURITY_ENCRYPTION_TRANSPORT(42L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 42L;

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
