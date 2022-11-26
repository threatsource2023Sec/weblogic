package weblogic.socket.utils;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import weblogic.socket.JSSEFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SocketMuxer;

public final class JSSEUtils {
   public static JSSESocket getJSSESocket(SSLSocket sslSock) {
      return sslSock instanceof JSSESocket ? (JSSESocket)sslSock : null;
   }

   public static void registerJSSEFilter(JSSESocket sslSocket, MuxableSocket muxableSocket) throws IOException {
      JSSEFilterImpl filter = sslSocket.getFilter();
      muxableSocket.setSocketFilter(filter);
      filter.setDelegate(muxableSocket);
      SocketMuxer.getMuxer().register(filter);
   }

   public static void activate(JSSESocket jsseSock, MuxableSocket muxSock) throws IOException {
      try {
         jsseSock.startHandshake();
      } catch (IOException var5) {
         if (!jsseSock.isClosed()) {
            try {
               jsseSock.close();
            } catch (IOException var4) {
            }
         }

         SocketMuxer.getMuxer().deliverHasException(muxSock, var5);
      }

      if (muxSock.isMessageComplete()) {
         muxSock.dispatch();
      } else {
         SocketMuxer.getMuxer().read(jsseSock.getFilter());
      }

   }
}
