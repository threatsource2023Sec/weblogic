package weblogic.nodemanager.grizzly;

import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import weblogic.nodemanager.server.NMServer;

public class ConProbe extends ConnectionProbe.Adapter {
   private static final Logger nmLog;

   public void onAcceptEvent(Connection serverConnection, Connection clientConnection) {
      nmLog.fine("Connection accepted : " + clientConnection.toString());
   }

   public void onCloseEvent(Connection connection) {
      nmLog.fine("Connection closed : " + connection.toString());
   }

   static {
      nmLog = NMServer.nmLog;
   }
}
