package weblogic.servlet.proxy;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import weblogic.servlet.FutureResponseServlet;
import weblogic.servlet.FutureServletResponse;
import weblogic.work.WorkManagerFactory;

public class GenericServlet extends FutureResponseServlet {
   public void service(HttpServletRequest request, FutureServletResponse response) {
      try {
         Cookie[] cookies = request.getCookies();
         SocketConnResource resource = new SocketConnResource("localhost", 7001);
         if (cookies != null && cookies.length > 0) {
            WorkManagerFactory.getInstance().getSystem().schedule(new ProxyRequest(resource, request, response, cookies));
         } else {
            WorkManagerFactory.getInstance().getSystem().schedule(new ProxyRequest(resource, request, response));
         }
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public void service(HttpServletRequestWrapper request, HttpServletResponseWrapper response) {
      this.service((HttpServletRequest)request.getRequest(), (FutureServletResponse)response.getResponse());
   }
}
