package javax.websocket.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;

public interface ServerEndpointConfig extends EndpointConfig {
   Class getEndpointClass();

   String getPath();

   List getSubprotocols();

   List getExtensions();

   Configurator getConfigurator();

   public static final class Builder {
      private String path;
      private Class endpointClass;
      private List subprotocols = Collections.emptyList();
      private List extensions = Collections.emptyList();
      private List encoders = Collections.emptyList();
      private List decoders = Collections.emptyList();
      private Configurator serverEndpointConfigurator;

      public static Builder create(Class endpointClass, String path) {
         return new Builder(endpointClass, path);
      }

      private Builder() {
      }

      public ServerEndpointConfig build() {
         return new DefaultServerEndpointConfig(this.endpointClass, this.path, this.subprotocols, this.extensions, this.encoders, this.decoders, this.serverEndpointConfigurator);
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

      public Builder configurator(Configurator serverEndpointConfigurator) {
         this.serverEndpointConfigurator = serverEndpointConfigurator;
         return this;
      }
   }

   public static class Configurator {
      private Configurator containerDefaultConfigurator;

      static Configurator fetchContainerDefaultConfigurator() {
         Iterator var0 = ServiceLoader.load(Configurator.class).iterator();
         if (var0.hasNext()) {
            Configurator impl = (Configurator)var0.next();
            return impl;
         } else {
            throw new RuntimeException("Cannot load platform configurator");
         }
      }

      Configurator getContainerDefaultConfigurator() {
         if (this.containerDefaultConfigurator == null) {
            this.containerDefaultConfigurator = fetchContainerDefaultConfigurator();
         }

         return this.containerDefaultConfigurator;
      }

      public String getNegotiatedSubprotocol(List supported, List requested) {
         return this.getContainerDefaultConfigurator().getNegotiatedSubprotocol(supported, requested);
      }

      public List getNegotiatedExtensions(List installed, List requested) {
         return this.getContainerDefaultConfigurator().getNegotiatedExtensions(installed, requested);
      }

      public boolean checkOrigin(String originHeaderValue) {
         return this.getContainerDefaultConfigurator().checkOrigin(originHeaderValue);
      }

      public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
      }

      public Object getEndpointInstance(Class endpointClass) throws InstantiationException {
         return this.getContainerDefaultConfigurator().getEndpointInstance(endpointClass);
      }
   }
}
