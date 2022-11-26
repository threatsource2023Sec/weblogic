package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum OpenFlags implements Constant {
   O_RDONLY(0L),
   O_WRONLY(1L),
   O_RDWR(2L),
   O_ACCMODE(3L),
   O_NONBLOCK(4L),
   O_APPEND(8L),
   O_SYNC(128L),
   O_SHLOCK(16L),
   O_EXLOCK(32L),
   O_ASYNC(64L),
   O_FSYNC(128L),
   O_NOFOLLOW(256L),
   O_CREAT(512L),
   O_TRUNC(1024L),
   O_EXCL(2048L),
   O_EVTONLY(32768L),
   O_DIRECTORY(1048576L),
   O_SYMLINK(2097152L),
   O_NOCTTY(131072L),
   O_CLOEXEC(16777216L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 2097152L;

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
