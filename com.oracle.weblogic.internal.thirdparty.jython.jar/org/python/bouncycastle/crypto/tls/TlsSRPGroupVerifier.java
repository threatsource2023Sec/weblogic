package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.params.SRP6GroupParameters;

public interface TlsSRPGroupVerifier {
   boolean accept(SRP6GroupParameters var1);
}
