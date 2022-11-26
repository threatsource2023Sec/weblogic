package org.python.icu.impl;

public final class PatternProps {
   private static final byte[] latin1 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0};
   private static final byte[] index2000 = new byte[]{2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9};
   private static final int[] syntax2000 = new int[]{0, -1, -65536, 2147418367, 2146435070, -65536, 4194303, -1048576, -242, 65537};
   private static final int[] syntaxOrWhiteSpace2000 = new int[]{0, -1, -16384, 2147419135, 2146435070, -65536, 4194303, -1048576, -242, 65537};

   public static boolean isSyntax(int c) {
      if (c < 0) {
         return false;
      } else if (c <= 255) {
         return latin1[c] == 3;
      } else if (c < 8208) {
         return false;
      } else if (c <= 12336) {
         int bits = syntax2000[index2000[c - 8192 >> 5]];
         return (bits >> (c & 31) & 1) != 0;
      } else if (64830 <= c && c <= 65094) {
         return c <= 64831 || 65093 <= c;
      } else {
         return false;
      }
   }

   public static boolean isSyntaxOrWhiteSpace(int c) {
      if (c < 0) {
         return false;
      } else if (c <= 255) {
         return latin1[c] != 0;
      } else if (c < 8206) {
         return false;
      } else if (c <= 12336) {
         int bits = syntaxOrWhiteSpace2000[index2000[c - 8192 >> 5]];
         return (bits >> (c & 31) & 1) != 0;
      } else if (64830 <= c && c <= 65094) {
         return c <= 64831 || 65093 <= c;
      } else {
         return false;
      }
   }

   public static boolean isWhiteSpace(int c) {
      if (c < 0) {
         return false;
      } else if (c <= 255) {
         return latin1[c] == 5;
      } else if (8206 <= c && c <= 8233) {
         return c <= 8207 || 8232 <= c;
      } else {
         return false;
      }
   }

   public static int skipWhiteSpace(CharSequence s, int i) {
      while(i < s.length() && isWhiteSpace(s.charAt(i))) {
         ++i;
      }

      return i;
   }

   public static String trimWhiteSpace(String s) {
      if (s.length() != 0 && (isWhiteSpace(s.charAt(0)) || isWhiteSpace(s.charAt(s.length() - 1)))) {
         int start = 0;

         int limit;
         for(limit = s.length(); start < limit && isWhiteSpace(s.charAt(start)); ++start) {
         }

         if (start < limit) {
            while(isWhiteSpace(s.charAt(limit - 1))) {
               --limit;
            }
         }

         return s.substring(start, limit);
      } else {
         return s;
      }
   }

   public static boolean isIdentifier(CharSequence s) {
      int limit = s.length();
      if (limit == 0) {
         return false;
      } else {
         int start = 0;

         while(!isSyntaxOrWhiteSpace(s.charAt(start++))) {
            if (start >= limit) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isIdentifier(CharSequence s, int start, int limit) {
      if (start >= limit) {
         return false;
      } else {
         while(!isSyntaxOrWhiteSpace(s.charAt(start++))) {
            if (start >= limit) {
               return true;
            }
         }

         return false;
      }
   }

   public static int skipIdentifier(CharSequence s, int i) {
      while(i < s.length() && !isSyntaxOrWhiteSpace(s.charAt(i))) {
         ++i;
      }

      return i;
   }
}
