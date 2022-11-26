package org.cryptacular.bean;

import org.bouncycastle.crypto.Digest;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.HashUtil;

public abstract class AbstractHashBean {
   private Spec digestSpec;
   private int iterations = 1;

   public AbstractHashBean() {
   }

   public AbstractHashBean(Spec digestSpec, int iterations) {
      this.setDigestSpec(digestSpec);
      this.setIterations(iterations);
   }

   public Spec getDigestSpec() {
      return this.digestSpec;
   }

   public void setDigestSpec(Spec digestSpec) {
      this.digestSpec = digestSpec;
   }

   public int getIterations() {
      return this.iterations;
   }

   public void setIterations(int iterations) {
      if (iterations < 1) {
         throw new IllegalArgumentException("Iterations must be positive");
      } else {
         this.iterations = iterations;
      }
   }

   protected byte[] hashInternal(Object... data) {
      return HashUtil.hash((Digest)this.digestSpec.newInstance(), this.iterations, data);
   }

   protected boolean compareInternal(byte[] hash, Object... data) {
      return HashUtil.compareHash((Digest)this.digestSpec.newInstance(), hash, this.iterations, data);
   }
}
