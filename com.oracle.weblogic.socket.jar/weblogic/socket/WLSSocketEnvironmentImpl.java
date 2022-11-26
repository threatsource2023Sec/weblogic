package weblogic.socket;

import java.net.Socket;
import weblogic.socket.internal.SocketEnvironment;

public class WLSSocketEnvironmentImpl extends SocketEnvironment {
   public boolean serverThrottleEnabled() {
      return ServerThrottle.getServerThrottle().isEnabled();
   }

   public Socket getWeblogicSocket(Socket socket) {
      return new WeblogicSocketImpl(socket);
   }

   public boolean isJSSE() {
      return false;
   }

   public void serverThrottleDecrementOpenSocketCount() {
      ServerThrottle.getServerThrottle().decrementOpenSocketCount();
   }
}
