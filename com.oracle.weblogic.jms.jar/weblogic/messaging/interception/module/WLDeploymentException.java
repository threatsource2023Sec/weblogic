package weblogic.messaging.interception.module;

import java.io.PrintWriter;

public final class WLDeploymentException extends Exception {
   private String msg;
   private Throwable th;

   public WLDeploymentException(String m) {
      super(m);
      this.msg = m;
   }

   public WLDeploymentException(String m, Throwable t) {
      super(m);
      this.msg = m;
      this.th = t;
   }

   public Throwable getEmbeddedThrowable() {
      return this.th;
   }

   public String getErrorMessage() {
      return this.msg;
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      if (this.th != null) {
         this.th.printStackTrace(out);
      }

   }
}
