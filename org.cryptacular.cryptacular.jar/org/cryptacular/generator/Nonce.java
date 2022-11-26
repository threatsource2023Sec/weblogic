package org.cryptacular.generator;

public interface Nonce {
   byte[] generate() throws LimitException;

   int getLength();
}
