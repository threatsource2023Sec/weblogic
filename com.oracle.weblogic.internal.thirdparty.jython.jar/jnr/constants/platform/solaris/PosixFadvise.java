package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum PosixFadvise implements Constant {
   POSIX_FADV_NORMAL(0L),
   POSIX_FADV_SEQUENTIAL(2L),
   POSIX_FADV_RANDOM(1L),
   POSIX_FADV_NOREUSE(5L),
   POSIX_FADV_WILLNEED(3L),
   POSIX_FADV_DONTNEED(4L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 5L;

   private PosixFadvise(long value) {
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
