package jnr.constants.platform.fake;

import jnr.constants.Constant;

public enum Errno implements Constant {
   EPERM(1L),
   ENOENT(2L),
   ESRCH(3L),
   EINTR(4L),
   EIO(5L),
   ENXIO(6L),
   E2BIG(7L),
   ENOEXEC(8L),
   EBADF(9L),
   ECHILD(10L),
   EDEADLK(11L),
   ENOMEM(12L),
   EACCES(13L),
   EFAULT(14L),
   ENOTBLK(15L),
   EBUSY(16L),
   EEXIST(17L),
   EXDEV(18L),
   ENODEV(19L),
   ENOTDIR(20L),
   EISDIR(21L),
   EINVAL(22L),
   ENFILE(23L),
   EMFILE(24L),
   ENOTTY(25L),
   ETXTBSY(26L),
   EFBIG(27L),
   ENOSPC(28L),
   ESPIPE(29L),
   EROFS(30L),
   EMLINK(31L),
   EPIPE(32L),
   EDOM(33L),
   ERANGE(34L),
   EWOULDBLOCK(35L),
   EAGAIN(36L),
   EINPROGRESS(37L),
   EALREADY(38L),
   ENOTSOCK(39L),
   EDESTADDRREQ(40L),
   EMSGSIZE(41L),
   EPROTOTYPE(42L),
   ENOPROTOOPT(43L),
   EPROTONOSUPPORT(44L),
   ESOCKTNOSUPPORT(45L),
   EOPNOTSUPP(46L),
   EPFNOSUPPORT(47L),
   EAFNOSUPPORT(48L),
   EADDRINUSE(49L),
   EADDRNOTAVAIL(50L),
   ENETDOWN(51L),
   ENETUNREACH(52L),
   ENETRESET(53L),
   ECONNABORTED(54L),
   ECONNRESET(55L),
   ENOBUFS(56L),
   EISCONN(57L),
   ENOTCONN(58L),
   ESHUTDOWN(59L),
   ETOOMANYREFS(60L),
   ETIMEDOUT(61L),
   ECONNREFUSED(62L),
   ELOOP(63L),
   ENAMETOOLONG(64L),
   EHOSTDOWN(65L),
   EHOSTUNREACH(66L),
   ENOTEMPTY(67L),
   EUSERS(68L),
   EDQUOT(69L),
   ESTALE(70L),
   EREMOTE(71L),
   ENOLCK(72L),
   ENOSYS(73L),
   EOVERFLOW(74L),
   EIDRM(75L),
   ENOMSG(76L),
   EILSEQ(77L),
   EBADMSG(78L),
   EMULTIHOP(79L),
   ENODATA(80L),
   ENOLINK(81L),
   ENOSR(82L),
   ENOSTR(83L),
   EPROTO(84L),
   ETIME(85L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 85L;

   private Errno(long value) {
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
