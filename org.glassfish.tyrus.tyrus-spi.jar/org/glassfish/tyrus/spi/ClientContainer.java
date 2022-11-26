package org.glassfish.tyrus.spi;

import java.io.IOException;
import java.util.Map;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

public interface ClientContainer {
   /** @deprecated */
   String INCOMING_BUFFER_SIZE = "org.glassfish.tyrus.incomingBufferSize";
   String WLS_INCOMING_BUFFER_SIZE = "weblogic.websocket.tyrus.incoming-buffer-size";

   void openClientSocket(ClientEndpointConfig var1, Map var2, ClientEngine var3) throws DeploymentException, IOException;
}
