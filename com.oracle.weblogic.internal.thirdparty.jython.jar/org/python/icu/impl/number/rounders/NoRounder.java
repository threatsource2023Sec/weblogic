package org.python.icu.impl.number.rounders;

import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Rounder;

public final class NoRounder extends Rounder {
   public static NoRounder getInstance(Rounder.IBasicRoundingProperties properties) {
      return new NoRounder(properties);
   }

   private NoRounder(Rounder.IBasicRoundingProperties properties) {
      super(properties);
   }

   public void apply(FormatQuantity input) {
      this.applyDefaults(input);
      input.roundToInfinity();
   }
}
