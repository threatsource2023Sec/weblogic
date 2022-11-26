package javax.websocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class DefaultClientEndpointConfig implements ClientEndpointConfig {
   private List preferredSubprotocols;
   private List extensions;
   private List encoders;
   private List decoders;
   private Map userProperties = new HashMap();
   private ClientEndpointConfig.Configurator clientEndpointConfigurator;

   DefaultClientEndpointConfig(List preferredSubprotocols, List extensions, List encoders, List decoders, ClientEndpointConfig.Configurator clientEndpointConfigurator) {
      this.preferredSubprotocols = Collections.unmodifiableList(preferredSubprotocols);
      this.extensions = Collections.unmodifiableList(extensions);
      this.encoders = Collections.unmodifiableList(encoders);
      this.decoders = Collections.unmodifiableList(decoders);
      this.clientEndpointConfigurator = clientEndpointConfigurator;
   }

   public List getPreferredSubprotocols() {
      return this.preferredSubprotocols;
   }

   public List getExtensions() {
      return this.extensions;
   }

   public List getEncoders() {
      return this.encoders;
   }

   public List getDecoders() {
      return this.decoders;
   }

   public final Map getUserProperties() {
      return this.userProperties;
   }

   public ClientEndpointConfig.Configurator getConfigurator() {
      return this.clientEndpointConfigurator;
   }
}
