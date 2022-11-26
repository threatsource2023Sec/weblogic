package org.python.icu.lang;

/** @deprecated */
@Deprecated
public class CharSequences {
   /** @deprecated */
   @Deprecated
   public static int matchAfter(CharSequence a, CharSequence b, int aIndex, int bIndex) {
      int i = aIndex;
      int j = bIndex;
      int alen = a.length();

      int result;
      for(int blen = b.length(); i < alen && j < blen; ++j) {
         result = a.charAt(i);
         char cb = b.charAt(j);
         if (result != cb) {
            break;
         }

         ++i;
      }

      result = i - aIndex;
      if (result != 0 && !onCharacterBoundary(a, i) && !onCharacterBoundary(b, j)) {
         --result;
      }

      return result;
   }

   /** @deprecated */
   @Deprecated
   public int codePointLength(CharSequence s) {
      return Character.codePointCount(s, 0, s.length());
   }

   /** @deprecated */
   @Deprecated
   public static final boolean equals(int codepoint, CharSequence other) {
      if (other == null) {
         return false;
      } else {
         switch (other.length()) {
            case 1:
               return codepoint == other.charAt(0);
            case 2:
               return codepoint > 65535 && codepoint == Character.codePointAt(other, 0);
            default:
               return false;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static final boolean equals(CharSequence other, int codepoint) {
      return equals(codepoint, other);
   }

   /** @deprecated */
   @Deprecated
   public static int compare(CharSequence string, int codePoint) {
      if (codePoint >= 0 && codePoint <= 1114111) {
         int stringLength = string.length();
         if (stringLength == 0) {
            return -1;
         } else {
            char firstChar = string.charAt(0);
            int offset = codePoint - 65536;
            int lead;
            if (offset < 0) {
               lead = firstChar - codePoint;
               return lead != 0 ? lead : stringLength - 1;
            } else {
               lead = (char)((offset >>> 10) + '\ud800');
               int result = firstChar - lead;
               if (result != 0) {
                  return result;
               } else {
                  if (stringLength > 1) {
                     char trail = (char)((offset & 1023) + '\udc00');
                     result = string.charAt(1) - trail;
                     if (result != 0) {
                        return result;
                     }
                  }

                  return stringLength - 2;
               }
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   /** @deprecated */
   @Deprecated
   public static int compare(int codepoint, CharSequence a) {
      int result = compare(a, codepoint);
      return result > 0 ? -1 : (result < 0 ? 1 : 0);
   }

   /** @deprecated */
   @Deprecated
   public static int getSingleCodePoint(CharSequence s) {
      int length = s.length();
      if (length >= 1 && length <= 2) {
         int result = Character.codePointAt(s, 0);
         return result < 65536 == (length == 1) ? result : Integer.MAX_VALUE;
      } else {
         return Integer.MAX_VALUE;
      }
   }

   /** @deprecated */
   @Deprecated
   public static final boolean equals(Object a, Object b) {
      return a == null ? b == null : (b == null ? false : a.equals(b));
   }

   /** @deprecated */
   @Deprecated
   public static int compare(CharSequence a, CharSequence b) {
      int alength = a.length();
      int blength = b.length();
      int min = alength <= blength ? alength : blength;

      for(int i = 0; i < min; ++i) {
         int diff = a.charAt(i) - b.charAt(i);
         if (diff != 0) {
            return diff;
         }
      }

      return alength - blength;
   }

   /** @deprecated */
   @Deprecated
   public static boolean equalsChars(CharSequence a, CharSequence b) {
      return a.length() == b.length() && compare(a, b) == 0;
   }

   /** @deprecated */
   @Deprecated
   public static boolean onCharacterBoundary(CharSequence s, int i) {
      return i <= 0 || i >= s.length() || !Character.isHighSurrogate(s.charAt(i - 1)) || !Character.isLowSurrogate(s.charAt(i));
   }

   /** @deprecated */
   @Deprecated
   public static int indexOf(CharSequence s, int codePoint) {
      int cp;
      for(int i = 0; i < s.length(); i += Character.charCount(cp)) {
         cp = Character.codePointAt(s, i);
         if (cp == codePoint) {
            return i;
         }
      }

      return -1;
   }

   /** @deprecated */
   @Deprecated
   public static int[] codePoints(CharSequence s) {
      int[] result = new int[s.length()];
      int j = 0;

      for(int i = 0; i < s.length(); ++i) {
         char cp = s.charAt(i);
         if (cp >= '\udc00' && cp <= '\udfff' && i != 0) {
            char last = (char)result[j - 1];
            if (last >= '\ud800' && last <= '\udbff') {
               result[j - 1] = Character.toCodePoint(last, cp);
               continue;
            }
         }

         result[j++] = cp;
      }

      if (j == result.length) {
         return result;
      } else {
         int[] shortResult = new int[j];
         System.arraycopy(result, 0, shortResult, 0, j);
         return shortResult;
      }
   }

   private CharSequences() {
   }
}
