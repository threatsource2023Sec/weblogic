package org.python.netty.handler.ssl;

import javax.net.ssl.SSLEngine;

public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
   private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory NPN_WRAPPER = new JdkApplicationProtocolNegotiator.SslEngineWrapperFactory() {
      {
         if (!JettyNpnSslEngine.isAvailable()) {
            throw new RuntimeException("NPN unsupported. Is your classpath configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
         }
      }

      public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
         return new JettyNpnSslEngine(engine, applicationNegotiator, isServer);
      }
   };

   public JdkNpnApplicationProtocolNegotiator(Iterable protocols) {
      this(false, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(String... protocols) {
      this(false, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable protocols) {
      this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
      this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable protocols) {
      this(clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
      this(clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable protocols) {
      super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
   }

   public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
      super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
   }
}
