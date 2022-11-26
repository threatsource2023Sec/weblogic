package monfox.jdom;

import java.io.PrintStream;
import java.io.PrintWriter;

public class JDOMException extends Exception {
   protected Throwable rootCause;

   public JDOMException() {
      super(a("J\t}E\u0011/\u0014lI\u0016}\tjNCf\u0015/`'@6/K\u0013\u007f\u0017fI\u0002{\u0012`DM"));
   }

   public JDOMException(String var1) {
      super(var1);
   }

   public JDOMException(String var1, Throwable var2) {
      super(var1);
      this.rootCause = var2;
   }

   public String getMessage() {
      return this.rootCause != null ? super.getMessage() + a("5[") + this.rootCause.getMessage() : super.getMessage();
   }

   public void printStackTrace() {
      super.printStackTrace();
      if (this.rootCause != null) {
         System.err.print(a("]\u0014`^Cl\u001azY\u00065["));
         this.rootCause.printStackTrace();
      }

   }

   public void printStackTrace(PrintStream var1) {
      super.printStackTrace(var1);
      if (this.rootCause != null) {
         var1.print(a("]\u0014`^Cl\u001azY\u00065["));
         this.rootCause.printStackTrace(var1);
      }

   }

   public void printStackTrace(PrintWriter var1) {
      super.printStackTrace(var1);
      if (this.rootCause != null) {
         var1.print(a("]\u0014`^Cl\u001azY\u00065["));
         this.rootCause.printStackTrace(var1);
      }

   }

   public Throwable getRootCause() {
      return this.rootCause;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 15;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 42;
               break;
            default:
               var10003 = 99;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
