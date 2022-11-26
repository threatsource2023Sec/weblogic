package org.glassfish.tyrus.spi;

import java.io.IOException;
import javax.websocket.DeploymentException;

public interface ServerContainer extends javax.websocket.server.ServerContainer {
   void start(String var1, int var2) throws IOException, DeploymentException;

   void stop();

   WebSocketEngine getWebSocketEngine();
}
