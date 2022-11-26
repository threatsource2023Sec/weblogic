package weblogic.cluster.messaging.internal;

import java.io.IOException;
import weblogic.socket.WeblogicSocket;

public interface ConnectionManager {
   Connection createConnection(WeblogicSocket var1) throws IOException;

   Connection createConnection(ServerConfigurationInformation var1) throws IOException;
}
