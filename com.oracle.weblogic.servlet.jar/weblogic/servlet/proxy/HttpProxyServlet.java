package weblogic.servlet.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public final class HttpProxyServlet extends GenericProxyServlet {
   public void init(ServletConfig sc) throws ServletException {
      super.init(sc);
      String prefixPath = this.getInitParameter("redirectURL");
      if (prefixPath != null) {
         try {
            URL url = new URL(prefixPath);
            this.destHost = url.getHost();
            this.destPort = url.getPort();
            if (this.destPort == -1) {
               this.destPort = 80;
            }
         } catch (MalformedURLException var4) {
            throw new ServletException("Bad redirectURL - " + var4.getMessage());
         }
      }

      if (this.destHost != null && this.destPort != 0) {
         this.httpVersion = this.getInitParameter("HttpVersion");
      } else {
         throw new ServletException("WebLogicHost/WebLogicPort is not defined.");
      }
   }
}
