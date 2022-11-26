package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.Digest;

public interface TlsHandshakeHash extends Digest {
   void init(TlsContext var1);

   TlsHandshakeHash notifyPRFDetermined();

   void trackHashAlgorithm(short var1);

   void sealHashAlgorithms();

   TlsHandshakeHash stopTracking();

   Digest forkPRFHash();

   byte[] getFinalHash(short var1);
}
