package org.python.netty.handler.ssl;

import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;

public interface JdkApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
   SslEngineWrapperFactory wrapperFactory();

   ProtocolSelectorFactory protocolSelectorFactory();

   ProtocolSelectionListenerFactory protocolListenerFactory();

   public interface ProtocolSelectionListenerFactory {
      ProtocolSelectionListener newListener(SSLEngine var1, List var2);
   }

   public interface ProtocolSelectorFactory {
      ProtocolSelector newSelector(SSLEngine var1, Set var2);
   }

   public interface ProtocolSelectionListener {
      void unsupported();

      void selected(String var1) throws Exception;
   }

   public interface ProtocolSelector {
      void unsupported();

      String select(List var1) throws Exception;
   }

   public interface SslEngineWrapperFactory {
      SSLEngine wrapSslEngine(SSLEngine var1, JdkApplicationProtocolNegotiator var2, boolean var3);
   }
}
