package org.python.netty.handler.ssl;

import javax.net.ssl.SSLEngine;

public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
   private static final boolean AVAILABLE = ConscryptAlpnSslEngine.isAvailable() || JettyAlpnSslEngine.isAvailable();
   private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory ALPN_WRAPPER;

   public JdkAlpnApplicationProtocolNegotiator(Iterable protocols) {
      this(false, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(String... protocols) {
      this(false, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable protocols) {
      this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
      this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable protocols) {
      this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
      this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable protocols) {
      super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
   }

   public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
      super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
   }

   static {
      ALPN_WRAPPER = (JdkApplicationProtocolNegotiator.SslEngineWrapperFactory)(AVAILABLE ? new AlpnWrapper() : new FailureWrapper());
   }

   private static final class AlpnWrapper implements JdkApplicationProtocolNegotiator.SslEngineWrapperFactory {
      private AlpnWrapper() {
      }

      public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
         if (ConscryptAlpnSslEngine.isEngineSupported(engine)) {
            return isServer ? ConscryptAlpnSslEngine.newServerEngine(engine, applicationNegotiator) : ConscryptAlpnSslEngine.newClientEngine(engine, applicationNegotiator);
         } else if (JettyAlpnSslEngine.isAvailable()) {
            return isServer ? JettyAlpnSslEngine.newServerEngine(engine, applicationNegotiator) : JettyAlpnSslEngine.newClientEngine(engine, applicationNegotiator);
         } else {
            throw new RuntimeException("Unable to wrap SSLEngine of type " + engine.getClass().getName());
         }
      }

      // $FF: synthetic method
      AlpnWrapper(Object x0) {
         this();
      }
   }

   private static final class FailureWrapper implements JdkApplicationProtocolNegotiator.SslEngineWrapperFactory {
      private FailureWrapper() {
      }

      public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
         throw new RuntimeException("ALPN unsupported. Is your classpath configured correctly? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider. For Jetty-ALPN, see http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
      }

      // $FF: synthetic method
      FailureWrapper(Object x0) {
         this();
      }
   }
}
