package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum OpenFlags implements Constant {
   O_RDONLY(0L),
   O_WRONLY(1L),
   O_RDWR(2L),
   O_ACCMODE(6291459L),
   O_NONBLOCK(128L),
   O_APPEND(8L),
   O_SYNC(16L),
   O_NOFOLLOW(131072L),
   O_CREAT(256L),
   O_TRUNC(512L),
   O_EXCL(1024L),
   O_DIRECTORY(16777216L),
   O_NOCTTY(2048L),
   O_CLOEXEC(8388608L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 16777216L;

   private OpenFlags(long value) {
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
