package weblogic.transaction;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NestedException extends Exception implements NestedThrowable {
   protected Throwable nested;
   private static final long serialVersionUID = -439506221202060860L;

   public NestedException() {
   }

   public NestedException(String msg) {
      super(msg);
   }

   public NestedException(Throwable nested) {
      super(nested);
      this.nested = nested;
   }

   public NestedException(String msg, Throwable nested) {
      super(msg, nested);
      this.nested = nested;
   }

   public Throwable getNestedException() {
      return this.getNested();
   }

   public String getMessage() {
      String message = super.getMessage();
      if (message == null && this.nested != null) {
         message = this.nested.getMessage();
      }

      return message;
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
      return NestedThrowable.Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      NestedThrowable.Util.printStackTrace(this, (PrintStream)s);
   }

   public void printStackTrace(PrintWriter w) {
      NestedThrowable.Util.printStackTrace(this, (PrintWriter)w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
