package org.python.icu.impl.number.rounders;

import java.math.BigDecimal;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.Rounder;

public class IncrementRounder extends Rounder {
   private final BigDecimal roundingIncrement;

   public static boolean useRoundingIncrement(IProperties properties) {
      return properties.getRoundingIncrement() != IncrementRounder.IProperties.DEFAULT_ROUNDING_INCREMENT;
   }

   public static IncrementRounder getInstance(IProperties properties) {
      return new IncrementRounder(properties);
   }

   private IncrementRounder(IProperties properties) {
      super(properties);
      if (properties.getRoundingIncrement().compareTo(BigDecimal.ZERO) <= 0) {
         throw new IllegalArgumentException("Rounding interval must be greater than zero");
      } else {
         this.roundingIncrement = properties.getRoundingIncrement();
      }
   }

   public void apply(FormatQuantity input) {
      input.roundToIncrement(this.roundingIncrement, this.mathContext);
      this.applyDefaults(input);
   }

   public void export(Properties properties) {
      super.export(properties);
      properties.setRoundingIncrement(this.roundingIncrement);
   }

   public interface IProperties extends Rounder.IBasicRoundingProperties {
      BigDecimal DEFAULT_ROUNDING_INCREMENT = null;

      BigDecimal getRoundingIncrement();

      IProperties setRoundingIncrement(BigDecimal var1);
   }
}
