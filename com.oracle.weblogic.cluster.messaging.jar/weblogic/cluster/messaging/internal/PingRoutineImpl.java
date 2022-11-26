package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class PingRoutineImpl implements PingRoutine {
   protected static final boolean DEBUG;

   public static PingRoutineImpl getInstance() {
      return PingRoutineImpl.Factory.THE_ONE;
   }

   public long ping(ServerConfigurationInformation info) {
      Socket socket = new Socket();

      long var4;
      try {
         info.refreshAddress();
         SocketAddress address = new InetSocketAddress(info.getAddress(), info.getPort());
         socket.connect(address, 3000);
         if (DEBUG) {
            this.debug("Successfully pinged " + info.getServerName());
         }

         var4 = 1L;
         return var4;
      } catch (IOException var15) {
         var4 = 0L;
      } finally {
         try {
            socket.close();
         } catch (IOException var14) {
         }

      }

      return var4;
   }

   protected void debug(String s) {
      Environment.getLogService().debug("[Group - PingRoutine] " + s);
   }

   static {
      DEBUG = Environment.DEBUG;
   }

   private static final class Factory {
      static final PingRoutineImpl THE_ONE = new PingRoutineImpl();
   }
}
