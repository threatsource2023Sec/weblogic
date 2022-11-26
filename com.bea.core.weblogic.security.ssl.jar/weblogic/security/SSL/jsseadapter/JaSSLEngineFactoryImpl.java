package weblogic.security.SSL.jsseadapter;

import javax.net.ssl.SSLException;
import weblogic.security.SSL.SSLEngineFactory;
import weblogic.security.SSL.WeblogicSSLEngine;
import weblogic.security.utils.SSLContextWrapper;

final class JaSSLEngineFactoryImpl implements SSLEngineFactory {
   private final SSLContextWrapper context;
   private final JaSSLContext jaContext;

   JaSSLEngineFactoryImpl(SSLContextWrapper context) {
      if (null == context) {
         throw new IllegalArgumentException("Non-null SSLContextWrapper expected.");
      } else {
         this.context = context;
         this.jaContext = null;
      }
   }

   JaSSLEngineFactoryImpl(JaSSLContext jaContext) {
      if (null == jaContext) {
         throw new IllegalArgumentException("Non-null JaSSLContext expected.");
      } else {
         this.context = null;
         this.jaContext = jaContext;
      }
   }

   public String[] getDefaultCipherSuites() {
      return null != this.jaContext ? this.jaContext.getDefaultCipherSuites() : this.context.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return null != this.jaContext ? this.jaContext.getSupportedCipherSuites() : this.context.getSupportedCipherSuites();
   }

   public WeblogicSSLEngine createSSLEngine() throws SSLException {
      return null != this.jaContext ? this.jaContext.createSSLEngine() : this.context.createSSLEngine();
   }

   public WeblogicSSLEngine createSSLEngine(String peerHost, int peerPort) throws SSLException {
      return this.createSSLEngine(peerHost, peerPort, true);
   }

   public WeblogicSSLEngine createSSLEngine(String peerHost, int peerPort, boolean useClientMode) throws SSLException {
      return null != this.jaContext ? this.jaContext.createSSLEngine(peerHost, peerPort, useClientMode) : this.context.createSSLEngine(peerHost, peerPort);
   }
}
