package org.python.bouncycastle.crypto.tls;

import java.security.SecureRandom;

class TlsServerContextImpl extends AbstractTlsContext implements TlsServerContext {
   TlsServerContextImpl(SecureRandom var1, SecurityParameters var2) {
      super(var1, var2);
   }

   public boolean isServer() {
      return true;
   }
}
