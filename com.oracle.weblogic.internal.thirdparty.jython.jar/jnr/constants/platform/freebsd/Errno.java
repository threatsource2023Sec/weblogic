package jnr.constants.platform.freebsd;

import java.util.EnumMap;
import java.util.Map;
import jnr.constants.Constant;

public enum Errno implements Constant {
   EPERM(1),
   ENOENT(2),
   ESRCH(3),
   EINTR(4),
   EIO(5),
   ENXIO(6),
   E2BIG(7),
   ENOEXEC(8),
   EBADF(9),
   ECHILD(10),
   EDEADLK(11),
   ENOMEM(12),
   EACCES(13),
   EFAULT(14),
   ENOTBLK(15),
   EBUSY(16),
   EEXIST(17),
   EXDEV(18),
   ENODEV(19),
   ENOTDIR(20),
   EISDIR(21),
   EINVAL(22),
   ENFILE(23),
   EMFILE(24),
   ENOTTY(25),
   ETXTBSY(26),
   EFBIG(27),
   ENOSPC(28),
   ESPIPE(29),
   EROFS(30),
   EMLINK(31),
   EPIPE(32),
   EDOM(33),
   ERANGE(34),
   EWOULDBLOCK(35),
   EAGAIN(35),
   EINPROGRESS(36),
   EALREADY(37),
   ENOTSOCK(38),
   EDESTADDRREQ(39),
   EMSGSIZE(40),
   EPROTOTYPE(41),
   ENOPROTOOPT(42),
   EPROTONOSUPPORT(43),
   ESOCKTNOSUPPORT(44),
   EOPNOTSUPP(45),
   EPFNOSUPPORT(46),
   EAFNOSUPPORT(47),
   EADDRINUSE(48),
   EADDRNOTAVAIL(49),
   ENETDOWN(50),
   ENETUNREACH(51),
   ENETRESET(52),
   ECONNABORTED(53),
   ECONNRESET(54),
   ENOBUFS(55),
   EISCONN(56),
   ENOTCONN(57),
   ESHUTDOWN(58),
   ETOOMANYREFS(59),
   ETIMEDOUT(60),
   ECONNREFUSED(61),
   ELOOP(62),
   ENAMETOOLONG(63),
   EHOSTDOWN(64),
   EHOSTUNREACH(65),
   ENOTEMPTY(66),
   EUSERS(68),
   EDQUOT(69),
   ESTALE(70),
   EREMOTE(71),
   ENOLCK(77),
   ENOSYS(78),
   EOVERFLOW(84),
   EIDRM(82),
   ENOMSG(83),
   EILSEQ(86),
   EBADMSG(89),
   EMULTIHOP(90),
   ENOLINK(91),
   EPROTO(92);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 92L;

   private Errno(int value) {
      this.value = value;
   }

   public final String toString() {
      return (String)Errno.StringTable.descriptions.get(this);
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
         map.put(Errno.EBUSY, "Device busy");
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
         map.put(Errno.EOPNOTSUPP, "Operation not supported");
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
         map.put(Errno.EMULTIHOP, "Multihop attempted");
         map.put(Errno.ENOLINK, "Link has been severed");
         map.put(Errno.EPROTO, "Protocol error");
         return map;
      }
   }
}
