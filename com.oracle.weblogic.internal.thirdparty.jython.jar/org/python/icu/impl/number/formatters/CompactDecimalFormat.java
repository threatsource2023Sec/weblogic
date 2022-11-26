package org.python.icu.impl.number.formatters;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.UResource;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.PNAffixGenerator;
import org.python.icu.impl.number.PatternString;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.Rounder;
import org.python.icu.impl.number.modifiers.ConstantAffixModifier;
import org.python.icu.impl.number.modifiers.PositiveNegativeAffixModifier;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;
import org.python.icu.text.DecimalFormat;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;
import org.python.icu.text.NumberingSystem;
import org.python.icu.text.PluralRules;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class CompactDecimalFormat extends Format.BeforeFormat {
   static final int MAX_DIGITS = 15;
   private final CompactDecimalData data;
   private final Rounder rounder;
   private final Modifier.PositiveNegativeModifier defaultMod;
   private final org.python.icu.text.CompactDecimalFormat.CompactStyle style;
   private static final int DEFAULT_MIN_SIG = 1;
   private static final int DEFAULT_MAX_SIG = 2;
   private static final DecimalFormat.SignificantDigitsMode DEFAULT_SIG_MODE;
   private static final ThreadLocal threadLocalProperties;
   protected static final ThreadLocal threadLocalDataCache;

   public static boolean useCompactDecimalFormat(IProperties properties) {
      return properties.getCompactStyle() != CompactDecimalFormat.IProperties.DEFAULT_COMPACT_STYLE;
   }

   public static CompactDecimalFormat getInstance(DecimalFormatSymbols symbols, IProperties properties) {
      return new CompactDecimalFormat(symbols, properties);
   }

   private static Rounder getRounder(IProperties properties) {
      Rounder rounder = null;
      if (!SignificantDigitsRounder.useSignificantDigits(properties)) {
         rounder = RoundingFormat.getDefaultOrNull(properties);
      }

      if (rounder == null) {
         int _minSig = properties.getMinimumSignificantDigits();
         int _maxSig = properties.getMaximumSignificantDigits();
         DecimalFormat.SignificantDigitsMode _mode = properties.getSignificantDigitsMode();
         Properties rprops = ((Properties)threadLocalProperties.get()).clear();
         rprops.setMinimumSignificantDigits(_minSig > 0 ? _minSig : 1);
         rprops.setMaximumSignificantDigits(_maxSig > 0 ? _maxSig : 2);
         rprops.setSignificantDigitsMode(_mode != null ? _mode : DEFAULT_SIG_MODE);
         rprops.setRoundingMode(properties.getRoundingMode());
         rprops.setMinimumFractionDigits(properties.getMinimumFractionDigits());
         rprops.setMaximumFractionDigits(properties.getMaximumFractionDigits());
         rprops.setMinimumIntegerDigits(properties.getMinimumIntegerDigits());
         rprops.setMaximumIntegerDigits(properties.getMaximumIntegerDigits());
         rounder = SignificantDigitsRounder.getInstance(rprops);
      }

      return (Rounder)rounder;
   }

   private static CompactDecimalData getData(DecimalFormatSymbols symbols, CompactDecimalFingerprint fingerprint) {
      CompactDecimalData data = (CompactDecimalData)((Map)threadLocalDataCache.get()).get(fingerprint);
      if (data != null) {
         return data;
      } else {
         data = new CompactDecimalData();
         ULocale ulocale = symbols.getULocale();
         CompactDecimalDataSink sink = new CompactDecimalDataSink(data, symbols, fingerprint);
         String nsName = NumberingSystem.getInstance(ulocale).getName();
         ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", ulocale);
         internalPopulateData(nsName, rb, sink, data);
         if (data.isEmpty() && fingerprint.compactStyle == org.python.icu.text.CompactDecimalFormat.CompactStyle.LONG) {
            sink.compactStyle = org.python.icu.text.CompactDecimalFormat.CompactStyle.SHORT;
            internalPopulateData(nsName, rb, sink, data);
         }

         ((Map)threadLocalDataCache.get()).put(fingerprint, data);
         return data;
      }
   }

   private static void internalPopulateData(String nsName, ICUResourceBundle rb, CompactDecimalDataSink sink, CompactDecimalData data) {
      try {
         rb.getAllItemsWithFallback("NumberElements/" + nsName, sink);
      } catch (MissingResourceException var5) {
      }

      if (data.isEmpty() && !nsName.equals("latn")) {
         rb.getAllItemsWithFallback("NumberElements/latn", sink);
      }

      if (sink.exception != null) {
         throw sink.exception;
      }
   }

   private static Modifier.PositiveNegativeModifier getDefaultMod(DecimalFormatSymbols symbols, CompactDecimalFingerprint fingerprint) {
      ULocale uloc = symbols.getULocale();
      String pattern;
      if (fingerprint.compactType == CompactDecimalFormat.CompactType.CURRENCY) {
         pattern = NumberFormat.getPatternForStyle(uloc, 1);
      } else {
         pattern = NumberFormat.getPatternForStyle(uloc, 0);
      }

      Properties properties = PatternString.parseToProperties(pattern);
      PNAffixGenerator pnag = PNAffixGenerator.getThreadLocalInstance();
      PNAffixGenerator.Result result = pnag.getModifiers(symbols, fingerprint.currencySymbol, properties);
      return new PositiveNegativeAffixModifier(result.positive, result.negative);
   }

   private CompactDecimalFormat(DecimalFormatSymbols symbols, IProperties properties) {
      CompactDecimalFingerprint fingerprint = new CompactDecimalFingerprint(symbols, properties);
      this.rounder = getRounder(properties);
      this.data = getData(symbols, fingerprint);
      this.defaultMod = getDefaultMod(symbols, fingerprint);
      this.style = properties.getCompactStyle();
   }

   public void before(FormatQuantity input, ModifierHolder mods, PluralRules rules) {
      apply(input, mods, rules, this.rounder, this.data, this.defaultMod);
   }

   protected void before(FormatQuantity input, ModifierHolder mods) {
      throw new UnsupportedOperationException();
   }

   public static void apply(FormatQuantity input, ModifierHolder mods, PluralRules rules, DecimalFormatSymbols symbols, IProperties properties) {
      CompactDecimalFingerprint fingerprint = new CompactDecimalFingerprint(symbols, properties);
      Rounder rounder = getRounder(properties);
      CompactDecimalData data = getData(symbols, fingerprint);
      Modifier.PositiveNegativeModifier defaultMod = getDefaultMod(symbols, fingerprint);
      apply(input, mods, rules, rounder, data, defaultMod);
   }

   private static void apply(FormatQuantity input, ModifierHolder mods, PluralRules rules, Rounder rounder, CompactDecimalData data, Modifier.PositiveNegativeModifier defaultMod) {
      int magnitude;
      if (input.isZero()) {
         magnitude = 0;
         rounder.apply(input);
      } else {
         int multiplier = rounder.chooseMultiplierAndApply(input, data);
         magnitude = input.getMagnitude() - multiplier;
      }

      StandardPlural plural = input.getStandardPlural(rules);
      boolean isNegative = input.isNegative();
      Modifier mod = data.getModifier(magnitude, plural, isNegative);
      if (mod == null) {
         mod = defaultMod.getModifier(isNegative);
      }

      mods.add(mod);
   }

   public void export(Properties properties) {
      properties.setCompactStyle(this.style);
      this.rounder.export(properties);
   }

   static {
      DEFAULT_SIG_MODE = DecimalFormat.SignificantDigitsMode.OVERRIDE_MAXIMUM_FRACTION;
      threadLocalProperties = new ThreadLocal() {
         protected Properties initialValue() {
            return new Properties();
         }
      };
      threadLocalDataCache = new ThreadLocal() {
         protected Map initialValue() {
            return new HashMap();
         }
      };
   }

   private static final class CompactDecimalDataSink extends UResource.Sink {
      CompactDecimalData data;
      DecimalFormatSymbols symbols;
      org.python.icu.text.CompactDecimalFormat.CompactStyle compactStyle;
      CompactType compactType;
      String currencySymbol;
      PNAffixGenerator pnag;
      IllegalArgumentException exception;

      public CompactDecimalDataSink(CompactDecimalData data, DecimalFormatSymbols symbols, CompactDecimalFingerprint fingerprint) {
         this.data = data;
         this.symbols = symbols;
         this.compactType = fingerprint.compactType;
         this.currencySymbol = fingerprint.currencySymbol;
         this.compactStyle = fingerprint.compactStyle;
         this.pnag = PNAffixGenerator.getThreadLocalInstance();
      }

      public void put(UResource.Key key, UResource.Value value, boolean isRoot) {
         UResource.Table patternsTable = value.getTable();

         for(int i1 = 0; patternsTable.getKeyAndValue(i1, key, value); ++i1) {
            if (key.contentEquals("patternsShort") && this.compactStyle == org.python.icu.text.CompactDecimalFormat.CompactStyle.SHORT || key.contentEquals("patternsLong") && this.compactStyle == org.python.icu.text.CompactDecimalFormat.CompactStyle.LONG) {
               UResource.Table formatsTable = value.getTable();

               for(int i2 = 0; formatsTable.getKeyAndValue(i2, key, value); ++i2) {
                  if (key.contentEquals("decimalFormat") && this.compactType == CompactDecimalFormat.CompactType.DECIMAL || key.contentEquals("currencyFormat") && this.compactType == CompactDecimalFormat.CompactType.CURRENCY) {
                     UResource.Table powersOfTenTable = value.getTable();

                     for(int i3 = 0; powersOfTenTable.getKeyAndValue(i3, key, value); ++i3) {
                        try {
                           byte magnitude = (byte)(key.length() - 1);
                           if (magnitude < 15) {
                              UResource.Table pluralVariantsTable = value.getTable();

                              for(int i4 = 0; pluralVariantsTable.getKeyAndValue(i4, key, value); ++i4) {
                                 StandardPlural plural = StandardPlural.fromString(key.toString());
                                 if (!this.data.has(magnitude, plural)) {
                                    String patternString = value.toString();
                                    if (patternString.equals("0")) {
                                       this.data.setNoFallback(magnitude, plural);
                                    } else {
                                       Properties properties = PatternString.parseToProperties(patternString);
                                       byte _multiplier = (byte)(-(magnitude - properties.getMinimumIntegerDigits() + 1));
                                       if (_multiplier != this.data.setOrGetMultiplier(magnitude, _multiplier)) {
                                          throw new IllegalArgumentException(String.format("Different number of zeros for same power of ten in compact decimal format data for locale '%s', style '%s', type '%s'", this.symbols.getULocale().toString(), this.compactStyle.toString(), this.compactType.toString()));
                                       }

                                       PNAffixGenerator.Result result = this.pnag.getModifiers(this.symbols, this.currencySymbol, properties);
                                       this.data.setModifiers(result.positive, result.negative, magnitude, plural);
                                    }
                                 }
                              }
                           }
                        } catch (IllegalArgumentException var18) {
                           this.exception = var18;
                        }
                     }

                     return;
                  }
               }
            }
         }

      }
   }

   static class CompactDecimalFingerprint {
      final org.python.icu.text.CompactDecimalFormat.CompactStyle compactStyle;
      final CompactType compactType;
      final ULocale uloc;
      final String currencySymbol;

      CompactDecimalFingerprint(DecimalFormatSymbols symbols, IProperties properties) {
         if (properties.getCurrency() != CurrencyFormat.ICurrencyProperties.DEFAULT_CURRENCY) {
            this.compactType = CompactDecimalFormat.CompactType.CURRENCY;
            this.currencySymbol = CurrencyFormat.getCurrencySymbol(symbols, properties);
         } else {
            this.compactType = CompactDecimalFormat.CompactType.DECIMAL;
            this.currencySymbol = "";
         }

         this.compactStyle = properties.getCompactStyle();
         this.uloc = symbols.getULocale();
      }

      public boolean equals(Object _other) {
         if (_other == null) {
            return false;
         } else {
            CompactDecimalFingerprint other = (CompactDecimalFingerprint)_other;
            if (this == other) {
               return true;
            } else if (this.compactStyle != other.compactStyle) {
               return false;
            } else if (this.compactType != other.compactType) {
               return false;
            } else {
               if (this.currencySymbol != other.currencySymbol) {
                  if (this.currencySymbol == null || other.currencySymbol == null) {
                     return false;
                  }

                  if (!this.currencySymbol.equals(other.currencySymbol)) {
                     return false;
                  }
               }

               return this.uloc.equals(other.uloc);
            }
         }
      }

      public int hashCode() {
         int hashCode = 0;
         if (this.compactStyle != null) {
            hashCode ^= this.compactStyle.hashCode();
         }

         if (this.compactType != null) {
            hashCode ^= this.compactType.hashCode();
         }

         if (this.uloc != null) {
            hashCode ^= this.uloc.hashCode();
         }

         if (this.currencySymbol != null) {
            hashCode ^= this.currencySymbol.hashCode();
         }

         return hashCode;
      }
   }

   static enum CompactType {
      DECIMAL,
      CURRENCY;
   }

   static class CompactDecimalData implements Rounder.MultiplierGenerator {
      private static final Modifier USE_FALLBACK = new ConstantAffixModifier();
      final Modifier[] mods;
      final byte[] multipliers;
      boolean isEmpty;
      int largestMagnitude;

      CompactDecimalData() {
         this.mods = new Modifier[16 * StandardPlural.COUNT * 2];
         this.multipliers = new byte[16];
         this.isEmpty = true;
         this.largestMagnitude = -1;
      }

      boolean isEmpty() {
         return this.isEmpty;
      }

      public int getMultiplier(int magnitude) {
         if (magnitude < 0) {
            return 0;
         } else {
            if (magnitude > this.largestMagnitude) {
               magnitude = this.largestMagnitude;
            }

            return this.multipliers[magnitude];
         }
      }

      int setOrGetMultiplier(int magnitude, byte multiplier) {
         if (this.multipliers[magnitude] != 0) {
            return this.multipliers[magnitude];
         } else {
            this.multipliers[magnitude] = multiplier;
            this.isEmpty = false;
            if (magnitude > this.largestMagnitude) {
               this.largestMagnitude = magnitude;
            }

            return multiplier;
         }
      }

      Modifier getModifier(int magnitude, StandardPlural plural, boolean isNegative) {
         if (magnitude < 0) {
            return null;
         } else {
            if (magnitude > this.largestMagnitude) {
               magnitude = this.largestMagnitude;
            }

            Modifier mod = this.mods[modIndex(magnitude, plural, isNegative)];
            if (mod == null && plural != StandardPlural.OTHER) {
               mod = this.mods[modIndex(magnitude, StandardPlural.OTHER, isNegative)];
            }

            if (mod == USE_FALLBACK) {
               mod = null;
            }

            return mod;
         }
      }

      public boolean has(int magnitude, StandardPlural plural) {
         return this.mods[modIndex(magnitude, plural, false)] != null;
      }

      void setModifiers(Modifier positive, Modifier negative, int magnitude, StandardPlural plural) {
         this.mods[modIndex(magnitude, plural, false)] = positive;
         this.mods[modIndex(magnitude, plural, true)] = negative;
         this.isEmpty = false;
         if (magnitude > this.largestMagnitude) {
            this.largestMagnitude = magnitude;
         }

      }

      void setNoFallback(int magnitude, StandardPlural plural) {
         this.setModifiers(USE_FALLBACK, USE_FALLBACK, magnitude, plural);
      }

      private static final int modIndex(int magnitude, StandardPlural plural, boolean isNegative) {
         return magnitude * StandardPlural.COUNT * 2 + plural.ordinal() * 2 + (isNegative ? 1 : 0);
      }
   }

   public interface IProperties extends RoundingFormat.IProperties, CurrencyFormat.ICurrencyProperties {
      org.python.icu.text.CompactDecimalFormat.CompactStyle DEFAULT_COMPACT_STYLE = null;

      org.python.icu.text.CompactDecimalFormat.CompactStyle getCompactStyle();

      IProperties setCompactStyle(org.python.icu.text.CompactDecimalFormat.CompactStyle var1);
   }
}
