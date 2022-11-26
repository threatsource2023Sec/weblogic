package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import org.python.icu.impl.CacheBase;
import org.python.icu.impl.CalendarUtil;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SoftCache;
import org.python.icu.impl.UResource;
import org.python.icu.impl.Utility;
import org.python.icu.util.Calendar;
import org.python.icu.util.ICUCloneNotSupportedException;
import org.python.icu.util.ICUException;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;

public class DateFormatSymbols implements Serializable, Cloneable {
   public static final int FORMAT = 0;
   public static final int STANDALONE = 1;
   /** @deprecated */
   @Deprecated
   public static final int NUMERIC = 2;
   /** @deprecated */
   @Deprecated
   public static final int DT_CONTEXT_COUNT = 3;
   public static final int ABBREVIATED = 0;
   public static final int WIDE = 1;
   public static final int NARROW = 2;
   public static final int SHORT = 3;
   /** @deprecated */
   @Deprecated
   public static final int DT_WIDTH_COUNT = 4;
   static final int DT_LEAP_MONTH_PATTERN_FORMAT_WIDE = 0;
   static final int DT_LEAP_MONTH_PATTERN_FORMAT_ABBREV = 1;
   static final int DT_LEAP_MONTH_PATTERN_FORMAT_NARROW = 2;
   static final int DT_LEAP_MONTH_PATTERN_STANDALONE_WIDE = 3;
   static final int DT_LEAP_MONTH_PATTERN_STANDALONE_ABBREV = 4;
   static final int DT_LEAP_MONTH_PATTERN_STANDALONE_NARROW = 5;
   static final int DT_LEAP_MONTH_PATTERN_NUMERIC = 6;
   static final int DT_MONTH_PATTERN_COUNT = 7;
   static final String DEFAULT_TIME_SEPARATOR = ":";
   static final String ALTERNATE_TIME_SEPARATOR = ".";
   String[] eras;
   String[] eraNames;
   String[] narrowEras;
   String[] months;
   String[] shortMonths;
   String[] narrowMonths;
   String[] standaloneMonths;
   String[] standaloneShortMonths;
   String[] standaloneNarrowMonths;
   String[] weekdays;
   String[] shortWeekdays;
   String[] shorterWeekdays;
   String[] narrowWeekdays;
   String[] standaloneWeekdays;
   String[] standaloneShortWeekdays;
   String[] standaloneShorterWeekdays;
   String[] standaloneNarrowWeekdays;
   String[] ampms;
   String[] ampmsNarrow;
   private String timeSeparator;
   String[] shortQuarters;
   String[] quarters;
   String[] standaloneShortQuarters;
   String[] standaloneQuarters;
   String[] leapMonthPatterns;
   String[] shortYearNames;
   String[] shortZodiacNames;
   private String[][] zoneStrings;
   static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
   String localPatternChars;
   String[] abbreviatedDayPeriods;
   String[] wideDayPeriods;
   String[] narrowDayPeriods;
   String[] standaloneAbbreviatedDayPeriods;
   String[] standaloneWideDayPeriods;
   String[] standaloneNarrowDayPeriods;
   private static final long serialVersionUID = -5987973545549424702L;
   private static final String[][] CALENDAR_CLASSES = new String[][]{{"GregorianCalendar", "gregorian"}, {"JapaneseCalendar", "japanese"}, {"BuddhistCalendar", "buddhist"}, {"TaiwanCalendar", "roc"}, {"PersianCalendar", "persian"}, {"IslamicCalendar", "islamic"}, {"HebrewCalendar", "hebrew"}, {"ChineseCalendar", "chinese"}, {"IndianCalendar", "indian"}, {"CopticCalendar", "coptic"}, {"EthiopicCalendar", "ethiopic"}};
   private static final Map contextUsageTypeMap = new HashMap();
   Map capitalization;
   static final int millisPerHour = 3600000;
   private static CacheBase DFSCACHE;
   private static final String[] LEAP_MONTH_PATTERNS_PATHS;
   private static final String[] DAY_PERIOD_KEYS;
   private ULocale requestedLocale;
   private ULocale validLocale;
   private ULocale actualLocale;

   public DateFormatSymbols() {
      this(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public DateFormatSymbols(Locale locale) {
      this(ULocale.forLocale(locale));
   }

   public DateFormatSymbols(ULocale locale) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      this.initializeData(locale, CalendarUtil.getCalendarType(locale));
   }

   public static DateFormatSymbols getInstance() {
      return new DateFormatSymbols();
   }

   public static DateFormatSymbols getInstance(Locale locale) {
      return new DateFormatSymbols(locale);
   }

   public static DateFormatSymbols getInstance(ULocale locale) {
      return new DateFormatSymbols(locale);
   }

   public static Locale[] getAvailableLocales() {
      return ICUResourceBundle.getAvailableLocales();
   }

   public static ULocale[] getAvailableULocales() {
      return ICUResourceBundle.getAvailableULocales();
   }

   public String[] getEras() {
      return this.duplicate(this.eras);
   }

   public void setEras(String[] newEras) {
      this.eras = this.duplicate(newEras);
   }

   public String[] getEraNames() {
      return this.duplicate(this.eraNames);
   }

   public void setEraNames(String[] newEraNames) {
      this.eraNames = this.duplicate(newEraNames);
   }

   public String[] getMonths() {
      return this.duplicate(this.months);
   }

   public String[] getMonths(int context, int width) {
      String[] returnValue;
      returnValue = null;
      label25:
      switch (context) {
         case 0:
            switch (width) {
               case 0:
               case 3:
                  returnValue = this.shortMonths;
                  break label25;
               case 1:
                  returnValue = this.months;
                  break label25;
               case 2:
                  returnValue = this.narrowMonths;
               default:
                  break label25;
            }
         case 1:
            switch (width) {
               case 0:
               case 3:
                  returnValue = this.standaloneShortMonths;
                  break;
               case 1:
                  returnValue = this.standaloneMonths;
                  break;
               case 2:
                  returnValue = this.standaloneNarrowMonths;
            }
      }

      if (returnValue == null) {
         throw new IllegalArgumentException("Bad context or width argument");
      } else {
         return this.duplicate(returnValue);
      }
   }

   public void setMonths(String[] newMonths) {
      this.months = this.duplicate(newMonths);
   }

   public void setMonths(String[] newMonths, int context, int width) {
      switch (context) {
         case 0:
            switch (width) {
               case 0:
                  this.shortMonths = this.duplicate(newMonths);
                  return;
               case 1:
                  this.months = this.duplicate(newMonths);
                  return;
               case 2:
                  this.narrowMonths = this.duplicate(newMonths);
                  return;
               default:
                  return;
            }
         case 1:
            switch (width) {
               case 0:
                  this.standaloneShortMonths = this.duplicate(newMonths);
                  break;
               case 1:
                  this.standaloneMonths = this.duplicate(newMonths);
                  break;
               case 2:
                  this.standaloneNarrowMonths = this.duplicate(newMonths);
            }
      }

   }

   public String[] getShortMonths() {
      return this.duplicate(this.shortMonths);
   }

   public void setShortMonths(String[] newShortMonths) {
      this.shortMonths = this.duplicate(newShortMonths);
   }

   public String[] getWeekdays() {
      return this.duplicate(this.weekdays);
   }

   public String[] getWeekdays(int context, int width) {
      String[] returnValue;
      returnValue = null;
      label37:
      switch (context) {
         case 0:
            switch (width) {
               case 0:
                  returnValue = this.shortWeekdays;
                  break label37;
               case 1:
                  returnValue = this.weekdays;
                  break label37;
               case 2:
                  returnValue = this.narrowWeekdays;
                  break label37;
               case 3:
                  returnValue = this.shorterWeekdays != null ? this.shorterWeekdays : this.shortWeekdays;
               default:
                  break label37;
            }
         case 1:
            switch (width) {
               case 0:
                  returnValue = this.standaloneShortWeekdays;
                  break;
               case 1:
                  returnValue = this.standaloneWeekdays;
                  break;
               case 2:
                  returnValue = this.standaloneNarrowWeekdays;
                  break;
               case 3:
                  returnValue = this.standaloneShorterWeekdays != null ? this.standaloneShorterWeekdays : this.standaloneShortWeekdays;
            }
      }

      if (returnValue == null) {
         throw new IllegalArgumentException("Bad context or width argument");
      } else {
         return this.duplicate(returnValue);
      }
   }

   public void setWeekdays(String[] newWeekdays, int context, int width) {
      switch (context) {
         case 0:
            switch (width) {
               case 0:
                  this.shortWeekdays = this.duplicate(newWeekdays);
                  return;
               case 1:
                  this.weekdays = this.duplicate(newWeekdays);
                  return;
               case 2:
                  this.narrowWeekdays = this.duplicate(newWeekdays);
                  return;
               case 3:
                  this.shorterWeekdays = this.duplicate(newWeekdays);
                  return;
               default:
                  return;
            }
         case 1:
            switch (width) {
               case 0:
                  this.standaloneShortWeekdays = this.duplicate(newWeekdays);
                  break;
               case 1:
                  this.standaloneWeekdays = this.duplicate(newWeekdays);
                  break;
               case 2:
                  this.standaloneNarrowWeekdays = this.duplicate(newWeekdays);
                  break;
               case 3:
                  this.standaloneShorterWeekdays = this.duplicate(newWeekdays);
            }
      }

   }

   public void setWeekdays(String[] newWeekdays) {
      this.weekdays = this.duplicate(newWeekdays);
   }

   public String[] getShortWeekdays() {
      return this.duplicate(this.shortWeekdays);
   }

   public void setShortWeekdays(String[] newAbbrevWeekdays) {
      this.shortWeekdays = this.duplicate(newAbbrevWeekdays);
   }

   public String[] getQuarters(int context, int width) {
      String[] returnValue;
      returnValue = null;
      label25:
      switch (context) {
         case 0:
            switch (width) {
               case 0:
               case 3:
                  returnValue = this.shortQuarters;
                  break label25;
               case 1:
                  returnValue = this.quarters;
                  break label25;
               case 2:
                  returnValue = null;
               default:
                  break label25;
            }
         case 1:
            switch (width) {
               case 0:
               case 3:
                  returnValue = this.standaloneShortQuarters;
                  break;
               case 1:
                  returnValue = this.standaloneQuarters;
                  break;
               case 2:
                  returnValue = null;
            }
      }

      if (returnValue == null) {
         throw new IllegalArgumentException("Bad context or width argument");
      } else {
         return this.duplicate(returnValue);
      }
   }

   public void setQuarters(String[] newQuarters, int context, int width) {
      switch (context) {
         case 0:
            switch (width) {
               case 0:
                  this.shortQuarters = this.duplicate(newQuarters);
                  return;
               case 1:
                  this.quarters = this.duplicate(newQuarters);
                  return;
               case 2:
               default:
                  return;
            }
         case 1:
            switch (width) {
               case 0:
                  this.standaloneShortQuarters = this.duplicate(newQuarters);
                  break;
               case 1:
                  this.standaloneQuarters = this.duplicate(newQuarters);
               case 2:
            }
      }

   }

   public String[] getYearNames(int context, int width) {
      return this.shortYearNames != null ? this.duplicate(this.shortYearNames) : null;
   }

   public void setYearNames(String[] yearNames, int context, int width) {
      if (context == 0 && width == 0) {
         this.shortYearNames = this.duplicate(yearNames);
      }

   }

   public String[] getZodiacNames(int context, int width) {
      return this.shortZodiacNames != null ? this.duplicate(this.shortZodiacNames) : null;
   }

   public void setZodiacNames(String[] zodiacNames, int context, int width) {
      if (context == 0 && width == 0) {
         this.shortZodiacNames = this.duplicate(zodiacNames);
      }

   }

   /** @deprecated */
   @Deprecated
   public String getLeapMonthPattern(int context, int width) {
      if (this.leapMonthPatterns != null) {
         byte leapMonthPatternIndex;
         leapMonthPatternIndex = -1;
         label29:
         switch (context) {
            case 0:
               switch (width) {
                  case 0:
                  case 3:
                     leapMonthPatternIndex = 1;
                     break label29;
                  case 1:
                     leapMonthPatternIndex = 0;
                     break label29;
                  case 2:
                     leapMonthPatternIndex = 2;
                  default:
                     break label29;
               }
            case 1:
               switch (width) {
                  case 0:
                  case 3:
                     leapMonthPatternIndex = 1;
                     break label29;
                  case 1:
                     leapMonthPatternIndex = 3;
                     break label29;
                  case 2:
                     leapMonthPatternIndex = 5;
                  default:
                     break label29;
               }
            case 2:
               leapMonthPatternIndex = 6;
         }

         if (leapMonthPatternIndex < 0) {
            throw new IllegalArgumentException("Bad context or width argument");
         } else {
            return this.leapMonthPatterns[leapMonthPatternIndex];
         }
      } else {
         return null;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setLeapMonthPattern(String leapMonthPattern, int context, int width) {
      if (this.leapMonthPatterns != null) {
         byte leapMonthPatternIndex;
         leapMonthPatternIndex = -1;
         label27:
         switch (context) {
            case 0:
               switch (width) {
                  case 0:
                     leapMonthPatternIndex = 1;
                     break label27;
                  case 1:
                     leapMonthPatternIndex = 0;
                     break label27;
                  case 2:
                     leapMonthPatternIndex = 2;
                  default:
                     break label27;
               }
            case 1:
               switch (width) {
                  case 0:
                     leapMonthPatternIndex = 1;
                     break label27;
                  case 1:
                     leapMonthPatternIndex = 3;
                     break label27;
                  case 2:
                     leapMonthPatternIndex = 5;
                  default:
                     break label27;
               }
            case 2:
               leapMonthPatternIndex = 6;
         }

         if (leapMonthPatternIndex >= 0) {
            this.leapMonthPatterns[leapMonthPatternIndex] = leapMonthPattern;
         }
      }

   }

   public String[] getAmPmStrings() {
      return this.duplicate(this.ampms);
   }

   public void setAmPmStrings(String[] newAmpms) {
      this.ampms = this.duplicate(newAmpms);
   }

   /** @deprecated */
   @Deprecated
   public String getTimeSeparatorString() {
      return this.timeSeparator;
   }

   /** @deprecated */
   @Deprecated
   public void setTimeSeparatorString(String newTimeSeparator) {
      this.timeSeparator = newTimeSeparator;
   }

   public String[][] getZoneStrings() {
      if (this.zoneStrings != null) {
         return this.duplicate(this.zoneStrings);
      } else {
         String[] tzIDs = TimeZone.getAvailableIDs();
         TimeZoneNames tznames = TimeZoneNames.getInstance(this.validLocale);
         tznames.loadAllDisplayNames();
         TimeZoneNames.NameType[] types = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT};
         long now = System.currentTimeMillis();
         String[][] array = new String[tzIDs.length][5];

         for(int i = 0; i < tzIDs.length; ++i) {
            String canonicalID = TimeZone.getCanonicalID(tzIDs[i]);
            if (canonicalID == null) {
               canonicalID = tzIDs[i];
            }

            array[i][0] = tzIDs[i];
            tznames.getDisplayNames(canonicalID, types, now, array[i], 1);
         }

         this.zoneStrings = array;
         return this.zoneStrings;
      }
   }

   public void setZoneStrings(String[][] newZoneStrings) {
      this.zoneStrings = this.duplicate(newZoneStrings);
   }

   public String getLocalPatternChars() {
      return this.localPatternChars;
   }

   public void setLocalPatternChars(String newLocalPatternChars) {
      this.localPatternChars = newLocalPatternChars;
   }

   public Object clone() {
      try {
         DateFormatSymbols other = (DateFormatSymbols)super.clone();
         return other;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }

   public int hashCode() {
      return this.requestedLocale.toString().hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         DateFormatSymbols that = (DateFormatSymbols)obj;
         return Utility.arrayEquals((Object[])this.eras, that.eras) && Utility.arrayEquals((Object[])this.eraNames, that.eraNames) && Utility.arrayEquals((Object[])this.months, that.months) && Utility.arrayEquals((Object[])this.shortMonths, that.shortMonths) && Utility.arrayEquals((Object[])this.narrowMonths, that.narrowMonths) && Utility.arrayEquals((Object[])this.standaloneMonths, that.standaloneMonths) && Utility.arrayEquals((Object[])this.standaloneShortMonths, that.standaloneShortMonths) && Utility.arrayEquals((Object[])this.standaloneNarrowMonths, that.standaloneNarrowMonths) && Utility.arrayEquals((Object[])this.weekdays, that.weekdays) && Utility.arrayEquals((Object[])this.shortWeekdays, that.shortWeekdays) && Utility.arrayEquals((Object[])this.shorterWeekdays, that.shorterWeekdays) && Utility.arrayEquals((Object[])this.narrowWeekdays, that.narrowWeekdays) && Utility.arrayEquals((Object[])this.standaloneWeekdays, that.standaloneWeekdays) && Utility.arrayEquals((Object[])this.standaloneShortWeekdays, that.standaloneShortWeekdays) && Utility.arrayEquals((Object[])this.standaloneShorterWeekdays, that.standaloneShorterWeekdays) && Utility.arrayEquals((Object[])this.standaloneNarrowWeekdays, that.standaloneNarrowWeekdays) && Utility.arrayEquals((Object[])this.ampms, that.ampms) && Utility.arrayEquals((Object[])this.ampmsNarrow, that.ampmsNarrow) && Utility.arrayEquals((Object[])this.abbreviatedDayPeriods, that.abbreviatedDayPeriods) && Utility.arrayEquals((Object[])this.wideDayPeriods, that.wideDayPeriods) && Utility.arrayEquals((Object[])this.narrowDayPeriods, that.narrowDayPeriods) && Utility.arrayEquals((Object[])this.standaloneAbbreviatedDayPeriods, that.standaloneAbbreviatedDayPeriods) && Utility.arrayEquals((Object[])this.standaloneWideDayPeriods, that.standaloneWideDayPeriods) && Utility.arrayEquals((Object[])this.standaloneNarrowDayPeriods, that.standaloneNarrowDayPeriods) && Utility.arrayEquals((Object)this.timeSeparator, that.timeSeparator) && arrayOfArrayEquals(this.zoneStrings, that.zoneStrings) && this.requestedLocale.getDisplayName().equals(that.requestedLocale.getDisplayName()) && Utility.arrayEquals((Object)this.localPatternChars, that.localPatternChars);
      } else {
         return false;
      }
   }

   protected void initializeData(ULocale desiredLocale, String type) {
      String key = desiredLocale.getBaseName() + '+' + type;
      String ns = desiredLocale.getKeywordValue("numbers");
      if (ns != null && ns.length() > 0) {
         key = key + '+' + ns;
      }

      DateFormatSymbols dfs = (DateFormatSymbols)DFSCACHE.getInstance(key, desiredLocale);
      this.initializeData(dfs);
   }

   void initializeData(DateFormatSymbols dfs) {
      this.eras = dfs.eras;
      this.eraNames = dfs.eraNames;
      this.narrowEras = dfs.narrowEras;
      this.months = dfs.months;
      this.shortMonths = dfs.shortMonths;
      this.narrowMonths = dfs.narrowMonths;
      this.standaloneMonths = dfs.standaloneMonths;
      this.standaloneShortMonths = dfs.standaloneShortMonths;
      this.standaloneNarrowMonths = dfs.standaloneNarrowMonths;
      this.weekdays = dfs.weekdays;
      this.shortWeekdays = dfs.shortWeekdays;
      this.shorterWeekdays = dfs.shorterWeekdays;
      this.narrowWeekdays = dfs.narrowWeekdays;
      this.standaloneWeekdays = dfs.standaloneWeekdays;
      this.standaloneShortWeekdays = dfs.standaloneShortWeekdays;
      this.standaloneShorterWeekdays = dfs.standaloneShorterWeekdays;
      this.standaloneNarrowWeekdays = dfs.standaloneNarrowWeekdays;
      this.ampms = dfs.ampms;
      this.ampmsNarrow = dfs.ampmsNarrow;
      this.timeSeparator = dfs.timeSeparator;
      this.shortQuarters = dfs.shortQuarters;
      this.quarters = dfs.quarters;
      this.standaloneShortQuarters = dfs.standaloneShortQuarters;
      this.standaloneQuarters = dfs.standaloneQuarters;
      this.leapMonthPatterns = dfs.leapMonthPatterns;
      this.shortYearNames = dfs.shortYearNames;
      this.shortZodiacNames = dfs.shortZodiacNames;
      this.abbreviatedDayPeriods = dfs.abbreviatedDayPeriods;
      this.wideDayPeriods = dfs.wideDayPeriods;
      this.narrowDayPeriods = dfs.narrowDayPeriods;
      this.standaloneAbbreviatedDayPeriods = dfs.standaloneAbbreviatedDayPeriods;
      this.standaloneWideDayPeriods = dfs.standaloneWideDayPeriods;
      this.standaloneNarrowDayPeriods = dfs.standaloneNarrowDayPeriods;
      this.zoneStrings = dfs.zoneStrings;
      this.localPatternChars = dfs.localPatternChars;
      this.capitalization = dfs.capitalization;
      this.actualLocale = dfs.actualLocale;
      this.validLocale = dfs.validLocale;
      this.requestedLocale = dfs.requestedLocale;
   }

   private DateFormatSymbols(ULocale desiredLocale, ICUResourceBundle b, String calendarType) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      this.initializeData(desiredLocale, b, calendarType);
   }

   /** @deprecated */
   @Deprecated
   protected void initializeData(ULocale desiredLocale, ICUResourceBundle b, String calendarType) {
      CalendarDataSink calendarSink = new CalendarDataSink();
      if (b == null) {
         b = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", desiredLocale);
      }

      while(calendarType != null) {
         ICUResourceBundle dataForType = b.findWithFallback("calendar/" + calendarType);
         if (dataForType == null) {
            if ("gregorian".equals(calendarType)) {
               throw new MissingResourceException("The 'gregorian' calendar type wasn't found for the locale: " + desiredLocale.getBaseName(), this.getClass().getName(), "gregorian");
            }

            calendarType = "gregorian";
            calendarSink.visitAllResources();
         } else {
            calendarSink.preEnumerate(calendarType);
            dataForType.getAllItemsWithFallback("", calendarSink);
            if (calendarType.equals("gregorian")) {
               break;
            }

            calendarType = calendarSink.nextCalendarType;
            if (calendarType == null) {
               calendarType = "gregorian";
               calendarSink.visitAllResources();
            }
         }
      }

      Map arrays = calendarSink.arrays;
      Map maps = calendarSink.maps;
      this.eras = (String[])arrays.get("eras/abbreviated");
      this.eraNames = (String[])arrays.get("eras/wide");
      this.narrowEras = (String[])arrays.get("eras/narrow");
      this.months = (String[])arrays.get("monthNames/format/wide");
      this.shortMonths = (String[])arrays.get("monthNames/format/abbreviated");
      this.narrowMonths = (String[])arrays.get("monthNames/format/narrow");
      this.standaloneMonths = (String[])arrays.get("monthNames/stand-alone/wide");
      this.standaloneShortMonths = (String[])arrays.get("monthNames/stand-alone/abbreviated");
      this.standaloneNarrowMonths = (String[])arrays.get("monthNames/stand-alone/narrow");
      String[] lWeekdays = (String[])arrays.get("dayNames/format/wide");
      this.weekdays = new String[8];
      this.weekdays[0] = "";
      System.arraycopy(lWeekdays, 0, this.weekdays, 1, lWeekdays.length);
      String[] aWeekdays = (String[])arrays.get("dayNames/format/abbreviated");
      this.shortWeekdays = new String[8];
      this.shortWeekdays[0] = "";
      System.arraycopy(aWeekdays, 0, this.shortWeekdays, 1, aWeekdays.length);
      String[] sWeekdays = (String[])arrays.get("dayNames/format/short");
      this.shorterWeekdays = new String[8];
      this.shorterWeekdays[0] = "";
      System.arraycopy(sWeekdays, 0, this.shorterWeekdays, 1, sWeekdays.length);
      String[] nWeekdays = (String[])arrays.get("dayNames/format/narrow");
      if (nWeekdays == null) {
         nWeekdays = (String[])arrays.get("dayNames/stand-alone/narrow");
         if (nWeekdays == null) {
            nWeekdays = (String[])arrays.get("dayNames/format/abbreviated");
            if (nWeekdays == null) {
               throw new MissingResourceException("Resource not found", this.getClass().getName(), "dayNames/format/abbreviated");
            }
         }
      }

      this.narrowWeekdays = new String[8];
      this.narrowWeekdays[0] = "";
      System.arraycopy(nWeekdays, 0, this.narrowWeekdays, 1, nWeekdays.length);
      String[] swWeekdays = null;
      swWeekdays = (String[])arrays.get("dayNames/stand-alone/wide");
      this.standaloneWeekdays = new String[8];
      this.standaloneWeekdays[0] = "";
      System.arraycopy(swWeekdays, 0, this.standaloneWeekdays, 1, swWeekdays.length);
      String[] saWeekdays = null;
      saWeekdays = (String[])arrays.get("dayNames/stand-alone/abbreviated");
      this.standaloneShortWeekdays = new String[8];
      this.standaloneShortWeekdays[0] = "";
      System.arraycopy(saWeekdays, 0, this.standaloneShortWeekdays, 1, saWeekdays.length);
      String[] ssWeekdays = null;
      ssWeekdays = (String[])arrays.get("dayNames/stand-alone/short");
      this.standaloneShorterWeekdays = new String[8];
      this.standaloneShorterWeekdays[0] = "";
      System.arraycopy(ssWeekdays, 0, this.standaloneShorterWeekdays, 1, ssWeekdays.length);
      String[] snWeekdays = null;
      snWeekdays = (String[])arrays.get("dayNames/stand-alone/narrow");
      this.standaloneNarrowWeekdays = new String[8];
      this.standaloneNarrowWeekdays[0] = "";
      System.arraycopy(snWeekdays, 0, this.standaloneNarrowWeekdays, 1, snWeekdays.length);
      this.ampms = (String[])arrays.get("AmPmMarkers");
      this.ampmsNarrow = (String[])arrays.get("AmPmMarkersNarrow");
      this.quarters = (String[])arrays.get("quarters/format/wide");
      this.shortQuarters = (String[])arrays.get("quarters/format/abbreviated");
      this.standaloneQuarters = (String[])arrays.get("quarters/stand-alone/wide");
      this.standaloneShortQuarters = (String[])arrays.get("quarters/stand-alone/abbreviated");
      this.abbreviatedDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/format/abbreviated"));
      this.wideDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/format/wide"));
      this.narrowDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/format/narrow"));
      this.standaloneAbbreviatedDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/stand-alone/abbreviated"));
      this.standaloneWideDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/stand-alone/wide"));
      this.standaloneNarrowDayPeriods = this.loadDayPeriodStrings((Map)maps.get("dayPeriod/stand-alone/narrow"));

      for(int i = 0; i < 7; ++i) {
         String monthPatternPath = LEAP_MONTH_PATTERNS_PATHS[i];
         if (monthPatternPath != null) {
            Map monthPatternMap = (Map)maps.get(monthPatternPath);
            if (monthPatternMap != null) {
               String leapMonthPattern = (String)monthPatternMap.get("leap");
               if (leapMonthPattern != null) {
                  if (this.leapMonthPatterns == null) {
                     this.leapMonthPatterns = new String[7];
                  }

                  this.leapMonthPatterns[i] = leapMonthPattern;
               }
            }
         }
      }

      this.shortYearNames = (String[])arrays.get("cyclicNameSets/years/format/abbreviated");
      this.shortZodiacNames = (String[])arrays.get("cyclicNameSets/zodiacs/format/abbreviated");
      this.requestedLocale = desiredLocale;
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", desiredLocale);
      this.localPatternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
      ULocale uloc = rb.getULocale();
      this.setLocale(uloc, uloc);
      this.capitalization = new HashMap();
      boolean[] noTransforms = new boolean[]{false, false};
      CapitalizationContextUsage[] allUsages = DateFormatSymbols.CapitalizationContextUsage.values();
      CapitalizationContextUsage[] contextTransformsBundle = allUsages;
      int var20 = allUsages.length;

      for(int var21 = 0; var21 < var20; ++var21) {
         CapitalizationContextUsage usage = contextTransformsBundle[var21];
         this.capitalization.put(usage, noTransforms);
      }

      contextTransformsBundle = null;

      ICUResourceBundle contextTransformsBundle;
      try {
         contextTransformsBundle = rb.getWithFallback("contextTransforms");
      } catch (MissingResourceException var27) {
         contextTransformsBundle = null;
      }

      if (contextTransformsBundle != null) {
         UResourceBundleIterator ctIterator = contextTransformsBundle.getIterator();

         while(ctIterator.hasNext()) {
            UResourceBundle contextTransformUsage = ctIterator.next();
            int[] intVector = contextTransformUsage.getIntVector();
            if (intVector.length >= 2) {
               String usageKey = contextTransformUsage.getKey();
               CapitalizationContextUsage usage = (CapitalizationContextUsage)contextUsageTypeMap.get(usageKey);
               if (usage != null) {
                  boolean[] transforms = new boolean[]{intVector[0] != 0, intVector[1] != 0};
                  this.capitalization.put(usage, transforms);
               }
            }
         }
      }

      NumberingSystem ns = NumberingSystem.getInstance(desiredLocale);
      String nsName = ns == null ? "latn" : ns.getName();
      String tsPath = "NumberElements/" + nsName + "/symbols/timeSeparator";

      try {
         this.setTimeSeparatorString(rb.getStringWithFallback(tsPath));
      } catch (MissingResourceException var26) {
         this.setTimeSeparatorString(":");
      }

   }

   private static final boolean arrayOfArrayEquals(Object[][] aa1, Object[][] aa2) {
      if (aa1 == aa2) {
         return true;
      } else if (aa1 != null && aa2 != null) {
         if (aa1.length != aa2.length) {
            return false;
         } else {
            boolean equal = true;

            for(int i = 0; i < aa1.length; ++i) {
               equal = Utility.arrayEquals((Object[])aa1[i], aa2[i]);
               if (!equal) {
                  break;
               }
            }

            return equal;
         }
      } else {
         return false;
      }
   }

   private String[] loadDayPeriodStrings(Map resourceMap) {
      String[] strings = new String[DAY_PERIOD_KEYS.length];
      if (resourceMap != null) {
         for(int i = 0; i < DAY_PERIOD_KEYS.length; ++i) {
            strings[i] = (String)resourceMap.get(DAY_PERIOD_KEYS[i]);
         }
      }

      return strings;
   }

   private final String[] duplicate(String[] srcArray) {
      return (String[])srcArray.clone();
   }

   private final String[][] duplicate(String[][] srcArray) {
      String[][] aCopy = new String[srcArray.length][];

      for(int i = 0; i < srcArray.length; ++i) {
         aCopy[i] = this.duplicate(srcArray[i]);
      }

      return aCopy;
   }

   public DateFormatSymbols(Calendar cal, Locale locale) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      this.initializeData(ULocale.forLocale(locale), cal.getType());
   }

   public DateFormatSymbols(Calendar cal, ULocale locale) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      this.initializeData(locale, cal.getType());
   }

   public DateFormatSymbols(Class calendarClass, Locale locale) {
      this(calendarClass, ULocale.forLocale(locale));
   }

   public DateFormatSymbols(Class calendarClass, ULocale locale) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      String fullName = calendarClass.getName();
      int lastDot = fullName.lastIndexOf(46);
      String className = fullName.substring(lastDot + 1);
      String calType = null;
      String[][] var7 = CALENDAR_CLASSES;
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String[] calClassInfo = var7[var9];
         if (calClassInfo[0].equals(className)) {
            calType = calClassInfo[1];
            break;
         }
      }

      if (calType == null) {
         calType = className.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH);
      }

      this.initializeData(locale, calType);
   }

   public DateFormatSymbols(ResourceBundle bundle, Locale locale) {
      this(bundle, ULocale.forLocale(locale));
   }

   public DateFormatSymbols(ResourceBundle bundle, ULocale locale) {
      this.eras = null;
      this.eraNames = null;
      this.narrowEras = null;
      this.months = null;
      this.shortMonths = null;
      this.narrowMonths = null;
      this.standaloneMonths = null;
      this.standaloneShortMonths = null;
      this.standaloneNarrowMonths = null;
      this.weekdays = null;
      this.shortWeekdays = null;
      this.shorterWeekdays = null;
      this.narrowWeekdays = null;
      this.standaloneWeekdays = null;
      this.standaloneShortWeekdays = null;
      this.standaloneShorterWeekdays = null;
      this.standaloneNarrowWeekdays = null;
      this.ampms = null;
      this.ampmsNarrow = null;
      this.timeSeparator = null;
      this.shortQuarters = null;
      this.quarters = null;
      this.standaloneShortQuarters = null;
      this.standaloneQuarters = null;
      this.leapMonthPatterns = null;
      this.shortYearNames = null;
      this.shortZodiacNames = null;
      this.zoneStrings = (String[][])null;
      this.localPatternChars = null;
      this.abbreviatedDayPeriods = null;
      this.wideDayPeriods = null;
      this.narrowDayPeriods = null;
      this.standaloneAbbreviatedDayPeriods = null;
      this.standaloneWideDayPeriods = null;
      this.standaloneNarrowDayPeriods = null;
      this.capitalization = null;
      this.initializeData(locale, (ICUResourceBundle)bundle, CalendarUtil.getCalendarType(locale));
   }

   /** @deprecated */
   @Deprecated
   public static ResourceBundle getDateFormatBundle(Class calendarClass, Locale locale) throws MissingResourceException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public static ResourceBundle getDateFormatBundle(Class calendarClass, ULocale locale) throws MissingResourceException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public static ResourceBundle getDateFormatBundle(Calendar cal, Locale locale) throws MissingResourceException {
      return null;
   }

   /** @deprecated */
   @Deprecated
   public static ResourceBundle getDateFormatBundle(Calendar cal, ULocale locale) throws MissingResourceException {
      return null;
   }

   public final ULocale getLocale(ULocale.Type type) {
      return type == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
   }

   final void setLocale(ULocale valid, ULocale actual) {
      if (valid == null != (actual == null)) {
         throw new IllegalArgumentException();
      } else {
         this.validLocale = valid;
         this.actualLocale = actual;
      }
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
   }

   // $FF: synthetic method
   DateFormatSymbols(ULocale x0, ICUResourceBundle x1, String x2, Object x3) {
      this(x0, x1, x2);
   }

   static {
      contextUsageTypeMap.put("month-format-except-narrow", DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT);
      contextUsageTypeMap.put("month-standalone-except-narrow", DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE);
      contextUsageTypeMap.put("month-narrow", DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW);
      contextUsageTypeMap.put("day-format-except-narrow", DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT);
      contextUsageTypeMap.put("day-standalone-except-narrow", DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE);
      contextUsageTypeMap.put("day-narrow", DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW);
      contextUsageTypeMap.put("era-name", DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE);
      contextUsageTypeMap.put("era-abbr", DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV);
      contextUsageTypeMap.put("era-narrow", DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW);
      contextUsageTypeMap.put("zone-long", DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG);
      contextUsageTypeMap.put("zone-short", DateFormatSymbols.CapitalizationContextUsage.ZONE_SHORT);
      contextUsageTypeMap.put("metazone-long", DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG);
      contextUsageTypeMap.put("metazone-short", DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT);
      DFSCACHE = new SoftCache() {
         protected DateFormatSymbols createInstance(String key, ULocale locale) {
            int typeStart = key.indexOf(43) + 1;
            int typeLimit = key.indexOf(43, typeStart);
            if (typeLimit < 0) {
               typeLimit = key.length();
            }

            String type = key.substring(typeStart, typeLimit);
            return new DateFormatSymbols(locale, (ICUResourceBundle)null, type);
         }
      };
      LEAP_MONTH_PATTERNS_PATHS = new String[7];
      LEAP_MONTH_PATTERNS_PATHS[0] = "monthPatterns/format/wide";
      LEAP_MONTH_PATTERNS_PATHS[1] = "monthPatterns/format/abbreviated";
      LEAP_MONTH_PATTERNS_PATHS[2] = "monthPatterns/format/narrow";
      LEAP_MONTH_PATTERNS_PATHS[3] = "monthPatterns/stand-alone/wide";
      LEAP_MONTH_PATTERNS_PATHS[4] = "monthPatterns/stand-alone/abbreviated";
      LEAP_MONTH_PATTERNS_PATHS[5] = "monthPatterns/stand-alone/narrow";
      LEAP_MONTH_PATTERNS_PATHS[6] = "monthPatterns/numeric/all";
      DAY_PERIOD_KEYS = new String[]{"midnight", "noon", "morning1", "afternoon1", "evening1", "night1", "morning2", "afternoon2", "evening2", "night2"};
   }

   private static final class CalendarDataSink extends UResource.Sink {
      Map arrays = new TreeMap();
      Map maps = new TreeMap();
      List aliasPathPairs = new ArrayList();
      String currentCalendarType = null;
      String nextCalendarType = null;
      private Set resourcesToVisit;
      private String aliasRelativePath;
      private static final String CALENDAR_ALIAS_PREFIX = "/LOCALE/calendar/";

      CalendarDataSink() {
      }

      void visitAllResources() {
         this.resourcesToVisit = null;
      }

      void preEnumerate(String calendarType) {
         this.currentCalendarType = calendarType;
         this.nextCalendarType = null;
         this.aliasPathPairs.clear();
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         assert this.currentCalendarType != null && !this.currentCalendarType.isEmpty();

         Set resourcesToVisitNext = null;
         UResource.Table calendarData = value.getTable();

         for(int i = 0; calendarData.getKeyAndValue(i, key, value); ++i) {
            String keyString = key.toString();
            AliasType aliasType = this.processAliasFromValue(keyString, value);
            if (aliasType != DateFormatSymbols.CalendarDataSink.AliasType.GREGORIAN) {
               if (aliasType == DateFormatSymbols.CalendarDataSink.AliasType.DIFFERENT_CALENDAR) {
                  if (resourcesToVisitNext == null) {
                     resourcesToVisitNext = new HashSet();
                  }

                  resourcesToVisitNext.add(this.aliasRelativePath);
               } else if (aliasType == DateFormatSymbols.CalendarDataSink.AliasType.SAME_CALENDAR) {
                  if (!this.arrays.containsKey(keyString) && !this.maps.containsKey(keyString)) {
                     this.aliasPathPairs.add(this.aliasRelativePath);
                     this.aliasPathPairs.add(keyString);
                  }
               } else if (this.resourcesToVisit == null || this.resourcesToVisit.isEmpty() || this.resourcesToVisit.contains(keyString) || keyString.equals("AmPmMarkersAbbr")) {
                  if (keyString.startsWith("AmPmMarkers")) {
                     if (!keyString.endsWith("%variant") && !this.arrays.containsKey(keyString)) {
                        String[] dataArray = value.getStringArray();
                        this.arrays.put(keyString, dataArray);
                     }
                  } else if (keyString.equals("eras") || keyString.equals("dayNames") || keyString.equals("monthNames") || keyString.equals("quarters") || keyString.equals("dayPeriod") || keyString.equals("monthPatterns") || keyString.equals("cyclicNameSets")) {
                     this.processResource(keyString, key, value);
                  }
               }
            }
         }

         boolean modified;
         do {
            modified = false;
            int i = 0;

            while(i < this.aliasPathPairs.size()) {
               boolean mod = false;
               String alias = (String)this.aliasPathPairs.get(i);
               if (this.arrays.containsKey(alias)) {
                  this.arrays.put(this.aliasPathPairs.get(i + 1), this.arrays.get(alias));
                  mod = true;
               } else if (this.maps.containsKey(alias)) {
                  this.maps.put(this.aliasPathPairs.get(i + 1), this.maps.get(alias));
                  mod = true;
               }

               if (mod) {
                  this.aliasPathPairs.remove(i + 1);
                  this.aliasPathPairs.remove(i);
                  modified = true;
               } else {
                  i += 2;
               }
            }
         } while(modified && !this.aliasPathPairs.isEmpty());

         if (resourcesToVisitNext != null) {
            this.resourcesToVisit = resourcesToVisitNext;
         }

      }

      protected void processResource(String path, UResource.Key key, UResource.Value value) {
         UResource.Table table = value.getTable();
         Map stringMap = null;

         for(int i = 0; table.getKeyAndValue(i, key, value); ++i) {
            if (!key.endsWith("%variant")) {
               String keyString = key.toString();
               if (value.getType() == 0) {
                  if (i == 0) {
                     stringMap = new HashMap();
                     this.maps.put(path, stringMap);
                  }

                  assert stringMap != null;

                  stringMap.put(keyString, value.getString());
               } else {
                  assert stringMap == null;

                  String currentPath = path + "/" + keyString;
                  if ((!currentPath.startsWith("cyclicNameSets") || "cyclicNameSets/years/format/abbreviated".startsWith(currentPath) || "cyclicNameSets/zodiacs/format/abbreviated".startsWith(currentPath) || "cyclicNameSets/dayParts/format/abbreviated".startsWith(currentPath)) && !this.arrays.containsKey(currentPath) && !this.maps.containsKey(currentPath)) {
                     AliasType aliasType = this.processAliasFromValue(currentPath, value);
                     if (aliasType == DateFormatSymbols.CalendarDataSink.AliasType.SAME_CALENDAR) {
                        this.aliasPathPairs.add(this.aliasRelativePath);
                        this.aliasPathPairs.add(currentPath);
                     } else {
                        assert aliasType == DateFormatSymbols.CalendarDataSink.AliasType.NONE;

                        if (value.getType() == 8) {
                           String[] dataArray = value.getStringArray();
                           this.arrays.put(currentPath, dataArray);
                        } else if (value.getType() == 2) {
                           this.processResource(currentPath, key, value);
                        }
                     }
                  }
               }
            }
         }

      }

      private AliasType processAliasFromValue(String currentRelativePath, UResource.Value value) {
         if (value.getType() != 3) {
            return DateFormatSymbols.CalendarDataSink.AliasType.NONE;
         } else {
            String aliasPath = value.getAliasString();
            if (aliasPath.startsWith("/LOCALE/calendar/") && aliasPath.length() > "/LOCALE/calendar/".length()) {
               int typeLimit = aliasPath.indexOf(47, "/LOCALE/calendar/".length());
               if (typeLimit > "/LOCALE/calendar/".length()) {
                  String aliasCalendarType = aliasPath.substring("/LOCALE/calendar/".length(), typeLimit);
                  this.aliasRelativePath = aliasPath.substring(typeLimit + 1);
                  if (this.currentCalendarType.equals(aliasCalendarType) && !currentRelativePath.equals(this.aliasRelativePath)) {
                     return DateFormatSymbols.CalendarDataSink.AliasType.SAME_CALENDAR;
                  }

                  if (!this.currentCalendarType.equals(aliasCalendarType) && currentRelativePath.equals(this.aliasRelativePath)) {
                     if (aliasCalendarType.equals("gregorian")) {
                        return DateFormatSymbols.CalendarDataSink.AliasType.GREGORIAN;
                     }

                     if (this.nextCalendarType == null || this.nextCalendarType.equals(aliasCalendarType)) {
                        this.nextCalendarType = aliasCalendarType;
                        return DateFormatSymbols.CalendarDataSink.AliasType.DIFFERENT_CALENDAR;
                     }
                  }
               }
            }

            throw new ICUException("Malformed 'calendar' alias. Path: " + aliasPath);
         }
      }

      private static enum AliasType {
         SAME_CALENDAR,
         DIFFERENT_CALENDAR,
         GREGORIAN,
         NONE;
      }
   }

   static enum CapitalizationContextUsage {
      OTHER,
      MONTH_FORMAT,
      MONTH_STANDALONE,
      MONTH_NARROW,
      DAY_FORMAT,
      DAY_STANDALONE,
      DAY_NARROW,
      ERA_WIDE,
      ERA_ABBREV,
      ERA_NARROW,
      ZONE_LONG,
      ZONE_SHORT,
      METAZONE_LONG,
      METAZONE_SHORT;
   }
}
