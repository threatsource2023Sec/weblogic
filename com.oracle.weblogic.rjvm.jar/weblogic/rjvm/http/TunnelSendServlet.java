package weblogic.rjvm.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class TunnelSendServlet extends HttpServlet {
   private static final boolean DEBUG = false;

   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      HTTPServerJVMConnection connection = Utils.getConnectionFromID(req);
      long reqId = Utils.getRequestIdentifier(req);
      if (connection == null) {
         Utils.sendDeadResponse(rsp);
      } else if (!connection.verifySendRequestIdentifier(reqId)) {
         Utils.sendOKResponse(rsp);
      } else {
         try {
            connection.dispatch(req, rsp);
            connection.updateSendRequestIdentifier(reqId);
         } catch (IOException var9) {
            try {
               Utils.sendDeadResponse(rsp);
            } catch (IOException var8) {
            }

            return;
         }

         Utils.sendOKResponse(rsp);
      }
   }
}
