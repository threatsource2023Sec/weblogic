package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public interface TlsPeer {
   boolean shouldUseGMTUnixTime();

   void notifySecureRenegotiation(boolean var1) throws IOException;

   TlsCompression getCompression() throws IOException;

   TlsCipher getCipher() throws IOException;

   void notifyAlertRaised(short var1, short var2, String var3, Throwable var4);

   void notifyAlertReceived(short var1, short var2);

   void notifyHandshakeComplete() throws IOException;
}
