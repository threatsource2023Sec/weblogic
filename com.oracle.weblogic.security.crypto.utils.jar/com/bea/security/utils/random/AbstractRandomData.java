package com.bea.security.utils.random;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.Security;

public abstract class AbstractRandomData {
   private String provider = null;
   private String algorithm = null;
   private int initialSeedSize = 0;
   private int incrementalSeedSize = 0;
   private int seedingIntervalMillis = 0;
   private SecureRandom random = null;
   private long lastSeedTime = 0L;
   private static EntropyConfig entropyCfg;

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

   public final synchronized int getRandomInt() {
      this.ensureInittedAndSeeded();
      return this.random.nextInt();
   }

   public final synchronized long getRandomLong() {
      this.ensureInittedAndSeeded();
      return this.random.nextLong();
   }

   public final synchronized long getRandomNonNegativeLong() {
      this.ensureInittedAndSeeded();
      return this.random.nextLong() & Long.MAX_VALUE;
   }

   public final synchronized double getRandomDouble() {
      this.ensureInittedAndSeeded();
      return this.random.nextDouble();
   }

   public static String getJavaEntropyConfiguration() {
      return getEntropCfgObj().toString();
   }

   public static boolean isJavaEntropyBlocking() {
      return getEntropCfgObj().isBlocking();
   }

   private static synchronized EntropyConfig getEntropCfgObj() {
      if (entropyCfg != null) {
         return entropyCfg;
      } else {
         entropyCfg = new EntropyConfig();
         return entropyCfg;
      }
   }

   public static class EntropyConfig {
      private static final String RANDONAME_PROP = "securerandom.source";
      private static final String SYS_RANDOM_CFG = "java.security.egd";
      private static final String RANDOM = "file:/dev/random";
      private static final String RANDOM1 = "file:///dev/random";
      private static final String RANDOM2 = "file:/dev/./random";
      private static final String URANDOM = "file:/dev/urandom";
      private static final String URANDOM1 = "file:///dev/urandom";
      private static final String URANDOM2 = "file:/dev/./urandom";
      private static final String LINUX = "Linux";
      private static final String SOLARIS = "Solaris";
      private static final String WINDOWS = "Windows";
      private static final String[] KNOWN_NO_BLOCKING_OS_LIST = new String[]{"Windows"};
      private String sysEntropyConfig = null;
      private String secJavaConfig = null;
      private boolean blockingCfg;
      private boolean blocking;
      private boolean assumedBlockingCfg;
      private String javaVer;
      private String os;

      public EntropyConfig() {
         this.getProperties();
         Boolean bSysBlock = null;
         if (this.sysEntropyConfig != null) {
            if (this.isUrandom(this.sysEntropyConfig)) {
               bSysBlock = Boolean.FALSE;
            } else if (this.isBrandom(this.sysEntropyConfig)) {
               bSysBlock = Boolean.TRUE;
            }
         }

         Boolean bFileBlock = null;
         if (this.secJavaConfig != null) {
            if (this.isUrandom(this.secJavaConfig)) {
               bFileBlock = Boolean.FALSE;
            } else if (this.isBrandom(this.secJavaConfig)) {
               bFileBlock = Boolean.TRUE;
            }
         }

         if (bSysBlock != null && bFileBlock != null) {
            this.blockingCfg = bSysBlock;
         } else if (bSysBlock != null) {
            this.blockingCfg = bSysBlock;
         } else if (bFileBlock != null) {
            this.blockingCfg = bFileBlock;
         } else {
            this.assumedBlockingCfg = true;
            this.blockingCfg = true;
         }

         boolean nonBlockOS = this.isItKnownNonBlockingOS(this.os);
         if (nonBlockOS) {
            this.blocking = false;
         } else {
            this.blocking = this.blockingCfg;
         }

      }

      public boolean isBlockingConfig() {
         return this.blockingCfg;
      }

      public boolean isBlocking() {
         return this.blocking;
      }

      private void getProperties() {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               EntropyConfig.this.sysEntropyConfig = System.getProperty("java.security.egd");
               EntropyConfig.this.secJavaConfig = Security.getProperty("securerandom.source");
               EntropyConfig.this.javaVer = System.getProperty("java.version");
               EntropyConfig.this.os = System.getProperty("os.name");
               return null;
            }
         });
      }

      private boolean isUrandom(String cfg) {
         boolean nameCorrect = cfg.equals("file:/dev/urandom") || cfg.equals("file:///dev/urandom") || cfg.equals("file:/dev/./urandom");
         return nameCorrect;
      }

      private boolean isBrandom(String cfg) {
         boolean nameCorrect = cfg.equals("file:/dev/random") || cfg.equals("file:///dev/random") || cfg.equals("file:/dev/./random");
         return nameCorrect;
      }

      private boolean isItKnownNonBlockingOS(String sOS) {
         boolean rtn = false;
         String[] var3 = KNOWN_NO_BLOCKING_OS_LIST;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (sOS.indexOf(s) != -1) {
               rtn = true;
               break;
            }
         }

         return rtn;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("System property \"java.security.egd= " + this.sysEntropyConfig + "\"");
         buf.append("; JRE's java.security file property \"securerandom.source= " + this.secJavaConfig + "\"");
         if (this.assumedBlockingCfg) {
            buf.append("; Assumed Blocking Config= " + this.blockingCfg);
         } else {
            buf.append("; Blocking Config= " + this.blockingCfg);
         }

         buf.append("; JDK version= " + this.javaVer);
         buf.append("; Operating System= " + this.os);
         return buf.toString();
      }
   }
}
