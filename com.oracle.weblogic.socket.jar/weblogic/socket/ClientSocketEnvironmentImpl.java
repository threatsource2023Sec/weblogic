package weblogic.socket;

import java.net.Socket;
import weblogic.socket.internal.SocketEnvironment;

public class ClientSocketEnvironmentImpl extends SocketEnvironment {
   public boolean serverThrottleEnabled() {
      return false;
   }

   public Socket getWeblogicSocket(Socket socket) {
      return null;
   }

   public boolean isJSSE() {
      return true;
   }

   public void serverThrottleDecrementOpenSocketCount() {
   }
}
