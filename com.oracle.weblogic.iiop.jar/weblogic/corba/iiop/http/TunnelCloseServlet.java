package weblogic.corba.iiop.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class TunnelCloseServlet extends HttpServlet {
   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      ServerConnection connection = Utils.getConnectionFromID(req);
      if (connection != null && Utils.requestIntended(req)) {
         synchronized(connection) {
            if (connection.isClosed()) {
               Utils.sendDeadResponse(rsp);
               return;
            }

            connection.close();
         }

         Utils.sendOKResponse(rsp);
      } else {
         Utils.sendDeadResponse(rsp);
      }
   }
}
