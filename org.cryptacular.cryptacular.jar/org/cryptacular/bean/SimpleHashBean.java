package org.cryptacular.bean;

import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;
import org.cryptacular.spec.Spec;

public class SimpleHashBean extends AbstractHashBean implements HashBean {
   public SimpleHashBean() {
   }

   public SimpleHashBean(Spec digestSpec, int iterations) {
      super(digestSpec, iterations);
   }

   public byte[] hash(Object... data) throws CryptoException, StreamException {
      return this.hashInternal(data);
   }

   public boolean compare(byte[] hash, Object... data) throws CryptoException, StreamException {
      return this.compareInternal(hash, data);
   }
}
