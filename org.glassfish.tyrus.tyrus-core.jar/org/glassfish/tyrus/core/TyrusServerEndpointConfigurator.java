package org.glassfish.tyrus.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.Frame;

public class TyrusServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
   private final ComponentProviderService componentProviderService = ComponentProviderService.create();

   public String getNegotiatedSubprotocol(List supported, List requested) {
      if (requested != null) {
         Iterator var3 = requested.iterator();

         while(var3.hasNext()) {
            String clientProtocol = (String)var3.next();
            if (supported.contains(clientProtocol)) {
               return clientProtocol;
            }
         }
      }

      return "";
   }

   public List getNegotiatedExtensions(List installed, List requested) {
      List installed = new ArrayList(installed);
      List result = new ArrayList();
      if (requested != null) {
         Iterator var4 = requested.iterator();

         label48:
         while(var4.hasNext()) {
            final Extension requestedExtension = (Extension)var4.next();
            Iterator var6 = installed.iterator();

            while(true) {
               Extension extension;
               final String name;
               do {
                  do {
                     if (!var6.hasNext()) {
                        continue label48;
                     }

                     extension = (Extension)var6.next();
                     name = extension.getName();
                  } while(name == null);
               } while(!name.equals(requestedExtension.getName()));

               boolean alreadyAdded = false;
               Iterator var10 = result.iterator();

               while(var10.hasNext()) {
                  Extension e = (Extension)var10.next();
                  if (e.getName().equals(name)) {
                     alreadyAdded = true;
                  }
               }

               if (!alreadyAdded) {
                  if (extension instanceof ExtendedExtension) {
                     final ExtendedExtension extendedExtension = (ExtendedExtension)extension;
                     result.add(new ExtendedExtension() {
                        public Frame processIncoming(ExtendedExtension.ExtensionContext context, Frame frame) {
                           return extendedExtension.processIncoming(context, frame);
                        }

                        public Frame processOutgoing(ExtendedExtension.ExtensionContext context, Frame frame) {
                           return extendedExtension.processOutgoing(context, frame);
                        }

                        public List onExtensionNegotiation(ExtendedExtension.ExtensionContext context, List requestedParameters) {
                           return extendedExtension.onExtensionNegotiation(context, requestedExtension.getParameters());
                        }

                        public void onHandshakeResponse(ExtendedExtension.ExtensionContext context, List responseParameters) {
                           extendedExtension.onHandshakeResponse(context, responseParameters);
                        }

                        public void destroy(ExtendedExtension.ExtensionContext context) {
                           extendedExtension.destroy(context);
                        }

                        public String getName() {
                           return name;
                        }

                        public List getParameters() {
                           return extendedExtension.getParameters();
                        }
                     });
                  } else {
                     result.add(requestedExtension);
                  }
               }
            }
         }
      }

      return result;
   }

   public boolean checkOrigin(String originHeaderValue) {
      return true;
   }

   public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
   }

   public Object getEndpointInstance(Class endpointClass) throws InstantiationException {
      return this.componentProviderService.getEndpointInstance(endpointClass);
   }
}
