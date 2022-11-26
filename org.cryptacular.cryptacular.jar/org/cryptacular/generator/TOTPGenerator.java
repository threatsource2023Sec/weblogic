package org.cryptacular.generator;

import org.bouncycastle.crypto.Digest;
import org.cryptacular.spec.DigestSpec;
import org.cryptacular.spec.Spec;

public class TOTPGenerator extends AbstractOTPGenerator {
   private Spec digestSpecification = new DigestSpec("SHA1");
   private int startTime;
   private int timeStep = 30;

   public Spec getDigestSpecification() {
      return this.digestSpecification;
   }

   public void setDigestSpecification(Spec specification) {
      if (!"SHA1".equalsIgnoreCase(specification.getAlgorithm()) && !"SHA-1".equalsIgnoreCase(specification.getAlgorithm()) && !"SHA256".equalsIgnoreCase(specification.getAlgorithm()) && !"SHA-256".equalsIgnoreCase(specification.getAlgorithm()) && !"SHA512".equalsIgnoreCase(specification.getAlgorithm()) && !"SHA-512".equalsIgnoreCase(specification.getAlgorithm())) {
         throw new IllegalArgumentException("Unsupported digest algorithm " + specification);
      } else {
         this.digestSpecification = specification;
      }
   }

   public int getStartTime() {
      return this.startTime;
   }

   public void setStartTime(int seconds) {
      this.startTime = seconds;
   }

   public int getTimeStep() {
      return this.timeStep;
   }

   public void setTimeStep(int seconds) {
      this.timeStep = seconds;
   }

   public int generate(byte[] key) {
      int unixTime = (int)(System.currentTimeMillis() / 1000L);
      int t = (unixTime - this.startTime) / this.timeStep;
      return this.generateInternal(key, (long)t);
   }

   protected Digest getDigest() {
      return (Digest)this.digestSpecification.newInstance();
   }
}
