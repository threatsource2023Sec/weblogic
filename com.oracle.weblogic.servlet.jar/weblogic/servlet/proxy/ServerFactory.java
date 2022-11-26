package weblogic.servlet.proxy;

import java.io.IOException;
import java.net.Socket;
import weblogic.utils.Debug;

final class ServerFactory {
   private static final boolean DEBUG = true;
   private final int jvmidHash;
   private final int port;
   private final String host;
   private final Socket socket;

   ServerFactory(int jvmidHash) {
      this(jvmidHash, (String)null, -1);
   }

   ServerFactory(int jvmidHash, String host, int port) {
      this.jvmidHash = jvmidHash;
      this.host = host;
      this.port = port;

      try {
         this.socket = new Socket(host, port);
         Debug.say("Created socket to " + host + ":" + port + "\t" + this.socket);
      } catch (IOException var5) {
         var5.printStackTrace();
         throw new AssertionError("Failed to initialize socket" + var5);
      }
   }

   public int hashCode() {
      return this.jvmidHash;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && object instanceof ServerFactory) {
         ServerFactory other = (ServerFactory)object;
         return other.jvmidHash == this.jvmidHash;
      } else {
         return false;
      }
   }

   Socket getConnection() {
      return this.socket;
   }

   void releaseConnection(Socket con) {
   }

   void cleanup() {
      try {
         if (this.socket != null) {
            this.socket.close();
         }
      } catch (IOException var2) {
      }

   }
}
