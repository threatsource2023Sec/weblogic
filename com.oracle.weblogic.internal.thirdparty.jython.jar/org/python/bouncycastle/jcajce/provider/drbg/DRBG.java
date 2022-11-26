package org.python.bouncycastle.jcajce.provider.drbg;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.python.bouncycastle.crypto.digests.SHA512Digest;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.prng.EntropySource;
import org.python.bouncycastle.crypto.prng.EntropySourceProvider;
import org.python.bouncycastle.crypto.prng.SP800SecureRandom;
import org.python.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Pack;
import org.python.bouncycastle.util.Strings;

public class DRBG {
   private static final String PREFIX = DRBG.class.getName();
   private static final String[][] initialEntropySourceNames = new String[][]{{"sun.security.provider.Sun", "sun.security.provider.SecureRandom"}, {"org.python.apache.harmony.security.provider.crypto.CryptoProvider", "org.python.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl"}, {"com.android.org.conscrypt.OpenSSLProvider", "com.android.org.conscrypt.OpenSSLRandom"}, {"org.conscrypt.OpenSSLProvider", "org.conscrypt.OpenSSLRandom"}};
   private static final Object[] initialEntropySourceAndSpi = findSource();

   private static final Object[] findSource() {
      int var0 = 0;

      while(var0 < initialEntropySourceNames.length) {
         String[] var1 = initialEntropySourceNames[var0];

         try {
            Object[] var2 = new Object[]{Class.forName(var1[0]).newInstance(), Class.forName(var1[1]).newInstance()};
            return var2;
         } catch (Throwable var3) {
            ++var0;
         }
      }

      return null;
   }

   private static SecureRandom createInitialEntropySource() {
      return (SecureRandom)(initialEntropySourceAndSpi != null ? new CoreSecureRandom() : new SecureRandom());
   }

   private static EntropySourceProvider createEntropySource() {
      final String var0 = System.getProperty("org.python.bouncycastle.drbg.entropysource");
      return (EntropySourceProvider)AccessController.doPrivileged(new PrivilegedAction() {
         public EntropySourceProvider run() {
            try {
               Class var1 = DRBG.class.getClassLoader().loadClass(var0);
               return (EntropySourceProvider)var1.newInstance();
            } catch (Exception var2) {
               throw new IllegalStateException("entropy source " + var0 + " not created: " + var2.getMessage(), var2);
            }
         }
      });
   }

   private static SecureRandom createBaseRandom(boolean var0) {
      if (System.getProperty("org.python.bouncycastle.drbg.entropysource") != null) {
         EntropySourceProvider var4 = createEntropySource();
         EntropySource var5 = var4.get(128);
         byte[] var3 = var0 ? generateDefaultPersonalizationString(var5.getEntropy()) : generateNonceIVPersonalizationString(var5.getEntropy());
         return (new SP800SecureRandomBuilder(var4)).setPersonalizationString(var3).buildHash(new SHA512Digest(), Arrays.concatenate(var5.getEntropy(), var5.getEntropy()), var0);
      } else {
         HybridSecureRandom var1 = new HybridSecureRandom();
         byte[] var2 = var0 ? generateDefaultPersonalizationString(var1.generateSeed(16)) : generateNonceIVPersonalizationString(var1.generateSeed(16));
         return (new SP800SecureRandomBuilder(var1, true)).setPersonalizationString(var2).buildHash(new SHA512Digest(), var1.generateSeed(32), var0);
      }
   }

   private static byte[] generateDefaultPersonalizationString(byte[] var0) {
      return Arrays.concatenate(Strings.toByteArray("Default"), var0, Pack.longToBigEndian(Thread.currentThread().getId()), Pack.longToBigEndian(System.currentTimeMillis()));
   }

   private static byte[] generateNonceIVPersonalizationString(byte[] var0) {
      return Arrays.concatenate(Strings.toByteArray("Nonce"), var0, Pack.longToLittleEndian(Thread.currentThread().getId()), Pack.longToLittleEndian(System.currentTimeMillis()));
   }

   private static class CoreSecureRandom extends SecureRandom {
      CoreSecureRandom() {
         super((SecureRandomSpi)DRBG.initialEntropySourceAndSpi[1], (Provider)DRBG.initialEntropySourceAndSpi[0]);
      }
   }

   public static class Default extends SecureRandomSpi {
      private static final SecureRandom random = DRBG.createBaseRandom(true);

      protected void engineSetSeed(byte[] var1) {
         random.setSeed(var1);
      }

      protected void engineNextBytes(byte[] var1) {
         random.nextBytes(var1);
      }

      protected byte[] engineGenerateSeed(int var1) {
         return random.generateSeed(var1);
      }
   }

   private static class HybridSecureRandom extends SecureRandom {
      private final AtomicBoolean seedAvailable = new AtomicBoolean(false);
      private final AtomicInteger samples = new AtomicInteger(0);
      private final SecureRandom baseRandom = DRBG.createInitialEntropySource();
      private final SP800SecureRandom drbg;

      HybridSecureRandom() {
         this.drbg = (new SP800SecureRandomBuilder(new EntropySourceProvider() {
            public EntropySource get(int var1) {
               return HybridSecureRandom.this.new SignallingEntropySource(var1);
            }
         })).setPersonalizationString(Strings.toByteArray("Bouncy Castle Hybrid Entropy Source")).buildHMAC(new HMac(new SHA512Digest()), this.baseRandom.generateSeed(32), false);
      }

      public byte[] generateSeed(int var1) {
         byte[] var2 = new byte[var1];
         if (this.samples.getAndIncrement() > 20 && this.seedAvailable.getAndSet(false)) {
            this.samples.set(0);
            this.drbg.reseed((byte[])null);
         }

         this.drbg.nextBytes(var2);
         return var2;
      }

      private class SignallingEntropySource implements EntropySource {
         private final int byteLength;
         private final AtomicReference entropy = new AtomicReference();
         private final AtomicBoolean scheduled = new AtomicBoolean(false);

         SignallingEntropySource(int var2) {
            this.byteLength = (var2 + 7) / 8;
         }

         public boolean isPredictionResistant() {
            return true;
         }

         public byte[] getEntropy() {
            byte[] var1 = (byte[])((byte[])this.entropy.getAndSet((Object)null));
            if (var1 != null && var1.length == this.byteLength) {
               this.scheduled.set(false);
            } else {
               var1 = HybridSecureRandom.this.baseRandom.generateSeed(this.byteLength);
            }

            if (!this.scheduled.getAndSet(true)) {
               (new Thread(new EntropyGatherer(this.byteLength))).start();
            }

            return var1;
         }

         public int entropySize() {
            return this.byteLength * 8;
         }

         private class EntropyGatherer implements Runnable {
            private final int numBytes;

            EntropyGatherer(int var2) {
               this.numBytes = var2;
            }

            public void run() {
               SignallingEntropySource.this.entropy.set(HybridSecureRandom.this.baseRandom.generateSeed(this.numBytes));
               HybridSecureRandom.this.seedAvailable.set(true);
            }
         }
      }
   }

   public static class Mappings extends AsymmetricAlgorithmProvider {
      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("SecureRandom.DEFAULT", DRBG.PREFIX + "$Default");
         var1.addAlgorithm("SecureRandom.NONCEANDIV", DRBG.PREFIX + "$NonceAndIV");
      }
   }

   public static class NonceAndIV extends SecureRandomSpi {
      private static final SecureRandom random = DRBG.createBaseRandom(false);

      protected void engineSetSeed(byte[] var1) {
         random.setSeed(var1);
      }

      protected void engineNextBytes(byte[] var1) {
         random.nextBytes(var1);
      }

      protected byte[] engineGenerateSeed(int var1) {
         return random.generateSeed(var1);
      }
   }
}
