package org.python.netty.handler.ssl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;
import org.python.netty.util.internal.ObjectUtil;

class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
   private final List protocols;
   private final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory;
   private final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory;
   private final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory;
   static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() {
      public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set supportedProtocols) {
         return new FailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
      }
   };
   static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() {
      public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine engine, Set supportedProtocols) {
         return new NoFailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
      }
   };
   static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() {
      public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List supportedProtocols) {
         return new FailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
      }
   };
   static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() {
      public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine engine, List supportedProtocols) {
         return new NoFailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
      }
   };

   JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, Iterable protocols) {
      this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
   }

   JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
      this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
   }

   private JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory selectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory listenerFactory, List protocols) {
      this.wrapperFactory = (JdkApplicationProtocolNegotiator.SslEngineWrapperFactory)ObjectUtil.checkNotNull(wrapperFactory, "wrapperFactory");
      this.selectorFactory = (JdkApplicationProtocolNegotiator.ProtocolSelectorFactory)ObjectUtil.checkNotNull(selectorFactory, "selectorFactory");
      this.listenerFactory = (JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory)ObjectUtil.checkNotNull(listenerFactory, "listenerFactory");
      this.protocols = Collections.unmodifiableList((List)ObjectUtil.checkNotNull(protocols, "protocols"));
   }

   public List protocols() {
      return this.protocols;
   }

   public JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
      return this.selectorFactory;
   }

   public JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
      return this.listenerFactory;
   }

   public JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
      return this.wrapperFactory;
   }

   private static final class FailProtocolSelectionListener extends NoFailProtocolSelectionListener {
      FailProtocolSelectionListener(JdkSslEngine engineWrapper, List supportedProtocols) {
         super(engineWrapper, supportedProtocols);
      }

      protected void noSelectedMatchFound(String protocol) throws Exception {
         throw new SSLHandshakeException("No compatible protocols found");
      }
   }

   private static class NoFailProtocolSelectionListener implements JdkApplicationProtocolNegotiator.ProtocolSelectionListener {
      private final JdkSslEngine engineWrapper;
      private final List supportedProtocols;

      NoFailProtocolSelectionListener(JdkSslEngine engineWrapper, List supportedProtocols) {
         this.engineWrapper = engineWrapper;
         this.supportedProtocols = supportedProtocols;
      }

      public void unsupported() {
         this.engineWrapper.getSession().setApplicationProtocol((String)null);
      }

      public void selected(String protocol) throws Exception {
         if (this.supportedProtocols.contains(protocol)) {
            this.engineWrapper.getSession().setApplicationProtocol(protocol);
         } else {
            this.noSelectedMatchFound(protocol);
         }

      }

      protected void noSelectedMatchFound(String protocol) throws Exception {
      }
   }

   private static final class FailProtocolSelector extends NoFailProtocolSelector {
      FailProtocolSelector(JdkSslEngine engineWrapper, Set supportedProtocols) {
         super(engineWrapper, supportedProtocols);
      }

      public String noSelectMatchFound() throws Exception {
         throw new SSLHandshakeException("Selected protocol is not supported");
      }
   }

   static class NoFailProtocolSelector implements JdkApplicationProtocolNegotiator.ProtocolSelector {
      private final JdkSslEngine engineWrapper;
      private final Set supportedProtocols;

      NoFailProtocolSelector(JdkSslEngine engineWrapper, Set supportedProtocols) {
         this.engineWrapper = engineWrapper;
         this.supportedProtocols = supportedProtocols;
      }

      public void unsupported() {
         this.engineWrapper.getSession().setApplicationProtocol((String)null);
      }

      public String select(List protocols) throws Exception {
         Iterator var2 = this.supportedProtocols.iterator();

         String p;
         do {
            if (!var2.hasNext()) {
               return this.noSelectMatchFound();
            }

            p = (String)var2.next();
         } while(!protocols.contains(p));

         this.engineWrapper.getSession().setApplicationProtocol(p);
         return p;
      }

      public String noSelectMatchFound() throws Exception {
         this.engineWrapper.getSession().setApplicationProtocol((String)null);
         return null;
      }
   }
}
