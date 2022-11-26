package org.python.icu.impl.number;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.python.icu.impl.number.formatters.BigDecimalMultiplier;
import org.python.icu.impl.number.formatters.CompactDecimalFormat;
import org.python.icu.impl.number.formatters.CurrencyFormat;
import org.python.icu.impl.number.formatters.MagnitudeMultiplier;
import org.python.icu.impl.number.formatters.MeasureFormat;
import org.python.icu.impl.number.formatters.PaddingFormat;
import org.python.icu.impl.number.formatters.PositiveDecimalFormat;
import org.python.icu.impl.number.formatters.PositiveNegativeAffixFormat;
import org.python.icu.impl.number.formatters.RoundingFormat;
import org.python.icu.impl.number.formatters.ScientificFormat;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.PluralRules;
import org.python.icu.util.ULocale;

public class Endpoint {
   private static final ThreadLocal threadLocalSymbolsCache = new ThreadLocal() {
      protected Map initialValue() {
         return new HashMap();
      }
   };
   private static final ThreadLocal threadLocalPropertiesCache = new ThreadLocal() {
      protected Map initialValue() {
         return new HashMap();
      }
   };
   private static final ThreadLocal threadLocalRulesCache = new ThreadLocal() {
      protected Map initialValue() {
         return new HashMap();
      }
   };

   public static Format fromBTA(Properties properties) {
      return fromBTA(properties, getSymbols());
   }

   public static Format.SingularFormat fromBTA(Properties properties, Locale locale) {
      return fromBTA(properties, getSymbols(locale));
   }

   public static Format.SingularFormat fromBTA(Properties properties, ULocale uLocale) {
      return fromBTA(properties, getSymbols(uLocale));
   }

   public static Format.SingularFormat fromBTA(String pattern) {
      return fromBTA(getProperties(pattern), getSymbols());
   }

   public static Format.SingularFormat fromBTA(String pattern, Locale locale) {
      return fromBTA(getProperties(pattern), getSymbols(locale));
   }

   public static Format.SingularFormat fromBTA(String pattern, ULocale uLocale) {
      return fromBTA(getProperties(pattern), getSymbols(uLocale));
   }

   public static Format.SingularFormat fromBTA(String pattern, DecimalFormatSymbols symbols) {
      return fromBTA(getProperties(pattern), symbols);
   }

   public static Format.SingularFormat fromBTA(Properties properties, DecimalFormatSymbols symbols) {
      if (symbols == null) {
         throw new IllegalArgumentException("symbols must not be null");
      } else {
         boolean canUseFastTrack = true;
         PluralRules rules = getPluralRules(symbols.getULocale(), properties);
         Format.BeforeTargetAfterFormat format = new Format.BeforeTargetAfterFormat(rules);
         Format.TargetFormat target = new PositiveDecimalFormat(symbols, properties);
         format.setTargetFormat(target);
         if (MagnitudeMultiplier.useMagnitudeMultiplier(properties)) {
            canUseFastTrack = false;
            format.addBeforeFormat(MagnitudeMultiplier.getInstance(properties));
         }

         if (BigDecimalMultiplier.useMultiplier(properties)) {
            canUseFastTrack = false;
            format.addBeforeFormat(BigDecimalMultiplier.getInstance(properties));
         }

         if (MeasureFormat.useMeasureFormat(properties)) {
            canUseFastTrack = false;
            format.addBeforeFormat(MeasureFormat.getInstance(symbols, properties));
         }

         if (CurrencyFormat.useCurrency(properties)) {
            canUseFastTrack = false;
            if (CompactDecimalFormat.useCompactDecimalFormat(properties)) {
               format.addBeforeFormat(CompactDecimalFormat.getInstance(symbols, properties));
            } else if (ScientificFormat.useScientificNotation(properties)) {
               format.addBeforeFormat(PositiveNegativeAffixFormat.getInstance(symbols, properties));
               format.addBeforeFormat(ScientificFormat.getInstance(symbols, properties));
            } else {
               format.addBeforeFormat(CurrencyFormat.getCurrencyRounder(symbols, properties));
               format.addBeforeFormat(CurrencyFormat.getCurrencyModifier(symbols, properties));
            }
         } else if (CompactDecimalFormat.useCompactDecimalFormat(properties)) {
            canUseFastTrack = false;
            format.addBeforeFormat(CompactDecimalFormat.getInstance(symbols, properties));
         } else if (ScientificFormat.useScientificNotation(properties)) {
            canUseFastTrack = false;
            format.addBeforeFormat(PositiveNegativeAffixFormat.getInstance(symbols, properties));
            format.addBeforeFormat(ScientificFormat.getInstance(symbols, properties));
         } else {
            format.addBeforeFormat(PositiveNegativeAffixFormat.getInstance(symbols, properties));
            format.addBeforeFormat(RoundingFormat.getDefaultOrNoRounder(properties));
         }

         if (PaddingFormat.usePadding(properties)) {
            canUseFastTrack = false;
            format.addAfterFormat(PaddingFormat.getInstance(properties));
         }

         return (Format.SingularFormat)(canUseFastTrack ? new Format.PositiveNegativeRounderTargetFormat(PositiveNegativeAffixFormat.getInstance(symbols, properties), RoundingFormat.getDefaultOrNoRounder(properties), target) : format);
      }
   }

   public static String staticFormat(FormatQuantity input, Properties properties) {
      return staticFormat(input, properties, getSymbols());
   }

   public static String staticFormat(FormatQuantity input, Properties properties, Locale locale) {
      return staticFormat(input, properties, getSymbols(locale));
   }

   public static String staticFormat(FormatQuantity input, Properties properties, ULocale uLocale) {
      return staticFormat(input, properties, getSymbols(uLocale));
   }

   public static String staticFormat(FormatQuantity input, String pattern) {
      return staticFormat(input, getProperties(pattern), getSymbols());
   }

   public static String staticFormat(FormatQuantity input, String pattern, Locale locale) {
      return staticFormat(input, getProperties(pattern), getSymbols(locale));
   }

   public static String staticFormat(FormatQuantity input, String pattern, ULocale uLocale) {
      return staticFormat(input, getProperties(pattern), getSymbols(uLocale));
   }

   public static String staticFormat(FormatQuantity input, String pattern, DecimalFormatSymbols symbols) {
      return staticFormat(input, getProperties(pattern), symbols);
   }

   public static String staticFormat(FormatQuantity input, Properties properties, DecimalFormatSymbols symbols) {
      PluralRules rules = null;
      ModifierHolder mods = ((ModifierHolder)Format.threadLocalModifierHolder.get()).clear();
      NumberStringBuilder sb = ((NumberStringBuilder)Format.threadLocalStringBuilder.get()).clear();
      int length = 0;
      if (!input.isNaN()) {
         if (MagnitudeMultiplier.useMagnitudeMultiplier(properties)) {
            MagnitudeMultiplier.getInstance(properties).before(input, mods, rules);
         }

         if (BigDecimalMultiplier.useMultiplier(properties)) {
            BigDecimalMultiplier.getInstance(properties).before(input, mods, rules);
         }

         if (MeasureFormat.useMeasureFormat(properties)) {
            rules = rules != null ? rules : getPluralRules(symbols.getULocale(), properties);
            MeasureFormat.getInstance(symbols, properties).before(input, mods, rules);
         }

         if (CompactDecimalFormat.useCompactDecimalFormat(properties)) {
            rules = rules != null ? rules : getPluralRules(symbols.getULocale(), properties);
            CompactDecimalFormat.apply(input, mods, rules, symbols, properties);
         } else if (CurrencyFormat.useCurrency(properties)) {
            rules = rules != null ? rules : getPluralRules(symbols.getULocale(), properties);
            CurrencyFormat.getCurrencyRounder(symbols, properties).before(input, mods, rules);
            CurrencyFormat.getCurrencyModifier(symbols, properties).before(input, mods, rules);
         } else if (ScientificFormat.useScientificNotation(properties)) {
            PositiveNegativeAffixFormat.getInstance(symbols, properties).before(input, mods, rules);
            ScientificFormat.getInstance(symbols, properties).before(input, mods, rules);
         } else {
            PositiveNegativeAffixFormat.apply(input, mods, symbols, properties);
            RoundingFormat.getDefaultOrNoRounder(properties).before(input, mods, rules);
         }
      }

      length += (new PositiveDecimalFormat(symbols, properties)).target(input, sb, 0);
      length += mods.applyStrong(sb, 0, length);
      if (PaddingFormat.usePadding(properties)) {
         length += PaddingFormat.getInstance(properties).after(mods, sb, 0, length);
      }

      length += mods.applyAll(sb, 0, length);
      return sb.toString();
   }

   private static DecimalFormatSymbols getSymbols() {
      ULocale uLocale = ULocale.getDefault();
      return getSymbols(uLocale);
   }

   private static DecimalFormatSymbols getSymbols(Locale locale) {
      ULocale uLocale = ULocale.forLocale(locale);
      return getSymbols(uLocale);
   }

   private static DecimalFormatSymbols getSymbols(ULocale uLocale) {
      if (uLocale == null) {
         uLocale = ULocale.getDefault();
      }

      DecimalFormatSymbols symbols = (DecimalFormatSymbols)((Map)threadLocalSymbolsCache.get()).get(uLocale);
      if (symbols == null) {
         symbols = DecimalFormatSymbols.getInstance(uLocale);
         ((Map)threadLocalSymbolsCache.get()).put(uLocale, symbols);
      }

      return symbols;
   }

   private static Properties getProperties(String pattern) {
      if (pattern == null) {
         pattern = "#";
      }

      Properties properties = (Properties)((Map)threadLocalPropertiesCache.get()).get(pattern);
      if (properties == null) {
         properties = PatternString.parseToProperties(pattern);
         ((Map)threadLocalPropertiesCache.get()).put(pattern.intern(), properties);
      }

      return properties;
   }

   private static PluralRules getPluralRules(ULocale uLocale, Properties properties) {
      if (properties.getCurrencyPluralInfo() != null) {
         return properties.getCurrencyPluralInfo().getPluralRules();
      } else {
         if (uLocale == null) {
            uLocale = ULocale.getDefault();
         }

         PluralRules rules = (PluralRules)((Map)threadLocalRulesCache.get()).get(uLocale);
         if (rules == null) {
            rules = PluralRules.forLocale(uLocale);
            ((Map)threadLocalRulesCache.get()).put(uLocale, rules);
         }

         return rules;
      }
   }
}
