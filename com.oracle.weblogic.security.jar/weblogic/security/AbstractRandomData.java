package weblogic.security;

import java.security.ProviderException;
import java.security.SecureRandom;

/** @deprecated */
@Deprecated
public abstract class AbstractRandomData {
   private String provider = null;
   private String algorithm = null;
   private int initialSeedSize = 0;
   private int incrementalSeedSize = 0;
   private int seedingIntervalMillis = 0;
   private SecureRandom random = null;
   private long lastSeedTime = 0L;

   private AbstractRandomData() {
   }

   protected AbstractRandomData(String provider, String algorithm, int initialSeedSize, int incrementalSeedSize, int seedingIntervalMillis) {
      this.provider = provider;
      this.algorithm = algorithm;
      this.initialSeedSize = initialSeedSize;
      this.incrementalSeedSize = incrementalSeedSize;
      this.seedingIntervalMillis = seedingIntervalMillis;
   }

   private final synchronized void ensureInittedAndSeeded() {
      int seedSize = this.incrementalSeedSize;
      if (this.random == null) {
         try {
            if (this.algorithm != null && this.provider != null) {
               this.random = SecureRandom.getInstance(this.algorithm, this.provider);
            } else if (this.algorithm != null) {
               this.random = SecureRandom.getInstance(this.algorithm);
            } else {
               this.random = new SecureRandom();
            }
         } catch (Exception var5) {
            this.random = null;
            throw new ProviderException("AbstractRandomData: Unable to instantiate SecureRandom");
         }

         seedSize = this.initialSeedSize;
         this.lastSeedTime = 0L;
      }

      if (seedSize > 0) {
         long currentTime = System.currentTimeMillis();
         if (currentTime >= this.lastSeedTime + (long)this.seedingIntervalMillis) {
            byte[] seed = this.random.generateSeed(seedSize);
            this.random.setSeed(seed);
            this.lastSeedTime = currentTime;
         }
      }

   }

   public final byte[] getRandomBytes(int howMany) {
      byte[] bytes = new byte[howMany];
      this.getRandomBytes(bytes);
      return bytes;
   }

   public final synchronized void getRandomBytes(byte[] bytes) {
      this.ensureInittedAndSeeded();
      this.random.nextBytes(bytes);
   }
}
