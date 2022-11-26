package org.python.icu.impl.number.rounders;

import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.Rounder;
import org.python.icu.text.DecimalFormat;

public class SignificantDigitsRounder extends Rounder {
   private final int minSig;
   private final int maxSig;
   private final DecimalFormat.SignificantDigitsMode mode;

   public static boolean useSignificantDigits(IProperties properties) {
      return properties.getMinimumSignificantDigits() != -1 || properties.getMaximumSignificantDigits() != -1 || properties.getSignificantDigitsMode() != SignificantDigitsRounder.IProperties.DEFAULT_SIGNIFICANT_DIGITS_MODE;
   }

   public static SignificantDigitsRounder getInstance(IProperties properties) {
      return new SignificantDigitsRounder(properties);
   }

   private SignificantDigitsRounder(IProperties properties) {
      super(properties);
      int _minSig = properties.getMinimumSignificantDigits();
      int _maxSig = properties.getMaximumSignificantDigits();
      this.minSig = _minSig < 1 ? 1 : (_minSig > 1000 ? 1000 : _minSig);
      this.maxSig = _maxSig < 0 ? 1000 : (_maxSig < this.minSig ? this.minSig : (_maxSig > 1000 ? 1000 : _maxSig));
      DecimalFormat.SignificantDigitsMode _mode = properties.getSignificantDigitsMode();
      this.mode = _mode == null ? DecimalFormat.SignificantDigitsMode.OVERRIDE_MAXIMUM_FRACTION : _mode;
   }

   public void apply(FormatQuantity input) {
      int magnitude;
      if (input.isZero()) {
         magnitude = this.minInt - 1;
      } else {
         magnitude = input.getMagnitude();
      }

      int effectiveMag = Math.min(magnitude + 1, this.maxInt);
      int magMinSig = effectiveMag - this.minSig;
      int magMaxSig = effectiveMag - this.maxSig;
      int roundingMagnitude;
      switch (this.mode) {
         case OVERRIDE_MAXIMUM_FRACTION:
            roundingMagnitude = magMaxSig;
            break;
         case RESPECT_MAXIMUM_FRACTION:
            roundingMagnitude = Math.max(-this.maxFrac, magMaxSig);
            break;
         case ENSURE_MINIMUM_SIGNIFICANT:
            roundingMagnitude = Math.min(magMinSig, Math.max(-this.maxFrac, magMaxSig));
            break;
         default:
            throw new AssertionError();
      }

      input.roundToMagnitude(roundingMagnitude, this.mathContext);
      if (input.isZero()) {
         magnitude = this.minInt - 1;
      } else {
         magnitude = input.getMagnitude();
      }

      effectiveMag = Math.min(magnitude + 1, this.maxInt);
      magMinSig = effectiveMag - this.minSig;
      int var10000 = effectiveMag - this.maxSig;
      switch (this.mode) {
         case OVERRIDE_MAXIMUM_FRACTION:
            input.setIntegerFractionLength(this.minInt, this.maxInt, Math.max(this.minFrac, -magMinSig), Integer.MAX_VALUE);
            break;
         case RESPECT_MAXIMUM_FRACTION:
            input.setIntegerFractionLength(this.minInt, this.maxInt, Math.min(this.maxFrac, Math.max(this.minFrac, -magMinSig)), this.maxFrac);
            break;
         case ENSURE_MINIMUM_SIGNIFICANT:
            input.setIntegerFractionLength(this.minInt, this.maxInt, this.minFrac, Integer.MAX_VALUE);
      }

   }

   public void export(Properties properties) {
      super.export(properties);
      properties.setMinimumSignificantDigits(this.minSig);
      properties.setMaximumSignificantDigits(this.maxSig);
      properties.setSignificantDigitsMode(this.mode);
   }

   public interface IProperties extends Rounder.IBasicRoundingProperties {
      int DEFAULT_MINIMUM_SIGNIFICANT_DIGITS = -1;
      int DEFAULT_MAXIMUM_SIGNIFICANT_DIGITS = -1;
      DecimalFormat.SignificantDigitsMode DEFAULT_SIGNIFICANT_DIGITS_MODE = null;

      int getMinimumSignificantDigits();

      IProperties setMinimumSignificantDigits(int var1);

      int getMaximumSignificantDigits();

      IProperties setMaximumSignificantDigits(int var1);

      DecimalFormat.SignificantDigitsMode getSignificantDigitsMode();

      IProperties setSignificantDigitsMode(DecimalFormat.SignificantDigitsMode var1);
   }
}
