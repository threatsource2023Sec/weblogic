package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum Fcntl implements Constant {
   FAPPEND(1024),
   FASYNC(8192),
   FFSYNC(4096),
   FNONBLOCK(2048),
   FNDELAY(2048),
   F_DUPFD(0),
   F_GETFD(1),
   F_SETFD(2),
   F_GETFL(3),
   F_SETFL(4),
   F_GETOWN(9),
   F_SETOWN(8),
   F_GETLK(12),
   F_SETLK(13),
   F_SETLKW(14),
   F_RDLCK(0),
   F_UNLCK(2),
   F_WRLCK(1);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 8192L;

   private Fcntl(int value) {
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
