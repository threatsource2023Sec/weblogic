package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.FormatQuantitySelector;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.Rounder;
import org.python.icu.impl.number.modifiers.ConstantAffixModifier;
import org.python.icu.impl.number.modifiers.PositiveNegativeAffixModifier;
import org.python.icu.impl.number.rounders.IncrementRounder;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;

public class ScientificFormat extends Format.BeforeFormat implements Rounder.MultiplierGenerator {
   private static final ThreadLocal threadLocalProperties = new ThreadLocal() {
      protected Properties initialValue() {
         return new Properties();
      }
   };
   private final boolean exponentShowPlusSign;
   private final int exponentDigits;
   private final int minInt;
   private final int maxInt;
   private final int interval;
   private final Rounder rounder;
   private final ConstantAffixModifier separatorMod;
   private final PositiveNegativeAffixModifier signMod;
   private final String[] digitStrings;
   private static final ThreadLocal threadLocalStringBuilder = new ThreadLocal() {
      protected StringBuilder initialValue() {
         return new StringBuilder();
      }
   };

   public static boolean useScientificNotation(IProperties properties) {
      return properties.getMinimumExponentDigits() != -1;
   }

   public static ScientificFormat getInstance(DecimalFormatSymbols symbols, IProperties properties) {
      Object rounder;
      if (IncrementRounder.useRoundingIncrement(properties)) {
         rounder = IncrementRounder.getInstance(properties);
      } else if (SignificantDigitsRounder.useSignificantDigits(properties)) {
         rounder = SignificantDigitsRounder.getInstance(properties);
      } else {
         Properties rprops = ((Properties)threadLocalProperties.get()).clear();
         int minInt = properties.getMinimumIntegerDigits();
         int maxInt = properties.getMaximumIntegerDigits();
         int minFrac = properties.getMinimumFractionDigits();
         int maxFrac = properties.getMaximumFractionDigits();
         if (CurrencyFormat.useCurrency(properties)) {
            CurrencyFormat.populateCurrencyRounderProperties(rprops, symbols, properties);
            minFrac = rprops.getMinimumFractionDigits();
            maxFrac = rprops.getMaximumFractionDigits();
            rprops.clear();
         }

         if (minInt < 0) {
            minInt = 0;
         }

         if (maxInt < minInt) {
            maxInt = minInt;
         }

         if (minFrac < 0) {
            minFrac = 0;
         }

         if (maxFrac < minFrac) {
            maxFrac = minFrac;
         }

         rprops.setRoundingMode(properties.getRoundingMode());
         if (minInt == 0 && maxFrac == 0) {
            rprops.setMinimumSignificantDigits(1);
            rprops.setMaximumSignificantDigits(Integer.MAX_VALUE);
         } else if (minInt == 0 && minFrac == 0) {
            rprops.setMinimumSignificantDigits(1);
            rprops.setMaximumSignificantDigits(1 + maxFrac);
         } else {
            rprops.setMinimumSignificantDigits(minInt + minFrac);
            rprops.setMaximumSignificantDigits(minInt + maxFrac);
         }

         rprops.setMinimumIntegerDigits(maxInt == 0 ? 0 : Math.max(1, minInt + minFrac - maxFrac));
         rprops.setMaximumIntegerDigits(maxInt);
         rprops.setMinimumFractionDigits(Math.max(0, minFrac + minInt - maxInt));
         rprops.setMaximumFractionDigits(maxFrac);
         rounder = SignificantDigitsRounder.getInstance(rprops);
      }

      return new ScientificFormat(symbols, properties, (Rounder)rounder);
   }

   public static ScientificFormat getInstance(DecimalFormatSymbols symbols, IProperties properties, Rounder rounder) {
      return new ScientificFormat(symbols, properties, rounder);
   }

   private ScientificFormat(DecimalFormatSymbols symbols, IProperties properties, Rounder rounder) {
      this.exponentShowPlusSign = properties.getExponentSignAlwaysShown();
      this.exponentDigits = Math.max(1, properties.getMinimumExponentDigits());
      int _maxInt = properties.getMaximumIntegerDigits();
      int _minInt = properties.getMinimumIntegerDigits();
      this.minInt = _minInt < 0 ? 0 : (_minInt >= 8 ? 1 : _minInt);
      this.maxInt = _maxInt < _minInt ? _minInt : (_maxInt >= 8 ? _minInt : _maxInt);

      assert 0 <= this.minInt && this.minInt <= this.maxInt && this.maxInt < 8;

      this.interval = this.maxInt < 1 ? 1 : this.maxInt;
      this.rounder = rounder;
      this.digitStrings = symbols.getDigitStrings();
      this.separatorMod = new ConstantAffixModifier("", symbols.getExponentSeparator(), NumberFormat.Field.EXPONENT_SYMBOL, true);
      this.signMod = new PositiveNegativeAffixModifier(new ConstantAffixModifier("", this.exponentShowPlusSign ? symbols.getPlusSignString() : "", NumberFormat.Field.EXPONENT_SIGN, true), new ConstantAffixModifier("", symbols.getMinusSignString(), NumberFormat.Field.EXPONENT_SIGN, true));
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      int exponent;
      if (input.isZero()) {
         this.rounder.apply(input);
         exponent = 0;
      } else {
         exponent = -this.rounder.chooseMultiplierAndApply(input, this);
      }

      FormatQuantity exponentQ = FormatQuantitySelector.from(exponent);
      StringBuilder exponentSB = (StringBuilder)threadLocalStringBuilder.get();
      exponentSB.setLength(0);
      exponentQ.setIntegerFractionLength(this.exponentDigits, Integer.MAX_VALUE, 0, 0);

      for(int i = exponentQ.getUpperDisplayMagnitude(); i >= 0; --i) {
         exponentSB.append(this.digitStrings[exponentQ.getDigit(i)]);
      }

      mods.add(new ConstantAffixModifier("", exponentSB.toString(), NumberFormat.Field.EXPONENT, true));
      mods.add(this.signMod.getModifier(exponent < 0));
      mods.add(this.separatorMod);
   }

   public int getMultiplier(int magnitude) {
      int digitsShown = (magnitude % this.interval + this.interval) % this.interval + 1;
      if (digitsShown < this.minInt) {
         digitsShown = this.minInt;
      } else if (digitsShown > this.maxInt) {
         digitsShown = this.maxInt;
      }

      int retval = digitsShown - magnitude - 1;
      return retval;
   }

   public void export(Properties properties) {
      properties.setMinimumExponentDigits(this.exponentDigits);
      properties.setExponentSignAlwaysShown(this.exponentShowPlusSign);
      this.rounder.export(properties);
   }

   public interface IProperties extends RoundingFormat.IProperties, CurrencyFormat.IProperties {
      boolean DEFAULT_EXPONENT_SIGN_ALWAYS_SHOWN = false;
      int DEFAULT_MINIMUM_EXPONENT_DIGITS = -1;

      boolean getExponentSignAlwaysShown();

      IProperties setExponentSignAlwaysShown(boolean var1);

      int getMinimumExponentDigits();

      IProperties setMinimumExponentDigits(int var1);
   }
}
