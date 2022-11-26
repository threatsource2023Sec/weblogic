package org.python.netty.handler.ssl;

import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.python.netty.buffer.ByteBufAllocator;

public abstract class OpenSslContext extends ReferenceCountedOpenSslContext {
   OpenSslContext(Iterable ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) throws SSLException {
      super(ciphers, cipherFilter, apnCfg, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, enableOcsp, false);
   }

   OpenSslContext(Iterable ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp) throws SSLException {
      super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, enableOcsp, false);
   }

   final SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
      return new OpenSslEngine(this, alloc, peerHost, peerPort);
   }

   protected final void finalize() throws Throwable {
      super.finalize();
      OpenSsl.releaseIfNeeded(this);
   }
}
