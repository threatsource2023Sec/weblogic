package jnr.constants.platform;

import jnr.constants.Constant;

public enum PosixFadvise implements Constant {
   POSIX_FADV_NORMAL,
   POSIX_FADV_SEQUENTIAL,
   POSIX_FADV_RANDOM,
   POSIX_FADV_NOREUSE,
   POSIX_FADV_WILLNEED,
   POSIX_FADV_DONTNEED,
   __UNKNOWN_CONSTANT__;

   private static final ConstantResolver resolver = ConstantResolver.getResolver(PosixFadvise.class, 20000, 29999);

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

   public static PosixFadvise valueOf(long value) {
      return (PosixFadvise)resolver.valueOf(value);
   }
}
