package jnr.constants.platform;

import jnr.constants.Constant;

public enum OpenFlags implements Constant {
   O_RDONLY,
   O_WRONLY,
   O_RDWR,
   O_ACCMODE,
   O_NONBLOCK,
   O_APPEND,
   O_SYNC,
   O_SHLOCK,
   O_EXLOCK,
   O_ASYNC,
   O_FSYNC,
   O_NOFOLLOW,
   O_CREAT,
   O_TRUNC,
   O_EXCL,
   O_EVTONLY,
   O_DIRECTORY,
   O_SYMLINK,
   O_BINARY,
   O_NOCTTY,
   O_TMPFILE,
   O_CLOEXEC,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getBitmaskResolver(OpenFlags.class);

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

   public static OpenFlags valueOf(long value) {
      return (OpenFlags)resolver.valueOf(value);
   }
}
