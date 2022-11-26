package org.python.google.common.net;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.escape.UnicodeEscaper;

@Beta
@GwtCompatible
public final class PercentEscaper extends UnicodeEscaper {
   private static final char[] PLUS_SIGN = new char[]{'+'};
   private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
   private final boolean plusForSpace;
   private final boolean[] safeOctets;

   public PercentEscaper(String safeChars, boolean plusForSpace) {
      Preconditions.checkNotNull(safeChars);
      if (safeChars.matches(".*[0-9A-Za-z].*")) {
         throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
      } else {
         safeChars = safeChars + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
         if (plusForSpace && safeChars.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
         } else {
            this.plusForSpace = plusForSpace;
            this.safeOctets = createSafeOctets(safeChars);
         }
      }
   }

   private static boolean[] createSafeOctets(String safeChars) {
      int maxChar = -1;
      char[] safeCharArray = safeChars.toCharArray();
      char[] var3 = safeCharArray;
      int var4 = safeCharArray.length;

      int var5;
      int c;
      for(var5 = 0; var5 < var4; ++var5) {
         c = var3[var5];
         maxChar = Math.max(c, maxChar);
      }

      boolean[] octets = new boolean[maxChar + 1];
      char[] var9 = safeCharArray;
      var5 = safeCharArray.length;

      for(c = 0; c < var5; ++c) {
         char c = var9[c];
         octets[c] = true;
      }

      return octets;
   }

   protected int nextEscapeIndex(CharSequence csq, int index, int end) {
      Preconditions.checkNotNull(csq);

      while(index < end) {
         char c = csq.charAt(index);
         if (c >= this.safeOctets.length || !this.safeOctets[c]) {
            break;
         }

         ++index;
      }

      return index;
   }

   public String escape(String s) {
      Preconditions.checkNotNull(s);
      int slen = s.length();

      for(int index = 0; index < slen; ++index) {
         char c = s.charAt(index);
         if (c >= this.safeOctets.length || !this.safeOctets[c]) {
            return this.escapeSlow(s, index);
         }
      }

      return s;
   }

   protected char[] escape(int cp) {
      if (cp < this.safeOctets.length && this.safeOctets[cp]) {
         return null;
      } else if (cp == 32 && this.plusForSpace) {
         return PLUS_SIGN;
      } else {
         char[] dest;
         if (cp <= 127) {
            dest = new char[]{'%', UPPER_HEX_DIGITS[cp >>> 4], UPPER_HEX_DIGITS[cp & 15]};
            return dest;
         } else if (cp <= 2047) {
            dest = new char[]{'%', '\u0000', '\u0000', '%', '\u0000', UPPER_HEX_DIGITS[cp & 15]};
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[2] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[1] = UPPER_HEX_DIGITS[12 | cp];
            return dest;
         } else if (cp <= 65535) {
            dest = new char[9];
            dest[0] = '%';
            dest[1] = 'E';
            dest[3] = '%';
            dest[6] = '%';
            dest[8] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[2] = UPPER_HEX_DIGITS[cp];
            return dest;
         } else if (cp <= 1114111) {
            dest = new char[12];
            dest[0] = '%';
            dest[1] = 'F';
            dest[3] = '%';
            dest[6] = '%';
            dest[9] = '%';
            dest[11] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[10] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[8] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[8 | cp & 3];
            cp >>>= 2;
            dest[2] = UPPER_HEX_DIGITS[cp & 7];
            return dest;
         } else {
            throw new IllegalArgumentException("Invalid unicode character value " + cp);
         }
      }
   }
}
