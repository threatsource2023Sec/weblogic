package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum Fcntl implements Constant {
   FREAD(1),
   FWRITE(2),
   FAPPEND(8),
   FASYNC(64),
   FFSYNC(128),
   FNONBLOCK(4),
   FNDELAY(4),
   F_DUPFD(0),
   F_GETFD(1),
   F_SETFD(2),
   F_GETFL(3),
   F_SETFL(4),
   F_GETOWN(5),
   F_SETOWN(6),
   F_GETLK(7),
   F_SETLK(8),
   F_SETLKW(9),
   F_RDLCK(1),
   F_UNLCK(2),
   F_WRLCK(3);

   private final int value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 128L;

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
