package jnr.constants.platform.darwin;

import java.util.EnumMap;
import java.util.Map;
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
   EAGAIN(35L),
   EINPROGRESS(36L),
   EALREADY(37L),
   ENOTSOCK(38L),
   EDESTADDRREQ(39L),
   EMSGSIZE(40L),
   EPROTOTYPE(41L),
   ENOPROTOOPT(42L),
   EPROTONOSUPPORT(43L),
   ESOCKTNOSUPPORT(44L),
   EOPNOTSUPP(102L),
   EPFNOSUPPORT(46L),
   EAFNOSUPPORT(47L),
   EADDRINUSE(48L),
   EADDRNOTAVAIL(49L),
   ENETDOWN(50L),
   ENETUNREACH(51L),
   ENETRESET(52L),
   ECONNABORTED(53L),
   ECONNRESET(54L),
   ENOBUFS(55L),
   EISCONN(56L),
   ENOTCONN(57L),
   ESHUTDOWN(58L),
   ETOOMANYREFS(59L),
   ETIMEDOUT(60L),
   ECONNREFUSED(61L),
   ELOOP(62L),
   ENAMETOOLONG(63L),
   EHOSTDOWN(64L),
   EHOSTUNREACH(65L),
   ENOTEMPTY(66L),
   EUSERS(68L),
   EDQUOT(69L),
   ESTALE(70L),
   EREMOTE(71L),
   ENOLCK(77L),
   ENOSYS(78L),
   EOVERFLOW(84L),
   EIDRM(90L),
   ENOMSG(91L),
   EILSEQ(92L),
   EBADMSG(94L),
   EMULTIHOP(95L),
   ENODATA(96L),
   ENOLINK(97L),
   ENOSR(98L),
   ENOSTR(99L),
   EPROTO(100L),
   ETIME(101L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 102L;

   private Errno(long value) {
      this.value = value;
   }

   public final String toString() {
      return (String)Errno.StringTable.descriptions.get(this);
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

   static final class StringTable {
      public static final Map descriptions = generateTable();

      public static final Map generateTable() {
         Map map = new EnumMap(Errno.class);
         map.put(Errno.EPERM, "Operation not permitted");
         map.put(Errno.ENOENT, "No such file or directory");
         map.put(Errno.ESRCH, "No such process");
         map.put(Errno.EINTR, "Interrupted system call");
         map.put(Errno.EIO, "Input/output error");
         map.put(Errno.ENXIO, "Device not configured");
         map.put(Errno.E2BIG, "Argument list too long");
         map.put(Errno.ENOEXEC, "Exec format error");
         map.put(Errno.EBADF, "Bad file descriptor");
         map.put(Errno.ECHILD, "No child processes");
         map.put(Errno.EDEADLK, "Resource deadlock avoided");
         map.put(Errno.ENOMEM, "Cannot allocate memory");
         map.put(Errno.EACCES, "Permission denied");
         map.put(Errno.EFAULT, "Bad address");
         map.put(Errno.ENOTBLK, "Block device required");
         map.put(Errno.EBUSY, "Resource busy");
         map.put(Errno.EEXIST, "File exists");
         map.put(Errno.EXDEV, "Cross-device link");
         map.put(Errno.ENODEV, "Operation not supported by device");
         map.put(Errno.ENOTDIR, "Not a directory");
         map.put(Errno.EISDIR, "Is a directory");
         map.put(Errno.EINVAL, "Invalid argument");
         map.put(Errno.ENFILE, "Too many open files in system");
         map.put(Errno.EMFILE, "Too many open files");
         map.put(Errno.ENOTTY, "Inappropriate ioctl for device");
         map.put(Errno.ETXTBSY, "Text file busy");
         map.put(Errno.EFBIG, "File too large");
         map.put(Errno.ENOSPC, "No space left on device");
         map.put(Errno.ESPIPE, "Illegal seek");
         map.put(Errno.EROFS, "Read-only file system");
         map.put(Errno.EMLINK, "Too many links");
         map.put(Errno.EPIPE, "Broken pipe");
         map.put(Errno.EDOM, "Numerical argument out of domain");
         map.put(Errno.ERANGE, "Result too large");
         map.put(Errno.EWOULDBLOCK, "Resource temporarily unavailable");
         map.put(Errno.EAGAIN, "Resource temporarily unavailable");
         map.put(Errno.EINPROGRESS, "Operation now in progress");
         map.put(Errno.EALREADY, "Operation already in progress");
         map.put(Errno.ENOTSOCK, "Socket operation on non-socket");
         map.put(Errno.EDESTADDRREQ, "Destination address required");
         map.put(Errno.EMSGSIZE, "Message too long");
         map.put(Errno.EPROTOTYPE, "Protocol wrong type for socket");
         map.put(Errno.ENOPROTOOPT, "Protocol not available");
         map.put(Errno.EPROTONOSUPPORT, "Protocol not supported");
         map.put(Errno.ESOCKTNOSUPPORT, "Socket type not supported");
         map.put(Errno.EOPNOTSUPP, "Operation not supported on socket");
         map.put(Errno.EPFNOSUPPORT, "Protocol family not supported");
         map.put(Errno.EAFNOSUPPORT, "Address family not supported by protocol family");
         map.put(Errno.EADDRINUSE, "Address already in use");
         map.put(Errno.EADDRNOTAVAIL, "Can't assign requested address");
         map.put(Errno.ENETDOWN, "Network is down");
         map.put(Errno.ENETUNREACH, "Network is unreachable");
         map.put(Errno.ENETRESET, "Network dropped connection on reset");
         map.put(Errno.ECONNABORTED, "Software caused connection abort");
         map.put(Errno.ECONNRESET, "Connection reset by peer");
         map.put(Errno.ENOBUFS, "No buffer space available");
         map.put(Errno.EISCONN, "Socket is already connected");
         map.put(Errno.ENOTCONN, "Socket is not connected");
         map.put(Errno.ESHUTDOWN, "Can't send after socket shutdown");
         map.put(Errno.ETOOMANYREFS, "Too many references: can't splice");
         map.put(Errno.ETIMEDOUT, "Operation timed out");
         map.put(Errno.ECONNREFUSED, "Connection refused");
         map.put(Errno.ELOOP, "Too many levels of symbolic links");
         map.put(Errno.ENAMETOOLONG, "File name too long");
         map.put(Errno.EHOSTDOWN, "Host is down");
         map.put(Errno.EHOSTUNREACH, "No route to host");
         map.put(Errno.ENOTEMPTY, "Directory not empty");
         map.put(Errno.EUSERS, "Too many users");
         map.put(Errno.EDQUOT, "Disc quota exceeded");
         map.put(Errno.ESTALE, "Stale NFS file handle");
         map.put(Errno.EREMOTE, "Too many levels of remote in path");
         map.put(Errno.ENOLCK, "No locks available");
         map.put(Errno.ENOSYS, "Function not implemented");
         map.put(Errno.EOVERFLOW, "Value too large to be stored in data type");
         map.put(Errno.EIDRM, "Identifier removed");
         map.put(Errno.ENOMSG, "No message of desired type");
         map.put(Errno.EILSEQ, "Illegal byte sequence");
         map.put(Errno.EBADMSG, "Bad message");
         map.put(Errno.EMULTIHOP, "EMULTIHOP (Reserved)");
         map.put(Errno.ENODATA, "No message available on STREAM");
         map.put(Errno.ENOLINK, "ENOLINK (Reserved)");
         map.put(Errno.ENOSR, "No STREAM resources");
         map.put(Errno.ENOSTR, "Not a STREAM");
         map.put(Errno.EPROTO, "Protocol error");
         map.put(Errno.ETIME, "STREAM ioctl timeout");
         return map;
      }
   }
}
