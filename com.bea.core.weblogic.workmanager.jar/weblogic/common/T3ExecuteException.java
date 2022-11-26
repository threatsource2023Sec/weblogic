package weblogic.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/** @deprecated */
@Deprecated
public final class T3ExecuteException extends Exception {
   private static final long serialVersionUID = 3951073947730818344L;
   Exception nestedException;
   String nestedExceptionStackTrace;

   /** @deprecated */
   @Deprecated
   public T3ExecuteException() {
   }

   /** @deprecated */
   @Deprecated
   public T3ExecuteException(String message, Exception ne, String nestedStackTrace) {
      super(message);
      this.nestedException = ne;
      this.setNestedExceptionStackTrace(nestedStackTrace);
   }

   /** @deprecated */
   @Deprecated
   public T3ExecuteException(String message, Exception ne) {
      super(message);
      this.nestedException = ne;
      this.captureStackTrace();
   }

   /** @deprecated */
   @Deprecated
   public T3ExecuteException(Exception ne) {
      this.nestedException = ne;
      this.captureStackTrace();
   }

   /** @deprecated */
   @Deprecated
   public Exception getNestedException() {
      return this.nestedException;
   }

   void captureStackTrace() {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos, true);
      this.nestedException.printStackTrace(ps);
      ps.flush();
      ps.close();
      this.setNestedExceptionStackTrace(baos.toString());
   }

   /** @deprecated */
   @Deprecated
   public String getNestedExceptionStackTrace() {
      return this.nestedExceptionStackTrace;
   }

   /** @deprecated */
   @Deprecated
   public void setNestedExceptionStackTrace(String cst) {
      this.nestedExceptionStackTrace = cst;
   }

   /** @deprecated */
   @Deprecated
   public void printStackTrace() {
      System.err.println(this.nestedExceptionStackTrace);
   }

   /** @deprecated */
   @Deprecated
   public void printStackTrace(PrintStream ps) {
      ps.println(this.nestedExceptionStackTrace);
   }

   /** @deprecated */
   @Deprecated
   public String toString() {
      return super.toString() + "\n[Server StackTrace: \n" + this.nestedExceptionStackTrace + "]";
   }
}
