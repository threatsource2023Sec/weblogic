package weblogic.cache.webapp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import weblogic.servlet.internal.ServletResponseImpl;

public class WebAppCacheSystem extends CacheSystem {
   private ServletRequestParameterScope srps;
   private ServletRequestAttributeScope sras;
   private ServletRequestCookieScope srcs;
   private ServletSessionAttributeScope ssas;
   private static ServletContextAttributeScope scas = new ServletContextAttributeScope();
   private ServletRequestHeaderScope sqhs;
   private ServletResponseHeaderScope sshs;
   private WebAppFileScope wafs;

   public WebAppCacheSystem() {
      this.registerScope("parameter", this.srps = new ServletRequestParameterScope());
      this.registerScope("request", this.sras = new ServletRequestAttributeScope());
      this.registerScope("cookie", this.srcs = new ServletRequestCookieScope());
      this.registerScope("requestHeader", this.sqhs = new ServletRequestHeaderScope());
      this.registerScope("responseHeader", this.sshs = new ServletResponseHeaderScope());
      this.registerScope("session", this.ssas = new ServletSessionAttributeScope());
      this.registerScope("application", scas);
      this.registerScope("file", this.wafs = new WebAppFileScope());
      this.registerScope("cluster", scas);
   }

   public void setRequest(HttpServletRequest request) {
      this.srps.setRequest(request);
      this.sras.setRequest(request);
      this.srcs.setRequest(request);
      this.sqhs.setRequest(request);
      this.setSession(request.getSession(false));
   }

   public void setResponse(HttpServletResponse response) {
      if (response instanceof ServletResponseImpl) {
         ServletResponseImpl sri = (ServletResponseImpl)response;
         this.sshs.setResponse(sri);
      }

   }

   public void setPageContext(PageContext pageContext) {
      this.setRequest((HttpServletRequest)pageContext.getRequest());
      this.setResponse((HttpServletResponse)pageContext.getResponse());
      this.setContext(pageContext.getServletContext());
   }

   public void setSession(HttpSession session) {
      this.ssas.setSession(session);
   }

   public void setContext(ServletContext servletContext) {
      scas.setContext(servletContext);
      this.wafs.setContext(servletContext);
   }
}
