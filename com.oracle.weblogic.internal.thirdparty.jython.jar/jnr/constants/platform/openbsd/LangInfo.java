package jnr.constants.platform.openbsd;

import jnr.constants.Constant;

public enum LangInfo implements Constant {
   CODESET(51L),
   D_T_FMT(0L),
   D_FMT(1L),
   T_FMT(2L),
   DAY_1(6L),
   DAY_2(7L),
   DAY_3(8L),
   DAY_4(9L),
   DAY_5(10L),
   DAY_6(11L),
   DAY_7(12L),
   ABDAY_1(13L),
   ABDAY_2(14L),
   ABDAY_3(15L),
   ABDAY_4(16L),
   ABDAY_5(17L),
   ABDAY_6(18L),
   ABDAY_7(19L),
   MON_1(20L),
   MON_2(21L),
   MON_3(22L),
   MON_4(23L),
   MON_5(24L),
   MON_6(25L),
   MON_7(26L),
   MON_8(27L),
   MON_9(28L),
   MON_10(29L),
   MON_11(30L),
   MON_12(31L),
   ABMON_1(32L),
   ABMON_2(33L),
   ABMON_3(34L),
   ABMON_4(35L),
   ABMON_5(36L),
   ABMON_6(37L),
   ABMON_7(38L),
   ABMON_8(39L),
   ABMON_9(40L),
   ABMON_10(41L),
   ABMON_11(42L),
   ABMON_12(43L),
   RADIXCHAR(44L),
   THOUSEP(45L),
   YESEXPR(47L),
   NOEXPR(49L),
   CRNCYSTR(50L);

   private final long value;
   public static final long MIN_VALUE = 0L;
   public static final long MAX_VALUE = 51L;

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
