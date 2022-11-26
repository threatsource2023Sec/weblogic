package javax.websocket.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class DefaultServerEndpointConfig implements ServerEndpointConfig {
   private String path;
   private Class endpointClass;
   private List subprotocols;
   private List extensions;
   private List encoders;
   private List decoders;
   private Map userProperties = new HashMap();
   private ServerEndpointConfig.Configurator serverEndpointConfigurator;

   DefaultServerEndpointConfig(Class endpointClass, String path, List subprotocols, List extensions, List encoders, List decoders, ServerEndpointConfig.Configurator serverEndpointConfigurator) {
      this.path = path;
      this.endpointClass = endpointClass;
      this.subprotocols = Collections.unmodifiableList(subprotocols);
      this.extensions = Collections.unmodifiableList(extensions);
      this.encoders = Collections.unmodifiableList(encoders);
      this.decoders = Collections.unmodifiableList(decoders);
      if (serverEndpointConfigurator == null) {
         this.serverEndpointConfigurator = ServerEndpointConfig.Configurator.fetchContainerDefaultConfigurator();
      } else {
         this.serverEndpointConfigurator = serverEndpointConfigurator;
      }

   }

   public Class getEndpointClass() {
      return this.endpointClass;
   }

   DefaultServerEndpointConfig(Class endpointClass, String path) {
      this.path = path;
      this.endpointClass = endpointClass;
   }

   public List getEncoders() {
      return this.encoders;
   }

   public List getDecoders() {
      return this.decoders;
   }

   public String getPath() {
      return this.path;
   }

   public ServerEndpointConfig.Configurator getConfigurator() {
      return this.serverEndpointConfigurator;
   }

   public final Map getUserProperties() {
      return this.userProperties;
   }

   public final List getSubprotocols() {
      return this.subprotocols;
   }

   public final List getExtensions() {
      return this.extensions;
   }
}
