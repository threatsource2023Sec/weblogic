package org.python.icu.impl.number.rounders;

import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Rounder;

public class MagnitudeRounder extends Rounder {
   public static boolean useFractionFormat(IProperties properties) {
      return properties.getMinimumFractionDigits() != -1 || properties.getMaximumFractionDigits() != -1;
   }

   public static MagnitudeRounder getInstance(Rounder.IBasicRoundingProperties properties) {
      return new MagnitudeRounder(properties);
   }

   private MagnitudeRounder(Rounder.IBasicRoundingProperties properties) {
      super(properties);
   }

   public void apply(FormatQuantity input) {
      input.roundToMagnitude(-this.maxFrac, this.mathContext);
      this.applyDefaults(input);
   }

   public interface IProperties extends Rounder.IBasicRoundingProperties {
   }
}
