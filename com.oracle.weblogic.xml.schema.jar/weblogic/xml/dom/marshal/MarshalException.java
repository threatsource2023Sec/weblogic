package weblogic.xml.dom.marshal;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MarshalException extends Exception {
   private String message;
   private Throwable cause;

   public MarshalException(String message) {
      super(message);
   }

   public MarshalException(String message, Throwable cause) {
      super(message, cause);
   }

   public MarshalException(Throwable cause) {
      super(cause);
   }

   public Throwable getCause() {
      return super.getCause();
   }

   public void printStackTrace() {
      super.printStackTrace();
   }

   public void printStackTrace(PrintStream s) {
      super.printStackTrace(s);
   }

   public void printStackTrace(PrintWriter s) {
      super.printStackTrace(s);
   }
}
