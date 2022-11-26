package com.bea.security.saml2.servlet;

import com.bea.common.security.service.SAML2Service;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SAML2Servlet extends HttpServlet {
   private SAML2ServletConfigHelper helper = new SAML2ServletConfigHelper("SAML2Servlet");

   public void init(ServletConfig config) throws ServletException {
      String key = config.getInitParameter("ServletInfoKey");
      this.helper.setConfigKey(key);
   }

   public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      SAML2Service s = this.helper.getSAML2Service();
      this.helper.debug("Processing request on URI '" + req.getRequestURI() + "'");
      if (s != null) {
         if (!s.process(req, resp)) {
            resp.sendError(404);
         }
      } else {
         resp.sendError(500);
      }

   }

   public synchronized void destroy() {
      this.helper.setConfigKey((String)null);
   }
}
