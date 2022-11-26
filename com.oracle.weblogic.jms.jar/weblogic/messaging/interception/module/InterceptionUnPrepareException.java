package weblogic.messaging.interception.module;

import java.io.PrintWriter;
import java.rmi.RemoteException;

public final class InterceptionUnPrepareException extends RemoteException {
   public InterceptionUnPrepareException(String s) {
      super(s);
   }

   public InterceptionUnPrepareException(String s, Throwable t) {
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
      sb.append("weblogic.messaging.interception.module.InterceptionUnPrepareException: ");
      sb.append(this.getMessage());
      return sb.toString();
   }
}
