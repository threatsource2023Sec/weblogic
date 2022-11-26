package jnr.constants.platform.fake;

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
   F_CHKCLEAN(10L),
   F_PREALLOCATE(11L),
   F_SETSIZE(12L),
   F_RDADVISE(13L),
   F_RDAHEAD(14L),
   F_READBOOTSTRAP(15L),
   F_WRITEBOOTSTRAP(16L),
   F_NOCACHE(17L),
   F_LOG2PHYS(18L),
   F_GETPATH(19L),
   F_FULLFSYNC(20L),
   F_PATHPKG_CHECK(21L),
   F_FREEZE_FS(22L),
   F_THAW_FS(23L),
   F_GLOBAL_NOCACHE(24L),
   F_ADDSIGS(25L),
   F_MARKDEPENDENCY(26L),
   F_RDLCK(27L),
   F_UNLCK(28L),
   F_WRLCK(29L),
   F_ALLOCATECONTIG(30L),
   F_ALLOCATEALL(31L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 31L;

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
