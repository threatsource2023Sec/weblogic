package weblogic.diagnostics.instrumentation;

import java.util.HashSet;

public class InstrumentationUtils {
   private InstrumentationUtils() {
   }

   public static String[] unionOf(String[] left, String[] right) {
      HashSet resultTemp = new HashSet();
      String[] var3;
      int var4;
      int var5;
      String rs;
      if (left != null) {
         var3 = left;
         var4 = left.length;

         for(var5 = 0; var5 < var4; ++var5) {
            rs = var3[var5];
            resultTemp.add(rs);
         }
      }

      if (right != null) {
         var3 = right;
         var4 = right.length;

         for(var5 = 0; var5 < var4; ++var5) {
            rs = var3[var5];
            resultTemp.add(rs);
         }
      }

      return resultTemp.size() > 0 ? (String[])resultTemp.toArray(new String[resultTemp.size()]) : null;
   }
}
