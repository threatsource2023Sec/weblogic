package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public abstract class AbstractTlsPeer implements TlsPeer {
   public boolean shouldUseGMTUnixTime() {
      return false;
   }

   public void notifySecureRenegotiation(boolean var1) throws IOException {
      if (!var1) {
         throw new TlsFatalAlert((short)40);
      }
   }

   public void notifyAlertRaised(short var1, short var2, String var3, Throwable var4) {
   }

   public void notifyAlertReceived(short var1, short var2) {
   }

   public void notifyHandshakeComplete() throws IOException {
   }
}
