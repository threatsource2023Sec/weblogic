package org.python.icu.text;

import java.io.InvalidObjectException;
import java.text.FieldPosition;
import java.util.Locale;
import org.python.icu.util.Calendar;
import org.python.icu.util.ChineseCalendar;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;

/** @deprecated */
@Deprecated
public class ChineseDateFormat extends SimpleDateFormat {
   static final long serialVersionUID = -4610300753104099899L;

   /** @deprecated */
   @Deprecated
   public ChineseDateFormat(String pattern, Locale locale) {
      this(pattern, ULocale.forLocale(locale));
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormat(String pattern, ULocale locale) {
      this(pattern, (String)null, locale);
   }

   /** @deprecated */
   @Deprecated
   public ChineseDateFormat(String pattern, String override, ULocale locale) {
      super(pattern, new ChineseDateFormatSymbols(locale), new ChineseCalendar(TimeZone.getDefault(), locale), locale, true, override);
   }

   /** @deprecated */
   @Deprecated
   protected void subFormat(StringBuffer buf, char ch, int count, int beginOffset, int fieldNum, DisplayContext capitalizationContext, FieldPosition pos, Calendar cal) {
      super.subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, cal);
   }

   /** @deprecated */
   @Deprecated
   protected int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal) {
      return super.subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal);
   }

   /** @deprecated */
   @Deprecated
   protected DateFormat.Field patternCharToDateFormatField(char ch) {
      return super.patternCharToDateFormatField(ch);
   }

   /** @deprecated */
   @Deprecated
   public static class Field extends DateFormat.Field {
      private static final long serialVersionUID = -5102130532751400330L;
      /** @deprecated */
      @Deprecated
      public static final Field IS_LEAP_MONTH = new Field("is leap month", 22);

      /** @deprecated */
      @Deprecated
      protected Field(String name, int calendarField) {
         super(name, calendarField);
      }

      /** @deprecated */
      @Deprecated
      public static DateFormat.Field ofCalendarField(int calendarField) {
         return (DateFormat.Field)(calendarField == 22 ? IS_LEAP_MONTH : DateFormat.Field.ofCalendarField(calendarField));
      }

      /** @deprecated */
      @Deprecated
      protected Object readResolve() throws InvalidObjectException {
         if (this.getClass() != Field.class) {
            throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
         } else if (this.getName().equals(IS_LEAP_MONTH.getName())) {
            return IS_LEAP_MONTH;
         } else {
            throw new InvalidObjectException("Unknown attribute name.");
         }
      }
   }
}
