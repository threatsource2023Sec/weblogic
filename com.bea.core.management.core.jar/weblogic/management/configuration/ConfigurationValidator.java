package weblogic.management.configuration;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ConfigurationValidator {
   static final char[] invalid_chars = new char[]{':', ',', '=', '*', '?', '%'};
   static final char[] dir_invalid_chars = new char[]{':', ',', '=', '*', '?', '%', '/', '\\'};
   static final char[] strict_invalid_chars = new char[]{':', ',', '=', '*', '?', '%', '/', '\\', '$', '\'', '"', '!', '^', '(', ')', '{', '}'};

   private static void checkIllegalCharacters(String name, char[] sortedIllegalChars) throws IllegalArgumentException {
      char[] var2 = name.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         if (Arrays.binarySearch(sortedIllegalChars, c) >= 0) {
            throw new IllegalArgumentException("Name '" + name + "' contains illegal character '" + c + "'");
         }
      }

   }

   public static void validateName(String name) throws IllegalArgumentException {
      if (name != null && name.length() != 0) {
         checkIllegalCharacters(name, invalid_chars);
      } else {
         throw new IllegalArgumentException("Name may not be null or empty string");
      }
   }

   public static void validateNameUsedInDirectory(String name) throws IllegalArgumentException {
      if (name != null && name.length() != 0) {
         checkIllegalCharacters(name, dir_invalid_chars);
      } else {
         throw new IllegalArgumentException("Name may not be null or empty string");
      }
   }

   public static void validateStrictName(String name) throws IllegalArgumentException {
      if (name != null && name.length() != 0) {
         char[] var1 = name.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char c = var1[var3];
            if (Character.isWhitespace(c)) {
               throw new IllegalArgumentException("Name '" + name + "' contains illegal character '[WHITESPACE]'");
            }

            if (Arrays.binarySearch(strict_invalid_chars, c) >= 0) {
               throw new IllegalArgumentException("Name '" + name + "' contains illegal character '" + c + "'");
            }
         }

      } else {
         throw new IllegalArgumentException("Name may not be null or empty string");
      }
   }

   public static void validateNameInMultiByte(String name) throws IllegalArgumentException {
      validateNameUsedInDirectory(name);
      char[] cArray = name.toCharArray();
      char[] var2 = cArray;
      int var3 = cArray.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         String s = Character.toString(c);

         try {
            byte[] byteArr = s.getBytes("UTF-8");
            if (byteArr.length > 1) {
               throw new IllegalArgumentException("Name '" + name + "' containing multibyte characters is not supported");
            }
         } catch (UnsupportedEncodingException var8) {
         }
      }

   }

   public static void validateClassName(String name) throws IllegalArgumentException {
      if (name != null) {
         if (name.endsWith(".class")) {
            throw new IllegalArgumentException("Invalid class name: " + name + " - Classnames may not end with '.class'");
         }
      }
   }

   static {
      Arrays.sort(invalid_chars);
      Arrays.sort(dir_invalid_chars);
      Arrays.sort(strict_invalid_chars);
   }
}
