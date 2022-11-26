package org.antlr.misc;

public class Utils {
   public static final int INTEGER_POOL_MAX_VALUE = 1000;
   static Integer[] ints = new Integer[1001];

   public static Integer integer(int x) {
      if (x >= 0 && x <= 1000) {
         if (ints[x] == null) {
            ints[x] = x;
         }

         return ints[x];
      } else {
         return x;
      }
   }

   public static String replace(String src, String replacee, String replacer) {
      StringBuilder result = new StringBuilder(src.length() + 50);
      int startIndex = 0;

      for(int endIndex = src.indexOf(replacee); endIndex != -1; endIndex = src.indexOf(replacee, startIndex)) {
         result.append(src.substring(startIndex, endIndex));
         if (replacer != null) {
            result.append(replacer);
         }

         startIndex = endIndex + replacee.length();
      }

      result.append(src.substring(startIndex, src.length()));
      return result.toString();
   }
}
