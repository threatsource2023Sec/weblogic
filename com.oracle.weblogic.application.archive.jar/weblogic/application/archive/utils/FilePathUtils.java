package weblogic.application.archive.utils;

import java.io.File;

public class FilePathUtils {
   private static String whichOS = System.getProperty("os.name");

   public static String fixFileSeparator(String input) {
      return input.indexOf("/") >= 0 && whichOS.toLowerCase().indexOf("win") >= 0 ? replaceString(input, "/", File.separator) : input;
   }

   public static String fixJarEntryOnWindows(String name) {
      return name.indexOf("\\") >= 0 && whichOS.toLowerCase().indexOf("win") >= 0 ? replaceString(name, "\\", "/") : name;
   }

   public static String replaceString(String original, String oldPattern, String newPattern) {
      StringBuffer result = new StringBuffer();
      int startIdx = 0;

      int index;
      for(int index = false; (index = original.indexOf(oldPattern, startIdx)) >= 0; startIdx = index + oldPattern.length()) {
         result.append(original.substring(startIdx, index));
         result.append(newPattern);
      }

      result.append(original.substring(startIdx));
      return result.toString();
   }
}
