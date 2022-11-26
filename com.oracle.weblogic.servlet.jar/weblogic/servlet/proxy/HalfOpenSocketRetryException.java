package weblogic.servlet.proxy;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class HalfOpenSocketRetryException extends IOException {
   private IOException nested;

   public HalfOpenSocketRetryException() {
   }

   public HalfOpenSocketRetryException(String msg) {
      super(msg);
   }

   public HalfOpenSocketRetryException(IOException nested) {
      this.nested = nested;
   }

   public String toString() {
      return this.nested != null ? this.nested.toString() : super.toString();
   }

   public void printStackTrace(PrintStream ps) {
      if (this.nested != null) {
         this.nested.printStackTrace(ps);
      } else {
         super.printStackTrace(ps);
      }

   }

   public void printStackTrace(PrintWriter pw) {
      if (this.nested != null) {
         this.nested.printStackTrace(pw);
      } else {
         super.printStackTrace(pw);
      }

   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
