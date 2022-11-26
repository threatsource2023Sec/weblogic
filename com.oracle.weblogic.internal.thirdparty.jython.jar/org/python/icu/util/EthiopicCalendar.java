package org.python.icu.util;

import java.util.Date;
import java.util.Locale;
import org.python.icu.impl.CalendarUtil;

public final class EthiopicCalendar extends CECalendar {
   private static final long serialVersionUID = -2438495771339315608L;
   public static final int MESKEREM = 0;
   public static final int TEKEMT = 1;
   public static final int HEDAR = 2;
   public static final int TAHSAS = 3;
   public static final int TER = 4;
   public static final int YEKATIT = 5;
   public static final int MEGABIT = 6;
   public static final int MIAZIA = 7;
   public static final int GENBOT = 8;
   public static final int SENE = 9;
   public static final int HAMLE = 10;
   public static final int NEHASSE = 11;
   public static final int PAGUMEN = 12;
   private static final int JD_EPOCH_OFFSET_AMETE_MIHRET = 1723856;
   private static final int AMETE_MIHRET_DELTA = 5500;
   private static final int AMETE_ALEM = 0;
   private static final int AMETE_MIHRET = 1;
   private static final int AMETE_MIHRET_ERA = 0;
   private static final int AMETE_ALEM_ERA = 1;
   private int eraType;

   public EthiopicCalendar() {
      this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public EthiopicCalendar(TimeZone zone) {
      this(zone, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public EthiopicCalendar(Locale aLocale) {
      this(TimeZone.getDefault(), aLocale);
   }

   public EthiopicCalendar(ULocale locale) {
      this(TimeZone.getDefault(), locale);
   }

   public EthiopicCalendar(TimeZone zone, Locale aLocale) {
      this(zone, ULocale.forLocale(aLocale));
   }

   public EthiopicCalendar(TimeZone zone, ULocale locale) {
      super(zone, locale);
      this.eraType = 0;
      this.setCalcTypeForLocale(locale);
   }

   public EthiopicCalendar(int year, int month, int date) {
      super(year, month, date);
      this.eraType = 0;
   }

   public EthiopicCalendar(Date date) {
      super(date);
      this.eraType = 0;
   }

   public EthiopicCalendar(int year, int month, int date, int hour, int minute, int second) {
      super(year, month, date, hour, minute, second);
      this.eraType = 0;
   }

   public String getType() {
      return this.isAmeteAlemEra() ? "ethiopic-amete-alem" : "ethiopic";
   }

   public void setAmeteAlemEra(boolean onOff) {
      this.eraType = onOff ? 1 : 0;
   }

   public boolean isAmeteAlemEra() {
      return this.eraType == 1;
   }

   /** @deprecated */
   @Deprecated
   protected int handleGetExtendedYear() {
      int eyear;
      if (this.newerField(19, 1) == 19) {
         eyear = this.internalGet(19, 1);
      } else if (this.isAmeteAlemEra()) {
         eyear = this.internalGet(1, 5501) - 5500;
      } else {
         int era = this.internalGet(0, 1);
         if (era == 1) {
            eyear = this.internalGet(1, 1);
         } else {
            eyear = this.internalGet(1, 1) - 5500;
         }
      }

      return eyear;
   }

   /** @deprecated */
   @Deprecated
   protected void handleComputeFields(int julianDay) {
      int[] fields = new int[3];
      jdToCE(julianDay, this.getJDEpochOffset(), fields);
      byte era;
      int year;
      if (this.isAmeteAlemEra()) {
         era = 0;
         year = fields[0] + 5500;
      } else if (fields[0] > 0) {
         era = 1;
         year = fields[0];
      } else {
         era = 0;
         year = fields[0] + 5500;
      }

      this.internalSet(19, fields[0]);
      this.internalSet(0, era);
      this.internalSet(1, year);
      this.internalSet(2, fields[1]);
      this.internalSet(5, fields[2]);
      this.internalSet(6, 30 * fields[1] + fields[2]);
   }

   /** @deprecated */
   @Deprecated
   protected int handleGetLimit(int field, int limitType) {
      return this.isAmeteAlemEra() && field == 0 ? 0 : super.handleGetLimit(field, limitType);
   }

   /** @deprecated */
   @Deprecated
   protected int getJDEpochOffset() {
      return 1723856;
   }

   public static int EthiopicToJD(long year, int month, int date) {
      return ceToJD(year, month, date, 1723856);
   }

   private void setCalcTypeForLocale(ULocale locale) {
      String localeCalType = CalendarUtil.getCalendarType(locale);
      if ("ethiopic-amete-alem".equals(localeCalType)) {
         this.setAmeteAlemEra(true);
      } else {
         this.setAmeteAlemEra(false);
      }

   }
}
