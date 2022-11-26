package javax.websocket.server;

import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

public interface ServerContainer extends WebSocketContainer {
   void addEndpoint(Class var1) throws DeploymentException;

   void addEndpoint(ServerEndpointConfig var1) throws DeploymentException;
}
