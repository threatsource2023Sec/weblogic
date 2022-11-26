package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum Fcntl implements Constant {
   F_DUPFD(0L),
   F_GETFD(1L),
   F_SETFD(2L),
   F_GETFL(3L),
   F_SETFL(4L),
   F_GETOWN(23L),
   F_SETOWN(24L),
   F_GETLK(33L),
   F_SETLK(34L),
   F_SETLKW(35L),
   F_RDLCK(1L),
   F_UNLCK(3L),
   F_WRLCK(2L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 35L;

   private Fcntl(long value) {
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
