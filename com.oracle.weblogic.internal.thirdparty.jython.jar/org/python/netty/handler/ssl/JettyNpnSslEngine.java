package org.python.netty.handler.ssl;

import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.npn.NextProtoNego;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

final class JettyNpnSslEngine extends JdkSslEngine {
   private static boolean available;

   static boolean isAvailable() {
      updateAvailability();
      return available;
   }

   private static void updateAvailability() {
      if (!available) {
         try {
            Class.forName("sun.security.ssl.NextProtoNegoExtension", true, (ClassLoader)null);
            available = true;
         } catch (Exception var1) {
         }

      }
   }

   JettyNpnSslEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator, boolean server) {
      super(engine);
      ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
      if (server) {
         final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener)ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
         NextProtoNego.put(engine, new NextProtoNego.ServerProvider() {
            public void unsupported() {
               protocolListener.unsupported();
            }

            public List protocols() {
               return applicationNegotiator.protocols();
            }

            public void protocolSelected(String protocol) {
               try {
                  protocolListener.selected(protocol);
               } catch (Throwable var3) {
                  PlatformDependent.throwException(var3);
               }

            }
         });
      } else {
         final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector)ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
         NextProtoNego.put(engine, new NextProtoNego.ClientProvider() {
            public boolean supports() {
               return true;
            }

            public void unsupported() {
               protocolSelector.unsupported();
            }

            public String selectProtocol(List protocols) {
               try {
                  return protocolSelector.select(protocols);
               } catch (Throwable var3) {
                  PlatformDependent.throwException(var3);
                  return null;
               }
            }
         });
      }

   }

   public void closeInbound() throws SSLException {
      NextProtoNego.remove(this.getWrappedEngine());
      super.closeInbound();
   }

   public void closeOutbound() {
      NextProtoNego.remove(this.getWrappedEngine());
      super.closeOutbound();
   }
}
