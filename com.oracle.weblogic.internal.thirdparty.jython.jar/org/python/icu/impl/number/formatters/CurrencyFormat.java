package org.python.icu.impl.number.formatters;

import java.math.BigDecimal;
import java.util.Iterator;
import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.number.AffixPatternUtils;
import org.python.icu.impl.number.PNAffixGenerator;
import org.python.icu.impl.number.PatternString;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.Rounder;
import org.python.icu.impl.number.modifiers.GeneralPluralModifier;
import org.python.icu.impl.number.rounders.IncrementRounder;
import org.python.icu.impl.number.rounders.MagnitudeRounder;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;
import org.python.icu.text.CurrencyPluralInfo;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.util.Currency;
import org.python.icu.util.ULocale;

public class CurrencyFormat {
   private static final Currency DEFAULT_CURRENCY = Currency.getInstance("XXX");
   private static final ThreadLocal threadLocalProperties = new ThreadLocal() {
      protected Properties initialValue() {
         return new Properties();
      }
   };

   public static boolean useCurrency(IProperties properties) {
      return properties.getCurrency() != null || properties.getCurrencyPluralInfo() != null || properties.getCurrencyUsage() != null || AffixPatternUtils.hasCurrencySymbols(properties.getPositivePrefixPattern()) || AffixPatternUtils.hasCurrencySymbols(properties.getPositiveSuffixPattern()) || AffixPatternUtils.hasCurrencySymbols(properties.getNegativePrefixPattern()) || AffixPatternUtils.hasCurrencySymbols(properties.getNegativeSuffixPattern());
   }

   public static String getCurrencySymbol(DecimalFormatSymbols symbols, ICurrencyProperties properties) {
      CurrencyStyle style = properties.getCurrencyStyle();
      if (style == CurrencyFormat.CurrencyStyle.ISO_CODE) {
         return getCurrencyIsoCode(symbols, properties);
      } else {
         Currency currency = properties.getCurrency();
         if (currency == null) {
            return symbols.getCurrencySymbol();
         } else {
            return currency.equals(symbols.getCurrency()) ? symbols.getCurrencySymbol() : currency.getName((ULocale)symbols.getULocale(), 0, (boolean[])null);
         }
      }
   }

   public static String getCurrencyIsoCode(DecimalFormatSymbols symbols, ICurrencyProperties properties) {
      Currency currency = properties.getCurrency();
      if (currency == null) {
         return symbols.getInternationalCurrencySymbol();
      } else {
         return currency.equals(symbols.getCurrency()) ? symbols.getInternationalCurrencySymbol() : currency.getCurrencyCode();
      }
   }

   public static String getCurrencyLongName(DecimalFormatSymbols symbols, ICurrencyProperties properties, StandardPlural plural) {
      Currency currency = properties.getCurrency();
      if (currency == null) {
         currency = symbols.getCurrency();
      }

      return currency == null ? getCurrencySymbol(symbols, properties) : currency.getName((ULocale)symbols.getULocale(), 2, plural.getKeyword(), (boolean[])null);
   }

   public static GeneralPluralModifier getCurrencyModifier(DecimalFormatSymbols symbols, IProperties properties) {
      PNAffixGenerator pnag = PNAffixGenerator.getThreadLocalInstance();
      String sym = getCurrencySymbol(symbols, properties);
      String iso = getCurrencyIsoCode(symbols, properties);
      CurrencyPluralInfo info = properties.getCurrencyPluralInfo();
      GeneralPluralModifier mod = new GeneralPluralModifier();
      Properties temp = new Properties();

      StandardPlural plural;
      PNAffixGenerator.Result result;
      for(Iterator var8 = StandardPlural.VALUES.iterator(); var8.hasNext(); mod.put(plural, result.positive, result.negative)) {
         plural = (StandardPlural)var8.next();
         String longName = getCurrencyLongName(symbols, properties, plural);
         if (info == null) {
            result = pnag.getModifiers(symbols, sym, iso, longName, properties);
         } else {
            String pluralPattern = info.getCurrencyPluralPattern(plural.getKeyword());
            PatternString.parseToExistingProperties(pluralPattern, temp, true);
            result = pnag.getModifiers(symbols, sym, iso, longName, temp);
         }
      }

      return mod;
   }

   public static void populateCurrencyRounderProperties(Properties destination, DecimalFormatSymbols symbols, IProperties properties) {
      Currency currency = properties.getCurrency();
      if (currency == null) {
         currency = symbols.getCurrency();
      }

      if (currency == null) {
         currency = DEFAULT_CURRENCY;
      }

      Currency.CurrencyUsage _currencyUsage = properties.getCurrencyUsage();
      int _minFrac = properties.getMinimumFractionDigits();
      int _maxFrac = properties.getMaximumFractionDigits();
      Currency.CurrencyUsage effectiveCurrencyUsage = _currencyUsage != null ? _currencyUsage : Currency.CurrencyUsage.STANDARD;
      double incrementDouble = currency.getRoundingIncrement(effectiveCurrencyUsage);
      int fractionDigits = currency.getDefaultFractionDigits(effectiveCurrencyUsage);
      destination.setRoundingMode(properties.getRoundingMode());
      destination.setMinimumIntegerDigits(properties.getMinimumIntegerDigits());
      destination.setMaximumIntegerDigits(properties.getMaximumIntegerDigits());
      if (_currencyUsage == null && (_minFrac >= 0 || _maxFrac >= 0)) {
         if (_minFrac < 0) {
            destination.setMinimumFractionDigits(fractionDigits < _maxFrac ? fractionDigits : _maxFrac);
            destination.setMaximumFractionDigits(_maxFrac);
         } else if (_maxFrac < 0) {
            destination.setMinimumFractionDigits(_minFrac);
            destination.setMaximumFractionDigits(fractionDigits > _minFrac ? fractionDigits : _minFrac);
         } else {
            destination.setMinimumFractionDigits(_minFrac);
            destination.setMaximumFractionDigits(_maxFrac);
         }
      } else {
         destination.setMinimumFractionDigits(fractionDigits);
         destination.setMaximumFractionDigits(fractionDigits);
      }

      if (incrementDouble > 0.0) {
         BigDecimal _roundingIncrement = properties.getRoundingIncrement();
         BigDecimal incrementBigDecimal;
         if (_roundingIncrement != null) {
            incrementBigDecimal = _roundingIncrement;
         } else {
            incrementBigDecimal = BigDecimal.valueOf(incrementDouble);
         }

         destination.setRoundingIncrement(incrementBigDecimal);
      }

   }

   public static Rounder getCurrencyRounder(DecimalFormatSymbols symbols, IProperties properties) {
      if (SignificantDigitsRounder.useSignificantDigits(properties)) {
         return SignificantDigitsRounder.getInstance(properties);
      } else {
         Properties cprops = ((Properties)threadLocalProperties.get()).clear();
         populateCurrencyRounderProperties(cprops, symbols, properties);
         return (Rounder)(cprops.getRoundingIncrement() != null ? IncrementRounder.getInstance(cprops) : MagnitudeRounder.getInstance(cprops));
      }
   }

   public interface IProperties extends ICurrencyProperties, RoundingFormat.IProperties, PositiveNegativeAffixFormat.IProperties {
   }

   public interface ICurrencyProperties {
      Currency DEFAULT_CURRENCY = null;
      CurrencyStyle DEFAULT_CURRENCY_STYLE = null;
      Currency.CurrencyUsage DEFAULT_CURRENCY_USAGE = null;
      CurrencyPluralInfo DEFAULT_CURRENCY_PLURAL_INFO = null;

      Currency getCurrency();

      IProperties setCurrency(Currency var1);

      CurrencyStyle getCurrencyStyle();

      IProperties setCurrencyStyle(CurrencyStyle var1);

      Currency.CurrencyUsage getCurrencyUsage();

      IProperties setCurrencyUsage(Currency.CurrencyUsage var1);

      /** @deprecated */
      @Deprecated
      CurrencyPluralInfo getCurrencyPluralInfo();

      /** @deprecated */
      @Deprecated
      IProperties setCurrencyPluralInfo(CurrencyPluralInfo var1);
   }

   public static enum CurrencyStyle {
      SYMBOL,
      ISO_CODE;
   }
}
