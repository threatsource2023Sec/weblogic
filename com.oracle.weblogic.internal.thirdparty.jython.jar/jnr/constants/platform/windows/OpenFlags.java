package jnr.constants.platform.windows;

import jnr.constants.Constant;

public enum OpenFlags implements Constant {
   O_APPEND(8),
   O_ACCMODE(3),
   O_CREAT(256),
   O_WRONLY(1),
   O_EXCL(1024),
   O_RDONLY(0),
   O_BINARY(32768),
   O_RDWR(2),
   O_TRUNC(512);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 32768L;

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
