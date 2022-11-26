package org.glassfish.grizzly.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.StaticHttpHandlerBase;

public class DefaultServlet extends HttpServlet {
   private final StaticHttpHandlerBase staticHttpHandlerBase;

   protected DefaultServlet(StaticHttpHandlerBase staticHttpHandlerBase) {
      this.staticHttpHandlerBase = staticHttpHandlerBase;
   }

   protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Request request = unwrap(req).getRequest();

      try {
         this.staticHttpHandlerBase.service(request, request.getResponse());
      } catch (IOException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new ServletException(var6);
      }
   }

   private static HttpServletRequestImpl unwrap(ServletRequest request) {
      return request instanceof HttpServletRequestImpl ? (HttpServletRequestImpl)request : unwrap(((ServletRequestWrapper)request).getRequest());
   }
}
