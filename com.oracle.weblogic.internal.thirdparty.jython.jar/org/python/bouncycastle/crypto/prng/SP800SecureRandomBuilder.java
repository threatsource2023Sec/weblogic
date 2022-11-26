package org.python.bouncycastle.crypto.prng;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.prng.drbg.CTRSP800DRBG;
import org.python.bouncycastle.crypto.prng.drbg.HMacSP800DRBG;
import org.python.bouncycastle.crypto.prng.drbg.HashSP800DRBG;
import org.python.bouncycastle.crypto.prng.drbg.SP80090DRBG;

public class SP800SecureRandomBuilder {
   private final SecureRandom random;
   private final EntropySourceProvider entropySourceProvider;
   private byte[] personalizationString;
   private int securityStrength;
   private int entropyBitsRequired;

   public SP800SecureRandomBuilder() {
      this(new SecureRandom(), false);
   }

   public SP800SecureRandomBuilder(SecureRandom var1, boolean var2) {
      this.securityStrength = 256;
      this.entropyBitsRequired = 256;
      this.random = var1;
      this.entropySourceProvider = new BasicEntropySourceProvider(this.random, var2);
   }

   public SP800SecureRandomBuilder(EntropySourceProvider var1) {
      this.securityStrength = 256;
      this.entropyBitsRequired = 256;
      this.random = null;
      this.entropySourceProvider = var1;
   }

   public SP800SecureRandomBuilder setPersonalizationString(byte[] var1) {
      this.personalizationString = var1;
      return this;
   }

   public SP800SecureRandomBuilder setSecurityStrength(int var1) {
      this.securityStrength = var1;
      return this;
   }

   public SP800SecureRandomBuilder setEntropyBitsRequired(int var1) {
      this.entropyBitsRequired = var1;
      return this;
   }

   public SP800SecureRandom buildHash(Digest var1, byte[] var2, boolean var3) {
      return new SP800SecureRandom(this.random, this.entropySourceProvider.get(this.entropyBitsRequired), new HashDRBGProvider(var1, var2, this.personalizationString, this.securityStrength), var3);
   }

   public SP800SecureRandom buildCTR(BlockCipher var1, int var2, byte[] var3, boolean var4) {
      return new SP800SecureRandom(this.random, this.entropySourceProvider.get(this.entropyBitsRequired), new CTRDRBGProvider(var1, var2, var3, this.personalizationString, this.securityStrength), var4);
   }

   public SP800SecureRandom buildHMAC(Mac var1, byte[] var2, boolean var3) {
      return new SP800SecureRandom(this.random, this.entropySourceProvider.get(this.entropyBitsRequired), new HMacDRBGProvider(var1, var2, this.personalizationString, this.securityStrength), var3);
   }

   private static class CTRDRBGProvider implements DRBGProvider {
      private final BlockCipher blockCipher;
      private final int keySizeInBits;
      private final byte[] nonce;
      private final byte[] personalizationString;
      private final int securityStrength;

      public CTRDRBGProvider(BlockCipher var1, int var2, byte[] var3, byte[] var4, int var5) {
         this.blockCipher = var1;
         this.keySizeInBits = var2;
         this.nonce = var3;
         this.personalizationString = var4;
         this.securityStrength = var5;
      }

      public SP80090DRBG get(EntropySource var1) {
         return new CTRSP800DRBG(this.blockCipher, this.keySizeInBits, this.securityStrength, var1, this.personalizationString, this.nonce);
      }
   }

   private static class HMacDRBGProvider implements DRBGProvider {
      private final Mac hMac;
      private final byte[] nonce;
      private final byte[] personalizationString;
      private final int securityStrength;

      public HMacDRBGProvider(Mac var1, byte[] var2, byte[] var3, int var4) {
         this.hMac = var1;
         this.nonce = var2;
         this.personalizationString = var3;
         this.securityStrength = var4;
      }

      public SP80090DRBG get(EntropySource var1) {
         return new HMacSP800DRBG(this.hMac, this.securityStrength, var1, this.personalizationString, this.nonce);
      }
   }

   private static class HashDRBGProvider implements DRBGProvider {
      private final Digest digest;
      private final byte[] nonce;
      private final byte[] personalizationString;
      private final int securityStrength;

      public HashDRBGProvider(Digest var1, byte[] var2, byte[] var3, int var4) {
         this.digest = var1;
         this.nonce = var2;
         this.personalizationString = var3;
         this.securityStrength = var4;
      }

      public SP80090DRBG get(EntropySource var1) {
         return new HashSP800DRBG(this.digest, this.securityStrength, var1, this.personalizationString, this.nonce);
      }
   }
}
