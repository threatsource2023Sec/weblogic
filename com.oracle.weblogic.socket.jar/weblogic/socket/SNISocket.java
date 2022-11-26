package weblogic.socket;

import java.net.Socket;

public class SNISocket extends JSSESocket {
   public SNISocket(Socket connectedSock, JSSEFilterImpl filter) {
      super(connectedSock, filter, false);
   }

   public void initialize() {
      this.bindSSLEngine();
   }
}
