package org.python.icu.impl.number.modifiers;

import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.NumberFormat;

public class ConstantMultiFieldModifier extends Modifier.BaseModifier implements Modifier.AffixModifier {
   public static final ConstantMultiFieldModifier EMPTY = new ConstantMultiFieldModifier();
   private final char[] prefixChars;
   private final char[] suffixChars;
   private final NumberFormat.Field[] prefixFields;
   private final NumberFormat.Field[] suffixFields;
   private final String prefix;
   private final String suffix;
   private final boolean strong;

   public ConstantMultiFieldModifier(NumberStringBuilder prefix, NumberStringBuilder suffix, boolean strong) {
      this.prefixChars = prefix.toCharArray();
      this.suffixChars = suffix.toCharArray();
      this.prefixFields = prefix.toFieldArray();
      this.suffixFields = suffix.toFieldArray();
      this.prefix = new String(this.prefixChars);
      this.suffix = new String(this.suffixChars);
      this.strong = strong;
   }

   private ConstantMultiFieldModifier() {
      this.prefixChars = new char[0];
      this.suffixChars = new char[0];
      this.prefixFields = new NumberFormat.Field[0];
      this.suffixFields = new NumberFormat.Field[0];
      this.prefix = "";
      this.suffix = "";
      this.strong = false;
   }

   public int apply(NumberStringBuilder output, int leftIndex, int rightIndex) {
      int length = output.insert(rightIndex, this.suffixChars, this.suffixFields);
      length += output.insert(leftIndex, this.prefixChars, this.prefixFields);
      return length;
   }

   public int length() {
      return this.prefixChars.length + this.suffixChars.length;
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

   public boolean contentEquals(NumberStringBuilder prefix, NumberStringBuilder suffix) {
      return prefix.contentEquals(this.prefixChars, this.prefixFields) && suffix.contentEquals(this.suffixChars, this.suffixFields);
   }

   public String toString() {
      return String.format("<ConstantMultiFieldModifier(%d) prefix:'%s' suffix:'%s'>", this.length(), this.prefix, this.suffix);
   }

   public void export(Properties properties) {
      throw new UnsupportedOperationException();
   }
}
