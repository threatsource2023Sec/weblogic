package org.python.google.common.escape;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public final class CharEscaperBuilder {
   private final Map map = new HashMap();
   private int max = -1;

   @CanIgnoreReturnValue
   public CharEscaperBuilder addEscape(char c, String r) {
      this.map.put(c, Preconditions.checkNotNull(r));
      if (c > this.max) {
         this.max = c;
      }

      return this;
   }

   @CanIgnoreReturnValue
   public CharEscaperBuilder addEscapes(char[] cs, String r) {
      Preconditions.checkNotNull(r);
      char[] var3 = cs;
      int var4 = cs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char c = var3[var5];
         this.addEscape(c, r);
      }

      return this;
   }

   public char[][] toArray() {
      char[][] result = new char[this.max + 1][];

      Map.Entry entry;
      for(Iterator var2 = this.map.entrySet().iterator(); var2.hasNext(); result[(Character)entry.getKey()] = ((String)entry.getValue()).toCharArray()) {
         entry = (Map.Entry)var2.next();
      }

      return result;
   }

   public Escaper toEscaper() {
      return new CharArrayDecorator(this.toArray());
   }

   private static class CharArrayDecorator extends CharEscaper {
      private final char[][] replacements;
      private final int replaceLength;

      CharArrayDecorator(char[][] replacements) {
         this.replacements = replacements;
         this.replaceLength = replacements.length;
      }

      public String escape(String s) {
         int slen = s.length();

         for(int index = 0; index < slen; ++index) {
            char c = s.charAt(index);
            if (c < this.replacements.length && this.replacements[c] != null) {
               return this.escapeSlow(s, index);
            }
         }

         return s;
      }

      protected char[] escape(char c) {
         return c < this.replaceLength ? this.replacements[c] : null;
      }
   }
}
