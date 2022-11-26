package org.python.bouncycastle.jce.interfaces;

import javax.crypto.interfaces.DHKey;
import org.python.bouncycastle.jce.spec.ElGamalParameterSpec;

public interface ElGamalKey extends DHKey {
   ElGamalParameterSpec getParameters();
}
