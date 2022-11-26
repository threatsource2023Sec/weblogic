package org.glassfish.tyrus.spi;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfig;

public interface WebSocketEngine {
   UpgradeInfo upgrade(UpgradeRequest var1, UpgradeResponse var2);

   void register(Class var1, String var2) throws DeploymentException;

   void register(ServerEndpointConfig var1, String var2) throws DeploymentException;

   public static enum UpgradeStatus {
      NOT_APPLICABLE,
      HANDSHAKE_FAILED,
      SUCCESS;
   }

   public interface UpgradeInfo {
      UpgradeStatus getStatus();

      Connection createConnection(Writer var1, Connection.CloseListener var2);
   }
}
