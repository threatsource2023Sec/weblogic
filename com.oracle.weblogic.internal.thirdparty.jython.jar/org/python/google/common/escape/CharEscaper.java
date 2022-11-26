package org.python.google.common.escape;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@Beta
@GwtCompatible
public abstract class CharEscaper extends Escaper {
   private static final int DEST_PAD_MULTIPLIER = 2;

   protected CharEscaper() {
   }

   public String escape(String string) {
      Preconditions.checkNotNull(string);
      int length = string.length();

      for(int index = 0; index < length; ++index) {
         if (this.escape(string.charAt(index)) != null) {
            return this.escapeSlow(string, index);
         }
      }

      return string;
   }

   protected final String escapeSlow(String s, int index) {
      int slen = s.length();
      char[] dest = Platform.charBufferFromThreadLocal();
      int destSize = dest.length;
      int destIndex = 0;

      int lastEscape;
      int rlen;
      for(lastEscape = 0; index < slen; ++index) {
         char[] r = this.escape(s.charAt(index));
         if (r != null) {
            rlen = r.length;
            int charsSkipped = index - lastEscape;
            int sizeNeeded = destIndex + charsSkipped + rlen;
            if (destSize < sizeNeeded) {
               destSize = sizeNeeded + 2 * (slen - index);
               dest = growBuffer(dest, destIndex, destSize);
            }

            if (charsSkipped > 0) {
               s.getChars(lastEscape, index, dest, destIndex);
               destIndex += charsSkipped;
            }

            if (rlen > 0) {
               System.arraycopy(r, 0, dest, destIndex, rlen);
               destIndex += rlen;
            }

            lastEscape = index + 1;
         }
      }

      int charsLeft = slen - lastEscape;
      if (charsLeft > 0) {
         rlen = destIndex + charsLeft;
         if (destSize < rlen) {
            dest = growBuffer(dest, destIndex, rlen);
         }

         s.getChars(lastEscape, slen, dest, destIndex);
         destIndex = rlen;
      }

      return new String(dest, 0, destIndex);
   }

   protected abstract char[] escape(char var1);

   private static char[] growBuffer(char[] dest, int index, int size) {
      if (size < 0) {
         throw new AssertionError("Cannot increase internal buffer any further");
      } else {
         char[] copy = new char[size];
         if (index > 0) {
            System.arraycopy(dest, 0, copy, 0, index);
         }

         return copy;
      }
   }
}
