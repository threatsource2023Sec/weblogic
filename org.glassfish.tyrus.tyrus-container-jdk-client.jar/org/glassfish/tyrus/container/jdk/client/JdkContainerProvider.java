package org.glassfish.tyrus.container.jdk.client;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import org.glassfish.tyrus.client.ClientManager;

public class JdkContainerProvider extends ContainerProvider {
   protected WebSocketContainer getContainer() {
      return ClientManager.createClient(JdkClientContainer.class.getName());
   }
}
