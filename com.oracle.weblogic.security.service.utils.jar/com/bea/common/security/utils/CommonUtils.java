package com.bea.common.security.utils;

public final class CommonUtils {
   public static final char SINGLE_ESCAPE_CHAR = '\\';
   public static final String SINGLE_ESCAPE_STR = "\\";
   public static final String DOUBLE_ESCAPE_STR = "\\\\";

   public static String convertLDAPPatternForJDO(String pattern, boolean useDoubleEscape) {
      if (pattern != null && !pattern.equals("*")) {
         int length = pattern.length();
         StringBuffer buf = new StringBuffer(length + 5);

         for(int i = 0; i < length; ++i) {
            char c = pattern.charAt(i);
            if (c == '\\') {
               ++i;
               if (i >= length) {
                  throw new IllegalArgumentException("Unexpected escape character in the pattern: " + pattern);
               }

               char escapedChar = pattern.charAt(i);
               if (escapedChar == '\\') {
                  buf.append(c).append(escapedChar);
               } else {
                  if (escapedChar != '*') {
                     throw new IllegalArgumentException("Unexpected character '" + escapedChar + "' escaped in pattern: " + pattern);
                  }

                  buf.append(escapedChar);
               }
            } else if (c == '*') {
               buf.append(".*");
            } else if (c == '.') {
               buf.append(useDoubleEscape ? "\\\\" : "\\").append(c);
            } else {
               buf.append(c);
            }
         }

         return buf.toString();
      } else {
         return ".*";
      }
   }

   public static String convertLDAPPatternForJDO(String pattern) {
      return convertLDAPPatternForJDO(pattern, false);
   }
}
