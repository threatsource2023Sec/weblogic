package weblogic.management.commo;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.management.OperationsException;

public class CommoOperationsException extends OperationsException {
   private Exception exception;

   public CommoOperationsException(Exception e) {
      this.exception = e;
   }

   public CommoOperationsException(Exception e, String message) {
      super(message);
      this.exception = e;
   }

   public Exception getTargetException() {
      return this.exception;
   }

   public void printStackTrace() {
      if (this.exception != null) {
         this.exception.printStackTrace(System.err);
         System.err.println("--------------- nested within: ------------------");
      }

      super.printStackTrace();
   }

   public void printStackTrace(PrintStream s) {
      if (this.exception != null) {
         this.exception.printStackTrace(s);
         s.println("--------------- nested within: ------------------");
      }

      super.printStackTrace(s);
   }

   public void printStackTrace(PrintWriter w) {
      if (this.exception != null) {
         this.exception.printStackTrace(w);
         w.println("--------------- nested within: ------------------");
      }

      super.printStackTrace(w);
   }
}
