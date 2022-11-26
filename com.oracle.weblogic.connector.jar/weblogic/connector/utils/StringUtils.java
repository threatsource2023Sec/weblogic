package weblogic.connector.utils;

public class StringUtils {
   private StringUtils() {
   }

   public static boolean notEmpty(String s) {
      return s != null && s.length() != 0;
   }
}
