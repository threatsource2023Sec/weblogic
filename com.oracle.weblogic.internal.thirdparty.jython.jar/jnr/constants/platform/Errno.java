package jnr.constants.platform;

import jnr.constants.Constant;

public enum Errno implements Constant {
   EPERM,
   ENOENT,
   ESRCH,
   EINTR,
   EIO,
   ENXIO,
   E2BIG,
   ENOEXEC,
   EBADF,
   ECHILD,
   EDEADLK,
   ENOMEM,
   EACCES,
   EFAULT,
   ENOTBLK,
   EBUSY,
   EEXIST,
   EXDEV,
   ENODEV,
   ENOTDIR,
   EISDIR,
   EINVAL,
   ENFILE,
   EMFILE,
   ENOTTY,
   ETXTBSY,
   EFBIG,
   ENOSPC,
   ESPIPE,
   EROFS,
   EMLINK,
   EPIPE,
   EDOM,
   ERANGE,
   EWOULDBLOCK,
   EAGAIN,
   EINPROGRESS,
   EALREADY,
   ENOTSOCK,
   EDESTADDRREQ,
   EMSGSIZE,
   EPROTOTYPE,
   ENOPROTOOPT,
   EPROTONOSUPPORT,
   ESOCKTNOSUPPORT,
   EOPNOTSUPP,
   EPFNOSUPPORT,
   EAFNOSUPPORT,
   EADDRINUSE,
   EADDRNOTAVAIL,
   ENETDOWN,
   ENETUNREACH,
   ENETRESET,
   ECONNABORTED,
   ECONNRESET,
   ENOBUFS,
   EISCONN,
   ENOTCONN,
   ESHUTDOWN,
   ETOOMANYREFS,
   ETIMEDOUT,
   ECONNREFUSED,
   ELOOP,
   ENAMETOOLONG,
   EHOSTDOWN,
   EHOSTUNREACH,
   ENOTEMPTY,
   EUSERS,
   EDQUOT,
   ESTALE,
   EREMOTE,
   ENOLCK,
   ENOSYS,
   EOVERFLOW,
   EIDRM,
   ENOMSG,
   EILSEQ,
   EBADMSG,
   EMULTIHOP,
   ENODATA,
   ENOLINK,
   ENOSR,
   ENOSTR,
   EPROTO,
   ETIME,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(Errno.class, 20000, 20999);

   public final int intValue() {
      return (int)resolver.longValue(this);
   }

   public final long longValue() {
      return resolver.longValue(this);
   }

   public final String description() {
      return resolver.description(this);
   }

   public final boolean defined() {
      return resolver.defined(this);
   }

   public final String toString() {
      return this.description();
   }

   public static Errno valueOf(long value) {
      return (Errno)resolver.valueOf(value);
   }
}
