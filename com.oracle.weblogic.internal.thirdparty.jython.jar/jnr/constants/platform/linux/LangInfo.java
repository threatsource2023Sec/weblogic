package jnr.constants.platform.linux;

import jnr.constants.Constant;

public enum LangInfo implements Constant {
   CODESET(14L),
   D_T_FMT(131112L),
   D_FMT(131113L),
   T_FMT(131114L),
   DAY_1(131079L),
   DAY_2(131080L),
   DAY_3(131081L),
   DAY_4(131082L),
   DAY_5(131083L),
   DAY_6(131084L),
   DAY_7(131085L),
   ABDAY_1(131072L),
   ABDAY_2(131073L),
   ABDAY_3(131074L),
   ABDAY_4(131075L),
   ABDAY_5(131076L),
   ABDAY_6(131077L),
   ABDAY_7(131078L),
   MON_1(131098L),
   MON_2(131099L),
   MON_3(131100L),
   MON_4(131101L),
   MON_5(131102L),
   MON_6(131103L),
   MON_7(131104L),
   MON_8(131105L),
   MON_9(131106L),
   MON_10(131107L),
   MON_11(131108L),
   MON_12(131109L),
   ABMON_1(131086L),
   ABMON_2(131087L),
   ABMON_3(131088L),
   ABMON_4(131089L),
   ABMON_5(131090L),
   ABMON_6(131091L),
   ABMON_7(131092L),
   ABMON_8(131093L),
   ABMON_9(131094L),
   ABMON_10(131095L),
   ABMON_11(131096L),
   ABMON_12(131097L),
   RADIXCHAR(65536L),
   THOUSEP(65537L),
   YESEXPR(327680L),
   NOEXPR(327681L),
   CRNCYSTR(262159L);

   private final long value;
   public static final long MIN_VALUE = 14L;
   public static final long MAX_VALUE = 327681L;

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
