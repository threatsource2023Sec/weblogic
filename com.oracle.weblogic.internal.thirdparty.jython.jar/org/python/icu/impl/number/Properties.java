package org.python.icu.impl.number;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.python.icu.impl.number.formatters.BigDecimalMultiplier;
import org.python.icu.impl.number.formatters.CompactDecimalFormat;
import org.python.icu.impl.number.formatters.CurrencyFormat;
import org.python.icu.impl.number.formatters.MagnitudeMultiplier;
import org.python.icu.impl.number.formatters.MeasureFormat;
import org.python.icu.impl.number.formatters.PaddingFormat;
import org.python.icu.impl.number.formatters.PositiveDecimalFormat;
import org.python.icu.impl.number.formatters.PositiveNegativeAffixFormat;
import org.python.icu.impl.number.formatters.ScientificFormat;
import org.python.icu.impl.number.rounders.IncrementRounder;
import org.python.icu.impl.number.rounders.MagnitudeRounder;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;
import org.python.icu.text.CurrencyPluralInfo;
import org.python.icu.text.DecimalFormat;
import org.python.icu.util.Currency;
import org.python.icu.util.MeasureUnit;

public class Properties implements Cloneable, Serializable, PositiveDecimalFormat.IProperties, PositiveNegativeAffixFormat.IProperties, MagnitudeMultiplier.IProperties, ScientificFormat.IProperties, MeasureFormat.IProperties, CompactDecimalFormat.IProperties, PaddingFormat.IProperties, BigDecimalMultiplier.IProperties, CurrencyFormat.IProperties, Parse.IProperties, IncrementRounder.IProperties, MagnitudeRounder.IProperties, SignificantDigitsRounder.IProperties {
   private static final Properties DEFAULT = new Properties();
   private static final long serialVersionUID = 4095518955889349243L;
   private transient org.python.icu.text.CompactDecimalFormat.CompactStyle compactStyle;
   private transient Currency currency;
   private transient CurrencyPluralInfo currencyPluralInfo;
   private transient CurrencyFormat.CurrencyStyle currencyStyle;
   private transient Currency.CurrencyUsage currencyUsage;
   private transient boolean decimalPatternMatchRequired;
   private transient boolean decimalSeparatorAlwaysShown;
   private transient boolean exponentSignAlwaysShown;
   private transient int formatWidth;
   private transient int groupingSize;
   private transient int magnitudeMultiplier;
   private transient MathContext mathContext;
   private transient int maximumFractionDigits;
   private transient int maximumIntegerDigits;
   private transient int maximumSignificantDigits;
   private transient org.python.icu.text.MeasureFormat.FormatWidth measureFormatWidth;
   private transient MeasureUnit measureUnit;
   private transient int minimumExponentDigits;
   private transient int minimumFractionDigits;
   private transient int minimumGroupingDigits;
   private transient int minimumIntegerDigits;
   private transient int minimumSignificantDigits;
   private transient BigDecimal multiplier;
   private transient String negativePrefix;
   private transient String negativePrefixPattern;
   private transient String negativeSuffix;
   private transient String negativeSuffixPattern;
   private transient PaddingFormat.PadPosition padPosition;
   private transient String padString;
   private transient boolean parseCaseSensitive;
   private transient Parse.GroupingMode parseGroupingMode;
   private transient boolean parseIntegerOnly;
   private transient Parse.ParseMode parseMode;
   private transient boolean parseNoExponent;
   private transient boolean parseToBigDecimal;
   private transient String positivePrefix;
   private transient String positivePrefixPattern;
   private transient String positiveSuffix;
   private transient String positiveSuffixPattern;
   private transient BigDecimal roundingIncrement;
   private transient RoundingMode roundingMode;
   private transient int secondaryGroupingSize;
   private transient boolean signAlwaysShown;
   private transient DecimalFormat.SignificantDigitsMode significantDigitsMode;

   public Properties() {
      this.clear();
   }

   private Properties _clear() {
      this.compactStyle = DEFAULT_COMPACT_STYLE;
      this.currency = DEFAULT_CURRENCY;
      this.currencyPluralInfo = DEFAULT_CURRENCY_PLURAL_INFO;
      this.currencyStyle = DEFAULT_CURRENCY_STYLE;
      this.currencyUsage = DEFAULT_CURRENCY_USAGE;
      this.decimalPatternMatchRequired = false;
      this.decimalSeparatorAlwaysShown = false;
      this.exponentSignAlwaysShown = false;
      this.formatWidth = 0;
      this.groupingSize = -1;
      this.magnitudeMultiplier = 0;
      this.mathContext = DEFAULT_MATH_CONTEXT;
      this.maximumFractionDigits = -1;
      this.maximumIntegerDigits = -1;
      this.maximumSignificantDigits = -1;
      this.measureFormatWidth = DEFAULT_MEASURE_FORMAT_WIDTH;
      this.measureUnit = DEFAULT_MEASURE_UNIT;
      this.minimumExponentDigits = -1;
      this.minimumFractionDigits = -1;
      this.minimumGroupingDigits = 1;
      this.minimumIntegerDigits = -1;
      this.minimumSignificantDigits = -1;
      this.multiplier = DEFAULT_MULTIPLIER;
      this.negativePrefix = DEFAULT_NEGATIVE_PREFIX;
      this.negativePrefixPattern = DEFAULT_NEGATIVE_PREFIX_PATTERN;
      this.negativeSuffix = DEFAULT_NEGATIVE_SUFFIX;
      this.negativeSuffixPattern = DEFAULT_NEGATIVE_SUFFIX_PATTERN;
      this.padPosition = DEFAULT_PAD_POSITION;
      this.padString = DEFAULT_PAD_STRING;
      this.parseCaseSensitive = false;
      this.parseGroupingMode = DEFAULT_PARSE_GROUPING_MODE;
      this.parseIntegerOnly = false;
      this.parseMode = DEFAULT_PARSE_MODE;
      this.parseNoExponent = false;
      this.parseToBigDecimal = false;
      this.positivePrefix = DEFAULT_POSITIVE_PREFIX;
      this.positivePrefixPattern = DEFAULT_POSITIVE_PREFIX_PATTERN;
      this.positiveSuffix = DEFAULT_POSITIVE_SUFFIX;
      this.positiveSuffixPattern = DEFAULT_POSITIVE_SUFFIX_PATTERN;
      this.roundingIncrement = DEFAULT_ROUNDING_INCREMENT;
      this.roundingMode = DEFAULT_ROUNDING_MODE;
      this.secondaryGroupingSize = -1;
      this.signAlwaysShown = false;
      this.significantDigitsMode = DEFAULT_SIGNIFICANT_DIGITS_MODE;
      return this;
   }

   private Properties _copyFrom(Properties other) {
      this.compactStyle = other.compactStyle;
      this.currency = other.currency;
      this.currencyPluralInfo = other.currencyPluralInfo;
      this.currencyStyle = other.currencyStyle;
      this.currencyUsage = other.currencyUsage;
      this.decimalPatternMatchRequired = other.decimalPatternMatchRequired;
      this.decimalSeparatorAlwaysShown = other.decimalSeparatorAlwaysShown;
      this.exponentSignAlwaysShown = other.exponentSignAlwaysShown;
      this.formatWidth = other.formatWidth;
      this.groupingSize = other.groupingSize;
      this.magnitudeMultiplier = other.magnitudeMultiplier;
      this.mathContext = other.mathContext;
      this.maximumFractionDigits = other.maximumFractionDigits;
      this.maximumIntegerDigits = other.maximumIntegerDigits;
      this.maximumSignificantDigits = other.maximumSignificantDigits;
      this.measureFormatWidth = other.measureFormatWidth;
      this.measureUnit = other.measureUnit;
      this.minimumExponentDigits = other.minimumExponentDigits;
      this.minimumFractionDigits = other.minimumFractionDigits;
      this.minimumGroupingDigits = other.minimumGroupingDigits;
      this.minimumIntegerDigits = other.minimumIntegerDigits;
      this.minimumSignificantDigits = other.minimumSignificantDigits;
      this.multiplier = other.multiplier;
      this.negativePrefix = other.negativePrefix;
      this.negativePrefixPattern = other.negativePrefixPattern;
      this.negativeSuffix = other.negativeSuffix;
      this.negativeSuffixPattern = other.negativeSuffixPattern;
      this.padPosition = other.padPosition;
      this.padString = other.padString;
      this.parseCaseSensitive = other.parseCaseSensitive;
      this.parseGroupingMode = other.parseGroupingMode;
      this.parseIntegerOnly = other.parseIntegerOnly;
      this.parseMode = other.parseMode;
      this.parseNoExponent = other.parseNoExponent;
      this.parseToBigDecimal = other.parseToBigDecimal;
      this.positivePrefix = other.positivePrefix;
      this.positivePrefixPattern = other.positivePrefixPattern;
      this.positiveSuffix = other.positiveSuffix;
      this.positiveSuffixPattern = other.positiveSuffixPattern;
      this.roundingIncrement = other.roundingIncrement;
      this.roundingMode = other.roundingMode;
      this.secondaryGroupingSize = other.secondaryGroupingSize;
      this.signAlwaysShown = other.signAlwaysShown;
      this.significantDigitsMode = other.significantDigitsMode;
      return this;
   }

   private boolean _equals(Properties other) {
      boolean eq = true;
      eq = eq && this._equalsHelper(this.compactStyle, other.compactStyle);
      eq = eq && this._equalsHelper(this.currency, other.currency);
      eq = eq && this._equalsHelper(this.currencyPluralInfo, other.currencyPluralInfo);
      eq = eq && this._equalsHelper(this.currencyStyle, other.currencyStyle);
      eq = eq && this._equalsHelper(this.currencyUsage, other.currencyUsage);
      eq = eq && this._equalsHelper(this.decimalPatternMatchRequired, other.decimalPatternMatchRequired);
      eq = eq && this._equalsHelper(this.decimalSeparatorAlwaysShown, other.decimalSeparatorAlwaysShown);
      eq = eq && this._equalsHelper(this.exponentSignAlwaysShown, other.exponentSignAlwaysShown);
      eq = eq && this._equalsHelper(this.formatWidth, other.formatWidth);
      eq = eq && this._equalsHelper(this.groupingSize, other.groupingSize);
      eq = eq && this._equalsHelper(this.magnitudeMultiplier, other.magnitudeMultiplier);
      eq = eq && this._equalsHelper(this.mathContext, other.mathContext);
      eq = eq && this._equalsHelper(this.maximumFractionDigits, other.maximumFractionDigits);
      eq = eq && this._equalsHelper(this.maximumIntegerDigits, other.maximumIntegerDigits);
      eq = eq && this._equalsHelper(this.maximumSignificantDigits, other.maximumSignificantDigits);
      eq = eq && this._equalsHelper(this.measureFormatWidth, other.measureFormatWidth);
      eq = eq && this._equalsHelper(this.measureUnit, other.measureUnit);
      eq = eq && this._equalsHelper(this.minimumExponentDigits, other.minimumExponentDigits);
      eq = eq && this._equalsHelper(this.minimumFractionDigits, other.minimumFractionDigits);
      eq = eq && this._equalsHelper(this.minimumGroupingDigits, other.minimumGroupingDigits);
      eq = eq && this._equalsHelper(this.minimumIntegerDigits, other.minimumIntegerDigits);
      eq = eq && this._equalsHelper(this.minimumSignificantDigits, other.minimumSignificantDigits);
      eq = eq && this._equalsHelper(this.multiplier, other.multiplier);
      eq = eq && this._equalsHelper(this.negativePrefix, other.negativePrefix);
      eq = eq && this._equalsHelper(this.negativePrefixPattern, other.negativePrefixPattern);
      eq = eq && this._equalsHelper(this.negativeSuffix, other.negativeSuffix);
      eq = eq && this._equalsHelper(this.negativeSuffixPattern, other.negativeSuffixPattern);
      eq = eq && this._equalsHelper(this.padPosition, other.padPosition);
      eq = eq && this._equalsHelper(this.padString, other.padString);
      eq = eq && this._equalsHelper(this.parseCaseSensitive, other.parseCaseSensitive);
      eq = eq && this._equalsHelper(this.parseGroupingMode, other.parseGroupingMode);
      eq = eq && this._equalsHelper(this.parseIntegerOnly, other.parseIntegerOnly);
      eq = eq && this._equalsHelper(this.parseMode, other.parseMode);
      eq = eq && this._equalsHelper(this.parseNoExponent, other.parseNoExponent);
      eq = eq && this._equalsHelper(this.parseToBigDecimal, other.parseToBigDecimal);
      eq = eq && this._equalsHelper(this.positivePrefix, other.positivePrefix);
      eq = eq && this._equalsHelper(this.positivePrefixPattern, other.positivePrefixPattern);
      eq = eq && this._equalsHelper(this.positiveSuffix, other.positiveSuffix);
      eq = eq && this._equalsHelper(this.positiveSuffixPattern, other.positiveSuffixPattern);
      eq = eq && this._equalsHelper(this.roundingIncrement, other.roundingIncrement);
      eq = eq && this._equalsHelper(this.roundingMode, other.roundingMode);
      eq = eq && this._equalsHelper(this.secondaryGroupingSize, other.secondaryGroupingSize);
      eq = eq && this._equalsHelper(this.signAlwaysShown, other.signAlwaysShown);
      eq = eq && this._equalsHelper(this.significantDigitsMode, other.significantDigitsMode);
      return eq;
   }

   private boolean _equalsHelper(boolean mine, boolean theirs) {
      return mine == theirs;
   }

   private boolean _equalsHelper(int mine, int theirs) {
      return mine == theirs;
   }

   private boolean _equalsHelper(Object mine, Object theirs) {
      if (mine == theirs) {
         return true;
      } else {
         return mine == null ? false : mine.equals(theirs);
      }
   }

   private int _hashCode() {
      int hashCode = 0;
      hashCode ^= this._hashCodeHelper(this.compactStyle);
      hashCode ^= this._hashCodeHelper(this.currency);
      hashCode ^= this._hashCodeHelper(this.currencyPluralInfo);
      hashCode ^= this._hashCodeHelper(this.currencyStyle);
      hashCode ^= this._hashCodeHelper(this.currencyUsage);
      hashCode ^= this._hashCodeHelper(this.decimalPatternMatchRequired);
      hashCode ^= this._hashCodeHelper(this.decimalSeparatorAlwaysShown);
      hashCode ^= this._hashCodeHelper(this.exponentSignAlwaysShown);
      hashCode ^= this._hashCodeHelper(this.formatWidth);
      hashCode ^= this._hashCodeHelper(this.groupingSize);
      hashCode ^= this._hashCodeHelper(this.magnitudeMultiplier);
      hashCode ^= this._hashCodeHelper(this.mathContext);
      hashCode ^= this._hashCodeHelper(this.maximumFractionDigits);
      hashCode ^= this._hashCodeHelper(this.maximumIntegerDigits);
      hashCode ^= this._hashCodeHelper(this.maximumSignificantDigits);
      hashCode ^= this._hashCodeHelper(this.measureFormatWidth);
      hashCode ^= this._hashCodeHelper(this.measureUnit);
      hashCode ^= this._hashCodeHelper(this.minimumExponentDigits);
      hashCode ^= this._hashCodeHelper(this.minimumFractionDigits);
      hashCode ^= this._hashCodeHelper(this.minimumGroupingDigits);
      hashCode ^= this._hashCodeHelper(this.minimumIntegerDigits);
      hashCode ^= this._hashCodeHelper(this.minimumSignificantDigits);
      hashCode ^= this._hashCodeHelper(this.multiplier);
      hashCode ^= this._hashCodeHelper(this.negativePrefix);
      hashCode ^= this._hashCodeHelper(this.negativePrefixPattern);
      hashCode ^= this._hashCodeHelper(this.negativeSuffix);
      hashCode ^= this._hashCodeHelper(this.negativeSuffixPattern);
      hashCode ^= this._hashCodeHelper(this.padPosition);
      hashCode ^= this._hashCodeHelper(this.padString);
      hashCode ^= this._hashCodeHelper(this.parseCaseSensitive);
      hashCode ^= this._hashCodeHelper(this.parseGroupingMode);
      hashCode ^= this._hashCodeHelper(this.parseIntegerOnly);
      hashCode ^= this._hashCodeHelper(this.parseMode);
      hashCode ^= this._hashCodeHelper(this.parseNoExponent);
      hashCode ^= this._hashCodeHelper(this.parseToBigDecimal);
      hashCode ^= this._hashCodeHelper(this.positivePrefix);
      hashCode ^= this._hashCodeHelper(this.positivePrefixPattern);
      hashCode ^= this._hashCodeHelper(this.positiveSuffix);
      hashCode ^= this._hashCodeHelper(this.positiveSuffixPattern);
      hashCode ^= this._hashCodeHelper(this.roundingIncrement);
      hashCode ^= this._hashCodeHelper(this.roundingMode);
      hashCode ^= this._hashCodeHelper(this.secondaryGroupingSize);
      hashCode ^= this._hashCodeHelper(this.signAlwaysShown);
      hashCode ^= this._hashCodeHelper(this.significantDigitsMode);
      return hashCode;
   }

   private int _hashCodeHelper(boolean value) {
      return value ? 1 : 0;
   }

   private int _hashCodeHelper(int value) {
      return value * 13;
   }

   private int _hashCodeHelper(Object value) {
      return value == null ? 0 : value.hashCode();
   }

   public Properties clear() {
      return this._clear();
   }

   public Properties clone() {
      try {
         return (Properties)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new UnsupportedOperationException(var2);
      }
   }

   public Properties copyFrom(Properties other) {
      return this._copyFrom(other);
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (this == other) {
         return true;
      } else {
         return !(other instanceof Properties) ? false : this._equals((Properties)other);
      }
   }

   public org.python.icu.text.CompactDecimalFormat.CompactStyle getCompactStyle() {
      return this.compactStyle;
   }

   public Currency getCurrency() {
      return this.currency;
   }

   /** @deprecated */
   @Deprecated
   public CurrencyPluralInfo getCurrencyPluralInfo() {
      return this.currencyPluralInfo;
   }

   public CurrencyFormat.CurrencyStyle getCurrencyStyle() {
      return this.currencyStyle;
   }

   public Currency.CurrencyUsage getCurrencyUsage() {
      return this.currencyUsage;
   }

   public boolean getDecimalPatternMatchRequired() {
      return this.decimalPatternMatchRequired;
   }

   public boolean getDecimalSeparatorAlwaysShown() {
      return this.decimalSeparatorAlwaysShown;
   }

   public boolean getExponentSignAlwaysShown() {
      return this.exponentSignAlwaysShown;
   }

   public int getFormatWidth() {
      return this.formatWidth;
   }

   public int getGroupingSize() {
      return this.groupingSize;
   }

   public int getMagnitudeMultiplier() {
      return this.magnitudeMultiplier;
   }

   public MathContext getMathContext() {
      return this.mathContext;
   }

   public int getMaximumFractionDigits() {
      return this.maximumFractionDigits;
   }

   public int getMaximumIntegerDigits() {
      return this.maximumIntegerDigits;
   }

   public int getMaximumSignificantDigits() {
      return this.maximumSignificantDigits;
   }

   public org.python.icu.text.MeasureFormat.FormatWidth getMeasureFormatWidth() {
      return this.measureFormatWidth;
   }

   public MeasureUnit getMeasureUnit() {
      return this.measureUnit;
   }

   public int getMinimumExponentDigits() {
      return this.minimumExponentDigits;
   }

   public int getMinimumFractionDigits() {
      return this.minimumFractionDigits;
   }

   public int getMinimumGroupingDigits() {
      return this.minimumGroupingDigits;
   }

   public int getMinimumIntegerDigits() {
      return this.minimumIntegerDigits;
   }

   public int getMinimumSignificantDigits() {
      return this.minimumSignificantDigits;
   }

   public BigDecimal getMultiplier() {
      return this.multiplier;
   }

   public String getNegativePrefix() {
      return this.negativePrefix;
   }

   public String getNegativePrefixPattern() {
      return this.negativePrefixPattern;
   }

   public String getNegativeSuffix() {
      return this.negativeSuffix;
   }

   public String getNegativeSuffixPattern() {
      return this.negativeSuffixPattern;
   }

   public PaddingFormat.PadPosition getPadPosition() {
      return this.padPosition;
   }

   public String getPadString() {
      return this.padString;
   }

   public boolean getParseCaseSensitive() {
      return this.parseCaseSensitive;
   }

   public Parse.GroupingMode getParseGroupingMode() {
      return this.parseGroupingMode;
   }

   public boolean getParseIntegerOnly() {
      return this.parseIntegerOnly;
   }

   public Parse.ParseMode getParseMode() {
      return this.parseMode;
   }

   public boolean getParseNoExponent() {
      return this.parseNoExponent;
   }

   public boolean getParseToBigDecimal() {
      return this.parseToBigDecimal;
   }

   public String getPositivePrefix() {
      return this.positivePrefix;
   }

   public String getPositivePrefixPattern() {
      return this.positivePrefixPattern;
   }

   public String getPositiveSuffix() {
      return this.positiveSuffix;
   }

   public String getPositiveSuffixPattern() {
      return this.positiveSuffixPattern;
   }

   public BigDecimal getRoundingIncrement() {
      return this.roundingIncrement;
   }

   public RoundingMode getRoundingMode() {
      return this.roundingMode;
   }

   public int getSecondaryGroupingSize() {
      return this.secondaryGroupingSize;
   }

   public boolean getSignAlwaysShown() {
      return this.signAlwaysShown;
   }

   public DecimalFormat.SignificantDigitsMode getSignificantDigitsMode() {
      return this.significantDigitsMode;
   }

   public int hashCode() {
      return this._hashCode();
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.clear();
      ois.readInt();
      int count = ois.readInt();

      for(int i = 0; i < count; ++i) {
         String name = (String)ois.readObject();
         Object value = ois.readObject();
         Field field = null;

         try {
            field = Properties.class.getDeclaredField(name);
         } catch (NoSuchFieldException var10) {
            continue;
         } catch (SecurityException var11) {
            throw new AssertionError(var11);
         }

         try {
            field.set(this, value);
         } catch (IllegalArgumentException var8) {
            throw new AssertionError(var8);
         } catch (IllegalAccessException var9) {
            throw new AssertionError(var9);
         }
      }

   }

   public Properties setCompactStyle(org.python.icu.text.CompactDecimalFormat.CompactStyle compactStyle) {
      this.compactStyle = compactStyle;
      return this;
   }

   public Properties setCurrency(Currency currency) {
      this.currency = currency;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public Properties setCurrencyPluralInfo(CurrencyPluralInfo currencyPluralInfo) {
      if (currencyPluralInfo != null) {
         currencyPluralInfo = (CurrencyPluralInfo)currencyPluralInfo.clone();
      }

      this.currencyPluralInfo = currencyPluralInfo;
      return this;
   }

   public Properties setCurrencyStyle(CurrencyFormat.CurrencyStyle currencyStyle) {
      this.currencyStyle = currencyStyle;
      return this;
   }

   public Properties setCurrencyUsage(Currency.CurrencyUsage currencyUsage) {
      this.currencyUsage = currencyUsage;
      return this;
   }

   public Properties setDecimalPatternMatchRequired(boolean decimalPatternMatchRequired) {
      this.decimalPatternMatchRequired = decimalPatternMatchRequired;
      return this;
   }

   public Properties setDecimalSeparatorAlwaysShown(boolean alwaysShowDecimal) {
      this.decimalSeparatorAlwaysShown = alwaysShowDecimal;
      return this;
   }

   public Properties setExponentSignAlwaysShown(boolean exponentSignAlwaysShown) {
      this.exponentSignAlwaysShown = exponentSignAlwaysShown;
      return this;
   }

   public Properties setFormatWidth(int paddingWidth) {
      this.formatWidth = paddingWidth;
      return this;
   }

   public Properties setGroupingSize(int groupingSize) {
      this.groupingSize = groupingSize;
      return this;
   }

   public Properties setMagnitudeMultiplier(int magnitudeMultiplier) {
      this.magnitudeMultiplier = magnitudeMultiplier;
      return this;
   }

   public Properties setMathContext(MathContext mathContext) {
      this.mathContext = mathContext;
      return this;
   }

   public Properties setMaximumFractionDigits(int maximumFractionDigits) {
      this.maximumFractionDigits = maximumFractionDigits;
      return this;
   }

   public Properties setMaximumIntegerDigits(int maximumIntegerDigits) {
      this.maximumIntegerDigits = maximumIntegerDigits;
      return this;
   }

   public Properties setMaximumSignificantDigits(int maximumSignificantDigits) {
      this.maximumSignificantDigits = maximumSignificantDigits;
      return this;
   }

   public Properties setMeasureFormatWidth(org.python.icu.text.MeasureFormat.FormatWidth measureFormatWidth) {
      this.measureFormatWidth = measureFormatWidth;
      return this;
   }

   public Properties setMeasureUnit(MeasureUnit measureUnit) {
      this.measureUnit = measureUnit;
      return this;
   }

   public Properties setMinimumExponentDigits(int exponentDigits) {
      this.minimumExponentDigits = exponentDigits;
      return this;
   }

   public Properties setMinimumFractionDigits(int minimumFractionDigits) {
      this.minimumFractionDigits = minimumFractionDigits;
      return this;
   }

   public Properties setMinimumGroupingDigits(int minimumGroupingDigits) {
      this.minimumGroupingDigits = minimumGroupingDigits;
      return this;
   }

   public Properties setMinimumIntegerDigits(int minimumIntegerDigits) {
      this.minimumIntegerDigits = minimumIntegerDigits;
      return this;
   }

   public Properties setMinimumSignificantDigits(int minimumSignificantDigits) {
      this.minimumSignificantDigits = minimumSignificantDigits;
      return this;
   }

   public Properties setMultiplier(BigDecimal multiplier) {
      this.multiplier = multiplier;
      return this;
   }

   public Properties setNegativePrefix(String negativePrefix) {
      this.negativePrefix = negativePrefix;
      return this;
   }

   public Properties setNegativePrefixPattern(String negativePrefixPattern) {
      this.negativePrefixPattern = negativePrefixPattern;
      return this;
   }

   public Properties setNegativeSuffix(String negativeSuffix) {
      this.negativeSuffix = negativeSuffix;
      return this;
   }

   public Properties setNegativeSuffixPattern(String negativeSuffixPattern) {
      this.negativeSuffixPattern = negativeSuffixPattern;
      return this;
   }

   public Properties setPadPosition(PaddingFormat.PadPosition paddingLocation) {
      this.padPosition = paddingLocation;
      return this;
   }

   public Properties setPadString(String paddingString) {
      this.padString = paddingString;
      return this;
   }

   public Properties setParseCaseSensitive(boolean parseCaseSensitive) {
      this.parseCaseSensitive = parseCaseSensitive;
      return this;
   }

   public Properties setParseGroupingMode(Parse.GroupingMode parseGroupingMode) {
      this.parseGroupingMode = parseGroupingMode;
      return this;
   }

   public Properties setParseIntegerOnly(boolean parseIntegerOnly) {
      this.parseIntegerOnly = parseIntegerOnly;
      return this;
   }

   public Properties setParseMode(Parse.ParseMode parseMode) {
      this.parseMode = parseMode;
      return this;
   }

   public Properties setParseNoExponent(boolean parseNoExponent) {
      this.parseNoExponent = parseNoExponent;
      return this;
   }

   public Properties setParseToBigDecimal(boolean parseToBigDecimal) {
      this.parseToBigDecimal = parseToBigDecimal;
      return this;
   }

   public Properties setPositivePrefix(String positivePrefix) {
      this.positivePrefix = positivePrefix;
      return this;
   }

   public Properties setPositivePrefixPattern(String positivePrefixPattern) {
      this.positivePrefixPattern = positivePrefixPattern;
      return this;
   }

   public Properties setPositiveSuffix(String positiveSuffix) {
      this.positiveSuffix = positiveSuffix;
      return this;
   }

   public Properties setPositiveSuffixPattern(String positiveSuffixPattern) {
      this.positiveSuffixPattern = positiveSuffixPattern;
      return this;
   }

   public Properties setRoundingIncrement(BigDecimal roundingIncrement) {
      this.roundingIncrement = roundingIncrement;
      return this;
   }

   public Properties setRoundingMode(RoundingMode roundingMode) {
      this.roundingMode = roundingMode;
      return this;
   }

   public Properties setSecondaryGroupingSize(int secondaryGroupingSize) {
      this.secondaryGroupingSize = secondaryGroupingSize;
      return this;
   }

   public Properties setSignAlwaysShown(boolean signAlwaysShown) {
      this.signAlwaysShown = signAlwaysShown;
      return this;
   }

   public Properties setSignificantDigitsMode(DecimalFormat.SignificantDigitsMode significantDigitsMode) {
      this.significantDigitsMode = significantDigitsMode;
      return this;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("<Properties");
      this.toStringBare(result);
      result.append(">");
      return result.toString();
   }

   public void toStringBare(StringBuilder result) {
      Field[] fields = Properties.class.getDeclaredFields();
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];

         Object myValue;
         Object defaultValue;
         try {
            myValue = field.get(this);
            defaultValue = field.get(DEFAULT);
         } catch (IllegalArgumentException var10) {
            var10.printStackTrace();
            continue;
         } catch (IllegalAccessException var11) {
            var11.printStackTrace();
            continue;
         }

         if (myValue != null || defaultValue != null) {
            if (myValue != null && defaultValue != null) {
               if (!myValue.equals(defaultValue)) {
                  result.append(" " + field.getName() + ":" + myValue);
               }
            } else {
               result.append(" " + field.getName() + ":" + myValue);
            }
         }
      }

   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      oos.writeInt(0);
      ArrayList fieldsToSerialize = new ArrayList();
      ArrayList valuesToSerialize = new ArrayList();
      Field[] fields = Properties.class.getDeclaredFields();
      Field[] var5 = fields;
      int i = fields.length;

      for(int var7 = 0; var7 < i; ++var7) {
         Field field = var5[var7];
         if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
            try {
               Object myValue = field.get(this);
               if (myValue != null) {
                  Object defaultValue = field.get(DEFAULT);
                  if (!myValue.equals(defaultValue)) {
                     fieldsToSerialize.add(field);
                     valuesToSerialize.add(myValue);
                  }
               }
            } catch (IllegalArgumentException var11) {
               throw new AssertionError(var11);
            } catch (IllegalAccessException var12) {
               throw new AssertionError(var12);
            }
         }
      }

      int count = fieldsToSerialize.size();
      oos.writeInt(count);

      for(i = 0; i < count; ++i) {
         Field field = (Field)fieldsToSerialize.get(i);
         Object value = valuesToSerialize.get(i);
         oos.writeObject(field.getName());
         oos.writeObject(value);
      }

   }
}
