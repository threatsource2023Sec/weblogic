package org.python.bouncycastle.crypto.prng;

import java.security.SecureRandom;

public class X931SecureRandom extends SecureRandom {
   private final boolean predictionResistant;
   private final SecureRandom randomSource;
   private final X931RNG drbg;

   X931SecureRandom(SecureRandom var1, X931RNG var2, boolean var3) {
      this.randomSource = var1;
      this.drbg = var2;
      this.predictionResistant = var3;
   }

   public void setSeed(byte[] var1) {
      synchronized(this) {
         if (this.randomSource != null) {
            this.randomSource.setSeed(var1);
         }

      }
   }

   public void setSeed(long var1) {
      synchronized(this) {
         if (this.randomSource != null) {
            this.randomSource.setSeed(var1);
         }

      }
   }

   public void nextBytes(byte[] var1) {
      synchronized(this) {
         if (this.drbg.generate(var1, this.predictionResistant) < 0) {
            this.drbg.reseed();
            this.drbg.generate(var1, this.predictionResistant);
         }

      }
   }

   public byte[] generateSeed(int var1) {
      return EntropyUtil.generateSeed(this.drbg.getEntropySource(), var1);
   }
}
