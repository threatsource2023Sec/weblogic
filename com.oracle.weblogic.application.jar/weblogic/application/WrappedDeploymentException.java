package weblogic.application;

import java.io.PrintStream;
import java.io.PrintWriter;

public class WrappedDeploymentException extends ModuleException {
   public WrappedDeploymentException() {
   }

   public WrappedDeploymentException(String msg) {
      super(msg);
   }

   public WrappedDeploymentException(Throwable th) {
      super(th);
   }

   public WrappedDeploymentException(String msg, Throwable th) {
      super(msg, th);
   }

   public String getMessage() {
      Throwable th = this.getCause();
      return th != null ? th.getMessage() : super.getMessage();
   }

   public StackTraceElement[] getStackTrace() {
      Throwable th = this.getCause();
      return th != null ? th.getStackTrace() : super.getStackTrace();
   }

   public String toString() {
      Throwable th = this.getCause();
      return th != null ? th.toString() : super.toString();
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream p) {
      Throwable th = this.getCause();
      if (th == null) {
         super.printStackTrace(p);
      } else {
         th.printStackTrace(p);
      }

   }

   public void printStackTrace(PrintWriter w) {
      Throwable th = this.getCause();
      if (th == null) {
         super.printStackTrace(w);
      } else {
         th.printStackTrace(w);
      }

   }
}
