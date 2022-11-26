package org.python.icu.text;

import java.util.Locale;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.util.Calendar;
import org.python.icu.util.ChineseCalendar;
import org.python.icu.util.ULocale;

/** @deprecated */
@Deprecated
public class ChineseDateFormatSymbols extends DateFormatSymbols {
   static final long serialVersionUID = 6827816119783952890L;
   String[] isLeapMonth;

   /** @deprecated */
   @Deprecated
   public ChineseDateFormatSymbols() {
      this(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormatSymbols(Locale locale) {
      super(ChineseCalendar.class, ULocale.forLocale(locale));
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormatSymbols(ULocale locale) {
      super(ChineseCalendar.class, locale);
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormatSymbols(Calendar cal, Locale locale) {
      super(cal.getClass(), locale);
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormatSymbols(Calendar cal, ULocale locale) {
      super(cal.getClass(), locale);
   }

   /** @deprecated */
   @Deprecated
   public String getLeapMonth(int leap) {
      return this.isLeapMonth[leap];
   }

   /** @deprecated */
   @Deprecated
   protected void initializeData(ULocale loc, ICUResourceBundle b, String calendarType) {
      super.initializeData(loc, b, calendarType);
      this.initializeIsLeapMonth();
   }

   void initializeData(DateFormatSymbols dfs) {
      super.initializeData(dfs);
      if (dfs instanceof ChineseDateFormatSymbols) {
         this.isLeapMonth = ((ChineseDateFormatSymbols)dfs).isLeapMonth;
      } else {
         this.initializeIsLeapMonth();
      }

   }

   private void initializeIsLeapMonth() {
      this.isLeapMonth = new String[2];
      this.isLeapMonth[0] = "";
      this.isLeapMonth[1] = this.leapMonthPatterns != null ? this.leapMonthPatterns[0].replace("{0}", "") : "";
   }
}
