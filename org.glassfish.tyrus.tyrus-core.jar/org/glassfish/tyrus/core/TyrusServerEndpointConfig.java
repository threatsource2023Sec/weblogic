package org.glassfish.tyrus.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.websocket.server.ServerEndpointConfig;

public interface TyrusServerEndpointConfig extends ServerEndpointConfig {
   int getMaxSessions();

   public static final class Builder {
      private String path;
      private Class endpointClass;
      private List subprotocols = Collections.emptyList();
      private List extensions = Collections.emptyList();
      private List encoders = Collections.emptyList();
      private List decoders = Collections.emptyList();
      private ServerEndpointConfig.Configurator serverEndpointConfigurator;
      private int maxSessions = 0;

      public static Builder create(Class endpointClass, String path) {
         return new Builder(endpointClass, path);
      }

      private Builder() {
      }

      public TyrusServerEndpointConfig build() {
         ServerEndpointConfig serverEndpointConfig = javax.websocket.server.ServerEndpointConfig.Builder.create(this.endpointClass, this.path).subprotocols(this.subprotocols).extensions(this.extensions).encoders(this.encoders).decoders(this.decoders).configurator(this.serverEndpointConfigurator).build();
         return new DefaultTyrusServerEndpointConfig(serverEndpointConfig, this.maxSessions);
      }

      private Builder(Class endpointClass, String path) {
         if (endpointClass == null) {
            throw new IllegalArgumentException("endpointClass cannot be null");
         } else {
            this.endpointClass = endpointClass;
            if (path != null && path.startsWith("/")) {
               this.path = path;
            } else {
               throw new IllegalStateException("Path cannot be null and must begin with /");
            }
         }
      }

      public Builder encoders(List encoders) {
         this.encoders = (List)(encoders == null ? new ArrayList() : encoders);
         return this;
      }

      public Builder decoders(List decoders) {
         this.decoders = (List)(decoders == null ? new ArrayList() : decoders);
         return this;
      }

      public Builder subprotocols(List subprotocols) {
         this.subprotocols = (List)(subprotocols == null ? new ArrayList() : subprotocols);
         return this;
      }

      public Builder extensions(List extensions) {
         this.extensions = (List)(extensions == null ? new ArrayList() : extensions);
         return this;
      }

      public Builder configurator(ServerEndpointConfig.Configurator serverEndpointConfigurator) {
         this.serverEndpointConfigurator = serverEndpointConfigurator;
         return this;
      }

      public Builder maxSessions(int maxSessions) {
         this.maxSessions = maxSessions;
         return this;
      }
   }
}
