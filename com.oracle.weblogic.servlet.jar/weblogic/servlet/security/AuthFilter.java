package weblogic.servlet.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/** @deprecated */
@Deprecated
public abstract class AuthFilter extends HttpServlet {
   /** @deprecated */
   @Deprecated
   public static final String TARGET_URL = "weblogic.formauth.targeturl";

   /** @deprecated */
   @Deprecated
   public final void service(ServletRequest request, ServletResponse response) {
      Integer res = (Integer)request.getAttribute("weblogic.auth.result");
      request.removeAttribute("weblogic.auth.result");
      boolean filterResult = true;
      int loginResult;
      if (res == null) {
         loginResult = 1;
      } else {
         loginResult = res;
      }

      switch (loginResult) {
         case -1:
            this.doPreAuth(request, response);
            break;
         case 0:
            filterResult = this.doSuccessAuth(request, response);
            break;
         case 1:
         case 2:
            this.doFailAuth(request, response);
            break;
         default:
            this.doFailAuth(request, response);
      }

      if (!filterResult) {
         request.setAttribute("weblogic.auth.result", new Integer(1));
      }

   }

   /** @deprecated */
   @Deprecated
   public void doPreAuth(ServletRequest request, ServletResponse response) {
   }

   /** @deprecated */
   @Deprecated
   public boolean doSuccessAuth(ServletRequest request, ServletResponse response) {
      return true;
   }

   /** @deprecated */
   @Deprecated
   public void doFailAuth(ServletRequest request, ServletResponse response) {
   }
}
