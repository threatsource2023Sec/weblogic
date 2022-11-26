package jnr.constants.platform.freebsd;

import jnr.constants.Constant;

public enum OpenFlags implements Constant {
   O_RDONLY(0),
   O_WRONLY(1),
   O_RDWR(2),
   O_ACCMODE(3),
   O_NONBLOCK(4),
   O_APPEND(8),
   O_SYNC(128),
   O_SHLOCK(16),
   O_EXLOCK(32),
   O_ASYNC(64),
   O_FSYNC(128),
   O_NOFOLLOW(256),
   O_CREAT(512),
   O_TRUNC(1024),
   O_EXCL(2048),
   O_CLOEXEC(1048576);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 2048L;

   private OpenFlags(int value) {
      this.value = value;
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
}
