package org.python.bouncycastle.jce.interfaces;

import java.security.PublicKey;
import org.python.bouncycastle.math.ec.ECPoint;

public interface ECPublicKey extends ECKey, PublicKey {
   ECPoint getQ();
}
