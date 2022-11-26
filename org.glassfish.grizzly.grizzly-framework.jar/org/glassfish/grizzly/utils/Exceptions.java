package org.glassfish.grizzly.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class Exceptions {
   public static String getStackTraceAsString(Throwable t) {
      StringWriter stringWriter = new StringWriter(2048);
      PrintWriter pw = new PrintWriter(stringWriter);
      t.printStackTrace(pw);
      pw.close();
      return stringWriter.toString();
   }

   public static IOException makeIOException(Throwable t) {
      return IOException.class.isAssignableFrom(t.getClass()) ? (IOException)t : new IOException(t);
   }

   public static String getAllStackTracesAsString() {
      StringBuilder sb = new StringBuilder(256);
      Map all = Thread.getAllStackTraces();
      Iterator var2 = all.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         sb.append(entry.getKey()).append('\n');
         StackTraceElement[] var4 = (StackTraceElement[])entry.getValue();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            StackTraceElement traceElement = var4[var6];
            sb.append("\tat ").append(traceElement).append('\n');
         }
      }

      return sb.toString();
   }
}
