package org.python.icu.impl.number;

import java.math.MathContext;
import java.math.RoundingMode;

public abstract class Rounder extends Format.BeforeFormat {
   protected final MathContext mathContext;
   protected final int minInt;
   protected final int maxInt;
   protected final int minFrac;
   protected final int maxFrac;

   protected Rounder(IBasicRoundingProperties properties) {
      this.mathContext = RoundingUtils.getMathContextOrUnlimited(properties);
      int _maxInt = properties.getMaximumIntegerDigits();
      int _minInt = properties.getMinimumIntegerDigits();
      int _maxFrac = properties.getMaximumFractionDigits();
      int _minFrac = properties.getMinimumFractionDigits();
      if (_minInt == 0 && _maxFrac != 0) {
         this.minFrac = _minFrac <= 0 ? 1 : _minFrac;
         this.maxFrac = _maxFrac < 0 ? Integer.MAX_VALUE : (_maxFrac < this.minFrac ? this.minFrac : _maxFrac);
         this.minInt = 0;
         this.maxInt = _maxInt < 0 ? Integer.MAX_VALUE : _maxInt;
      } else {
         this.minFrac = _minFrac < 0 ? 0 : _minFrac;
         this.maxFrac = _maxFrac < 0 ? Integer.MAX_VALUE : (_maxFrac < this.minFrac ? this.minFrac : _maxFrac);
         this.minInt = _minInt <= 0 ? 1 : _minInt;
         this.maxInt = _maxInt < 0 ? Integer.MAX_VALUE : (_maxInt < this.minInt ? this.minInt : _maxInt);
      }

   }

   public abstract void apply(FormatQuantity var1);

   public int chooseMultiplierAndApply(FormatQuantity input, MultiplierGenerator mg) {
      FormatQuantity copy = input.createCopy();
      int magnitude = input.getMagnitude();
      int multiplier = mg.getMultiplier(magnitude);
      input.adjustMagnitude(multiplier);
      this.apply(input);
      if (input.getMagnitude() == magnitude + multiplier + 1) {
         ++magnitude;
         input.copyFrom(copy);
         multiplier = mg.getMultiplier(magnitude);
         input.adjustMagnitude(multiplier);

         assert input.getMagnitude() == magnitude + multiplier - 1;

         this.apply(input);

         assert input.getMagnitude() == magnitude + multiplier;
      }

      return multiplier;
   }

   protected void applyDefaults(FormatQuantity input) {
      input.setIntegerFractionLength(this.minInt, this.maxInt, this.minFrac, this.maxFrac);
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      this.apply(input);
   }

   public void export(Properties properties) {
      properties.setMathContext(this.mathContext);
      properties.setRoundingMode(this.mathContext.getRoundingMode());
      properties.setMinimumFractionDigits(this.minFrac);
      properties.setMinimumIntegerDigits(this.minInt);
      properties.setMaximumFractionDigits(this.maxFrac);
      properties.setMaximumIntegerDigits(this.maxInt);
   }

   public interface MultiplierGenerator {
      int getMultiplier(int var1);
   }

   public interface IBasicRoundingProperties {
      int DEFAULT_MINIMUM_INTEGER_DIGITS = -1;
      int DEFAULT_MAXIMUM_INTEGER_DIGITS = -1;
      int DEFAULT_MINIMUM_FRACTION_DIGITS = -1;
      int DEFAULT_MAXIMUM_FRACTION_DIGITS = -1;
      RoundingMode DEFAULT_ROUNDING_MODE = null;
      MathContext DEFAULT_MATH_CONTEXT = null;

      int getMinimumIntegerDigits();

      IBasicRoundingProperties setMinimumIntegerDigits(int var1);

      int getMaximumIntegerDigits();

      IBasicRoundingProperties setMaximumIntegerDigits(int var1);

      int getMinimumFractionDigits();

      IBasicRoundingProperties setMinimumFractionDigits(int var1);

      int getMaximumFractionDigits();

      IBasicRoundingProperties setMaximumFractionDigits(int var1);

      RoundingMode getRoundingMode();

      IBasicRoundingProperties setRoundingMode(RoundingMode var1);

      MathContext getMathContext();

      IBasicRoundingProperties setMathContext(MathContext var1);
   }
}
