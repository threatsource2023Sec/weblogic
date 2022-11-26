package weblogic.messaging.interception.module;

import java.io.PrintWriter;
import java.rmi.RemoteException;

public final class InterceptionUnDeploymentException extends RemoteException {
   public InterceptionUnDeploymentException(String s) {
      super(s);
   }

   public InterceptionUnDeploymentException(String s, Throwable t) {
      super(s, t);
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      if (this.detail != null) {
         this.detail.printStackTrace(out);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("weblogic.messaging.interception.module.InterceptionUnDeploymentException: ");
      sb.append(this.getMessage());
      return sb.toString();
   }
}
