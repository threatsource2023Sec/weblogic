package org.python.icu.impl.number;

import org.python.icu.impl.StandardPlural;

public interface Modifier {
   int apply(NumberStringBuilder var1, int var2, int var3);

   int length();

   boolean isStrong();

   String getPrefix();

   String getSuffix();

   public abstract static class BaseModifier extends Format.BeforeFormat implements Modifier, PositiveNegativeModifier {
      public void before(FormatQuantity input, ModifierHolder mods) {
         mods.add(this);
      }

      public Modifier getModifier(boolean isNegative) {
         return this;
      }
   }

   public interface AffixModifier extends Modifier {
   }

   public interface PositiveNegativePluralModifier extends Exportable {
      Modifier getModifier(StandardPlural var1, boolean var2);
   }

   public interface PositiveNegativeModifier extends Exportable {
      Modifier getModifier(boolean var1);
   }
}
