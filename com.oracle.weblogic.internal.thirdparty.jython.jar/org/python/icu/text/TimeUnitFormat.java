package org.python.icu.text;

import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.UResource;
import org.python.icu.util.Measure;
import org.python.icu.util.TimeUnit;
import org.python.icu.util.TimeUnitAmount;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

/** @deprecated */
@Deprecated
public class TimeUnitFormat extends MeasureFormat {
   /** @deprecated */
   @Deprecated
   public static final int FULL_NAME = 0;
   /** @deprecated */
   @Deprecated
   public static final int ABBREVIATED_NAME = 1;
   private static final int TOTAL_STYLES = 2;
   private static final long serialVersionUID = -3707773153184971529L;
   private NumberFormat format;
   private ULocale locale;
   private int style;
   private transient MeasureFormat mf;
   private transient Map timeUnitToCountToPatterns;
   private transient PluralRules pluralRules;
   private transient boolean isReady;
   private static final String DEFAULT_PATTERN_FOR_SECOND = "{0} s";
   private static final String DEFAULT_PATTERN_FOR_MINUTE = "{0} min";
   private static final String DEFAULT_PATTERN_FOR_HOUR = "{0} h";
   private static final String DEFAULT_PATTERN_FOR_DAY = "{0} d";
   private static final String DEFAULT_PATTERN_FOR_WEEK = "{0} w";
   private static final String DEFAULT_PATTERN_FOR_MONTH = "{0} m";
   private static final String DEFAULT_PATTERN_FOR_YEAR = "{0} y";

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat() {
      this.mf = MeasureFormat.getInstance(ULocale.getDefault(), MeasureFormat.FormatWidth.WIDE);
      this.isReady = false;
      this.style = 0;
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat(ULocale locale) {
      this((ULocale)locale, 0);
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat(Locale locale) {
      this((Locale)locale, 0);
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat(ULocale locale, int style) {
      if (style >= 0 && style < 2) {
         this.mf = MeasureFormat.getInstance(locale, style == 0 ? MeasureFormat.FormatWidth.WIDE : MeasureFormat.FormatWidth.SHORT);
         this.style = style;
         this.setLocale(locale, locale);
         this.locale = locale;
         this.isReady = false;
      } else {
         throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
      }
   }

   private TimeUnitFormat(ULocale locale, int style, NumberFormat numberFormat) {
      this(locale, style);
      if (numberFormat != null) {
         this.setNumberFormat((NumberFormat)numberFormat.clone());
      }

   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat(Locale locale, int style) {
      this(ULocale.forLocale(locale), style);
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat setLocale(ULocale locale) {
      if (locale != this.locale) {
         this.mf = this.mf.withLocale(locale);
         this.setLocale(locale, locale);
         this.locale = locale;
         this.isReady = false;
      }

      return this;
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat setLocale(Locale locale) {
      return this.setLocale(ULocale.forLocale(locale));
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitFormat setNumberFormat(NumberFormat format) {
      if (format == this.format) {
         return this;
      } else {
         if (format == null) {
            if (this.locale == null) {
               this.isReady = false;
               this.mf = this.mf.withLocale(ULocale.getDefault());
            } else {
               this.format = NumberFormat.getNumberInstance(this.locale);
               this.mf = this.mf.withNumberFormat(this.format);
            }
         } else {
            this.format = format;
            this.mf = this.mf.withNumberFormat(this.format);
         }

         return this;
      }
   }

   /** @deprecated */
   @Deprecated
   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      return this.mf.format(obj, toAppendTo, pos);
   }

   /** @deprecated */
   @Deprecated
   public TimeUnitAmount parseObject(String source, ParsePosition pos) {
      if (!this.isReady) {
         this.setup();
      }

      Number resultNumber = null;
      TimeUnit resultTimeUnit = null;
      int oldPos = pos.getIndex();
      int newPos = -1;
      int longestParseDistance = 0;
      String countOfLongestMatch = null;
      Iterator var9 = this.timeUnitToCountToPatterns.keySet().iterator();

      while(var9.hasNext()) {
         TimeUnit timeUnit = (TimeUnit)var9.next();
         Map countToPattern = (Map)this.timeUnitToCountToPatterns.get(timeUnit);
         Iterator var12 = countToPattern.entrySet().iterator();

         while(var12.hasNext()) {
            Map.Entry patternEntry = (Map.Entry)var12.next();
            String count = (String)patternEntry.getKey();

            for(int styl = 0; styl < 2; ++styl) {
               MessageFormat pattern = (MessageFormat)((Object[])patternEntry.getValue())[styl];
               pos.setErrorIndex(-1);
               pos.setIndex(oldPos);
               Object parsed = pattern.parseObject(source, pos);
               if (pos.getErrorIndex() == -1 && pos.getIndex() != oldPos) {
                  Number temp = null;
                  if (((Object[])((Object[])parsed)).length != 0) {
                     Object tempObj = ((Object[])((Object[])parsed))[0];
                     if (tempObj instanceof Number) {
                        temp = (Number)tempObj;
                     } else {
                        try {
                           temp = this.format.parse(tempObj.toString());
                        } catch (ParseException var21) {
                           continue;
                        }
                     }
                  }

                  int parseDistance = pos.getIndex() - oldPos;
                  if (parseDistance > longestParseDistance) {
                     resultNumber = temp;
                     resultTimeUnit = timeUnit;
                     newPos = pos.getIndex();
                     longestParseDistance = parseDistance;
                     countOfLongestMatch = count;
                  }
               }
            }
         }
      }

      if (resultNumber == null && longestParseDistance != 0) {
         if (countOfLongestMatch.equals("zero")) {
            resultNumber = 0;
         } else if (countOfLongestMatch.equals("one")) {
            resultNumber = 1;
         } else if (countOfLongestMatch.equals("two")) {
            resultNumber = 2;
         } else {
            resultNumber = 3;
         }
      }

      if (longestParseDistance == 0) {
         pos.setIndex(oldPos);
         pos.setErrorIndex(0);
         return null;
      } else {
         pos.setIndex(newPos);
         pos.setErrorIndex(-1);
         return new TimeUnitAmount((Number)resultNumber, resultTimeUnit);
      }
   }

   private void setup() {
      if (this.locale == null) {
         if (this.format != null) {
            this.locale = this.format.getLocale((ULocale.Type)null);
         } else {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
         }

         this.setLocale(this.locale, this.locale);
      }

      if (this.format == null) {
         this.format = NumberFormat.getNumberInstance(this.locale);
      }

      this.pluralRules = PluralRules.forLocale(this.locale);
      this.timeUnitToCountToPatterns = new HashMap();
      Set pluralKeywords = this.pluralRules.getKeywords();
      this.setup("units/duration", this.timeUnitToCountToPatterns, 0, pluralKeywords);
      this.setup("unitsShort/duration", this.timeUnitToCountToPatterns, 1, pluralKeywords);
      this.isReady = true;
   }

   private void setup(String resourceKey, Map timeUnitToCountToPatterns, int style, Set pluralKeywords) {
      try {
         ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/unit", this.locale);
         TimeUnitFormatSetupSink sink = new TimeUnitFormatSetupSink(timeUnitToCountToPatterns, style, pluralKeywords, this.locale);
         resource.getAllItemsWithFallback(resourceKey, sink);
      } catch (MissingResourceException var12) {
      }

      TimeUnit[] timeUnits = TimeUnit.values();
      Set keywords = this.pluralRules.getKeywords();

      label39:
      for(int i = 0; i < timeUnits.length; ++i) {
         TimeUnit timeUnit = timeUnits[i];
         Map countToPatterns = (Map)timeUnitToCountToPatterns.get(timeUnit);
         if (countToPatterns == null) {
            countToPatterns = new TreeMap();
            timeUnitToCountToPatterns.put(timeUnit, countToPatterns);
         }

         Iterator var10 = keywords.iterator();

         while(true) {
            String pluralCount;
            do {
               if (!var10.hasNext()) {
                  continue label39;
               }

               pluralCount = (String)var10.next();
            } while(((Map)countToPatterns).get(pluralCount) != null && ((Object[])((Map)countToPatterns).get(pluralCount))[style] != null);

            this.searchInTree(resourceKey, style, timeUnit, pluralCount, pluralCount, (Map)countToPatterns);
         }
      }

   }

   private void searchInTree(String resourceKey, int styl, TimeUnit timeUnit, String srcPluralCount, String searchPluralCount, Map countToPatterns) {
      ULocale parentLocale = this.locale;
      String srcTimeUnitName = timeUnit.toString();

      while(parentLocale != null) {
         try {
            ICUResourceBundle unitsRes = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/unit", parentLocale);
            unitsRes = unitsRes.getWithFallback(resourceKey);
            ICUResourceBundle oneUnitRes = unitsRes.getWithFallback(srcTimeUnitName);
            String pattern = oneUnitRes.getStringWithFallback(searchPluralCount);
            MessageFormat messageFormat = new MessageFormat(pattern, this.locale);
            Object[] pair = (Object[])countToPatterns.get(srcPluralCount);
            if (pair == null) {
               pair = new Object[2];
               countToPatterns.put(srcPluralCount, pair);
            }

            pair[styl] = messageFormat;
            return;
         } catch (MissingResourceException var14) {
            parentLocale = parentLocale.getFallback();
         }
      }

      if (parentLocale == null && resourceKey.equals("unitsShort")) {
         this.searchInTree("units", styl, timeUnit, srcPluralCount, searchPluralCount, countToPatterns);
         if (countToPatterns.get(srcPluralCount) != null && ((Object[])countToPatterns.get(srcPluralCount))[styl] != null) {
            return;
         }
      }

      if (searchPluralCount.equals("other")) {
         MessageFormat messageFormat = null;
         if (timeUnit == TimeUnit.SECOND) {
            messageFormat = new MessageFormat("{0} s", this.locale);
         } else if (timeUnit == TimeUnit.MINUTE) {
            messageFormat = new MessageFormat("{0} min", this.locale);
         } else if (timeUnit == TimeUnit.HOUR) {
            messageFormat = new MessageFormat("{0} h", this.locale);
         } else if (timeUnit == TimeUnit.WEEK) {
            messageFormat = new MessageFormat("{0} w", this.locale);
         } else if (timeUnit == TimeUnit.DAY) {
            messageFormat = new MessageFormat("{0} d", this.locale);
         } else if (timeUnit == TimeUnit.MONTH) {
            messageFormat = new MessageFormat("{0} m", this.locale);
         } else if (timeUnit == TimeUnit.YEAR) {
            messageFormat = new MessageFormat("{0} y", this.locale);
         }

         Object[] pair = (Object[])countToPatterns.get(srcPluralCount);
         if (pair == null) {
            pair = new Object[2];
            countToPatterns.put(srcPluralCount, pair);
         }

         pair[styl] = messageFormat;
      } else {
         this.searchInTree(resourceKey, styl, timeUnit, srcPluralCount, "other", countToPatterns);
      }

   }

   /** @deprecated */
   @Deprecated
   public StringBuilder formatMeasures(StringBuilder appendTo, FieldPosition fieldPosition, Measure... measures) {
      return this.mf.formatMeasures(appendTo, fieldPosition, measures);
   }

   /** @deprecated */
   @Deprecated
   public MeasureFormat.FormatWidth getWidth() {
      return this.mf.getWidth();
   }

   /** @deprecated */
   @Deprecated
   public NumberFormat getNumberFormat() {
      return this.mf.getNumberFormat();
   }

   /** @deprecated */
   @Deprecated
   public Object clone() {
      TimeUnitFormat result = (TimeUnitFormat)super.clone();
      result.format = (NumberFormat)this.format.clone();
      return result;
   }

   private Object writeReplace() throws ObjectStreamException {
      return this.mf.toTimeUnitProxy();
   }

   private Object readResolve() throws ObjectStreamException {
      return new TimeUnitFormat(this.locale, this.style, this.format);
   }

   private static final class TimeUnitFormatSetupSink extends UResource.Sink {
      Map timeUnitToCountToPatterns;
      int style;
      Set pluralKeywords;
      ULocale locale;
      boolean beenHere;

      TimeUnitFormatSetupSink(Map timeUnitToCountToPatterns, int style, Set pluralKeywords, ULocale locale) {
         this.timeUnitToCountToPatterns = timeUnitToCountToPatterns;
         this.style = style;
         this.pluralKeywords = pluralKeywords;
         this.locale = locale;
         this.beenHere = false;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         if (!this.beenHere) {
            this.beenHere = true;
            UResource.Table units = value.getTable();

            for(int i = 0; units.getKeyAndValue(i, key, value); ++i) {
               String timeUnitName = key.toString();
               TimeUnit timeUnit = null;
               if (timeUnitName.equals("year")) {
                  timeUnit = TimeUnit.YEAR;
               } else if (timeUnitName.equals("month")) {
                  timeUnit = TimeUnit.MONTH;
               } else if (timeUnitName.equals("day")) {
                  timeUnit = TimeUnit.DAY;
               } else if (timeUnitName.equals("hour")) {
                  timeUnit = TimeUnit.HOUR;
               } else if (timeUnitName.equals("minute")) {
                  timeUnit = TimeUnit.MINUTE;
               } else if (timeUnitName.equals("second")) {
                  timeUnit = TimeUnit.SECOND;
               } else {
                  if (!timeUnitName.equals("week")) {
                     continue;
                  }

                  timeUnit = TimeUnit.WEEK;
               }

               Map countToPatterns = (Map)this.timeUnitToCountToPatterns.get(timeUnit);
               if (countToPatterns == null) {
                  countToPatterns = new TreeMap();
                  this.timeUnitToCountToPatterns.put(timeUnit, countToPatterns);
               }

               UResource.Table countsToPatternTable = value.getTable();

               for(int j = 0; countsToPatternTable.getKeyAndValue(j, key, value); ++j) {
                  String pluralCount = key.toString();
                  if (this.pluralKeywords.contains(pluralCount)) {
                     Object[] pair = (Object[])((Map)countToPatterns).get(pluralCount);
                     if (pair == null) {
                        pair = new Object[2];
                        ((Map)countToPatterns).put(pluralCount, pair);
                     }

                     if (pair[this.style] == null) {
                        String pattern = value.getString();
                        MessageFormat messageFormat = new MessageFormat(pattern, this.locale);
                        pair[this.style] = messageFormat;
                     }
                  }
               }
            }

         }
      }
   }
}
