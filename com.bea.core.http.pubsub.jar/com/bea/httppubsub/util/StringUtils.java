package com.bea.httppubsub.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class StringUtils {
   private static final String EMPTY_STRING = "";
   private static final String DELIM = ",";

   private StringUtils() {
   }

   public static boolean isEmpty(String string) {
      return string == null || string.length() == 0;
   }

   public static boolean isNotEmpty(String string) {
      return !isEmpty(string);
   }

   public static String arrayToString(Object[] array) {
      return arrayToString((String)null, array);
   }

   public static String arrayToString(String wrapperWith, Object[] array) {
      if (array != null && array.length != 0) {
         String wrapperWithForUse = wrapperWith == null ? "" : wrapperWith;
         StringBuilder sbuf = new StringBuilder();

         for(int i = 0; i < array.length; ++i) {
            sbuf.append(wrapperWithForUse).append(array[i]).append(wrapperWithForUse);
            if (i != array.length - 1) {
               sbuf.append(",");
            }
         }

         return sbuf.toString();
      } else {
         return "";
      }
   }

   public static String defaultString(String string, String defaultString) {
      return string == null ? defaultString : string;
   }

   public static String defaultString(String string) {
      return defaultString(string, "");
   }

   public static String[] splitString(String source) {
      return splitString(source, ",");
   }

   public static String[] splitString(String source, String delim) {
      if (isEmpty(source)) {
         return new String[0];
      } else {
         String delimForUse = isEmpty(delim) ? "," : delim;
         StringTokenizer st = new StringTokenizer(source, delimForUse);
         List result = new ArrayList();

         while(st.hasMoreTokens()) {
            String each = st.nextToken();
            result.add(each);
         }

         return (String[])result.toArray(new String[result.size()]);
      }
   }
}
