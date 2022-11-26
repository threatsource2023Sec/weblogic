package com.bea.adaptive.harvester.utils.display;

public class Strings {
   public static String fromArray(String[] array) {
      StringBuffer str = new StringBuffer(64);
      int length = array.length;

      for(int i = 0; i < length; ++i) {
         if (i != 0) {
            if (i == length - 1) {
               str.append(" and ");
            } else {
               str.append(", ");
            }
         }

         str.append(array[i]);
      }

      return str.toString();
   }
}
