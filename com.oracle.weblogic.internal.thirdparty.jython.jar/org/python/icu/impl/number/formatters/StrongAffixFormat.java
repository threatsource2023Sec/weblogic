package org.python.icu.impl.number.formatters;

import java.util.Deque;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;

public class StrongAffixFormat extends Format implements Format.AfterFormat {
   private final Format child;

   public StrongAffixFormat(Format child) {
      this.child = child;
      if (child == null) {
         throw new IllegalArgumentException("A child formatter is required for StrongAffixFormat");
      }
   }

   public int process(Deque inputs, ModifierHolder mods, NumberStringBuilder string, int startIndex) {
      int length = this.child.process(inputs, mods, string, startIndex);
      length += mods.applyAll(string, startIndex, startIndex + length);
      return length;
   }

   public int after(ModifierHolder mods, NumberStringBuilder string, int leftIndex, int rightIndex) {
      return mods.applyAll(string, leftIndex, rightIndex);
   }

   public void export(Properties properties) {
   }
}
