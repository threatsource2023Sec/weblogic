package org.python.icu.impl;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import org.python.icu.lang.UCharacter;
import org.python.icu.text.BreakIterator;
import org.python.icu.text.DateFormat;
import org.python.icu.text.DisplayContext;
import org.python.icu.text.MessageFormat;
import org.python.icu.text.SimpleDateFormat;
import org.python.icu.util.Calendar;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class RelativeDateFormat extends DateFormat {
   private static final long serialVersionUID = 1131984966440549435L;
   private DateFormat fDateFormat;
   private DateFormat fTimeFormat;
   private MessageFormat fCombinedFormat;
   private SimpleDateFormat fDateTimeFormat = null;
   private String fDatePattern = null;
   private String fTimePattern = null;
   int fDateStyle;
   int fTimeStyle;
   ULocale fLocale;
   private transient List fDates = null;
   private boolean combinedFormatHasDateAtStart = false;
   private boolean capitalizationInfoIsSet = false;
   private boolean capitalizationOfRelativeUnitsForListOrMenu = false;
   private boolean capitalizationOfRelativeUnitsForStandAlone = false;
   private transient BreakIterator capitalizationBrkIter = null;

   public RelativeDateFormat(int timeStyle, int dateStyle, ULocale locale, Calendar cal) {
      this.calendar = cal;
      this.fLocale = locale;
      this.fTimeStyle = timeStyle;
      this.fDateStyle = dateStyle;
      int newStyle;
      DateFormat df;
      if (this.fDateStyle != -1) {
         newStyle = this.fDateStyle & -129;
         df = DateFormat.getDateInstance(newStyle, locale);
         if (!(df instanceof SimpleDateFormat)) {
            throw new IllegalArgumentException("Can't create SimpleDateFormat for date style");
         }

         this.fDateTimeFormat = (SimpleDateFormat)df;
         this.fDatePattern = this.fDateTimeFormat.toPattern();
         if (this.fTimeStyle != -1) {
            newStyle = this.fTimeStyle & -129;
            df = DateFormat.getTimeInstance(newStyle, locale);
            if (df instanceof SimpleDateFormat) {
               this.fTimePattern = ((SimpleDateFormat)df).toPattern();
            }
         }
      } else {
         newStyle = this.fTimeStyle & -129;
         df = DateFormat.getTimeInstance(newStyle, locale);
         if (!(df instanceof SimpleDateFormat)) {
            throw new IllegalArgumentException("Can't create SimpleDateFormat for time style");
         }

         this.fDateTimeFormat = (SimpleDateFormat)df;
         this.fTimePattern = this.fDateTimeFormat.toPattern();
      }

      this.initializeCalendar((TimeZone)null, this.fLocale);
      this.loadDates();
      this.initializeCombinedFormat(this.calendar, this.fLocale);
   }

   public StringBuffer format(Calendar cal, StringBuffer toAppendTo, FieldPosition fieldPosition) {
      String relativeDayString = null;
      DisplayContext capitalizationContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
      if (this.fDateStyle != -1) {
         int dayDiff = dayDifference(cal);
         relativeDayString = this.getStringForDay(dayDiff);
      }

      if (this.fDateTimeFormat != null) {
         if (relativeDayString != null && this.fDatePattern != null && (this.fTimePattern == null || this.fCombinedFormat == null || this.combinedFormatHasDateAtStart)) {
            if (relativeDayString.length() > 0 && UCharacter.isLowerCase(relativeDayString.codePointAt(0)) && (capitalizationContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || capitalizationContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu || capitalizationContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)) {
               if (this.capitalizationBrkIter == null) {
                  this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
               }

               relativeDayString = UCharacter.toTitleCase((ULocale)this.fLocale, relativeDayString, this.capitalizationBrkIter, 768);
            }

            this.fDateTimeFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
         } else {
            this.fDateTimeFormat.setContext(capitalizationContext);
         }
      }

      if (this.fDateTimeFormat != null && (this.fDatePattern != null || this.fTimePattern != null)) {
         if (this.fDatePattern == null) {
            this.fDateTimeFormat.applyPattern(this.fTimePattern);
            this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
         } else if (this.fTimePattern == null) {
            if (relativeDayString != null) {
               toAppendTo.append(relativeDayString);
            } else {
               this.fDateTimeFormat.applyPattern(this.fDatePattern);
               this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
            }
         } else {
            String datePattern = this.fDatePattern;
            if (relativeDayString != null) {
               datePattern = "'" + relativeDayString.replace("'", "''") + "'";
            }

            StringBuffer combinedPattern = new StringBuffer("");
            this.fCombinedFormat.format(new Object[]{this.fTimePattern, datePattern}, combinedPattern, new FieldPosition(0));
            this.fDateTimeFormat.applyPattern(combinedPattern.toString());
            this.fDateTimeFormat.format(cal, toAppendTo, fieldPosition);
         }
      } else if (this.fDateFormat != null) {
         if (relativeDayString != null) {
            toAppendTo.append(relativeDayString);
         } else {
            this.fDateFormat.format(cal, toAppendTo, fieldPosition);
         }
      }

      return toAppendTo;
   }

   public void parse(String text, Calendar cal, ParsePosition pos) {
      throw new UnsupportedOperationException("Relative Date parse is not implemented yet");
   }

   public void setContext(DisplayContext context) {
      super.setContext(context);
      if (!this.capitalizationInfoIsSet && (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
         this.initCapitalizationContextInfo(this.fLocale);
         this.capitalizationInfoIsSet = true;
      }

      if (this.capitalizationBrkIter == null && (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationOfRelativeUnitsForListOrMenu || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationOfRelativeUnitsForStandAlone)) {
         this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.fLocale);
      }

   }

   private String getStringForDay(int day) {
      if (this.fDates == null) {
         this.loadDates();
      }

      Iterator var2 = this.fDates.iterator();

      URelativeString dayItem;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         dayItem = (URelativeString)var2.next();
      } while(dayItem.offset != day);

      return dayItem.string;
   }

   private synchronized void loadDates() {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", this.fLocale);
      this.fDates = new ArrayList();
      RelDateFmtDataSink sink = new RelDateFmtDataSink();
      rb.getAllItemsWithFallback("fields/day/relative", sink);
   }

   private void initCapitalizationContextInfo(ULocale locale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);

      try {
         ICUResourceBundle rdb = rb.getWithFallback("contextTransforms/relative");
         int[] intVector = rdb.getIntVector();
         if (intVector.length >= 2) {
            this.capitalizationOfRelativeUnitsForListOrMenu = intVector[0] != 0;
            this.capitalizationOfRelativeUnitsForStandAlone = intVector[1] != 0;
         }
      } catch (MissingResourceException var5) {
      }

   }

   private static int dayDifference(Calendar until) {
      Calendar nowCal = (Calendar)until.clone();
      Date nowDate = new Date(System.currentTimeMillis());
      nowCal.clear();
      nowCal.setTime(nowDate);
      int dayDiff = until.get(20) - nowCal.get(20);
      return dayDiff;
   }

   private Calendar initializeCalendar(TimeZone zone, ULocale locale) {
      if (this.calendar == null) {
         if (zone == null) {
            this.calendar = Calendar.getInstance(locale);
         } else {
            this.calendar = Calendar.getInstance(zone, locale);
         }
      }

      return this.calendar;
   }

   private MessageFormat initializeCombinedFormat(Calendar cal, ULocale locale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);
      String resourcePath = "calendar/" + cal.getType() + "/DateTimePatterns";
      ICUResourceBundle patternsRb = rb.findWithFallback(resourcePath);
      if (patternsRb == null && !cal.getType().equals("gregorian")) {
         patternsRb = rb.findWithFallback("calendar/gregorian/DateTimePatterns");
      }

      String pattern;
      if (patternsRb != null && patternsRb.getSize() >= 9) {
         int glueIndex = 8;
         if (patternsRb.getSize() >= 13) {
            if (this.fDateStyle >= 0 && this.fDateStyle <= 3) {
               glueIndex += this.fDateStyle + 1;
            } else if (this.fDateStyle >= 128 && this.fDateStyle <= 131) {
               glueIndex += this.fDateStyle + 1 - 128;
            }
         }

         int elementType = patternsRb.get(glueIndex).getType();
         if (elementType == 8) {
            pattern = patternsRb.get(glueIndex).getString(0);
         } else {
            pattern = patternsRb.getString(glueIndex);
         }
      } else {
         pattern = "{1} {0}";
      }

      this.combinedFormatHasDateAtStart = pattern.startsWith("{1}");
      this.fCombinedFormat = new MessageFormat(pattern, locale);
      return this.fCombinedFormat;
   }

   private final class RelDateFmtDataSink extends UResource.Sink {
      private RelDateFmtDataSink() {
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         if (value.getType() != 3) {
            UResource.Table table = value.getTable();

            for(int i = 0; table.getKeyAndValue(i, key, value); ++i) {
               int keyOffset;
               try {
                  keyOffset = Integer.parseInt(key.toString());
               } catch (NumberFormatException var8) {
                  return;
               }

               if (RelativeDateFormat.this.getStringForDay(keyOffset) == null) {
                  URelativeString newDayInfo = new URelativeString(keyOffset, value.getString());
                  RelativeDateFormat.this.fDates.add(newDayInfo);
               }
            }

         }
      }

      // $FF: synthetic method
      RelDateFmtDataSink(Object x1) {
         this();
      }
   }

   public static class URelativeString {
      public int offset;
      public String string;

      URelativeString(int offset, String string) {
         this.offset = offset;
         this.string = string;
      }

      URelativeString(String offset, String string) {
         this.offset = Integer.parseInt(offset);
         this.string = string;
      }
   }
}
