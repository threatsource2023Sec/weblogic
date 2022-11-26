package org.python.netty.handler.ssl;

import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.alpn.ALPN;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

abstract class JettyAlpnSslEngine extends JdkSslEngine {
   private static final boolean available = initAvailable();

   static boolean isAvailable() {
      return available;
   }

   private static boolean initAvailable() {
      if (PlatformDependent.javaVersion() <= 8) {
         try {
            Class.forName("sun.security.ssl.ALPNExtension", true, (ClassLoader)null);
            return true;
         } catch (Throwable var1) {
         }
      }

      return false;
   }

   static JettyAlpnSslEngine newClientEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
      return new ClientEngine(engine, applicationNegotiator);
   }

   static JettyAlpnSslEngine newServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
      return new ServerEngine(engine, applicationNegotiator);
   }

   private JettyAlpnSslEngine(SSLEngine engine) {
      super(engine);
   }

   // $FF: synthetic method
   JettyAlpnSslEngine(SSLEngine x0, Object x1) {
      this(x0);
   }

   private static final class ServerEngine extends JettyAlpnSslEngine {
      ServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
         super(engine, null);
         ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
         final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector)ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
         ALPN.put(engine, new ALPN.ServerProvider() {
            public String select(List protocols) throws SSLException {
               try {
                  return protocolSelector.select(protocols);
               } catch (Throwable var3) {
                  throw SslUtils.toSSLHandshakeException(var3);
               }
            }

            public void unsupported() {
               protocolSelector.unsupported();
            }
         });
      }

      public void closeInbound() throws SSLException {
         try {
            ALPN.remove(this.getWrappedEngine());
         } finally {
            super.closeInbound();
         }

      }

      public void closeOutbound() {
         try {
            ALPN.remove(this.getWrappedEngine());
         } finally {
            super.closeOutbound();
         }

      }
   }

   private static final class ClientEngine extends JettyAlpnSslEngine {
      ClientEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator) {
         super(engine, null);
         ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
         final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener)ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
         ALPN.put(engine, new ALPN.ClientProvider() {
            public List protocols() {
               return applicationNegotiator.protocols();
            }

            public void selected(String protocol) throws SSLException {
               try {
                  protocolListener.selected(protocol);
               } catch (Throwable var3) {
                  throw SslUtils.toSSLHandshakeException(var3);
               }
            }

            public void unsupported() {
               protocolListener.unsupported();
            }
         });
      }

      public void closeInbound() throws SSLException {
         try {
            ALPN.remove(this.getWrappedEngine());
         } finally {
            super.closeInbound();
         }

      }

      public void closeOutbound() {
         try {
            ALPN.remove(this.getWrappedEngine());
         } finally {
            super.closeOutbound();
         }

      }
   }
}
