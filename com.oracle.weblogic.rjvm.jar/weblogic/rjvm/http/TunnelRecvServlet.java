package weblogic.rjvm.http;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import weblogic.protocol.OutgoingMessage;
import weblogic.servlet.FutureResponseServlet;
import weblogic.servlet.FutureServletResponse;

public final class TunnelRecvServlet extends FutureResponseServlet {
   public void service(HttpServletRequest req, FutureServletResponse rsp) throws IOException {
      boolean pending = false;

      try {
         HTTPServerJVMConnection connection = Utils.getConnectionFromID(req);
         long reqId = Utils.getRequestIdentifier(req);
         if (connection == null) {
            Utils.sendDeadResponse(rsp);
            return;
         }

         if (!connection.verifyReceiveRequestIdentifier(reqId, rsp)) {
            return;
         }

         synchronized(connection) {
            if (!connection.isClosed()) {
               if (connection.getQueueCount() == 0) {
                  connection.registerPending(rsp);
                  pending = true;
                  return;
               }

               OutgoingMessage msg = connection.getNextMessage();
               connection.writeMessage(msg, rsp);
               return;
            }

            Utils.sendDeadResponse(rsp);
         }
      } finally {
         if (!pending) {
            rsp.send();
         }

      }

   }
}
