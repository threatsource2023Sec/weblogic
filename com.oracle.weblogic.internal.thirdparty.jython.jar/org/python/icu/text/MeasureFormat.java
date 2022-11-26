package org.python.icu.text;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;
import org.python.icu.impl.DontCareFieldPosition;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.UResource;
import org.python.icu.util.Currency;
import org.python.icu.util.CurrencyAmount;
import org.python.icu.util.ICUException;
import org.python.icu.util.Measure;
import org.python.icu.util.MeasureUnit;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class MeasureFormat extends UFormat {
   static final long serialVersionUID = -7182021401701778240L;
   private final transient MeasureFormatData cache;
   private final transient ImmutableNumberFormat numberFormat;
   private final transient FormatWidth formatWidth;
   private final transient PluralRules rules;
   private final transient NumericFormatters numericFormatters;
   private final transient ImmutableNumberFormat currencyFormat;
   private final transient ImmutableNumberFormat integerFormat;
   private static final SimpleCache localeMeasureFormatData = new SimpleCache();
   private static final SimpleCache localeToNumericDurationFormatters = new SimpleCache();
   private static final Map hmsTo012 = new HashMap();
   private static final int MEASURE_FORMAT = 0;
   private static final int TIME_UNIT_FORMAT = 1;
   private static final int CURRENCY_FORMAT = 2;
   private static final Map localeIdToRangeFormat;

   public static MeasureFormat getInstance(ULocale locale, FormatWidth formatWidth) {
      return getInstance(locale, formatWidth, NumberFormat.getInstance(locale));
   }

   public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth) {
      return getInstance(ULocale.forLocale(locale), formatWidth);
   }

   public static MeasureFormat getInstance(ULocale locale, FormatWidth formatWidth, NumberFormat format) {
      PluralRules rules = PluralRules.forLocale(locale);
      NumericFormatters formatters = null;
      MeasureFormatData data = (MeasureFormatData)localeMeasureFormatData.get(locale);
      if (data == null) {
         data = loadLocaleData(locale);
         localeMeasureFormatData.put(locale, data);
      }

      if (formatWidth == MeasureFormat.FormatWidth.NUMERIC) {
         formatters = (NumericFormatters)localeToNumericDurationFormatters.get(locale);
         if (formatters == null) {
            formatters = loadNumericFormatters(locale);
            localeToNumericDurationFormatters.put(locale, formatters);
         }
      }

      NumberFormat intFormat = NumberFormat.getInstance(locale);
      intFormat.setMaximumFractionDigits(0);
      intFormat.setMinimumFractionDigits(0);
      intFormat.setRoundingMode(1);
      return new MeasureFormat(locale, data, formatWidth, new ImmutableNumberFormat(format), rules, formatters, new ImmutableNumberFormat(NumberFormat.getInstance(locale, formatWidth.getCurrencyStyle())), new ImmutableNumberFormat(intFormat));
   }

   public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth, NumberFormat format) {
      return getInstance(ULocale.forLocale(locale), formatWidth, format);
   }

   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      int prevLength = toAppendTo.length();
      FieldPosition fpos = new FieldPosition(pos.getFieldAttribute(), pos.getField());
      if (obj instanceof Collection) {
         Collection coll = (Collection)obj;
         Measure[] measures = new Measure[coll.size()];
         int idx = 0;

         Object o;
         for(Iterator var9 = coll.iterator(); var9.hasNext(); measures[idx++] = (Measure)o) {
            o = var9.next();
            if (!(o instanceof Measure)) {
               throw new IllegalArgumentException(obj.toString());
            }
         }

         toAppendTo.append(this.formatMeasures(new StringBuilder(), fpos, measures));
      } else if (obj instanceof Measure[]) {
         toAppendTo.append(this.formatMeasures(new StringBuilder(), fpos, (Measure[])((Measure[])obj)));
      } else {
         if (!(obj instanceof Measure)) {
            throw new IllegalArgumentException(obj.toString());
         }

         toAppendTo.append(this.formatMeasure((Measure)obj, this.numberFormat, new StringBuilder(), fpos));
      }

      if (fpos.getBeginIndex() != 0 || fpos.getEndIndex() != 0) {
         pos.setBeginIndex(fpos.getBeginIndex() + prevLength);
         pos.setEndIndex(fpos.getEndIndex() + prevLength);
      }

      return toAppendTo;
   }

   public Measure parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException();
   }

   public final String formatMeasures(Measure... measures) {
      return this.formatMeasures(new StringBuilder(), DontCareFieldPosition.INSTANCE, measures).toString();
   }

   /** @deprecated */
   @Deprecated
   public final String formatMeasureRange(Measure lowValue, Measure highValue) {
      MeasureUnit unit = lowValue.getUnit();
      if (!unit.equals(highValue.getUnit())) {
         throw new IllegalArgumentException("Units must match: " + unit + " ≠ " + highValue.getUnit());
      } else {
         Number lowNumber = lowValue.getNumber();
         Number highNumber = highValue.getNumber();
         boolean isCurrency = unit instanceof Currency;
         UFieldPosition lowFpos = new UFieldPosition();
         UFieldPosition highFpos = new UFieldPosition();
         StringBuffer lowFormatted = null;
         StringBuffer highFormatted = null;
         if (isCurrency) {
            Currency currency = (Currency)unit;
            int fracDigits = currency.getDefaultFractionDigits();
            int maxFrac = this.numberFormat.nf.getMaximumFractionDigits();
            int minFrac = this.numberFormat.nf.getMinimumFractionDigits();
            if (fracDigits != maxFrac || fracDigits != minFrac) {
               DecimalFormat currentNumberFormat = (DecimalFormat)this.numberFormat.get();
               currentNumberFormat.setMaximumFractionDigits(fracDigits);
               currentNumberFormat.setMinimumFractionDigits(fracDigits);
               lowFormatted = currentNumberFormat.format((Object)lowNumber, new StringBuffer(), lowFpos);
               highFormatted = currentNumberFormat.format((Object)highNumber, new StringBuffer(), highFpos);
            }
         }

         if (lowFormatted == null) {
            lowFormatted = this.numberFormat.format((Number)lowNumber, new StringBuffer(), lowFpos);
            highFormatted = this.numberFormat.format((Number)highNumber, new StringBuffer(), highFpos);
         }

         double lowDouble = lowNumber.doubleValue();
         String keywordLow = this.rules.select(new PluralRules.FixedDecimal(lowDouble, lowFpos.getCountVisibleFractionDigits(), lowFpos.getFractionDigits()));
         double highDouble = highNumber.doubleValue();
         String keywordHigh = this.rules.select(new PluralRules.FixedDecimal(highDouble, highFpos.getCountVisibleFractionDigits(), highFpos.getFractionDigits()));
         PluralRanges pluralRanges = PluralRules.Factory.getDefaultFactory().getPluralRanges(this.getLocale());
         StandardPlural resolvedPlural = pluralRanges.get(StandardPlural.fromString(keywordLow), StandardPlural.fromString(keywordHigh));
         String rangeFormatter = getRangeFormat(this.getLocale(), this.formatWidth);
         String formattedNumber = SimpleFormatterImpl.formatCompiledPattern(rangeFormatter, lowFormatted, highFormatted);
         if (isCurrency) {
            this.currencyFormat.format(1.0);
            Currency currencyUnit = (Currency)unit;
            StringBuilder result = new StringBuilder();
            this.appendReplacingCurrency(this.currencyFormat.getPrefix(lowDouble >= 0.0), currencyUnit, resolvedPlural, result);
            result.append(formattedNumber);
            this.appendReplacingCurrency(this.currencyFormat.getSuffix(highDouble >= 0.0), currencyUnit, resolvedPlural, result);
            return result.toString();
         } else {
            String formatter = this.getPluralFormatter(lowValue.getUnit(), this.formatWidth, resolvedPlural.ordinal());
            return SimpleFormatterImpl.formatCompiledPattern(formatter, formattedNumber);
         }
      }
   }

   private void appendReplacingCurrency(String affix, Currency unit, StandardPlural resolvedPlural, StringBuilder result) {
      String replacement = "¤";
      int pos = affix.indexOf(replacement);
      if (pos < 0) {
         replacement = "XXX";
         pos = affix.indexOf(replacement);
      }

      if (pos < 0) {
         result.append(affix);
      } else {
         result.append(affix.substring(0, pos));
         int currentStyle = this.formatWidth.getCurrencyStyle();
         if (currentStyle == 5) {
            result.append(unit.getCurrencyCode());
         } else {
            result.append(unit.getName((ULocale)this.currencyFormat.nf.getLocale(ULocale.ACTUAL_LOCALE), currentStyle == 1 ? 0 : 2, resolvedPlural.getKeyword(), (boolean[])null));
         }

         result.append(affix.substring(pos + replacement.length()));
      }

   }

   public StringBuilder formatMeasurePerUnit(Measure measure, MeasureUnit perUnit, StringBuilder appendTo, FieldPosition pos) {
      MeasureUnit resolvedUnit = MeasureUnit.resolveUnitPerUnit(measure.getUnit(), perUnit);
      if (resolvedUnit != null) {
         Measure newMeasure = new Measure(measure.getNumber(), resolvedUnit);
         return this.formatMeasure(newMeasure, this.numberFormat, appendTo, pos);
      } else {
         FieldPosition fpos = new FieldPosition(pos.getFieldAttribute(), pos.getField());
         int offset = this.withPerUnitAndAppend(this.formatMeasure(measure, this.numberFormat, new StringBuilder(), fpos), perUnit, appendTo);
         if (fpos.getBeginIndex() != 0 || fpos.getEndIndex() != 0) {
            pos.setBeginIndex(fpos.getBeginIndex() + offset);
            pos.setEndIndex(fpos.getEndIndex() + offset);
         }

         return appendTo;
      }
   }

   public StringBuilder formatMeasures(StringBuilder appendTo, FieldPosition fieldPosition, Measure... measures) {
      if (measures.length == 0) {
         return appendTo;
      } else if (measures.length == 1) {
         return this.formatMeasure(measures[0], this.numberFormat, appendTo, fieldPosition);
      } else {
         if (this.formatWidth == MeasureFormat.FormatWidth.NUMERIC) {
            Number[] hms = toHMS(measures);
            if (hms != null) {
               return this.formatNumeric(hms, appendTo);
            }
         }

         ListFormatter listFormatter = ListFormatter.getInstance(this.getLocale(), this.formatWidth.getListFormatterStyle());
         if (fieldPosition != DontCareFieldPosition.INSTANCE) {
            return this.formatMeasuresSlowTrack(listFormatter, appendTo, fieldPosition, measures);
         } else {
            String[] results = new String[measures.length];

            for(int i = 0; i < measures.length; ++i) {
               results[i] = this.formatMeasure(measures[i], i == measures.length - 1 ? this.numberFormat : this.integerFormat);
            }

            return appendTo.append(listFormatter.format((Object[])results));
         }
      }
   }

   public String getUnitDisplayName(MeasureUnit unit) {
      FormatWidth width = getRegularWidth(this.formatWidth);
      Map styleToDnam = (Map)this.cache.unitToStyleToDnam.get(unit);
      if (styleToDnam == null) {
         return null;
      } else {
         String dnam = (String)styleToDnam.get(width);
         if (dnam != null) {
            return dnam;
         } else {
            FormatWidth fallbackWidth = this.cache.widthFallback[width.ordinal()];
            if (fallbackWidth != null) {
               dnam = (String)styleToDnam.get(fallbackWidth);
            }

            return dnam;
         }
      }
   }

   public final boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MeasureFormat)) {
         return false;
      } else {
         MeasureFormat rhs = (MeasureFormat)other;
         return this.getWidth() == rhs.getWidth() && this.getLocale().equals(rhs.getLocale()) && this.getNumberFormat().equals(rhs.getNumberFormat());
      }
   }

   public final int hashCode() {
      return (this.getLocale().hashCode() * 31 + this.getNumberFormat().hashCode()) * 31 + this.getWidth().hashCode();
   }

   public FormatWidth getWidth() {
      return this.formatWidth;
   }

   public final ULocale getLocale() {
      return this.getLocale(ULocale.VALID_LOCALE);
   }

   public NumberFormat getNumberFormat() {
      return this.numberFormat.get();
   }

   public static MeasureFormat getCurrencyFormat(ULocale locale) {
      return new CurrencyFormat(locale);
   }

   public static MeasureFormat getCurrencyFormat(Locale locale) {
      return getCurrencyFormat(ULocale.forLocale(locale));
   }

   public static MeasureFormat getCurrencyFormat() {
      return getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   MeasureFormat withLocale(ULocale locale) {
      return getInstance(locale, this.getWidth());
   }

   MeasureFormat withNumberFormat(NumberFormat format) {
      return new MeasureFormat(this.getLocale(), this.cache, this.formatWidth, new ImmutableNumberFormat(format), this.rules, this.numericFormatters, this.currencyFormat, this.integerFormat);
   }

   private MeasureFormat(ULocale locale, MeasureFormatData data, FormatWidth formatWidth, ImmutableNumberFormat format, PluralRules rules, NumericFormatters formatters, ImmutableNumberFormat currencyFormat, ImmutableNumberFormat integerFormat) {
      this.setLocale(locale, locale);
      this.cache = data;
      this.formatWidth = formatWidth;
      this.numberFormat = format;
      this.rules = rules;
      this.numericFormatters = formatters;
      this.currencyFormat = currencyFormat;
      this.integerFormat = integerFormat;
   }

   MeasureFormat() {
      this.cache = null;
      this.formatWidth = null;
      this.numberFormat = null;
      this.rules = null;
      this.numericFormatters = null;
      this.currencyFormat = null;
      this.integerFormat = null;
   }

   private static NumericFormatters loadNumericFormatters(ULocale locale) {
      ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/unit", locale);
      return new NumericFormatters(loadNumericDurationFormat(r, "hm"), loadNumericDurationFormat(r, "ms"), loadNumericDurationFormat(r, "hms"));
   }

   private static MeasureFormatData loadLocaleData(ULocale locale) {
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/unit", locale);
      MeasureFormatData cacheData = new MeasureFormatData();
      UnitDataSink sink = new UnitDataSink(cacheData);
      resource.getAllItemsWithFallback("", sink);
      return cacheData;
   }

   private static final FormatWidth getRegularWidth(FormatWidth width) {
      return width == MeasureFormat.FormatWidth.NUMERIC ? MeasureFormat.FormatWidth.NARROW : width;
   }

   private String getFormatterOrNull(MeasureUnit unit, FormatWidth width, int index) {
      width = getRegularWidth(width);
      Map styleToPatterns = (Map)this.cache.unitToStyleToPatterns.get(unit);
      String[] patterns = (String[])styleToPatterns.get(width);
      if (patterns != null && patterns[index] != null) {
         return patterns[index];
      } else {
         FormatWidth fallbackWidth = this.cache.widthFallback[width.ordinal()];
         if (fallbackWidth != null) {
            patterns = (String[])styleToPatterns.get(fallbackWidth);
            if (patterns != null && patterns[index] != null) {
               return patterns[index];
            }
         }

         return null;
      }
   }

   private String getFormatter(MeasureUnit unit, FormatWidth width, int index) {
      String pattern = this.getFormatterOrNull(unit, width, index);
      if (pattern == null) {
         throw new MissingResourceException("no formatting pattern for " + unit + ", width " + width + ", index " + index, (String)null, (String)null);
      } else {
         return pattern;
      }
   }

   /** @deprecated */
   @Deprecated
   public String getPluralFormatter(MeasureUnit unit, FormatWidth width, int index) {
      if (index != StandardPlural.OTHER_INDEX) {
         String pattern = this.getFormatterOrNull(unit, width, index);
         if (pattern != null) {
            return pattern;
         }
      }

      return this.getFormatter(unit, width, StandardPlural.OTHER_INDEX);
   }

   private String getPerFormatter(FormatWidth width) {
      width = getRegularWidth(width);
      String perPattern = (String)this.cache.styleToPerPattern.get(width);
      if (perPattern != null) {
         return perPattern;
      } else {
         FormatWidth fallbackWidth = this.cache.widthFallback[width.ordinal()];
         if (fallbackWidth != null) {
            perPattern = (String)this.cache.styleToPerPattern.get(fallbackWidth);
            if (perPattern != null) {
               return perPattern;
            }
         }

         throw new MissingResourceException("no x-per-y pattern for width " + width, (String)null, (String)null);
      }
   }

   private int withPerUnitAndAppend(CharSequence formatted, MeasureUnit perUnit, StringBuilder appendTo) {
      int[] offsets = new int[1];
      String perUnitPattern = this.getFormatterOrNull(perUnit, this.formatWidth, MeasureFormat.MeasureFormatData.PER_UNIT_INDEX);
      if (perUnitPattern != null) {
         SimpleFormatterImpl.formatAndAppend(perUnitPattern, appendTo, offsets, formatted);
         return offsets[0];
      } else {
         String perPattern = this.getPerFormatter(this.formatWidth);
         String pattern = this.getPluralFormatter(perUnit, this.formatWidth, StandardPlural.ONE.ordinal());
         String perUnitString = SimpleFormatterImpl.getTextWithNoArguments(pattern).trim();
         SimpleFormatterImpl.formatAndAppend(perPattern, appendTo, offsets, formatted, perUnitString);
         return offsets[0];
      }
   }

   private String formatMeasure(Measure measure, ImmutableNumberFormat nf) {
      return this.formatMeasure(measure, nf, new StringBuilder(), DontCareFieldPosition.INSTANCE).toString();
   }

   private StringBuilder formatMeasure(Measure measure, ImmutableNumberFormat nf, StringBuilder appendTo, FieldPosition fieldPosition) {
      Number n = measure.getNumber();
      MeasureUnit unit = measure.getUnit();
      if (unit instanceof Currency) {
         return appendTo.append(this.currencyFormat.format(new CurrencyAmount(n, (Currency)unit), new StringBuffer(), fieldPosition));
      } else {
         StringBuffer formattedNumber = new StringBuffer();
         StandardPlural pluralForm = QuantityFormatter.selectPlural(n, nf.nf, this.rules, formattedNumber, fieldPosition);
         String formatter = this.getPluralFormatter(unit, this.formatWidth, pluralForm.ordinal());
         return QuantityFormatter.format(formatter, formattedNumber, appendTo, fieldPosition);
      }
   }

   Object toTimeUnitProxy() {
      return new MeasureProxy(this.getLocale(), this.formatWidth, this.numberFormat.get(), 1);
   }

   Object toCurrencyProxy() {
      return new MeasureProxy(this.getLocale(), this.formatWidth, this.numberFormat.get(), 2);
   }

   private StringBuilder formatMeasuresSlowTrack(ListFormatter listFormatter, StringBuilder appendTo, FieldPosition fieldPosition, Measure... measures) {
      String[] results = new String[measures.length];
      FieldPosition fpos = new FieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
      int fieldPositionFoundIndex = -1;

      for(int i = 0; i < measures.length; ++i) {
         ImmutableNumberFormat nf = i == measures.length - 1 ? this.numberFormat : this.integerFormat;
         if (fieldPositionFoundIndex == -1) {
            results[i] = this.formatMeasure(measures[i], nf, new StringBuilder(), fpos).toString();
            if (fpos.getBeginIndex() != 0 || fpos.getEndIndex() != 0) {
               fieldPositionFoundIndex = i;
            }
         } else {
            results[i] = this.formatMeasure(measures[i], nf);
         }
      }

      ListFormatter.FormattedListBuilder builder = listFormatter.format(Arrays.asList(results), fieldPositionFoundIndex);
      if (builder.getOffset() != -1) {
         fieldPosition.setBeginIndex(fpos.getBeginIndex() + builder.getOffset() + appendTo.length());
         fieldPosition.setEndIndex(fpos.getEndIndex() + builder.getOffset() + appendTo.length());
      }

      return appendTo.append(builder.toString());
   }

   private static DateFormat loadNumericDurationFormat(ICUResourceBundle r, String type) {
      r = r.getWithFallback(String.format("durationUnits/%s", type));
      DateFormat result = new SimpleDateFormat(r.getString().replace("h", "H"));
      result.setTimeZone(TimeZone.GMT_ZONE);
      return result;
   }

   private static Number[] toHMS(Measure[] measures) {
      Number[] result = new Number[3];
      int lastIdx = -1;
      Measure[] var3 = measures;
      int var4 = measures.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Measure m = var3[var5];
         if (m.getNumber().doubleValue() < 0.0) {
            return null;
         }

         Integer idxObj = (Integer)hmsTo012.get(m.getUnit());
         if (idxObj == null) {
            return null;
         }

         int idx = idxObj;
         if (idx <= lastIdx) {
            return null;
         }

         lastIdx = idx;
         result[idx] = m.getNumber();
      }

      return result;
   }

   private StringBuilder formatNumeric(Number[] hms, StringBuilder appendable) {
      int startIndex = -1;
      int endIndex = -1;

      for(int i = 0; i < hms.length; ++i) {
         if (hms[i] != null) {
            endIndex = i;
            if (startIndex == -1) {
               startIndex = i;
            }
         } else {
            hms[i] = 0;
         }
      }

      long millis = (long)(((Math.floor(hms[0].doubleValue()) * 60.0 + Math.floor(hms[1].doubleValue())) * 60.0 + Math.floor(hms[2].doubleValue())) * 1000.0);
      Date d = new Date(millis);
      if (startIndex == 0 && endIndex == 2) {
         return this.formatNumeric(d, this.numericFormatters.getHourMinuteSecond(), DateFormat.Field.SECOND, hms[endIndex], appendable);
      } else if (startIndex == 1 && endIndex == 2) {
         return this.formatNumeric(d, this.numericFormatters.getMinuteSecond(), DateFormat.Field.SECOND, hms[endIndex], appendable);
      } else if (startIndex == 0 && endIndex == 1) {
         return this.formatNumeric(d, this.numericFormatters.getHourMinute(), DateFormat.Field.MINUTE, hms[endIndex], appendable);
      } else {
         throw new IllegalStateException();
      }
   }

   private StringBuilder formatNumeric(Date duration, DateFormat formatter, DateFormat.Field smallestField, Number smallestAmount, StringBuilder appendTo) {
      FieldPosition intFieldPosition = new FieldPosition(0);
      String smallestAmountFormatted = this.numberFormat.format(smallestAmount, new StringBuffer(), intFieldPosition).toString();
      if (intFieldPosition.getBeginIndex() == 0 && intFieldPosition.getEndIndex() == 0) {
         throw new IllegalStateException();
      } else {
         FieldPosition smallestFieldPosition = new FieldPosition(smallestField);
         String draft = formatter.format(duration, new StringBuffer(), smallestFieldPosition).toString();
         if (smallestFieldPosition.getBeginIndex() == 0 && smallestFieldPosition.getEndIndex() == 0) {
            appendTo.append(draft);
         } else {
            appendTo.append(draft, 0, smallestFieldPosition.getBeginIndex());
            appendTo.append(smallestAmountFormatted, 0, intFieldPosition.getBeginIndex());
            appendTo.append(draft, smallestFieldPosition.getBeginIndex(), smallestFieldPosition.getEndIndex());
            appendTo.append(smallestAmountFormatted, intFieldPosition.getEndIndex(), smallestAmountFormatted.length());
            appendTo.append(draft, smallestFieldPosition.getEndIndex(), draft.length());
         }

         return appendTo;
      }
   }

   private Object writeReplace() throws ObjectStreamException {
      return new MeasureProxy(this.getLocale(), this.formatWidth, this.numberFormat.get(), 0);
   }

   private static FormatWidth fromFormatWidthOrdinal(int ordinal) {
      FormatWidth[] values = MeasureFormat.FormatWidth.values();
      return ordinal >= 0 && ordinal < values.length ? values[ordinal] : MeasureFormat.FormatWidth.SHORT;
   }

   /** @deprecated */
   @Deprecated
   public static String getRangeFormat(ULocale forLocale, FormatWidth width) {
      if (forLocale.getLanguage().equals("fr")) {
         return getRangeFormat(ULocale.ROOT, width);
      } else {
         String result = (String)localeIdToRangeFormat.get(forLocale);
         if (result == null) {
            ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", forLocale);
            ULocale realLocale = rb.getULocale();
            if (!forLocale.equals(realLocale)) {
               result = (String)localeIdToRangeFormat.get(forLocale);
               if (result != null) {
                  localeIdToRangeFormat.put(forLocale, result);
                  return result;
               }
            }

            NumberingSystem ns = NumberingSystem.getInstance(forLocale);
            String resultString = null;

            try {
               resultString = rb.getStringWithFallback("NumberElements/" + ns.getName() + "/miscPatterns/range");
            } catch (MissingResourceException var8) {
               resultString = rb.getStringWithFallback("NumberElements/latn/patterns/range");
            }

            result = SimpleFormatterImpl.compileToStringMinMaxArguments(resultString, new StringBuilder(), 2, 2);
            localeIdToRangeFormat.put(forLocale, result);
            if (!forLocale.equals(realLocale)) {
               localeIdToRangeFormat.put(realLocale, result);
            }
         }

         return result;
      }
   }

   static {
      hmsTo012.put(MeasureUnit.HOUR, 0);
      hmsTo012.put(MeasureUnit.MINUTE, 1);
      hmsTo012.put(MeasureUnit.SECOND, 2);
      localeIdToRangeFormat = new ConcurrentHashMap();
   }

   static class MeasureProxy implements Externalizable {
      private static final long serialVersionUID = -6033308329886716770L;
      private ULocale locale;
      private FormatWidth formatWidth;
      private NumberFormat numberFormat;
      private int subClass;
      private HashMap keyValues;

      public MeasureProxy(ULocale locale, FormatWidth width, NumberFormat numberFormat, int subClass) {
         this.locale = locale;
         this.formatWidth = width;
         this.numberFormat = numberFormat;
         this.subClass = subClass;
         this.keyValues = new HashMap();
      }

      public MeasureProxy() {
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeByte(0);
         out.writeUTF(this.locale.toLanguageTag());
         out.writeByte(this.formatWidth.ordinal());
         out.writeObject(this.numberFormat);
         out.writeByte(this.subClass);
         out.writeObject(this.keyValues);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         in.readByte();
         this.locale = ULocale.forLanguageTag(in.readUTF());
         this.formatWidth = MeasureFormat.fromFormatWidthOrdinal(in.readByte() & 255);
         this.numberFormat = (NumberFormat)in.readObject();
         if (this.numberFormat == null) {
            throw new InvalidObjectException("Missing number format.");
         } else {
            this.subClass = in.readByte() & 255;
            this.keyValues = (HashMap)in.readObject();
            if (this.keyValues == null) {
               throw new InvalidObjectException("Missing optional values map.");
            }
         }
      }

      private TimeUnitFormat createTimeUnitFormat() throws InvalidObjectException {
         byte style;
         if (this.formatWidth == MeasureFormat.FormatWidth.WIDE) {
            style = 0;
         } else {
            if (this.formatWidth != MeasureFormat.FormatWidth.SHORT) {
               throw new InvalidObjectException("Bad width: " + this.formatWidth);
            }

            style = 1;
         }

         TimeUnitFormat result = new TimeUnitFormat(this.locale, style);
         result.setNumberFormat(this.numberFormat);
         return result;
      }

      private Object readResolve() throws ObjectStreamException {
         switch (this.subClass) {
            case 0:
               return MeasureFormat.getInstance(this.locale, this.formatWidth, this.numberFormat);
            case 1:
               return this.createTimeUnitFormat();
            case 2:
               return new CurrencyFormat(this.locale);
            default:
               throw new InvalidObjectException("Unknown subclass: " + this.subClass);
         }
      }
   }

   static final class PatternData {
      final String prefix;
      final String suffix;

      public PatternData(String pattern) {
         int pos = pattern.indexOf("{0}");
         if (pos < 0) {
            this.prefix = pattern;
            this.suffix = null;
         } else {
            this.prefix = pattern.substring(0, pos);
            this.suffix = pattern.substring(pos + 3);
         }

      }

      public String toString() {
         return this.prefix + "; " + this.suffix;
      }
   }

   private static final class ImmutableNumberFormat {
      private NumberFormat nf;

      public ImmutableNumberFormat(NumberFormat nf) {
         this.nf = (NumberFormat)nf.clone();
      }

      public synchronized NumberFormat get() {
         return (NumberFormat)this.nf.clone();
      }

      public synchronized StringBuffer format(Number n, StringBuffer buffer, FieldPosition pos) {
         return this.nf.format((Object)n, buffer, pos);
      }

      public synchronized StringBuffer format(CurrencyAmount n, StringBuffer buffer, FieldPosition pos) {
         return this.nf.format(n, buffer, pos);
      }

      public synchronized String format(Number number) {
         return this.nf.format((Object)number);
      }

      public String getPrefix(boolean positive) {
         return positive ? ((DecimalFormat)this.nf).getPositivePrefix() : ((DecimalFormat)this.nf).getNegativePrefix();
      }

      public String getSuffix(boolean positive) {
         return positive ? ((DecimalFormat)this.nf).getPositiveSuffix() : ((DecimalFormat)this.nf).getNegativeSuffix();
      }
   }

   private static final class MeasureFormatData {
      static final int PER_UNIT_INDEX;
      static final int PATTERN_COUNT;
      final FormatWidth[] widthFallback;
      final Map unitToStyleToPatterns;
      final Map unitToStyleToDnam;
      final EnumMap styleToPerPattern;

      private MeasureFormatData() {
         this.widthFallback = new FormatWidth[3];
         this.unitToStyleToPatterns = new HashMap();
         this.unitToStyleToDnam = new HashMap();
         this.styleToPerPattern = new EnumMap(FormatWidth.class);
      }

      boolean hasPerFormatter(FormatWidth width) {
         return this.styleToPerPattern.containsKey(width);
      }

      // $FF: synthetic method
      MeasureFormatData(Object x0) {
         this();
      }

      static {
         PER_UNIT_INDEX = StandardPlural.COUNT;
         PATTERN_COUNT = PER_UNIT_INDEX + 1;
      }
   }

   private static final class UnitDataSink extends UResource.Sink {
      MeasureFormatData cacheData;
      FormatWidth width;
      String type;
      MeasureUnit unit;
      StringBuilder sb = new StringBuilder();
      String[] patterns;

      void setFormatterIfAbsent(int index, UResource.Value value, int minPlaceholders) {
         if (this.patterns == null) {
            EnumMap styleToPatterns = (EnumMap)this.cacheData.unitToStyleToPatterns.get(this.unit);
            if (styleToPatterns == null) {
               styleToPatterns = new EnumMap(FormatWidth.class);
               this.cacheData.unitToStyleToPatterns.put(this.unit, styleToPatterns);
            } else {
               this.patterns = (String[])styleToPatterns.get(this.width);
            }

            if (this.patterns == null) {
               this.patterns = new String[MeasureFormat.MeasureFormatData.PATTERN_COUNT];
               styleToPatterns.put(this.width, this.patterns);
            }
         }

         if (this.patterns[index] == null) {
            this.patterns[index] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, minPlaceholders, 1);
         }

      }

      void setDnamIfAbsent(UResource.Value value) {
         EnumMap styleToDnam = (EnumMap)this.cacheData.unitToStyleToDnam.get(this.unit);
         if (styleToDnam == null) {
            styleToDnam = new EnumMap(FormatWidth.class);
            this.cacheData.unitToStyleToDnam.put(this.unit, styleToDnam);
         }

         if (styleToDnam.get(this.width) == null) {
            styleToDnam.put(this.width, value.getString());
         }

      }

      void consumePattern(UResource.Key key, UResource.Value value) {
         if (key.contentEquals("dnam")) {
            this.setDnamIfAbsent(value);
         } else if (key.contentEquals("per")) {
            this.setFormatterIfAbsent(MeasureFormat.MeasureFormatData.PER_UNIT_INDEX, value, 1);
         } else {
            this.setFormatterIfAbsent(StandardPlural.indexFromString(key), value, 0);
         }

      }

      void consumeSubtypeTable(UResource.Key key, UResource.Value value) {
         this.unit = MeasureUnit.internalGetInstance(this.type, key.toString());
         this.patterns = null;
         if (value.getType() != 2) {
            throw new ICUException("Data for unit '" + this.unit + "' is in an unknown format");
         } else {
            UResource.Table patternTableTable = value.getTable();

            for(int i = 0; patternTableTable.getKeyAndValue(i, key, value); ++i) {
               this.consumePattern(key, value);
            }

         }
      }

      void consumeCompoundPattern(UResource.Key key, UResource.Value value) {
         if (key.contentEquals("per")) {
            this.cacheData.styleToPerPattern.put(this.width, SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 2, 2));
         }

      }

      void consumeUnitTypesTable(UResource.Key key, UResource.Value value) {
         if (!key.contentEquals("currency")) {
            UResource.Table compoundTable;
            int i;
            if (key.contentEquals("compound")) {
               if (!this.cacheData.hasPerFormatter(this.width)) {
                  compoundTable = value.getTable();

                  for(i = 0; compoundTable.getKeyAndValue(i, key, value); ++i) {
                     this.consumeCompoundPattern(key, value);
                  }
               }
            } else if (!key.contentEquals("coordinate")) {
               this.type = key.toString();
               compoundTable = value.getTable();

               for(i = 0; compoundTable.getKeyAndValue(i, key, value); ++i) {
                  this.consumeSubtypeTable(key, value);
               }
            }
         }

      }

      UnitDataSink(MeasureFormatData outputData) {
         this.cacheData = outputData;
      }

      void consumeAlias(UResource.Key key, UResource.Value value) {
         FormatWidth sourceWidth = widthFromKey(key);
         if (sourceWidth != null) {
            FormatWidth targetWidth = widthFromAlias(value);
            if (targetWidth == null) {
               throw new ICUException("Units data fallback from " + key + " to unknown " + value.getAliasString());
            } else if (this.cacheData.widthFallback[targetWidth.ordinal()] != null) {
               throw new ICUException("Units data fallback from " + key + " to " + value.getAliasString() + " which falls back to something else");
            } else {
               this.cacheData.widthFallback[sourceWidth.ordinal()] = targetWidth;
            }
         }
      }

      public void consumeTable(UResource.Key key, UResource.Value value) {
         if ((this.width = widthFromKey(key)) != null) {
            UResource.Table unitTypesTable = value.getTable();

            for(int i = 0; unitTypesTable.getKeyAndValue(i, key, value); ++i) {
               this.consumeUnitTypesTable(key, value);
            }
         }

      }

      static FormatWidth widthFromKey(UResource.Key key) {
         if (key.startsWith("units")) {
            if (key.length() == 5) {
               return MeasureFormat.FormatWidth.WIDE;
            }

            if (key.regionMatches(5, "Short")) {
               return MeasureFormat.FormatWidth.SHORT;
            }

            if (key.regionMatches(5, "Narrow")) {
               return MeasureFormat.FormatWidth.NARROW;
            }
         }

         return null;
      }

      static FormatWidth widthFromAlias(UResource.Value value) {
         String s = value.getAliasString();
         if (s.startsWith("/LOCALE/units")) {
            if (s.length() == 13) {
               return MeasureFormat.FormatWidth.WIDE;
            }

            if (s.length() == 18 && s.endsWith("Short")) {
               return MeasureFormat.FormatWidth.SHORT;
            }

            if (s.length() == 19 && s.endsWith("Narrow")) {
               return MeasureFormat.FormatWidth.NARROW;
            }
         }

         return null;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table widthsTable = value.getTable();

         for(int i = 0; widthsTable.getKeyAndValue(i, key, value); ++i) {
            if (value.getType() == 3) {
               this.consumeAlias(key, value);
            } else {
               this.consumeTable(key, value);
            }
         }

      }
   }

   static class NumericFormatters {
      private DateFormat hourMinute;
      private DateFormat minuteSecond;
      private DateFormat hourMinuteSecond;

      public NumericFormatters(DateFormat hourMinute, DateFormat minuteSecond, DateFormat hourMinuteSecond) {
         this.hourMinute = hourMinute;
         this.minuteSecond = minuteSecond;
         this.hourMinuteSecond = hourMinuteSecond;
      }

      public DateFormat getHourMinute() {
         return this.hourMinute;
      }

      public DateFormat getMinuteSecond() {
         return this.minuteSecond;
      }

      public DateFormat getHourMinuteSecond() {
         return this.hourMinuteSecond;
      }
   }

   public static enum FormatWidth {
      WIDE(ListFormatter.Style.DURATION, 6),
      SHORT(ListFormatter.Style.DURATION_SHORT, 5),
      NARROW(ListFormatter.Style.DURATION_NARROW, 1),
      NUMERIC(ListFormatter.Style.DURATION_NARROW, 1);

      private static final int INDEX_COUNT = 3;
      private final ListFormatter.Style listFormatterStyle;
      private final int currencyStyle;

      private FormatWidth(ListFormatter.Style style, int currencyStyle) {
         this.listFormatterStyle = style;
         this.currencyStyle = currencyStyle;
      }

      ListFormatter.Style getListFormatterStyle() {
         return this.listFormatterStyle;
      }

      int getCurrencyStyle() {
         return this.currencyStyle;
      }
   }
}
