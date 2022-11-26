package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.Properties;

public class MagnitudeMultiplier extends Format.BeforeFormat {
   private static final MagnitudeMultiplier DEFAULT = new MagnitudeMultiplier(0);
   final int delta;

   public static boolean useMagnitudeMultiplier(IProperties properties) {
      return properties.getMagnitudeMultiplier() != 0;
   }

   public static Format.BeforeFormat getInstance(Properties properties) {
      return properties.getMagnitudeMultiplier() == 0 ? DEFAULT : new MagnitudeMultiplier(properties.getMagnitudeMultiplier());
   }

   private MagnitudeMultiplier(int delta) {
      this.delta = delta;
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      input.adjustMagnitude(this.delta);
   }

   public void export(Properties properties) {
      properties.setMagnitudeMultiplier(this.delta);
   }

   public interface IProperties {
      int DEFAULT_MAGNITUDE_MULTIPLIER = 0;

      int getMagnitudeMultiplier();

      IProperties setMagnitudeMultiplier(int var1);
   }
}
