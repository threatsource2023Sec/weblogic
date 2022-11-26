package weblogic.corba.iiop.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class TunnelSendServlet extends HttpServlet {
   private static final DebugCategory debug = Debug.getCategory("weblogic.iiop.http.tunnelSend");

   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      ServerConnection connection = Utils.getConnectionFromID(req);
      if (connection != null && Utils.requestIntended(req)) {
         try {
            connection.dispatch(req);
         } catch (IOException var7) {
            try {
               Utils.sendDeadResponse(rsp);
            } catch (IOException var6) {
            }

            return;
         }

         Utils.sendOKResponse(rsp);
      } else {
         Utils.sendDeadResponse(rsp);
      }
   }
}
