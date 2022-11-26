package jnr.constants.platform.windows;

import java.util.EnumMap;
import java.util.Map;
import jnr.constants.Constant;

public enum Errno implements Constant {
   E2BIG(7),
   ENFILE(23),
   EPERM(1),
   EDEADLK(36),
   EBADF(9),
   EMFILE(24),
   ENXIO(6),
   ECHILD(10),
   ERANGE(34),
   EBUSY(16),
   EAGAIN(11),
   ESPIPE(29),
   EFBIG(27),
   EINVAL(22),
   EXDEV(18),
   EACCES(13),
   EIO(5),
   EDOM(33),
   ENOTEMPTY(41),
   ENOLCK(39),
   EINTR(4),
   ENOTDIR(20),
   EROFS(30),
   EISDIR(21),
   ENOMEM(12),
   ENODEV(19),
   ENOSPC(28),
   ENOEXEC(8),
   ENOSYS(40),
   EEXIST(17),
   ENOTTY(25),
   ENAMETOOLONG(38),
   EPIPE(32),
   ESRCH(3),
   EILSEQ(42),
   EFAULT(14),
   ENOENT(2),
   EMLINK(31);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 42L;

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
         map.put(Errno.E2BIG, "Arg list too long");
         map.put(Errno.ENFILE, "Too many open files in system");
         map.put(Errno.EPERM, "Operation not permitted");
         map.put(Errno.EDEADLK, "Resource deadlock avoided");
         map.put(Errno.EBADF, "Bad file descriptor");
         map.put(Errno.EMFILE, "Too many open files");
         map.put(Errno.ENXIO, "No such device or address");
         map.put(Errno.ECHILD, "No child processes");
         map.put(Errno.ERANGE, "Result too large");
         map.put(Errno.EBUSY, "Resource device");
         map.put(Errno.EAGAIN, "Resource temporarily unavailable");
         map.put(Errno.ESPIPE, "Invalid seek");
         map.put(Errno.EFBIG, "File too large");
         map.put(Errno.EINVAL, "Invalid argument");
         map.put(Errno.EXDEV, "Improper link");
         map.put(Errno.EACCES, "Permission denied");
         map.put(Errno.EIO, "Input/output error");
         map.put(Errno.EDOM, "Domain error");
         map.put(Errno.ENOTEMPTY, "Directory not empty");
         map.put(Errno.ENOLCK, "No locks available");
         map.put(Errno.EINTR, "Interrupted function call");
         map.put(Errno.ENOTDIR, "Not a directory");
         map.put(Errno.EROFS, "Read-only file system");
         map.put(Errno.EISDIR, "Is a directory");
         map.put(Errno.ENOMEM, "Not enough space");
         map.put(Errno.ENODEV, "No such device");
         map.put(Errno.ENOSPC, "No space left on device");
         map.put(Errno.ENOEXEC, "Exec format error");
         map.put(Errno.ENOSYS, "Function not implemented");
         map.put(Errno.EEXIST, "File exists");
         map.put(Errno.ENOTTY, "Inappropriate I/O control operation");
         map.put(Errno.ENAMETOOLONG, "Filename too long");
         map.put(Errno.EPIPE, "Broken pipe");
         map.put(Errno.ESRCH, "No such process");
         map.put(Errno.EILSEQ, "Illegal byte sequence");
         map.put(Errno.EFAULT, "Bad address");
         map.put(Errno.ENOENT, "No such file or directory");
         map.put(Errno.EMLINK, "Too many links");
         return map;
      }
   }
}
