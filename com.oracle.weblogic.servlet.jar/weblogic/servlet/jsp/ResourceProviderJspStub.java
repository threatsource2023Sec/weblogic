package weblogic.servlet.jsp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import oracle.jsp.provider.JspResourceProvider;
import weblogic.servlet.JSPServlet;
import weblogic.servlet.internal.FilterChainImpl;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppConfigManager;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.utils.ServletMapping;
import weblogic.servlet.utils.URLMapping;

public class ResourceProviderJspStub extends ServletStubImpl {
   private String uri;
   private JspConfig jspConfig;
   private JspResourceProvider jspResourceProvider;
   private URLMapping servletMapping = new ServletMapping(WebAppConfigManager.isCaseInsensitive(), WebAppSecurity.getProvider().getEnforceStrictURLPattern());

   public ResourceProviderJspStub(String uri, WebAppServletContext sci, JspConfig jspConfig) {
      super(uri, "weblogic.servlet.jsp.ResourceProviderJspStub", sci);
      this.uri = uri;
      this.jspConfig = jspConfig;
      this.jspResourceProvider = sci.getJspResourceProvider();
   }

   public void execute(ServletRequest req, ServletResponse rsp, FilterChainImpl chain) throws ServletException, IOException {
      String providerUri = this.jspResourceProvider.getProviderURI(this.uri);
      WebAppServletContext sci = (WebAppServletContext)req.getServletContext();
      if (providerUri != null && sci.getResourceAsSourceWithMDS(this.uri) != null) {
         ServletStubImpl sstub;
         synchronized(this.servletMapping) {
            sstub = (ServletStubImpl)this.servletMapping.get(providerUri);
         }

         if (sstub != null) {
            sstub.execute(req, rsp, chain);
         } else {
            ServletStubImpl sstub = new ResourceProviderJavelinxJspStub(providerUri, JSPServlet.uri2classname(this.jspConfig.getPackagePrefix(), providerUri), this.getContext(), this.jspConfig, this.uri);
            synchronized(this.servletMapping) {
               this.servletMapping.put(providerUri, sstub);
            }

            sstub.execute(req, rsp, chain);
         }
      } else {
         ((HttpServletResponse)rsp).sendError(404);
      }
   }
}
