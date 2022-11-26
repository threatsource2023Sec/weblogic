package weblogic.servlet.internal;

import java.security.PrivilegedAction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class ForwardAction implements PrivilegedAction {
   private RequestDispatcher rd;
   private ServletRequest req;
   private ServletResponse rsp;

   public ForwardAction(RequestDispatcher rd, ServletRequest req, ServletResponse rsp) {
      this.rd = rd;
      this.req = req;
      this.rsp = rsp;
   }

   public Object run() {
      try {
         this.rd.forward(this.req, this.rsp);
         return null;
      } catch (Throwable var2) {
         return var2;
      }
   }
}
