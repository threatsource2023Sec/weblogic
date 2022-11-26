package weblogic.security.internal.encryption;

import com.rsa.jsafe.CryptoJ;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import weblogic.diagnostics.debug.DebugLogger;

public final class JSafeEncryptionServiceImpl implements EncryptionServiceV2 {
   private static final boolean USE_AES256_KEY = true;
   static final String ENABLE_AES256_PROPERTY = "weblogic.security.internal.encryption.enableAES256";
   static final String OVERALL_ALGORITHM = "3DES";
   static final String ALGORITHM_3DES = "3DES_EDE/CBC/PKCS5Padding";
   static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
   static final String ALGORITHM_AES256 = "AES256/CBC/PKCS5Padding";
   static final String ALGORITHM_RANDOM = "HMACDRBG";
   static final String ALGORITHM_RANDOM_ALTERNATIVE = "HMACDRBG-256-0";
   private static String algorithm_used = "HMACDRBG";
   private static final String PREFIX_3DES = "{3DES}";
   private static final int RANDOM_LEN_3DES = 0;
   private static final int KEY_LEN_3DES = 168;
   static final String PREFIX_AES = "{AES}";
   static final String PREFIX_AES256 = "{AES256}";
   private static final int RANDOM_LEN_AES = 16;
   private static final int KEY_LEN_AES = 128;
   private static final int KEY_LEN_AES256 = 256;
   private static final int ENCRYPTED_KEY_LEN_AES256 = 40;
   static final String ENCODING = "UTF-8";
   private static DebugLogger logger = DebugLogger.getDebugLogger("DebugSecurityEncryptionService");
   private JSAFE_SecureRandom randomIV;
   private KeyContext keyContext3DES;
   private KeyContext keyContextAES;
   private HashMap keyContextMap;
   private static FIPS140Context NON_FIPS140_CONTEXT;
   private static final int INSTANCE_RANDOM_SEED_SIZE = 32;
   private static final Object seedingLock = new Object();
   private static JSAFE_SecureRandom seedingRandom = null;

   public byte[] encryptBytes(byte[] data) throws EncryptionServiceException {
      return this.encryptBytes(data, this.keyContext3DES);
   }

   private byte[] encryptBytes(byte[] data, KeyContext kc) throws EncryptionServiceException {
      JSAFE_SymmetricCipher cipher = this.getEncryptCipher(kc);
      byte[] cipherBytes = null;

      byte[] var8;
      try {
         if (logger.isDebugEnabled()) {
            log("starting encrypt operation " + kc.prefix);
         }

         int ivLen = kc.randomLen;
         byte[] cipherBytes = new byte[ivLen + cipher.getOutputBufferSize(data.length)];
         if (ivLen > 0) {
            this.getRandomIV(cipherBytes, 0, ivLen);
            cipher.setIV(cipherBytes, 0, ivLen);
            cipher.encryptReInit();
         }

         int partOut = cipher.encryptUpdate(data, 0, data.length, cipherBytes, ivLen);
         cipher.encryptFinal(cipherBytes, partOut + ivLen);
         if (logger.isDebugEnabled()) {
            log("done with encrypt operation " + kc.prefix);
         }

         var8 = cipherBytes;
      } catch (Exception var12) {
         throw new EncryptionServiceException(var12);
      } finally {
         cipher.clearSensitiveData();
      }

      return var8;
   }

   public byte[] decryptBytes(byte[] data) throws EncryptionServiceException {
      return this.decryptBytes(data, this.keyContext3DES);
   }

   private byte[] decryptBytes(byte[] data, KeyContext kc) throws EncryptionServiceException {
      JSAFE_SymmetricCipher cipher = this.getDecryptCipher(kc);
      byte[] decryptedData = null;

      byte[] actualData;
      try {
         if (logger.isDebugEnabled()) {
            log("starting decrypt operation " + kc.prefix);
         }

         int ivLen = kc.randomLen;
         int cipherLen = data.length - ivLen;
         if (cipherLen < 0) {
            throw new IllegalStateException("Invalid input length");
         }

         byte[] decryptedData = new byte[cipherLen];
         if (ivLen > 0) {
            cipher.setIV(data, 0, ivLen);
            cipher.decryptReInit();
         }

         int partOut = cipher.decryptUpdate(data, ivLen, cipherLen, decryptedData, 0);
         int finalOut = cipher.decryptFinal(decryptedData, partOut);
         int totalOut = partOut + finalOut;
         if (totalOut < decryptedData.length) {
            actualData = new byte[totalOut];
            System.arraycopy(decryptedData, 0, actualData, 0, totalOut);
            decryptedData = actualData;
         }

         if (logger.isDebugEnabled()) {
            log("done with decrypt operation " + kc.prefix);
         }

         actualData = decryptedData;
      } catch (Exception var14) {
         if (logger.isDebugEnabled()) {
            log("Exception during decrypt operation " + var14.getMessage());
         }

         throw new EncryptionServiceException(var14);
      } finally {
         cipher.clearSensitiveData();
      }

      return actualData;
   }

   public byte[] encryptString(String clearString) throws EncryptionServiceException {
      try {
         return this.encryptBytes(clearString.getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var3) {
         throw new EncryptionServiceException(var3);
      }
   }

   public String decryptString(byte[] encryptedString) throws EncryptionServiceException {
      try {
         return new String(this.decryptBytes(encryptedString), "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         throw new EncryptionServiceException(var3);
      }
   }

   public byte[] encryptBytes(String keyContext, byte[] plainText) throws EncryptionServiceException {
      KeyContext kc = this.getKeyContextFromString(keyContext);
      return this.encryptBytes(plainText, kc);
   }

   public byte[] decryptBytes(String keyContext, byte[] cipherText) throws EncryptionServiceException {
      KeyContext kc = this.getKeyContextFromString(keyContext);
      return this.decryptBytes(cipherText, kc);
   }

   public byte[] encryptString(String keyContext, String clearString) throws EncryptionServiceException {
      KeyContext kc = this.getKeyContextFromString(keyContext);

      try {
         return this.encryptBytes(clearString.getBytes("UTF-8"), kc);
      } catch (UnsupportedEncodingException var5) {
         throw new EncryptionServiceException(var5);
      }
   }

   public String decryptString(String keyContext, byte[] encryptedString) throws EncryptionServiceException {
      KeyContext kc = this.getKeyContextFromString(keyContext);

      try {
         byte[] value = this.decryptBytes(encryptedString, kc);
         return new String(value, "UTF-8");
      } catch (UnsupportedEncodingException var5) {
         throw new EncryptionServiceException(var5);
      }
   }

   public boolean isKeyContextAvailable(String keyContext) {
      return this.keyContextMap.containsKey(keyContext);
   }

   public String getDefaultKeyContext() {
      return this.keyContextAES != null ? this.keyContextAES.prefix : this.keyContext3DES.prefix;
   }

   private KeyContext getKeyContextFromString(String keyContext) {
      KeyContext kc = (KeyContext)this.keyContextMap.get(keyContext);
      if (kc == null) {
         throw new IllegalStateException("KeyContext Unavailable!");
      } else {
         return kc;
      }
   }

   /** @deprecated */
   @Deprecated
   static byte[] createEncryptedSecretKey(String password, byte[] salt) throws EncryptionServiceException {
      return createEncryptedSecretKey("3DES_EDE/CBC/PKCS5Padding", 168, (String)password, salt);
   }

   static byte[] createEncryptedSecretKey(char[] password, byte[] salt) throws EncryptionServiceException {
      return createEncryptedSecretKey("3DES_EDE/CBC/PKCS5Padding", 168, (char[])password, salt);
   }

   /** @deprecated */
   @Deprecated
   static byte[] createAESEncryptedSecretKey(String password, byte[] salt) throws EncryptionServiceException {
      return isAES256KeyEnabled() ? createEncryptedSecretKey("AES256/CBC/PKCS5Padding", 256, (String)password, salt) : createEncryptedSecretKey("AES/CBC/PKCS5Padding", 128, (String)password, salt);
   }

   static byte[] createAESEncryptedSecretKey(char[] password, byte[] salt) throws EncryptionServiceException {
      return isAES256KeyEnabled() ? createEncryptedSecretKey("AES256/CBC/PKCS5Padding", 256, (char[])password, salt) : createEncryptedSecretKey("AES/CBC/PKCS5Padding", 128, (char[])password, salt);
   }

   private static byte[] createEncryptedSecretKey(String algorithm, int keyLen, String password, byte[] salt) throws EncryptionServiceException {
      char[] passwd = new char[password.length()];
      password.getChars(0, password.length(), passwd, 0);
      return createEncryptedSecretKey(algorithm, keyLen, passwd, salt);
   }

   private static byte[] createEncryptedSecretKey(String algorithm, int keyLen, char[] password, byte[] salt) throws EncryptionServiceException {
      JSAFE_SymmetricCipher cipher = null;
      JSAFE_SecureRandom random = null;
      JSAFE_SecretKey secretKey = null;
      boolean var15 = false;

      byte[] var9;
      try {
         var15 = true;
         log("creating new key: " + algorithm);
         int[] keyParameters = new int[]{keyLen};
         cipher = JSAFE_SymmetricCipher.getInstance(algorithm, "Java");
         random = getSeededSecureRandomInstance();
         secretKey = cipher.getBlankKey();
         generateInit(secretKey, keyParameters, random);
         secretKey.generate();
         log("created new key: " + secretKey.toString());
         byte[] encryptedKey = JSafeSecretKeyEncryptor.encryptSecretKey(secretKey, password, salt);
         log("new key (encrypted) key byte array length: " + encryptedKey.length);
         var9 = encryptedKey;
         var15 = false;
      } catch (Exception var16) {
         throw new EncryptionServiceException(var16);
      } finally {
         if (var15) {
            for(int i = 0; i < password.length; ++i) {
               password[i] = 0;
            }

            if (cipher != null) {
               cipher.clearSensitiveData();
            }

            if (random != null) {
               random.clearSensitiveData();
            }

            if (secretKey != null) {
               secretKey.clearSensitiveData();
            }

         }
      }

      for(int i = 0; i < password.length; ++i) {
         password[i] = 0;
      }

      if (cipher != null) {
         cipher.clearSensitiveData();
      }

      if (random != null) {
         random.clearSensitiveData();
      }

      if (secretKey != null) {
         secretKey.clearSensitiveData();
      }

      return var9;
   }

   private static void generateInit(JSAFE_SecretKey secretKey, int[] keyParameters, JSAFE_SecureRandom random) throws Exception {
      String method = "generateInit";
      Method generateInit = null;
      Object[] params = null;
      Class clazz = secretKey.getClass();

      try {
         log("Attempting generateInit() with RSA V6 library");
         Class[] v6rsa = new Class[]{int[].class, SecureRandom.class, byte[][].class};
         generateInit = clazz.getMethod(method, v6rsa);
         params = new Object[]{keyParameters, random, new byte[0][]};
      } catch (Exception var9) {
         log("Fallback to generateInit() with RSA V5 library");
         Class[] v5rsa = new Class[]{int[].class, SecureRandom.class};
         generateInit = clazz.getMethod(method, v5rsa);
         params = new Object[]{keyParameters, random};
      }

      generateInit.invoke(secretKey, params);
      log("secretKey.generateInit(...) success");
   }

   /** @deprecated */
   @Deprecated
   static byte[] reEncryptSecretKey(String algorithm, byte[] oldEncryptedKey, String oldPassword, byte[] oldSalt, String newPassword, byte[] newSalt) throws EncryptionServiceException {
      char[] oldPasswd = new char[oldPassword.length()];
      oldPassword.getChars(0, oldPassword.length(), oldPasswd, 0);
      char[] newPasswd = new char[newPassword.length()];
      newPassword.getChars(0, newPassword.length(), newPasswd, 0);
      return reEncryptSecretKey(algorithm, oldEncryptedKey, oldPasswd, oldSalt, newPasswd, newSalt);
   }

   static byte[] reEncryptSecretKey(String algorithm, byte[] oldEncryptedKey, char[] oldPasswd, byte[] oldSalt, char[] newPasswd, byte[] newSalt) throws EncryptionServiceException {
      JSAFE_SecretKey key = null;
      boolean var13 = false;

      byte[] var7;
      try {
         var13 = true;
         key = JSafeSecretKeyEncryptor.decryptSecretKey(algorithm, oldEncryptedKey, oldPasswd, oldSalt);
         var7 = JSafeSecretKeyEncryptor.encryptSecretKey(key, newPasswd, newSalt);
         var13 = false;
      } catch (Exception var14) {
         throw new EncryptionServiceException(var14);
      } finally {
         if (var13) {
            int i;
            for(i = 0; i < oldPasswd.length; ++i) {
               oldPasswd[i] = 0;
            }

            for(i = 0; i < newPasswd.length; ++i) {
               newPasswd[i] = 0;
            }

            if (key != null) {
               key.clearSensitiveData();
            }

         }
      }

      int i;
      for(i = 0; i < oldPasswd.length; ++i) {
         oldPasswd[i] = 0;
      }

      for(i = 0; i < newPasswd.length; ++i) {
         newPasswd[i] = 0;
      }

      if (key != null) {
         key.clearSensitiveData();
      }

      return var7;
   }

   static boolean isAES256KeyEnabled() {
      boolean isKeyEnabled = true;

      try {
         String prop = System.getProperty("weblogic.security.internal.encryption.enableAES256");
         if (prop != null) {
            isKeyEnabled = !prop.equalsIgnoreCase("false");
         }
      } catch (Exception var2) {
      }

      log("isAES256KeyEnabled() = " + isKeyEnabled);
      return isKeyEnabled;
   }

   static boolean isAES256EncryptedKey(byte[] encryptedAESKey) {
      return encryptedAESKey != null && encryptedAESKey.length >= 40;
   }

   public String getAlgorithm() {
      return "3DES";
   }

   JSafeEncryptionServiceImpl(byte[] encryptedKey, byte[] salt, char[] password, byte[] encryptedAESKey) throws EncryptionServiceException {
      this.randomIV = null;
      this.keyContext3DES = null;
      this.keyContextAES = null;
      this.keyContextMap = new HashMap(2);
      char[] copyPassword = new char[password.length];
      System.arraycopy(password, 0, copyPassword, 0, password.length);

      try {
         log("Encryption service constructor called");
         this.create3DESKeyContext(encryptedKey, password, salt);
         if (encryptedAESKey != null) {
            if (!isAES256EncryptedKey(encryptedAESKey)) {
               log("Encryption service AES key available");
               this.createAESKeyContext(encryptedAESKey, copyPassword, salt);
            } else {
               log("Encryption service AES256 key available");
               this.createAES256KeyContext(encryptedAESKey, copyPassword, salt);
            }
         }

      } catch (Exception var7) {
         throw new EncryptionServiceException(var7);
      }
   }

   JSafeEncryptionServiceImpl(byte[] encryptedKey, byte[] salt, String password, byte[] encryptedAESKey) throws EncryptionServiceException {
      this(encryptedKey, salt, password.toCharArray(), encryptedAESKey);
   }

   private void create3DESKeyContext(byte[] encrypted3DESKey, char[] passwd, byte[] salt) throws EncryptionServiceException {
      KeyContext kc = new KeyContext();
      kc.prefix = "{3DES}";
      kc.algorithm = "3DES_EDE/CBC/PKCS5Padding";
      kc.randomLen = 0;
      kc.salt = JSafeSecretKeyEncryptor.doubleSalt(salt);
      this.setupKey(kc, encrypted3DESKey, passwd, salt);
      this.keyContext3DES = kc;
   }

   private void createAESKeyContext(byte[] encryptedAESKey, char[] passwd, byte[] salt) throws EncryptionServiceException {
      KeyContext kc = new KeyContext();
      kc.prefix = "{AES}";
      kc.algorithm = "AES/CBC/PKCS5Padding";
      kc.randomLen = 16;
      kc.salt = null;
      this.setupKey(kc, encryptedAESKey, passwd, salt);
      this.keyContextAES = kc;
   }

   private void createAES256KeyContext(byte[] encryptedAESKey, char[] passwd, byte[] salt) throws EncryptionServiceException {
      KeyContext kc = new KeyContext();
      kc.prefix = "{AES256}";
      kc.algorithm = "AES256/CBC/PKCS5Padding";
      kc.randomLen = 16;
      kc.salt = null;
      this.setupKey(kc, encryptedAESKey, passwd, salt);
      this.keyContextAES = kc;
   }

   private void setupKey(KeyContext keyContext, byte[] encryptedKey, char[] passwd, byte[] salt) throws EncryptionServiceException {
      JSAFE_SecretKey secretKey = null;

      try {
         log("Initializing key: " + keyContext.prefix);
         secretKey = JSafeSecretKeyEncryptor.decryptSecretKey(keyContext.algorithm, encryptedKey, passwd, salt);
         log("key: " + secretKey.toString());
         keyContext.secretKey = secretKey;
         log("Placing KeyContext into Map: " + keyContext.prefix);
         this.keyContextMap.put(keyContext.prefix, keyContext);
      } catch (Exception var7) {
         throw new EncryptionServiceException(var7);
      }
   }

   private synchronized void initRandomIV() throws NoSuchAlgorithmException {
      if (this.randomIV == null) {
         this.randomIV = getSeededSecureRandomInstance();
      }

   }

   private synchronized void getRandomIV(byte[] out, int offset, int len) throws Exception {
      this.initRandomIV();
      this.randomIV.generateRandomBytes(out, offset, len);
   }

   private synchronized JSAFE_SymmetricCipher getEncryptCipher(KeyContext kc) throws EncryptionServiceException {
      JSAFE_SymmetricCipher cipher = null;

      try {
         cipher = JSAFE_SymmetricCipher.getInstance(kc.algorithm, "Java");
         if (kc.salt != null) {
            cipher.setIV(kc.salt, 0, kc.salt.length);
         }

         cipher.encryptInit(kc.secretKey);
         return cipher;
      } catch (Exception var4) {
         throw new EncryptionServiceException(var4);
      }
   }

   private synchronized JSAFE_SymmetricCipher getDecryptCipher(KeyContext kc) throws EncryptionServiceException {
      JSAFE_SymmetricCipher cipher = null;

      try {
         cipher = JSAFE_SymmetricCipher.getInstance(kc.algorithm, "Java");
         if (kc.salt != null) {
            cipher.setIV(kc.salt, 0, kc.salt.length);
         }

         cipher.decryptInit(kc.secretKey);
         return cipher;
      } catch (Exception var4) {
         throw new EncryptionServiceException(var4);
      }
   }

   public static EncryptionServiceFactory getFactory() throws EncryptionServiceException {
      return new JSafeEncryptionServiceFactory();
   }

   public static void log(String msg) {
      if (logger.isDebugEnabled()) {
         logger.debug(System.currentTimeMillis() + " : " + Thread.currentThread().getName() + " : " + msg);
      }

   }

   public static JSAFE_SymmetricCipher getSymmetricCipher(String algId, String device) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      JSAFE_SymmetricCipher symCipher = null;
      if (getNonFIPS140Ctx() == null) {
         symCipher = JSAFE_SymmetricCipher.getInstance(algId, device);
      } else {
         symCipher = JSAFE_SymmetricCipher.getInstance(algId, device, getNonFIPS140Ctx());
      }

      return symCipher;
   }

   public static FIPS140Context getNonFIPS140Ctx() {
      if (CryptoJ.isFIPS140Compliant() && NON_FIPS140_CONTEXT == null) {
         try {
            FIPS140Context fips140Ctx = CryptoJ.getFIPS140Context();
            NON_FIPS140_CONTEXT = fips140Ctx.setMode(1);
         } catch (JSAFE_InvalidUseException var1) {
            throw new EncryptionServiceException(var1.getMessage());
         }
      }

      return NON_FIPS140_CONTEXT;
   }

   private static JSAFE_SecureRandom getSeededSecureRandomInstance() throws NoSuchAlgorithmException {
      byte[] seedBytes = null;
      if (logger.isDebugEnabled()) {
         log("starting getSeededSecureRandomInstance - default: HMACDRBG");
      }

      byte[] seedBytes;
      synchronized(seedingLock) {
         if (seedingRandom == null) {
            algorithm_used = "HMACDRBG";

            try {
               seedingRandom = initSecureRandom(algorithm_used);
            } catch (Exception var5) {
               algorithm_used = "HMACDRBG-256-0";
               seedingRandom = initSecureRandom(algorithm_used);
            }

            try {
               seedBytes = seedingRandom.generateRandomBytes(32);
            } catch (SecurityException var4) {
               seedingRandom.clearSensitiveData();
               algorithm_used = "HMACDRBG-256-0";
               seedingRandom = initSecureRandom(algorithm_used);
               seedBytes = seedingRandom.generateRandomBytes(32);
            }
         } else {
            seedBytes = seedingRandom.generateRandomBytes(32);
         }
      }

      JSAFE_SecureRandom newRandom = (JSAFE_SecureRandom)JSAFE_SecureRandom.getInstance(algorithm_used, "Java");
      newRandom.setSeed(seedBytes);
      if (logger.isDebugEnabled()) {
         log("done getSeededSecureRandomInstance - used: " + algorithm_used);
      }

      return newRandom;
   }

   private static JSAFE_SecureRandom initSecureRandom(String algorithm) throws NoSuchAlgorithmException {
      JSAFE_SecureRandom random = (JSAFE_SecureRandom)JSAFE_SecureRandom.getInstance(algorithm, "Java");
      random.autoseed();
      return random;
   }

   private class KeyContext {
      public String prefix;
      public String algorithm;
      public int randomLen;
      private byte[] salt;
      private JSAFE_SecretKey secretKey;

      private KeyContext() {
      }

      // $FF: synthetic method
      KeyContext(Object x1) {
         this();
      }
   }
}
