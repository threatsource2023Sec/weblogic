package jnr.constants.platform.darwin;

import jnr.constants.Constant;

public enum LangInfo implements Constant {
   CODESET(0L),
   D_T_FMT(1L),
   D_FMT(2L),
   T_FMT(3L),
   DAY_1(7L),
   DAY_2(8L),
   DAY_3(9L),
   DAY_4(10L),
   DAY_5(11L),
   DAY_6(12L),
   DAY_7(13L),
   ABDAY_1(14L),
   ABDAY_2(15L),
   ABDAY_3(16L),
   ABDAY_4(17L),
   ABDAY_5(18L),
   ABDAY_6(19L),
   ABDAY_7(20L),
   MON_1(21L),
   MON_2(22L),
   MON_3(23L),
   MON_4(24L),
   MON_5(25L),
   MON_6(26L),
   MON_7(27L),
   MON_8(28L),
   MON_9(29L),
   MON_10(30L),
   MON_11(31L),
   MON_12(32L),
   ABMON_1(33L),
   ABMON_2(34L),
   ABMON_3(35L),
   ABMON_4(36L),
   ABMON_5(37L),
   ABMON_6(38L),
   ABMON_7(39L),
   ABMON_8(40L),
   ABMON_9(41L),
   ABMON_10(42L),
   ABMON_11(43L),
   ABMON_12(44L),
   RADIXCHAR(50L),
   THOUSEP(51L),
   YESEXPR(52L),
   NOEXPR(53L),
   CRNCYSTR(56L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 56L;

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
