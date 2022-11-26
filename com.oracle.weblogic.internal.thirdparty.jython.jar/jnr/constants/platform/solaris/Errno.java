package jnr.constants.platform.solaris;

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
   EDEADLK(45L),
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
   EWOULDBLOCK(11L),
   EAGAIN(11L),
   EINPROGRESS(150L),
   EALREADY(149L),
   ENOTSOCK(95L),
   EDESTADDRREQ(96L),
   EMSGSIZE(97L),
   EPROTOTYPE(98L),
   ENOPROTOOPT(99L),
   EPROTONOSUPPORT(120L),
   ESOCKTNOSUPPORT(121L),
   EOPNOTSUPP(122L),
   EPFNOSUPPORT(123L),
   EAFNOSUPPORT(124L),
   EADDRINUSE(125L),
   EADDRNOTAVAIL(126L),
   ENETDOWN(127L),
   ENETUNREACH(128L),
   ENETRESET(129L),
   ECONNABORTED(130L),
   ECONNRESET(131L),
   ENOBUFS(132L),
   EISCONN(133L),
   ENOTCONN(134L),
   ESHUTDOWN(143L),
   ETOOMANYREFS(144L),
   ETIMEDOUT(145L),
   ECONNREFUSED(146L),
   ELOOP(90L),
   ENAMETOOLONG(78L),
   EHOSTDOWN(147L),
   EHOSTUNREACH(148L),
   ENOTEMPTY(93L),
   EUSERS(94L),
   EDQUOT(49L),
   ESTALE(151L),
   EREMOTE(66L),
   ENOLCK(46L),
   ENOSYS(89L),
   EOVERFLOW(79L),
   EIDRM(36L),
   ENOMSG(35L),
   EILSEQ(88L),
   EBADMSG(77L),
   EMULTIHOP(74L),
   ENODATA(61L),
   ENOLINK(67L),
   ENOSR(63L),
   ENOSTR(60L),
   EPROTO(71L),
   ETIME(62L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 151L;

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
         map.put(Errno.EPERM, "Not owner");
         map.put(Errno.ENOENT, "No such file or directory");
         map.put(Errno.ESRCH, "No such process");
         map.put(Errno.EINTR, "Interrupted system call");
         map.put(Errno.EIO, "I/O error");
         map.put(Errno.ENXIO, "No such device or address");
         map.put(Errno.E2BIG, "Arg list too long");
         map.put(Errno.ENOEXEC, "Exec format error");
         map.put(Errno.EBADF, "Bad file number");
         map.put(Errno.ECHILD, "No child processes");
         map.put(Errno.EDEADLK, "Deadlock situation detected/avoided");
         map.put(Errno.ENOMEM, "Not enough space");
         map.put(Errno.EACCES, "Permission denied");
         map.put(Errno.EFAULT, "Bad address");
         map.put(Errno.ENOTBLK, "Block device required");
         map.put(Errno.EBUSY, "Device busy");
         map.put(Errno.EEXIST, "File exists");
         map.put(Errno.EXDEV, "Cross-device link");
         map.put(Errno.ENODEV, "No such device");
         map.put(Errno.ENOTDIR, "Not a directory");
         map.put(Errno.EISDIR, "Is a directory");
         map.put(Errno.EINVAL, "Invalid argument");
         map.put(Errno.ENFILE, "File table overflow");
         map.put(Errno.EMFILE, "Too many open files");
         map.put(Errno.ENOTTY, "Inappropriate ioctl for device");
         map.put(Errno.ETXTBSY, "Text file busy");
         map.put(Errno.EFBIG, "File too large");
         map.put(Errno.ENOSPC, "No space left on device");
         map.put(Errno.ESPIPE, "Illegal seek");
         map.put(Errno.EROFS, "Read-only file system");
         map.put(Errno.EMLINK, "Too many links");
         map.put(Errno.EPIPE, "Broken pipe");
         map.put(Errno.EDOM, "Argument out of domain");
         map.put(Errno.ERANGE, "Result too large");
         map.put(Errno.EWOULDBLOCK, "Resource temporarily unavailable");
         map.put(Errno.EAGAIN, "Resource temporarily unavailable");
         map.put(Errno.EINPROGRESS, "Operation now in progress");
         map.put(Errno.EALREADY, "Operation already in progress");
         map.put(Errno.ENOTSOCK, "Socket operation on non-socket");
         map.put(Errno.EDESTADDRREQ, "Destination address required");
         map.put(Errno.EMSGSIZE, "Message too long");
         map.put(Errno.EPROTOTYPE, "Protocol wrong type for socket");
         map.put(Errno.ENOPROTOOPT, "Option not supported by protocol");
         map.put(Errno.EPROTONOSUPPORT, "Protocol not supported");
         map.put(Errno.ESOCKTNOSUPPORT, "Socket type not supported");
         map.put(Errno.EOPNOTSUPP, "Operation not supported on transport endpoint");
         map.put(Errno.EPFNOSUPPORT, "Protocol family not supported");
         map.put(Errno.EAFNOSUPPORT, "Address family not supported by protocol family");
         map.put(Errno.EADDRINUSE, "Address already in use");
         map.put(Errno.EADDRNOTAVAIL, "Cannot assign requested address");
         map.put(Errno.ENETDOWN, "Network is down");
         map.put(Errno.ENETUNREACH, "Network is unreachable");
         map.put(Errno.ENETRESET, "Network dropped connection because of reset");
         map.put(Errno.ECONNABORTED, "Software caused connection abort");
         map.put(Errno.ECONNRESET, "Connection reset by peer");
         map.put(Errno.ENOBUFS, "No buffer space available");
         map.put(Errno.EISCONN, "Transport endpoint is already connected");
         map.put(Errno.ENOTCONN, "Transport endpoint is not connected");
         map.put(Errno.ESHUTDOWN, "Cannot send after socket shutdown");
         map.put(Errno.ETOOMANYREFS, "Too many references: cannot splice");
         map.put(Errno.ETIMEDOUT, "Connection timed out");
         map.put(Errno.ECONNREFUSED, "Connection refused");
         map.put(Errno.ELOOP, "Number of symbolic links encountered during path name traversal exceeds MAXSYMLINKS");
         map.put(Errno.ENAMETOOLONG, "File name too long");
         map.put(Errno.EHOSTDOWN, "Host is down");
         map.put(Errno.EHOSTUNREACH, "No route to host");
         map.put(Errno.ENOTEMPTY, "Directory not empty");
         map.put(Errno.EUSERS, "Too many users");
         map.put(Errno.EDQUOT, "Disc quota exceeded");
         map.put(Errno.ESTALE, "Stale NFS file handle");
         map.put(Errno.EREMOTE, "Object is remote");
         map.put(Errno.ENOLCK, "No record locks available");
         map.put(Errno.ENOSYS, "Operation not applicable");
         map.put(Errno.EOVERFLOW, "Value too large for defined data type");
         map.put(Errno.EIDRM, "Identifier removed");
         map.put(Errno.ENOMSG, "No message of desired type");
         map.put(Errno.EILSEQ, "Illegal byte sequence");
         map.put(Errno.EBADMSG, "Not a data message");
         map.put(Errno.EMULTIHOP, "Multihop attempted");
         map.put(Errno.ENODATA, "No data available");
         map.put(Errno.ENOLINK, "Link has been severed");
         map.put(Errno.ENOSR, "Out of stream resources");
         map.put(Errno.ENOSTR, "Not a stream device");
         map.put(Errno.EPROTO, "Protocol error");
         map.put(Errno.ETIME, "Timer expired");
         return map;
      }
   }
}
