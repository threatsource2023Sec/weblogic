package org.glassfish.tyrus.core;

import java.util.List;
import java.util.Map;
import javax.websocket.server.ServerEndpointConfig;

final class DefaultTyrusServerEndpointConfig implements TyrusServerEndpointConfig {
   private ServerEndpointConfig config;
   private int maxSessions;

   DefaultTyrusServerEndpointConfig(ServerEndpointConfig config, int maxSessions) {
      this.config = config;
      this.maxSessions = maxSessions;
   }

   public int getMaxSessions() {
      return this.maxSessions;
   }

   public Class getEndpointClass() {
      return this.config.getEndpointClass();
   }

   public List getEncoders() {
      return this.config.getEncoders();
   }

   public List getDecoders() {
      return this.config.getDecoders();
   }

   public String getPath() {
      return this.config.getPath();
   }

   public ServerEndpointConfig.Configurator getConfigurator() {
      return this.config.getConfigurator();
   }

   public final Map getUserProperties() {
      return this.config.getUserProperties();
   }

   public final List getSubprotocols() {
      return this.config.getSubprotocols();
   }

   public final List getExtensions() {
      return this.config.getExtensions();
   }
}
