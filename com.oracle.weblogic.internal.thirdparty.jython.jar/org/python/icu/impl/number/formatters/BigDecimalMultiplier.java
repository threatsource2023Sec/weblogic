package org.python.icu.impl.number.formatters;

import java.math.BigDecimal;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.Properties;

public class BigDecimalMultiplier extends Format.BeforeFormat {
   private final BigDecimal multiplier;

   public static boolean useMultiplier(IProperties properties) {
      return properties.getMultiplier() != BigDecimalMultiplier.IProperties.DEFAULT_MULTIPLIER;
   }

   public static BigDecimalMultiplier getInstance(IProperties properties) {
      if (properties.getMultiplier() == null) {
         throw new IllegalArgumentException("The multiplier must be present for MultiplierFormat");
      } else {
         return new BigDecimalMultiplier(properties);
      }
   }

   private BigDecimalMultiplier(IProperties properties) {
      this.multiplier = properties.getMultiplier();
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      input.multiplyBy(this.multiplier);
   }

   public void export(Properties properties) {
      properties.setMultiplier(this.multiplier);
   }

   public interface IProperties {
      BigDecimal DEFAULT_MULTIPLIER = null;

      BigDecimal getMultiplier();

      IProperties setMultiplier(BigDecimal var1);
   }
}
