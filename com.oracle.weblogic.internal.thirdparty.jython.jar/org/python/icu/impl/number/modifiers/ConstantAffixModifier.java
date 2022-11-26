package org.python.icu.impl.number.modifiers;

import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.NumberFormat;

public class ConstantAffixModifier extends Modifier.BaseModifier implements Modifier.AffixModifier {
   public static final Modifier.AffixModifier EMPTY = new ConstantAffixModifier();
   private final String prefix;
   private final String suffix;
   private final NumberFormat.Field field;
   private final boolean strong;

   public ConstantAffixModifier(String prefix, String suffix, NumberFormat.Field field, boolean strong) {
      this.prefix = prefix == null ? "" : prefix;
      this.suffix = suffix == null ? "" : suffix;
      this.field = field;
      this.strong = strong;
   }

   public ConstantAffixModifier() {
      this.prefix = "";
      this.suffix = "";
      this.field = null;
      this.strong = false;
   }

   public int apply(NumberStringBuilder output, int leftIndex, int rightIndex) {
      int length = output.insert(rightIndex, (CharSequence)this.suffix, (NumberFormat.Field)this.field);
      length += output.insert(leftIndex, (CharSequence)this.prefix, (NumberFormat.Field)this.field);
      return length;
   }

   public int length() {
      return this.prefix.length() + this.suffix.length();
   }

   public boolean isStrong() {
      return this.strong;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public boolean contentEquals(CharSequence _prefix, CharSequence _suffix) {
      if (_prefix == null && !this.prefix.isEmpty()) {
         return false;
      } else if (_suffix == null && !this.suffix.isEmpty()) {
         return false;
      } else if (_prefix != null && this.prefix.length() != _prefix.length()) {
         return false;
      } else if (_suffix != null && this.suffix.length() != _suffix.length()) {
         return false;
      } else {
         int i;
         for(i = 0; i < this.prefix.length(); ++i) {
            if (this.prefix.charAt(i) != _prefix.charAt(i)) {
               return false;
            }
         }

         for(i = 0; i < this.suffix.length(); ++i) {
            if (this.suffix.charAt(i) != _suffix.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public String toString() {
      return String.format("<ConstantAffixModifier(%d) prefix:'%s' suffix:'%s'>", this.length(), this.prefix, this.suffix);
   }

   public void export(Properties properties) {
      throw new UnsupportedOperationException();
   }
}
