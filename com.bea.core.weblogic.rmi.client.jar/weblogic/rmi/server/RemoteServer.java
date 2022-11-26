package weblogic.rmi.server;

import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.server.LogStream;
import java.rmi.server.ServerNotActiveException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.Channel;

public abstract class RemoteServer extends RemoteObject {
   protected RemoteServer() {
   }

   public static String getClientHost() throws ServerNotActiveException {
      Channel c = ServerHelper.getClientEndPoint().getRemoteChannel();
      return c == null ? null : c.getInetAddress().toString();
   }

   public static void setLog(OutputStream out) {
   }

   public static PrintStream getLog() {
      return LogStream.getDefaultStream();
   }
}
