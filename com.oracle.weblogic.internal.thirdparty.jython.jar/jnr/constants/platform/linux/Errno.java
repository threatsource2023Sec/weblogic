package jnr.constants.platform.linux;

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
   EDEADLK(35),
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
   EWOULDBLOCK(11),
   EAGAIN(11),
   EINPROGRESS(115),
   EALREADY(114),
   ENOTSOCK(88),
   EDESTADDRREQ(89),
   EMSGSIZE(90),
   EPROTOTYPE(91),
   ENOPROTOOPT(92),
   EPROTONOSUPPORT(93),
   ESOCKTNOSUPPORT(94),
   EOPNOTSUPP(95),
   EPFNOSUPPORT(96),
   EAFNOSUPPORT(97),
   EADDRINUSE(98),
   EADDRNOTAVAIL(99),
   ENETDOWN(100),
   ENETUNREACH(101),
   ENETRESET(102),
   ECONNABORTED(103),
   ECONNRESET(104),
   ENOBUFS(105),
   EISCONN(106),
   ENOTCONN(107),
   ESHUTDOWN(108),
   ETOOMANYREFS(109),
   ETIMEDOUT(110),
   ECONNREFUSED(111),
   ELOOP(40),
   ENAMETOOLONG(36),
   EHOSTDOWN(112),
   EHOSTUNREACH(113),
   ENOTEMPTY(39),
   EUSERS(87),
   EDQUOT(122),
   ESTALE(116),
   EREMOTE(66),
   ENOLCK(37),
   ENOSYS(38),
   EOVERFLOW(75),
   EIDRM(43),
   ENOMSG(42),
   EILSEQ(84),
   EBADMSG(74),
   EMULTIHOP(72),
   ENODATA(61),
   ENOLINK(67),
   ENOSR(63),
   ENOSTR(60),
   EPROTO(71),
   ETIME(62);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 122L;

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
         map.put(Errno.ENXIO, "No such device or address");
         map.put(Errno.E2BIG, "Argument list too long");
         map.put(Errno.ENOEXEC, "Exec format error");
         map.put(Errno.EBADF, "Bad file descriptor");
         map.put(Errno.ECHILD, "No child processes");
         map.put(Errno.EDEADLK, "Resource deadlock avoided");
         map.put(Errno.ENOMEM, "Cannot allocate memory");
         map.put(Errno.EACCES, "Permission denied");
         map.put(Errno.EFAULT, "Bad address");
         map.put(Errno.ENOTBLK, "Block device required");
         map.put(Errno.EBUSY, "Device or resource busy");
         map.put(Errno.EEXIST, "File exists");
         map.put(Errno.EXDEV, "Invalid cross-device link");
         map.put(Errno.ENODEV, "No such device");
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
         map.put(Errno.ERANGE, "Numerical result out of range");
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
         map.put(Errno.EAFNOSUPPORT, "Address family not supported by protocol");
         map.put(Errno.EADDRINUSE, "Address already in use");
         map.put(Errno.EADDRNOTAVAIL, "Cannot assign requested address");
         map.put(Errno.ENETDOWN, "Network is down");
         map.put(Errno.ENETUNREACH, "Network is unreachable");
         map.put(Errno.ENETRESET, "Network dropped connection on reset");
         map.put(Errno.ECONNABORTED, "Software caused connection abort");
         map.put(Errno.ECONNRESET, "Connection reset by peer");
         map.put(Errno.ENOBUFS, "No buffer space available");
         map.put(Errno.EISCONN, "Transport endpoint is already connected");
         map.put(Errno.ENOTCONN, "Transport endpoint is not connected");
         map.put(Errno.ESHUTDOWN, "Cannot send after transport endpoint shutdown");
         map.put(Errno.ETOOMANYREFS, "Too many references: cannot splice");
         map.put(Errno.ETIMEDOUT, "Connection timed out");
         map.put(Errno.ECONNREFUSED, "Connection refused");
         map.put(Errno.ELOOP, "Too many levels of symbolic links");
         map.put(Errno.ENAMETOOLONG, "File name too long");
         map.put(Errno.EHOSTDOWN, "Host is down");
         map.put(Errno.EHOSTUNREACH, "No route to host");
         map.put(Errno.ENOTEMPTY, "Directory not empty");
         map.put(Errno.EUSERS, "Too many users");
         map.put(Errno.EDQUOT, "Disk quota exceeded");
         map.put(Errno.ESTALE, "Stale NFS file handle");
         map.put(Errno.EREMOTE, "Object is remote");
         map.put(Errno.ENOLCK, "No locks available");
         map.put(Errno.ENOSYS, "Function not implemented");
         map.put(Errno.EOVERFLOW, "Value too large for defined data type");
         map.put(Errno.EIDRM, "Identifier removed");
         map.put(Errno.ENOMSG, "No message of desired type");
         map.put(Errno.EILSEQ, "Invalid or incomplete multibyte or wide character");
         map.put(Errno.EBADMSG, "Bad message");
         map.put(Errno.EMULTIHOP, "Multihop attempted");
         map.put(Errno.ENODATA, "No data available");
         map.put(Errno.ENOLINK, "Link has been severed");
         map.put(Errno.ENOSR, "Out of streams resources");
         map.put(Errno.ENOSTR, "Device not a stream");
         map.put(Errno.EPROTO, "Protocol error");
         map.put(Errno.ETIME, "Timer expired");
         return map;
      }
   }
}
