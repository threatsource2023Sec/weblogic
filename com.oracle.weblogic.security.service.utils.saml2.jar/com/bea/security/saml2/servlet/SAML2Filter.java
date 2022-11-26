package com.bea.security.saml2.servlet;

import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.SAML2Service;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SAML2Filter implements Filter {
   private SAML2ServletConfigHelper helper = new SAML2ServletConfigHelper("SAML2Filter");

   public void init(FilterConfig filterConfig) throws ServletException {
      String key = filterConfig.getInitParameter("ServletInfoKey");
      this.helper.setConfigKey(key);
   }

   public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
      if (!(req instanceof HttpServletRequest)) {
         if (filterChain != null) {
            filterChain.doFilter(req, resp);
         }

      } else {
         this.helper.debug("Processing request on URI '" + ((HttpServletRequest)req).getRequestURI() + "'");
         SAML2Service s = this.helper.getSAML2Service();
         if (s == null) {
            ((HttpServletResponse)resp).sendError(500);
         } else {
            if ((this.isUserAuthenticated() || s == null || !s.process((HttpServletRequest)req, (HttpServletResponse)resp)) && filterChain != null) {
               filterChain.doFilter(req, resp);
            }

         }
      }
   }

   protected boolean isUserAuthenticated() {
      IdentityService service = this.helper.getIdentityService();
      if (service != null) {
         Identity identity = service.getCurrentIdentity();
         if (identity != null && !identity.isAnonymous()) {
            return true;
         }
      }

      return false;
   }

   public void destroy() {
      this.helper.setConfigKey((String)null);
   }
}
