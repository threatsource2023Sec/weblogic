package org.python.bouncycastle.crypto.tls;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.prng.RandomGenerator;

public interface TlsContext {
   RandomGenerator getNonceRandomGenerator();

   SecureRandom getSecureRandom();

   SecurityParameters getSecurityParameters();

   boolean isServer();

   ProtocolVersion getClientVersion();

   ProtocolVersion getServerVersion();

   TlsSession getResumableSession();

   Object getUserObject();

   void setUserObject(Object var1);

   byte[] exportKeyingMaterial(String var1, byte[] var2, int var3);
}
