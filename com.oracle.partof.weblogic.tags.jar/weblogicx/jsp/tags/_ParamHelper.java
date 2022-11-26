package weblogicx.jsp.tags;

import javax.servlet.ServletRequest;

class _ParamHelper {
   ServletRequest rq;
   XParams p;

   _ParamHelper(ServletRequest rq, XParams p) {
      this.rq = rq;
      this.p = p;
   }

   public String getParameter(String name) {
      return this.rq != null ? this.rq.getParameter(name) : this.p.get(name);
   }
}
