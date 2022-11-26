package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum Sysconf implements Constant {
   _SC_ARG_MAX(1),
   _SC_CHILD_MAX(2),
   _SC_CLK_TCK(3),
   _SC_NGROUPS_MAX(4),
   _SC_OPEN_MAX(5),
   _SC_JOB_CONTROL(6),
   _SC_SAVED_IDS(7),
   _SC_VERSION(8),
   _SC_BC_BASE_MAX(9),
   _SC_BC_DIM_MAX(10),
   _SC_BC_SCALE_MAX(11),
   _SC_BC_STRING_MAX(12),
   _SC_COLL_WEIGHTS_MAX(13),
   _SC_EXPR_NEST_MAX(14),
   _SC_LINE_MAX(15),
   _SC_RE_DUP_MAX(16),
   _SC_2_VERSION(17),
   _SC_2_C_BIND(18),
   _SC_2_C_DEV(19),
   _SC_2_CHAR_TERM(20),
   _SC_2_FORT_DEV(21),
   _SC_2_FORT_RUN(22),
   _SC_2_LOCALEDEF(23),
   _SC_2_SW_DEV(24),
   _SC_2_UPE(25),
   _SC_STREAM_MAX(26),
   _SC_TZNAME_MAX(27),
   _SC_PAGESIZE(28),
   _SC_FSYNC(29),
   _SC_SEM_NSEMS_MAX(31),
   _SC_SEM_VALUE_MAX(32),
   _SC_PAGE_SIZE(28),
   _SC_XOPEN_SHM(30);

   private final int value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 32L;

   private Sysconf(int value) {
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
