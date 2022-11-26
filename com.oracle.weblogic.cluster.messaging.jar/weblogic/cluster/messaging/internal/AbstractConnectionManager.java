package weblogic.cluster.messaging.internal;

import java.io.IOException;
import weblogic.socket.WeblogicSocket;

public abstract class AbstractConnectionManager implements ConnectionManager {
   private static final boolean DEBUG;

   private static void debug(String s) {
      Environment.getLogService().debug("[AbstractConnectionManager] " + s);
   }

   public abstract Connection createConnection(WeblogicSocket var1) throws IOException;

   public abstract Connection createConnection(ServerConfigurationInformation var1) throws IOException;

   static {
      DEBUG = Environment.DEBUG;
   }
}
