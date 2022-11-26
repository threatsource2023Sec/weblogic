package org.python.bouncycastle.crypto.prng;

public interface EntropySource {
   boolean isPredictionResistant();

   byte[] getEntropy();

   int entropySize();
}
