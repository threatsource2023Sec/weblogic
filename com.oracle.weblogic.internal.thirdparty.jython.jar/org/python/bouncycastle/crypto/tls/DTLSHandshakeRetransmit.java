package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

interface DTLSHandshakeRetransmit {
   void receivedHandshakeRecord(int var1, byte[] var2, int var3, int var4) throws IOException;
}
