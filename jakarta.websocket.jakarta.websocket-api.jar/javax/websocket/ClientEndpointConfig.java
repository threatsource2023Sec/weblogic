package javax.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ClientEndpointConfig extends EndpointConfig {
   List getPreferredSubprotocols();

   List getExtensions();

   Configurator getConfigurator();

   public static final class Builder {
      private List preferredSubprotocols = Collections.emptyList();
      private List extensions = Collections.emptyList();
      private List encoders = Collections.emptyList();
      private List decoders = Collections.emptyList();
      private Configurator clientEndpointConfigurator = new Configurator() {
      };

      private Builder() {
      }

      public static Builder create() {
         return new Builder();
      }

      public ClientEndpointConfig build() {
         return new DefaultClientEndpointConfig(this.preferredSubprotocols, this.extensions, this.encoders, this.decoders, this.clientEndpointConfigurator);
      }

      public Builder configurator(Configurator clientEndpointConfigurator) {
         this.clientEndpointConfigurator = clientEndpointConfigurator;
         return this;
      }

      public Builder preferredSubprotocols(List preferredSubprotocols) {
         this.preferredSubprotocols = (List)(preferredSubprotocols == null ? new ArrayList() : preferredSubprotocols);
         return this;
      }

      public Builder extensions(List extensions) {
         this.extensions = (List)(extensions == null ? new ArrayList() : extensions);
         return this;
      }

      public Builder encoders(List encoders) {
         this.encoders = (List)(encoders == null ? new ArrayList() : encoders);
         return this;
      }

      public Builder decoders(List decoders) {
         this.decoders = (List)(decoders == null ? new ArrayList() : decoders);
         return this;
      }
   }

   public static class Configurator {
      public void beforeRequest(Map headers) {
      }

      public void afterResponse(HandshakeResponse hr) {
      }
   }
}
