package com.oracle.weblogic.diagnostics.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class ActionUtils {
   public static String buildWatchDataString(Map watchData) {
      if (watchData == null) {
         return null;
      } else {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         Iterator var3 = watchData.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            printDataElement(pw, (String)entry.getKey(), entry.getValue());
         }

         pw.close();
         return sw.toString();
      }
   }

   private static void printDataElement(PrintWriter pw, String key, Object value) {
      pw.print(key);
      pw.print(" = ");
      pw.print(value);
      pw.println();
   }
}
