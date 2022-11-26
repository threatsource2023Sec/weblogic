package weblogic.corba.iiop.http;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.servlet.FutureResponseServlet;
import weblogic.servlet.FutureServletResponse;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class TunnelRecvServlet extends FutureResponseServlet {
   private static final DebugCategory debug = Debug.getCategory("weblogic.iiop.http.tunnelRecv");

   public void service(HttpServletRequest req, FutureServletResponse rsp) throws IOException {
      boolean pending = false;

      try {
         ServerConnection connection = Utils.getConnectionFromID(req);
         Enumeration msg;
         if (debug.isEnabled()) {
            msg = req.getHeaderNames();

            while(msg.hasMoreElements()) {
               System.out.println("<TunnelRecv>: " + req.getHeader((String)msg.nextElement()));
            }
         }

         if (connection == null || !Utils.requestIntended(req)) {
            Utils.sendDeadResponse(rsp);
         } else {
            msg = null;
            AsyncOutgoingMessage msg;
            synchronized(connection) {
               if (connection.isClosed()) {
                  Utils.sendDeadResponse(rsp);
                  return;
               }

               if (connection.getQueueCount() == 0) {
                  connection.registerPending(req, rsp);
                  pending = true;
                  return;
               }

               msg = connection.getNextMessage();
            }

            Utils.sendResponse(rsp, msg);
         }
      } finally {
         if (!pending) {
            rsp.send();
         }

      }
   }
}
