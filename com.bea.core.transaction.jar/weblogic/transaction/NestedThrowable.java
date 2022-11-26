package weblogic.transaction;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public interface NestedThrowable {
   Throwable getNested();

   String superToString();

   void superPrintStackTrace(PrintStream var1);

   void superPrintStackTrace(PrintWriter var1);

   public static class Util {
      public static String toString(NestedThrowable nt) {
         return nt.superToString();
      }

      private static String nestedToString(Throwable nested) {
         if (nested instanceof InvocationTargetException) {
            InvocationTargetException ite = (InvocationTargetException)nested;
            return nested.toString() + " - with target exception:" + System.getProperty("line.separator") + "[" + ite.getTargetException().toString() + "]";
         } else {
            return nested.toString();
         }
      }

      public static void printStackTrace(NestedThrowable nt, PrintStream s) {
         nt.superPrintStackTrace(s);
      }

      public static void printStackTrace(NestedThrowable nt, PrintWriter w) {
         nt.superPrintStackTrace(w);
      }
   }
}
