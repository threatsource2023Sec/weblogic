package weblogic.iiop;

import java.io.IOException;
import weblogic.iiop.messages.CloseConnectionMessage;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.utils.net.SocketResetException;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class ConnectionShutdownHandler extends WorkAdapter {
   private final Connection c;
   private final Throwable th;
   private final boolean sendClose;

   public ConnectionShutdownHandler(Connection c, Throwable th) {
      this(c, th, true);
   }

   ConnectionShutdownHandler(Connection c, Throwable th, boolean sendClose) {
      this.c = c;
      this.th = th;
      this.sendClose = sendClose;
      WorkManagerFactory.getInstance().getSystem().schedule(this);
   }

   public void run() {
      if (this.sendClose) {
         this.requestConnectionClose();
      }

      ConnectionManager.handleConnectionShutdown(this.c, this.th);
   }

   private void requestConnectionClose() {
      EndPoint endPoint = this.removeAssociatedEndPoint();
      if (this.isCloseConnectionMessageSupported(endPoint) && !indicatesSocketAlreadyClosed(this.th)) {
         CloseConnectionMessage closeMsg = new CloseConnectionMessage(endPoint.getMinorVersion());

         try {
            CorbaOutputStream os = closeMsg.marshalTo(IiopProtocolFacade.createOutputStream());
            Throwable var4 = null;

            try {
               endPoint.send(os);
            } catch (Throwable var14) {
               var4 = var14;
               throw var14;
            } finally {
               if (os != null) {
                  if (var4 != null) {
                     try {
                        os.close();
                     } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                     }
                  } else {
                     os.close();
                  }
               }

            }
         } catch (Exception var16) {
         }
      }

   }

   private EndPoint removeAssociatedEndPoint() {
      EndPoint endPoint = EndPointManager.removeConnection(this.c);
      if (endPoint != null) {
         endPoint.cleanupPendingResponses(this.th);
      }

      return endPoint;
   }

   private static boolean indicatesSocketAlreadyClosed(Throwable throwable) {
      return throwable instanceof IOException && SocketResetException.isResetException((IOException)throwable);
   }

   private boolean isCloseConnectionMessageSupported(EndPoint endPoint) {
      return endPoint != null && endPoint.getMinorVersion() > 0;
   }
}
