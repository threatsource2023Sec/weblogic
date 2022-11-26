package org.python.icu.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.RelativeDateFormat;
import org.python.icu.util.Calendar;
import org.python.icu.util.GregorianCalendar;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;

public abstract class DateFormat extends UFormat {
   protected Calendar calendar;
   protected NumberFormat numberFormat;
   public static final int ERA_FIELD = 0;
   public static final int YEAR_FIELD = 1;
   public static final int MONTH_FIELD = 2;
   public static final int DATE_FIELD = 3;
   public static final int HOUR_OF_DAY1_FIELD = 4;
   public static final int HOUR_OF_DAY0_FIELD = 5;
   public static final int MINUTE_FIELD = 6;
   public static final int SECOND_FIELD = 7;
   public static final int FRACTIONAL_SECOND_FIELD = 8;
   public static final int MILLISECOND_FIELD = 8;
   public static final int DAY_OF_WEEK_FIELD = 9;
   public static final int DAY_OF_YEAR_FIELD = 10;
   public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
   public static final int WEEK_OF_YEAR_FIELD = 12;
   public static final int WEEK_OF_MONTH_FIELD = 13;
   public static final int AM_PM_FIELD = 14;
   public static final int HOUR1_FIELD = 15;
   public static final int HOUR0_FIELD = 16;
   public static final int TIMEZONE_FIELD = 17;
   public static final int YEAR_WOY_FIELD = 18;
   public static final int DOW_LOCAL_FIELD = 19;
   public static final int EXTENDED_YEAR_FIELD = 20;
   public static final int JULIAN_DAY_FIELD = 21;
   public static final int MILLISECONDS_IN_DAY_FIELD = 22;
   public static final int TIMEZONE_RFC_FIELD = 23;
   public static final int TIMEZONE_GENERIC_FIELD = 24;
   public static final int STANDALONE_DAY_FIELD = 25;
   public static final int STANDALONE_MONTH_FIELD = 26;
   public static final int QUARTER_FIELD = 27;
   public static final int STANDALONE_QUARTER_FIELD = 28;
   public static final int TIMEZONE_SPECIAL_FIELD = 29;
   public static final int YEAR_NAME_FIELD = 30;
   public static final int TIMEZONE_LOCALIZED_GMT_OFFSET_FIELD = 31;
   public static final int TIMEZONE_ISO_FIELD = 32;
   public static final int TIMEZONE_ISO_LOCAL_FIELD = 33;
   /** @deprecated */
   @Deprecated
   static final int RELATED_YEAR = 34;
   public static final int AM_PM_MIDNIGHT_NOON_FIELD = 35;
   public static final int FLEXIBLE_DAY_PERIOD_FIELD = 36;
   /** @deprecated */
   @Deprecated
   public static final int TIME_SEPARATOR = 37;
   /** @deprecated */
   @Deprecated
   public static final int FIELD_COUNT = 38;
   private EnumSet booleanAttributes = EnumSet.allOf(BooleanAttribute.class);
   private DisplayContext capitalizationSetting;
   static final int currentSerialVersion = 1;
   private int serialVersionOnStream;
   private static final long serialVersionUID = 7218322306649953788L;
   public static final int NONE = -1;
   public static final int FULL = 0;
   public static final int LONG = 1;
   public static final int MEDIUM = 2;
   public static final int SHORT = 3;
   public static final int DEFAULT = 2;
   public static final int RELATIVE = 128;
   public static final int RELATIVE_FULL = 128;
   public static final int RELATIVE_LONG = 129;
   public static final int RELATIVE_MEDIUM = 130;
   public static final int RELATIVE_SHORT = 131;
   public static final int RELATIVE_DEFAULT = 130;
   public static final String YEAR = "y";
   public static final String QUARTER = "QQQQ";
   public static final String ABBR_QUARTER = "QQQ";
   public static final String YEAR_QUARTER = "yQQQQ";
   public static final String YEAR_ABBR_QUARTER = "yQQQ";
   public static final String MONTH = "MMMM";
   public static final String ABBR_MONTH = "MMM";
   public static final String NUM_MONTH = "M";
   public static final String YEAR_MONTH = "yMMMM";
   public static final String YEAR_ABBR_MONTH = "yMMM";
   public static final String YEAR_NUM_MONTH = "yM";
   public static final String DAY = "d";
   public static final String YEAR_MONTH_DAY = "yMMMMd";
   public static final String YEAR_ABBR_MONTH_DAY = "yMMMd";
   public static final String YEAR_NUM_MONTH_DAY = "yMd";
   public static final String WEEKDAY = "EEEE";
   public static final String ABBR_WEEKDAY = "E";
   public static final String YEAR_MONTH_WEEKDAY_DAY = "yMMMMEEEEd";
   public static final String YEAR_ABBR_MONTH_WEEKDAY_DAY = "yMMMEd";
   public static final String YEAR_NUM_MONTH_WEEKDAY_DAY = "yMEd";
   public static final String MONTH_DAY = "MMMMd";
   public static final String ABBR_MONTH_DAY = "MMMd";
   public static final String NUM_MONTH_DAY = "Md";
   public static final String MONTH_WEEKDAY_DAY = "MMMMEEEEd";
   public static final String ABBR_MONTH_WEEKDAY_DAY = "MMMEd";
   public static final String NUM_MONTH_WEEKDAY_DAY = "MEd";
   /** @deprecated */
   @Deprecated
   public static final List DATE_SKELETONS = Arrays.asList("y", "QQQQ", "QQQ", "yQQQQ", "yQQQ", "MMMM", "MMM", "M", "yMMMM", "yMMM", "yM", "d", "yMMMMd", "yMMMd", "yMd", "EEEE", "E", "yMMMMEEEEd", "yMMMEd", "yMEd", "MMMMd", "MMMd", "Md", "MMMMEEEEd", "MMMEd", "MEd");
   public static final String HOUR = "j";
   public static final String HOUR24 = "H";
   public static final String MINUTE = "m";
   public static final String HOUR_MINUTE = "jm";
   public static final String HOUR24_MINUTE = "Hm";
   public static final String SECOND = "s";
   public static final String HOUR_MINUTE_SECOND = "jms";
   public static final String HOUR24_MINUTE_SECOND = "Hms";
   public static final String MINUTE_SECOND = "ms";
   /** @deprecated */
   @Deprecated
   public static final List TIME_SKELETONS = Arrays.asList("j", "H", "m", "jm", "Hm", "s", "jms", "Hms", "ms");
   public static final String LOCATION_TZ = "VVVV";
   public static final String GENERIC_TZ = "vvvv";
   public static final String ABBR_GENERIC_TZ = "v";
   public static final String SPECIFIC_TZ = "zzzz";
   public static final String ABBR_SPECIFIC_TZ = "z";
   public static final String ABBR_UTC_TZ = "ZZZZ";
   /** @deprecated */
   @Deprecated
   public static final List ZONE_SKELETONS = Arrays.asList("VVVV", "vvvv", "v", "zzzz", "z", "ZZZZ");
   /** @deprecated */
   @Deprecated
   public static final String STANDALONE_MONTH = "LLLL";
   /** @deprecated */
   @Deprecated
   public static final String ABBR_STANDALONE_MONTH = "LLL";
   /** @deprecated */
   @Deprecated
   public static final String HOUR_MINUTE_GENERIC_TZ = "jmv";
   /** @deprecated */
   @Deprecated
   public static final String HOUR_MINUTE_TZ = "jmz";
   /** @deprecated */
   @Deprecated
   public static final String HOUR_GENERIC_TZ = "jv";
   /** @deprecated */
   @Deprecated
   public static final String HOUR_TZ = "jz";

   public final StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition fieldPosition) {
      if (obj instanceof Calendar) {
         return this.format((Calendar)obj, toAppendTo, fieldPosition);
      } else if (obj instanceof Date) {
         return this.format((Date)obj, toAppendTo, fieldPosition);
      } else if (obj instanceof Number) {
         return this.format(new Date(((Number)obj).longValue()), toAppendTo, fieldPosition);
      } else {
         throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a Date");
      }
   }

   public abstract StringBuffer format(Calendar var1, StringBuffer var2, FieldPosition var3);

   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
      this.calendar.setTime(date);
      return this.format(this.calendar, toAppendTo, fieldPosition);
   }

   public final String format(Date date) {
      return this.format(date, new StringBuffer(64), new FieldPosition(0)).toString();
   }

   public Date parse(String text) throws ParseException {
      ParsePosition pos = new ParsePosition(0);
      Date result = this.parse(text, pos);
      if (pos.getIndex() == 0) {
         throw new ParseException("Unparseable date: \"" + text + "\"", pos.getErrorIndex());
      } else {
         return result;
      }
   }

   public abstract void parse(String var1, Calendar var2, ParsePosition var3);

   public Date parse(String text, ParsePosition pos) {
      Date result = null;
      int start = pos.getIndex();
      TimeZone tzsav = this.calendar.getTimeZone();
      this.calendar.clear();
      this.parse(text, this.calendar, pos);
      if (pos.getIndex() != start) {
         try {
            result = this.calendar.getTime();
         } catch (IllegalArgumentException var7) {
            pos.setIndex(start);
            pos.setErrorIndex(start);
         }
      }

      this.calendar.setTimeZone(tzsav);
      return result;
   }

   public Object parseObject(String source, ParsePosition pos) {
      return this.parse(source, pos);
   }

   public static final DateFormat getTimeInstance() {
      return get(-1, 2, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getTimeInstance(int style) {
      return get(-1, style, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getTimeInstance(int style, Locale aLocale) {
      return get(-1, style, ULocale.forLocale(aLocale), (Calendar)null);
   }

   public static final DateFormat getTimeInstance(int style, ULocale locale) {
      return get(-1, style, locale, (Calendar)null);
   }

   public static final DateFormat getDateInstance() {
      return get(2, -1, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getDateInstance(int style) {
      return get(style, -1, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getDateInstance(int style, Locale aLocale) {
      return get(style, -1, ULocale.forLocale(aLocale), (Calendar)null);
   }

   public static final DateFormat getDateInstance(int style, ULocale locale) {
      return get(style, -1, locale, (Calendar)null);
   }

   public static final DateFormat getDateTimeInstance() {
      return get(2, 2, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
      return get(dateStyle, timeStyle, ULocale.getDefault(ULocale.Category.FORMAT), (Calendar)null);
   }

   public static final DateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale aLocale) {
      return get(dateStyle, timeStyle, ULocale.forLocale(aLocale), (Calendar)null);
   }

   public static final DateFormat getDateTimeInstance(int dateStyle, int timeStyle, ULocale locale) {
      return get(dateStyle, timeStyle, locale, (Calendar)null);
   }

   public static final DateFormat getInstance() {
      return getDateTimeInstance(3, 3);
   }

   public static Locale[] getAvailableLocales() {
      return ICUResourceBundle.getAvailableLocales();
   }

   public static ULocale[] getAvailableULocales() {
      return ICUResourceBundle.getAvailableULocales();
   }

   public void setCalendar(Calendar newCalendar) {
      this.calendar = newCalendar;
   }

   public Calendar getCalendar() {
      return this.calendar;
   }

   public void setNumberFormat(NumberFormat newNumberFormat) {
      this.numberFormat = newNumberFormat;
      this.numberFormat.setParseIntegerOnly(true);
   }

   public NumberFormat getNumberFormat() {
      return this.numberFormat;
   }

   public void setTimeZone(TimeZone zone) {
      this.calendar.setTimeZone(zone);
   }

   public TimeZone getTimeZone() {
      return this.calendar.getTimeZone();
   }

   public void setLenient(boolean lenient) {
      this.calendar.setLenient(lenient);
      this.setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC, lenient);
      this.setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE, lenient);
   }

   public boolean isLenient() {
      return this.calendar.isLenient() && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE);
   }

   public void setCalendarLenient(boolean lenient) {
      this.calendar.setLenient(lenient);
   }

   public boolean isCalendarLenient() {
      return this.calendar.isLenient();
   }

   public DateFormat setBooleanAttribute(BooleanAttribute key, boolean value) {
      if (key.equals(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
         key = DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH;
      }

      if (value) {
         this.booleanAttributes.add(key);
      } else {
         this.booleanAttributes.remove(key);
      }

      return this;
   }

   public boolean getBooleanAttribute(BooleanAttribute key) {
      if (key == DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH) {
         key = DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH;
      }

      return this.booleanAttributes.contains(key);
   }

   public void setContext(DisplayContext context) {
      if (context.type() == DisplayContext.Type.CAPITALIZATION) {
         this.capitalizationSetting = context;
      }

   }

   public DisplayContext getContext(DisplayContext.Type type) {
      return type == DisplayContext.Type.CAPITALIZATION && this.capitalizationSetting != null ? this.capitalizationSetting : DisplayContext.CAPITALIZATION_NONE;
   }

   public int hashCode() {
      return this.numberFormat.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         DateFormat other = (DateFormat)obj;
         return (this.calendar == null && other.calendar == null || this.calendar != null && other.calendar != null && this.calendar.isEquivalentTo(other.calendar)) && (this.numberFormat == null && other.numberFormat == null || this.numberFormat != null && other.numberFormat != null && this.numberFormat.equals(other.numberFormat)) && this.capitalizationSetting == other.capitalizationSetting;
      } else {
         return false;
      }
   }

   public Object clone() {
      DateFormat other = (DateFormat)super.clone();
      other.calendar = (Calendar)this.calendar.clone();
      if (this.numberFormat != null) {
         other.numberFormat = (NumberFormat)this.numberFormat.clone();
      }

      return other;
   }

   private static DateFormat get(int dateStyle, int timeStyle, ULocale loc, Calendar cal) {
      if ((timeStyle == -1 || (timeStyle & 128) <= 0) && (dateStyle == -1 || (dateStyle & 128) <= 0)) {
         if (timeStyle >= -1 && timeStyle <= 3) {
            if (dateStyle >= -1 && dateStyle <= 3) {
               if (cal == null) {
                  cal = Calendar.getInstance(loc);
               }

               try {
                  DateFormat result = cal.getDateTimeFormat(dateStyle, timeStyle, loc);
                  result.setLocale(cal.getLocale(ULocale.VALID_LOCALE), cal.getLocale(ULocale.ACTUAL_LOCALE));
                  return result;
               } catch (MissingResourceException var5) {
                  return new SimpleDateFormat("M/d/yy h:mm a");
               }
            } else {
               throw new IllegalArgumentException("Illegal date style " + dateStyle);
            }
         } else {
            throw new IllegalArgumentException("Illegal time style " + timeStyle);
         }
      } else {
         RelativeDateFormat r = new RelativeDateFormat(timeStyle, dateStyle, loc, cal);
         return r;
      }
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      if (this.serialVersionOnStream < 1) {
         this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
      }

      if (this.booleanAttributes == null) {
         this.booleanAttributes = EnumSet.allOf(BooleanAttribute.class);
      }

      this.serialVersionOnStream = 1;
   }

   protected DateFormat() {
      this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
      this.serialVersionOnStream = 1;
   }

   public static final DateFormat getDateInstance(Calendar cal, int dateStyle, Locale locale) {
      return getDateTimeInstance(cal, dateStyle, -1, (ULocale)ULocale.forLocale(locale));
   }

   public static final DateFormat getDateInstance(Calendar cal, int dateStyle, ULocale locale) {
      return getDateTimeInstance(cal, dateStyle, -1, (ULocale)locale);
   }

   public static final DateFormat getTimeInstance(Calendar cal, int timeStyle, Locale locale) {
      return getDateTimeInstance(cal, -1, timeStyle, (ULocale)ULocale.forLocale(locale));
   }

   public static final DateFormat getTimeInstance(Calendar cal, int timeStyle, ULocale locale) {
      return getDateTimeInstance(cal, -1, timeStyle, (ULocale)locale);
   }

   public static final DateFormat getDateTimeInstance(Calendar cal, int dateStyle, int timeStyle, Locale locale) {
      return getDateTimeInstance(dateStyle, timeStyle, ULocale.forLocale(locale));
   }

   public static final DateFormat getDateTimeInstance(Calendar cal, int dateStyle, int timeStyle, ULocale locale) {
      if (cal == null) {
         throw new IllegalArgumentException("Calendar must be supplied");
      } else {
         return get(dateStyle, timeStyle, locale, cal);
      }
   }

   public static final DateFormat getInstance(Calendar cal, Locale locale) {
      return getDateTimeInstance(cal, 3, 3, (ULocale)ULocale.forLocale(locale));
   }

   public static final DateFormat getInstance(Calendar cal, ULocale locale) {
      return getDateTimeInstance(cal, 3, 3, (ULocale)locale);
   }

   public static final DateFormat getInstance(Calendar cal) {
      return getInstance(cal, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateFormat getDateInstance(Calendar cal, int dateStyle) {
      return getDateInstance(cal, dateStyle, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateFormat getTimeInstance(Calendar cal, int timeStyle) {
      return getTimeInstance(cal, timeStyle, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateFormat getDateTimeInstance(Calendar cal, int dateStyle, int timeStyle) {
      return getDateTimeInstance(cal, dateStyle, timeStyle, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateFormat getInstanceForSkeleton(String skeleton) {
      return getPatternInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateFormat getInstanceForSkeleton(String skeleton, Locale locale) {
      return getPatternInstance(skeleton, ULocale.forLocale(locale));
   }

   public static final DateFormat getInstanceForSkeleton(String skeleton, ULocale locale) {
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      String bestPattern = generator.getBestPattern(skeleton);
      return new SimpleDateFormat(bestPattern, locale);
   }

   public static final DateFormat getInstanceForSkeleton(Calendar cal, String skeleton, Locale locale) {
      return getPatternInstance(cal, skeleton, ULocale.forLocale(locale));
   }

   public static final DateFormat getInstanceForSkeleton(Calendar cal, String skeleton, ULocale locale) {
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      String bestPattern = generator.getBestPattern(skeleton);
      SimpleDateFormat format = new SimpleDateFormat(bestPattern, locale);
      format.setCalendar(cal);
      return format;
   }

   public static final DateFormat getPatternInstance(String skeleton) {
      return getInstanceForSkeleton(skeleton);
   }

   public static final DateFormat getPatternInstance(String skeleton, Locale locale) {
      return getInstanceForSkeleton(skeleton, locale);
   }

   public static final DateFormat getPatternInstance(String skeleton, ULocale locale) {
      return getInstanceForSkeleton(skeleton, locale);
   }

   public static final DateFormat getPatternInstance(Calendar cal, String skeleton, Locale locale) {
      return getInstanceForSkeleton(cal, skeleton, locale);
   }

   public static final DateFormat getPatternInstance(Calendar cal, String skeleton, ULocale locale) {
      return getInstanceForSkeleton(cal, skeleton, locale);
   }

   public static class Field extends Format.Field {
      private static final long serialVersionUID = -3627456821000730829L;
      private static final int CAL_FIELD_COUNT;
      private static final Field[] CAL_FIELDS;
      private static final Map FIELD_NAME_MAP;
      public static final Field AM_PM;
      public static final Field DAY_OF_MONTH;
      public static final Field DAY_OF_WEEK;
      public static final Field DAY_OF_WEEK_IN_MONTH;
      public static final Field DAY_OF_YEAR;
      public static final Field ERA;
      public static final Field HOUR_OF_DAY0;
      public static final Field HOUR_OF_DAY1;
      public static final Field HOUR0;
      public static final Field HOUR1;
      public static final Field MILLISECOND;
      public static final Field MINUTE;
      public static final Field MONTH;
      public static final Field SECOND;
      public static final Field TIME_ZONE;
      public static final Field WEEK_OF_MONTH;
      public static final Field WEEK_OF_YEAR;
      public static final Field YEAR;
      public static final Field DOW_LOCAL;
      public static final Field EXTENDED_YEAR;
      public static final Field JULIAN_DAY;
      public static final Field MILLISECONDS_IN_DAY;
      public static final Field YEAR_WOY;
      public static final Field QUARTER;
      /** @deprecated */
      @Deprecated
      public static final Field RELATED_YEAR;
      public static final Field AM_PM_MIDNIGHT_NOON;
      public static final Field FLEXIBLE_DAY_PERIOD;
      /** @deprecated */
      @Deprecated
      public static final Field TIME_SEPARATOR;
      private final int calendarField;

      protected Field(String name, int calendarField) {
         super(name);
         this.calendarField = calendarField;
         if (this.getClass() == Field.class) {
            FIELD_NAME_MAP.put(name, this);
            if (calendarField >= 0 && calendarField < CAL_FIELD_COUNT) {
               CAL_FIELDS[calendarField] = this;
            }
         }

      }

      public static Field ofCalendarField(int calendarField) {
         if (calendarField >= 0 && calendarField < CAL_FIELD_COUNT) {
            return CAL_FIELDS[calendarField];
         } else {
            throw new IllegalArgumentException("Calendar field number is out of range");
         }
      }

      public int getCalendarField() {
         return this.calendarField;
      }

      protected Object readResolve() throws InvalidObjectException {
         if (this.getClass() != Field.class) {
            throw new InvalidObjectException("A subclass of DateFormat.Field must implement readResolve.");
         } else {
            Object o = FIELD_NAME_MAP.get(this.getName());
            if (o == null) {
               throw new InvalidObjectException("Unknown attribute name.");
            } else {
               return o;
            }
         }
      }

      static {
         GregorianCalendar cal = new GregorianCalendar();
         CAL_FIELD_COUNT = cal.getFieldCount();
         CAL_FIELDS = new Field[CAL_FIELD_COUNT];
         FIELD_NAME_MAP = new HashMap(CAL_FIELD_COUNT);
         AM_PM = new Field("am pm", 9);
         DAY_OF_MONTH = new Field("day of month", 5);
         DAY_OF_WEEK = new Field("day of week", 7);
         DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
         DAY_OF_YEAR = new Field("day of year", 6);
         ERA = new Field("era", 0);
         HOUR_OF_DAY0 = new Field("hour of day", 11);
         HOUR_OF_DAY1 = new Field("hour of day 1", -1);
         HOUR0 = new Field("hour", 10);
         HOUR1 = new Field("hour 1", -1);
         MILLISECOND = new Field("millisecond", 14);
         MINUTE = new Field("minute", 12);
         MONTH = new Field("month", 2);
         SECOND = new Field("second", 13);
         TIME_ZONE = new Field("time zone", -1);
         WEEK_OF_MONTH = new Field("week of month", 4);
         WEEK_OF_YEAR = new Field("week of year", 3);
         YEAR = new Field("year", 1);
         DOW_LOCAL = new Field("local day of week", 18);
         EXTENDED_YEAR = new Field("extended year", 19);
         JULIAN_DAY = new Field("Julian day", 20);
         MILLISECONDS_IN_DAY = new Field("milliseconds in day", 21);
         YEAR_WOY = new Field("year for week of year", 17);
         QUARTER = new Field("quarter", -1);
         RELATED_YEAR = new Field("related year", -1);
         AM_PM_MIDNIGHT_NOON = new Field("am/pm/midnight/noon", -1);
         FLEXIBLE_DAY_PERIOD = new Field("flexible day period", -1);
         TIME_SEPARATOR = new Field("time separator", -1);
      }
   }

   public static enum BooleanAttribute {
      PARSE_ALLOW_WHITESPACE,
      PARSE_ALLOW_NUMERIC,
      PARSE_MULTIPLE_PATTERNS_FOR_MATCH,
      PARSE_PARTIAL_LITERAL_MATCH,
      /** @deprecated */
      @Deprecated
      PARSE_PARTIAL_MATCH;
   }
}
