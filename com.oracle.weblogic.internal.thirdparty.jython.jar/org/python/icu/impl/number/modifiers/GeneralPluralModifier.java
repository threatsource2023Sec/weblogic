package org.python.icu.impl.number.modifiers;

import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.PluralRules;

public class GeneralPluralModifier extends Format.BeforeFormat implements Modifier.PositiveNegativePluralModifier {
   private final Modifier[] mods;

   public GeneralPluralModifier() {
      this.mods = new Modifier[StandardPlural.COUNT * 2];
   }

   public void put(StandardPlural plural, Modifier modifier) {
      this.put(plural, modifier, modifier);
   }

   public void put(StandardPlural plural, Modifier positive, Modifier negative) {
      assert this.mods[plural.ordinal() * 2] == null;

      assert this.mods[plural.ordinal() * 2 + 1] == null;

      assert positive != null;

      assert negative != null;

      this.mods[plural.ordinal() * 2] = positive;
      this.mods[plural.ordinal() * 2 + 1] = negative;
   }

   public Modifier getModifier(StandardPlural plural, boolean isNegative) {
      Modifier mod = this.mods[plural.ordinal() * 2 + (isNegative ? 1 : 0)];
      if (mod == null) {
         mod = this.mods[StandardPlural.OTHER.ordinal() * 2 + (isNegative ? 1 : 0)];
      }

      if (mod == null) {
         throw new UnsupportedOperationException();
      } else {
         return mod;
      }
   }

   public void before(FormatQuantity input, ModifierHolder mods, PluralRules rules) {
      mods.add(this.getModifier(input.getStandardPlural(rules), input.isNegative()));
   }

   public void before(FormatQuantity input, ModifierHolder mods) {
      throw new UnsupportedOperationException();
   }

   public void export(Properties properties) {
      Modifier positive = this.getModifier(StandardPlural.OTHER, false);
      Modifier negative = this.getModifier(StandardPlural.OTHER, true);
      PositiveNegativeAffixModifier.exportPositiveNegative(properties, positive, negative);
   }
}
