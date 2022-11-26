package weblogic.socket.utils;

import com.oracle.net.Sdp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class SDPSocketUtils {
   public static Socket createSDPSocket() {
      try {
         return Sdp.openSocketChannel().socket();
      } catch (IOException var2) {
         AssertionError ae = new AssertionError("Failed to create SDP Server Socket");
         ae.initCause(var2);
         throw ae;
      }
   }

   public static ServerSocket createSDPServerSocket() {
      try {
         return Sdp.openServerSocketChannel().socket();
      } catch (IOException var2) {
         AssertionError ae = new AssertionError("Failed to create SDP Server Socket");
         ae.initCause(var2);
         throw ae;
      }
   }

   public static void ensureEnvironment() {
      if (!Boolean.getBoolean("java.net.preferIPv4Stack")) {
         throw new AssertionError("SDP protocol requires system property java.net.preferIPv4Stack to be set to true");
      }
   }
}
