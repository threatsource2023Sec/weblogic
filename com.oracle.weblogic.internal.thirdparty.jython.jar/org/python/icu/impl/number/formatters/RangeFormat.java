package org.python.icu.impl.number.formatters;

import java.util.Deque;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.text.NumberFormat;

public class RangeFormat extends Format {
   private final String separator;
   private final Format left;
   private final Format right;

   public RangeFormat(Format left, Format right, String separator) {
      this.separator = separator;
      this.left = left;
      this.right = right;
      if (left == null || right == null) {
         throw new IllegalArgumentException("Both child formatters are required for RangeFormat");
      }
   }

   public int process(Deque inputs, ModifierHolder mods, NumberStringBuilder string, int startIndex) {
      ModifierHolder lMods = new ModifierHolder();
      ModifierHolder rMods = new ModifierHolder();
      int lLen = this.left.process(inputs, lMods, string, startIndex);
      int rLen = this.right.process(inputs, rMods, string, startIndex + lLen);

      while(lMods.peekLast() != null && lMods.peekLast() == rMods.peekLast()) {
         mods.add(lMods.removeLast());
         rMods.removeLast();
      }

      lLen += lMods.applyAll(string, startIndex, startIndex + lLen);
      rLen += rMods.applyAll(string, startIndex + lLen, startIndex + lLen + rLen);
      int sLen = string.insert(startIndex + lLen, (CharSequence)this.separator, (NumberFormat.Field)null);
      return lLen + sLen + rLen;
   }
}
