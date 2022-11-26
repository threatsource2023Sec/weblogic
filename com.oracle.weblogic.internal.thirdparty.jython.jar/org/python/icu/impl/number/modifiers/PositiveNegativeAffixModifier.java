package org.python.icu.impl.number.modifiers;

import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.Properties;

public class PositiveNegativeAffixModifier extends Format.BeforeFormat implements Modifier.PositiveNegativeModifier {
   private final Modifier.AffixModifier positive;
   private final Modifier.AffixModifier negative;

   public PositiveNegativeAffixModifier(Modifier.AffixModifier positive, Modifier.AffixModifier negative) {
      this.positive = positive;
      this.negative = negative;
   }

   public Modifier getModifier(boolean isNegative) {
      return isNegative ? this.negative : this.positive;
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      Modifier mod = this.getModifier(input.isNegative());
      mods.add(mod);
   }

   public void export(Properties properties) {
      exportPositiveNegative(properties, this.positive, this.negative);
   }

   static void exportPositiveNegative(Properties properties, Modifier positive, Modifier negative) {
      properties.setPositivePrefix(positive.getPrefix().isEmpty() ? null : positive.getPrefix());
      properties.setPositiveSuffix(positive.getSuffix().isEmpty() ? null : positive.getSuffix());
      properties.setNegativePrefix(negative.getPrefix().isEmpty() ? null : negative.getPrefix());
      properties.setNegativeSuffix(negative.getSuffix().isEmpty() ? null : negative.getSuffix());
   }
}
