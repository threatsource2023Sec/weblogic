package com.ziclix.python.sql.pipe.csv;

public class CSVString {
   public static final String DELIMITER = ",";

   private CSVString() {
   }

   public static String toCSV(String string) {
      return toCSV(string, ",");
   }

   public static String toCSV(String string, String delimiter) {
      String res = replace(string, "\"", "\"\"");
      if (res.indexOf("\"") >= 0 || string.indexOf(delimiter) >= 0) {
         res = "\"" + res + "\"";
      }

      return res;
   }

   public static String replace(String original, String search, String replace, boolean all) {
      String valReturn = new String("");
      int l = original.length();
      int lo = search.length();
      int i = 0;

      while(i <= l) {
         int j = original.indexOf(search, i);
         if (j == -1) {
            valReturn = valReturn.concat(original.substring(i, l));
            i = l + 1;
         } else {
            valReturn = valReturn.concat(original.substring(i, j));
            valReturn = valReturn.concat(replace);
            i = j + lo;
            if (!all) {
               valReturn = valReturn.concat(original.substring(i, l));
               i = l + 1;
            }
         }
      }

      return valReturn;
   }

   public static String replace(String original, String search, String replace) {
      return replace(original, search, replace, true);
   }

   public static String replaceEndWith(String original, String oldSuffix, String newSuffix) {
      if (original.endsWith(oldSuffix)) {
         String st = original.substring(0, original.length() - oldSuffix.length());
         return st.concat(newSuffix);
      } else {
         return original;
      }
   }
}
