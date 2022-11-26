package weblogic.xml.xpath;

import antlr.TokenStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class XPathException extends TokenStreamException {
   private Throwable nested;

   public XPathException() {
   }

   public XPathException(String msg) {
      super(msg);
   }

   public XPathException(Throwable nested) {
      this.nested = nested;
   }

   public XPathException(String msg, Throwable nested) {
      super(msg);
      this.nested = nested;
   }

   public Throwable getNestedException() {
      return this.getNested();
   }

   public Throwable getNested() {
      return this.nested;
   }

   public String superToString() {
      return super.toString();
   }

   public void superPrintStackTrace(PrintStream ps) {
      super.printStackTrace(ps);
   }

   public void superPrintStackTrace(PrintWriter pw) {
      super.printStackTrace(pw);
   }

   public String toString() {
      return XPathException.Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      XPathException.Util.printStackTrace(this, s);
   }

   public void printStackTrace(PrintWriter w) {
      XPathException.Util.printStackTrace(this, w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   static class Util {
      static String EOL = System.getProperty("line.separator");

      public static String toString(XPathException nt) {
         Throwable nested = nt.getNested();
         return nested == null ? nt.superToString() : nt.superToString() + " - with nested exception:" + EOL + "[" + nestedToString(nested) + "]";
      }

      private static String nestedToString(Throwable nested) {
         if (nested instanceof InvocationTargetException) {
            InvocationTargetException ite = (InvocationTargetException)nested;
            return nested.toString() + " - with target exception:" + EOL + "[" + ite.getTargetException().toString() + "]";
         } else {
            return nested.toString();
         }
      }

      public static void printStackTrace(XPathException nt, PrintStream s) {
         Throwable nested = nt.getNested();
         if (nested != null) {
            nested.printStackTrace(s);
            s.println("--------------- nested within: ------------------");
         }

         nt.superPrintStackTrace(s);
      }

      public static void printStackTrace(XPathException nt, PrintWriter w) {
         Throwable nested = nt.getNested();
         if (nested != null) {
            nested.printStackTrace(w);
            w.println("--------------- nested within: ------------------");
         }

         nt.superPrintStackTrace(w);
      }
   }
}
