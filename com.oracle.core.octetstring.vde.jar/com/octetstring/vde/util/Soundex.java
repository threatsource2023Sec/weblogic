package com.octetstring.vde.util;

final class Soundex {
   private static final char[] map = "01230120022455012623010202".toCharArray();

   public static String code(String in) {
      char[] out = new char[]{'0', '0', '0', '0'};
      int incount = 1;
      int count = 1;
      out[0] = Character.toUpperCase(in.charAt(0));

      char mapped;
      for(char last = getMappingCode(in.charAt(0)); incount < in.length() && (mapped = getMappingCode(in.charAt(incount++))) != 0 && count < 4; last = mapped) {
         if (mapped != '0' && mapped != last) {
            out[count++] = mapped;
         }
      }

      return new String(out);
   }

   private static char getMappingCode(char c) {
      return !Character.isLetter(c) ? '\u0000' : map[Character.toUpperCase(c) - 65];
   }
}
