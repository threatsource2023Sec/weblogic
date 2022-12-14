package jnr.constants.platform.solaris;

import jnr.constants.Constant;

public enum LangInfo implements Constant {
   CODESET(49L),
   D_T_FMT(44L),
   D_FMT(45L),
   T_FMT(46L),
   DAY_1(1L),
   DAY_2(2L),
   DAY_3(3L),
   DAY_4(4L),
   DAY_5(5L),
   DAY_6(6L),
   DAY_7(7L),
   ABDAY_1(8L),
   ABDAY_2(9L),
   ABDAY_3(10L),
   ABDAY_4(11L),
   ABDAY_5(12L),
   ABDAY_6(13L),
   ABDAY_7(14L),
   MON_1(15L),
   MON_2(16L),
   MON_3(17L),
   MON_4(18L),
   MON_5(19L),
   MON_6(20L),
   MON_7(21L),
   MON_8(22L),
   MON_9(23L),
   MON_10(24L),
   MON_11(25L),
   MON_12(26L),
   ABMON_1(27L),
   ABMON_2(28L),
   ABMON_3(29L),
   ABMON_4(30L),
   ABMON_5(31L),
   ABMON_6(32L),
   ABMON_7(33L),
   ABMON_8(34L),
   ABMON_9(35L),
   ABMON_10(36L),
   ABMON_11(37L),
   ABMON_12(38L),
   RADIXCHAR(39L),
   THOUSEP(40L),
   YESEXPR(56L),
   NOEXPR(57L),
   CRNCYSTR(43L);

   private final long value;
   public static final long MIN_VALUE = 1L;
   public static final long MAX_VALUE = 57L;

   private LangInfo(long value) {
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
