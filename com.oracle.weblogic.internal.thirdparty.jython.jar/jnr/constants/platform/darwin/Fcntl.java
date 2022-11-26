package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum Fcntl implements Constant {
   F_DUPFD(0L),
   F_GETFD(1L),
   F_SETFD(2L),
   F_GETFL(3L),
   F_SETFL(4L),
   F_GETOWN(5L),
   F_SETOWN(6L),
   F_GETLK(7L),
   F_SETLK(8L),
   F_SETLKW(9L),
   F_CHKCLEAN(41L),
   F_PREALLOCATE(42L),
   F_SETSIZE(43L),
   F_RDADVISE(44L),
   F_RDAHEAD(45L),
   F_READBOOTSTRAP(46L),
   F_WRITEBOOTSTRAP(47L),
   F_NOCACHE(48L),
   F_LOG2PHYS(49L),
   F_GETPATH(50L),
   F_FULLFSYNC(51L),
   F_PATHPKG_CHECK(52L),
   F_FREEZE_FS(53L),
   F_THAW_FS(54L),
   F_GLOBAL_NOCACHE(55L),
   F_ADDSIGS(59L),
   F_MARKDEPENDENCY(60L),
   F_RDLCK(1L),
   F_UNLCK(2L),
   F_WRLCK(3L),
   F_ALLOCATECONTIG(2L),
   F_ALLOCATEALL(4L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 60L;

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
