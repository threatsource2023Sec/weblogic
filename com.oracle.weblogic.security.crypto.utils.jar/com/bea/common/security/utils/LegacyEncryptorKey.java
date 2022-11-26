package com.bea.common.security.utils;

import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;

public class LegacyEncryptorKey {
   private static final String ALGORITHM_3DES = "3DES_EDE/CBC/PKCS5Padding";
   private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
   private static final String ALGORITHM_AES256 = "AES256/CBC/PKCS5Padding";
   private static final String ALGORITHM_RANDOM = "HMACDRBG";
   private static final String FALLBACK_ALGORITHM_RANDOM = "FIPS186Random";
   private static final String PREFIX_3DES = "{3DES}";
   private static final int RANDOM_LEN_3DES = 0;
   private static final int KEY_LEN_3DES = 168;
   private static final String PREFIX_AES = "{AES}";
   private static final String PREFIX_AES256 = "{AES256}";
   private static final int RANDOM_LEN_AES = 16;
   private static final int KEY_LEN_AES = 128;
   private static final int ENCRYPTED_KEY_LEN_AES256 = 40;
   private static final int SALT_LENGTH = 8;
   private static final String PBE_ALGORITHM = "PBE/SHA1/RC2/CBC/PKCS12PBE-5-128";
   private static final int VERSION = 2;
   private static final int UPDATED_VERSION = 1;
   private static boolean nonFIPS140Ctx = false;
   private byte[] salt;
   private byte[] encryptedKey;
   private byte[] encryptedAESKey;
   private JSAFE_SecretKey secretKey;
   private JSAFE_SecretKey secretAESKey;
   private KeyContextMap keyContextMap;

   public LegacyEncryptorKey(char[] password) throws KeyException {
      try {
         this.generateInitialValues(password);
         this.initializeKeyContexts(password);
      } catch (JSAFE_Exception var3) {
         throw getKeyException(var3);
      } catch (NoSuchAlgorithmException var4) {
         throw getKeyException(var4);
      }
   }

   public LegacyEncryptorKey(char[] password, byte[] salt, byte[] encryptedKey) throws KeyException {
      this(password, salt, encryptedKey, (byte[])null);
   }

   public LegacyEncryptorKey(char[] password, byte[] salt, byte[] encryptedKey, byte[] encryptedAESKey) throws KeyException {
      this(password, salt, encryptedKey, encryptedAESKey, (Object)null);
   }

   private static void setNonFIPS140Ctx(boolean value) {
      nonFIPS140Ctx = value;
   }

   public LegacyEncryptorKey(char[] password, byte[] salt, byte[] encryptedKey, byte[] encryptedAESKey, Object nonFIPSCtx) throws KeyException {
      if (nonFIPSCtx != null) {
         setNonFIPS140Ctx(true);
      }

      try {
         this.salt = makeCopy(salt);
         this.encryptedKey = makeCopy(encryptedKey);
         if (encryptedAESKey != null) {
            this.encryptedAESKey = makeCopy(encryptedAESKey);
         }

         this.initializeKeyContexts(password);
      } catch (JSAFE_Exception var7) {
         throw getKeyException(var7);
      }
   }

   public LegacyEncryptorKey(char[] password, InputStream dataInput) throws IOException, KeyException {
      this.salt = readBytes(dataInput);
      int version = dataInput.read();

      try {
         if (version != -1) {
            this.encryptedKey = readBytes(dataInput);
            if (version >= 2) {
               this.encryptedAESKey = readBytes(dataInput);
            }
         } else {
            this.generateInitialValues(password);
         }

         this.initializeKeyContexts(password);
      } catch (JSAFE_Exception var5) {
         throw getKeyException(var5);
      } catch (NoSuchAlgorithmException var6) {
         throw getKeyException(var6);
      }
   }

   private void generateInitialValues(char[] password) throws JSAFE_Exception, NoSuchAlgorithmException {
      JSAFE_SecureRandom random = null;

      try {
         random = (JSAFE_SecureRandom)getRandom();
         random.autoseed();
         if (this.salt == null) {
            this.salt = random.generateRandomBytes(8);
         }

         this.encryptedKey = this.generateKey("3DES_EDE/CBC/PKCS5Padding", 168, random, this.salt, password);
         this.encryptedAESKey = this.generateKey("AES/CBC/PKCS5Padding", 128, random, this.salt, password);
      } finally {
         if (random != null) {
            random.clearSensitiveData();
         }

      }

   }

   private static SecureRandom getRandom() throws NoSuchAlgorithmException {
      SecureRandom sr = null;

      try {
         sr = JSAFE_SecureRandom.getInstance("HMACDRBG", "Java");
      } catch (NoSuchAlgorithmException var2) {
         sr = JSAFE_SecureRandom.getInstance("FIPS186Random", "Java");
      }

      return sr;
   }

   private byte[] generateKey(String algorithm, int keyLen, JSAFE_SecureRandom random, byte[] salt, char[] password) throws JSAFE_Exception, NoSuchAlgorithmException {
      JSAFE_SymmetricCipher cipher = null;
      JSAFE_SecretKey secretKey = null;

      byte[] var9;
      try {
         int[] keyParameters = new int[]{keyLen};
         cipher = JSAFE_SymmetricCipher.getInstance(algorithm, "Java");
         secretKey = cipher.getBlankKey();
         secretKey.generateInit(keyParameters, random);
         secretKey.generate();
         var9 = encryptKey(password, salt, secretKey);
      } finally {
         if (cipher != null) {
            cipher.clearSensitiveData();
         }

         if (secretKey != null) {
            random.clearSensitiveData();
         }

      }

      return var9;
   }

   private static boolean isAES256EncryptedKey(byte[] encryptedAESKey) {
      return encryptedAESKey != null && encryptedAESKey.length >= 40;
   }

   private void initializeKeyContexts(char[] password) throws JSAFE_Exception {
      KeyContext kc3DES = null;
      KeyContext kcAES = null;
      this.keyContextMap = new KeyContextMap();
      this.secretKey = decryptKey("3DES_EDE/CBC/PKCS5Padding", password, this.salt, this.encryptedKey);
      kc3DES = this.create3DESKeyContext(this.secretKey);
      if (this.encryptedAESKey != null) {
         if (!isAES256EncryptedKey(this.encryptedAESKey)) {
            this.secretAESKey = decryptKey("AES/CBC/PKCS5Padding", password, this.salt, this.encryptedAESKey);
            kcAES = this.createAESKeyContext(this.secretAESKey);
         } else {
            this.secretAESKey = decryptKey("AES256/CBC/PKCS5Padding", password, this.salt, this.encryptedAESKey);
            kcAES = this.createAES256KeyContext(this.secretAESKey);
         }
      }

      if (kcAES != null) {
         this.keyContextMap.kcDefault = kcAES;
      } else {
         this.keyContextMap.kcDefault = kc3DES;
      }

   }

   private KeyContext create3DESKeyContext(JSAFE_SecretKey secretKey3DES) throws JSAFE_Exception {
      KeyContext kc = new KeyContext();
      kc.prefix = "{3DES}";
      kc.algorithm = "3DES_EDE/CBC/PKCS5Padding";
      kc.randomLen = 0;
      kc.salt = this.salt;
      if (this.salt.length != 8) {
         byte[] iv = new byte[8];
         int i = 0;

         for(int j = 0; i < iv.length; ++j) {
            if (j >= this.salt.length) {
               j = 0;
            }

            iv[i] = this.salt[j];
            ++i;
         }

         kc.salt = iv;
      }

      this.setupKey(kc, secretKey3DES);
      return kc;
   }

   private KeyContext createAESKeyContext(JSAFE_SecretKey secretKeyAES) throws JSAFE_Exception {
      KeyContext kc = new KeyContext();
      kc.prefix = "{AES}";
      kc.algorithm = "AES/CBC/PKCS5Padding";
      kc.randomLen = 16;
      kc.salt = null;
      this.setupKey(kc, secretKeyAES);
      return kc;
   }

   private KeyContext createAES256KeyContext(JSAFE_SecretKey secretKeyAES) throws JSAFE_Exception {
      KeyContext kc = new KeyContext();
      kc.prefix = "{AES256}";
      kc.algorithm = "AES256/CBC/PKCS5Padding";
      kc.randomLen = 16;
      kc.salt = null;
      this.setupKey(kc, secretKeyAES);
      return kc;
   }

   private void setupKey(KeyContext keyContext, JSAFE_SecretKey key) throws JSAFE_Exception {
      keyContext.secretKey = key;
      this.keyContextMap.keyContexts.put(keyContext.prefix, keyContext);
   }

   public byte[] getSalt() {
      this.checkDisposed();
      return makeCopy(this.salt);
   }

   public byte[] getEncryptedSecretKey() {
      this.checkDisposed();
      return makeCopy(this.encryptedKey);
   }

   public byte[] getEncryptedAESSecretKey() {
      this.checkDisposed();
      return this.encryptedAESKey == null ? null : makeCopy(this.encryptedAESKey);
   }

   public void generateAESKey(char[] password) throws KeyException {
      this.checkDisposed();
      if (this.encryptedAESKey != null) {
         throw getKeyException(new IllegalStateException("Key Exists"));
      } else {
         JSAFE_SecureRandom random = null;

         try {
            random = (JSAFE_SecureRandom)getRandom();
            random.autoseed();
            this.encryptedAESKey = this.generateKey("AES/CBC/PKCS5Padding", 128, random, this.salt, password);
         } catch (JSAFE_Exception var8) {
            throw getKeyException(var8);
         } catch (NoSuchAlgorithmException var9) {
            throw getKeyException(var9);
         } finally {
            if (random != null) {
               random.clearSensitiveData();
            }

         }

      }
   }

   public void updateProtection(char[] password, byte[] salt) throws KeyException {
      this.checkDisposed();

      try {
         this.encryptedKey = encryptKey(password, salt, this.secretKey);
         this.salt = salt;
         if (this.secretAESKey != null) {
            this.encryptedAESKey = encryptKey(password, salt, this.secretAESKey);
         }

      } catch (JSAFE_Exception var4) {
         throw getKeyException(var4);
      }
   }

   public void write(OutputStream stream) throws IOException {
      this.checkDisposed();
      int fileVersion = 2;
      if (this.encryptedAESKey == null) {
         fileVersion = 1;
      }

      stream.write(this.salt.length);
      stream.write(this.salt);
      stream.write(fileVersion);
      stream.write(this.encryptedKey.length);
      stream.write(this.encryptedKey);
      if (fileVersion == 2) {
         stream.write(this.encryptedAESKey.length);
         stream.write(this.encryptedAESKey);
      }

   }

   public void dispose() {
      if (this.salt != null) {
         Arrays.fill(this.salt, (byte)0);
      }

      if (this.encryptedKey != null) {
         Arrays.fill(this.encryptedKey, (byte)0);
      }

      if (this.encryptedAESKey != null) {
         Arrays.fill(this.encryptedAESKey, (byte)0);
      }

      this.salt = null;
      this.encryptedKey = null;
      this.encryptedAESKey = null;
      this.secretAESKey = null;
      this.secretKey = null;
      this.keyContextMap = null;
   }

   KeyContextMap getKeyContextMap() {
      this.checkDisposed();
      return this.keyContextMap;
   }

   private final void checkDisposed() {
      if (this.secretKey == null) {
         throw new IllegalStateException("The key is disposed");
      }
   }

   private static final KeyException getKeyException(Exception ex) {
      KeyException kex = new KeyException(ex.getLocalizedMessage());
      kex.initCause(ex);
      return kex;
   }

   private static byte[] readBytes(InputStream is) throws IOException {
      int len = is.read();
      if (len < 0) {
         throw new IOException("Stream is empty");
      } else {
         byte[] bytes = new byte[len];
         int readin = 0;

         int justread;
         for(int justread = false; readin < len; readin += justread) {
            justread = is.read(bytes, readin, len - readin);
            if (justread == -1) {
               throw new IOException("End of stream while expecting " + (len - readin) + " more bytes");
            }
         }

         return bytes;
      }
   }

   private static final byte[] makeCopy(byte[] bytes) {
      byte[] copy = new byte[bytes.length];
      System.arraycopy(bytes, 0, copy, 0, bytes.length);
      return copy;
   }

   private static byte[] encryptKey(char[] password, byte[] salt, JSAFE_SecretKey secretKey) throws JSAFE_Exception {
      byte[] key = secretKey.getSecretKeyData();

      byte[] var4;
      try {
         var4 = pbeEncrypt(password, salt, key);
      } finally {
         Arrays.fill(key, (byte)0);
      }

      return var4;
   }

   private static JSAFE_SecretKey decryptKey(String algorithm, char[] password, byte[] salt, byte[] encryptedKey) throws JSAFE_Exception {
      byte[] key = pbeDecrypt(password, salt, encryptedKey);
      JSAFE_SymmetricCipher cipher = null;

      JSAFE_SecretKey var7;
      try {
         cipher = JSAFE_SymmetricCipher.getInstance(algorithm, "Java");
         JSAFE_SecretKey returnKey = cipher.getBlankKey();
         returnKey.setSecretKeyData(key, 0, key.length);
         var7 = returnKey;
      } finally {
         Arrays.fill(key, (byte)0);
         if (cipher != null) {
            cipher.clearSensitiveData();
         }

      }

      return var7;
   }

   private static byte[] pbeEncrypt(char[] password, byte[] salt, byte[] plainText) throws JSAFE_Exception {
      JSAFE_SymmetricCipher cipher = null;
      JSAFE_SecretKey secretKey = null;

      byte[] var7;
      try {
         cipher = getNonFIPSSymmetricCipher("PBE/SHA1/RC2/CBC/PKCS12PBE-5-128", "Java");
         cipher.setSalt(salt, 0, salt.length);
         secretKey = cipher.getBlankKey();
         secretKey.setPassword(password, 0, password.length);
         cipher.encryptInit(secretKey);
         byte[] cipherText = new byte[cipher.getOutputBufferSize(plainText.length)];
         int partOut = cipher.encryptUpdate(plainText, 0, plainText.length, cipherText, 0);
         cipher.encryptFinal(cipherText, partOut);
         var7 = cipherText;
      } finally {
         if (cipher != null) {
            cipher.clearSensitiveData();
         }

         if (secretKey != null) {
            secretKey.clearSensitiveData();
         }

      }

      return var7;
   }

   private static byte[] pbeDecrypt(char[] password, byte[] salt, byte[] cipherText) throws JSAFE_Exception {
      JSAFE_SymmetricCipher cipher = null;
      JSAFE_SecretKey secretKey = null;

      byte[] text;
      try {
         cipher = getNonFIPSSymmetricCipher("PBE/SHA1/RC2/CBC/PKCS12PBE-5-128", "Java");
         cipher.setSalt(salt, 0, salt.length);
         secretKey = cipher.getBlankKey();
         secretKey.setPassword(password, 0, password.length);
         cipher.decryptInit(secretKey);
         byte[] plainText = new byte[cipherText.length];
         int partOut = cipher.decryptUpdate(cipherText, 0, cipherText.length, plainText, 0);
         int finalOut = cipher.decryptFinal(plainText, partOut);
         int totalOut = partOut + finalOut;
         if (plainText.length > totalOut) {
            text = new byte[totalOut];
            System.arraycopy(plainText, 0, text, 0, totalOut);
            plainText = text;
         }

         text = plainText;
      } finally {
         if (cipher != null) {
            cipher.clearSensitiveData();
         }

         if (secretKey != null) {
            secretKey.clearSensitiveData();
         }

      }

      return text;
   }

   private static JSAFE_SymmetricCipher getNonFIPSSymmetricCipher(String algId, String device) throws JSAFE_Exception {
      if (nonFIPS140Ctx) {
         try {
            Class JSafeImpl = Class.forName("weblogic.security.internal.encryption.JSafeEncryptionServiceImpl");
            Method getSymmetricCipher = JSafeImpl.getMethod("getSymmetricCipher", String.class, String.class);
            return (JSAFE_SymmetricCipher)getSymmetricCipher.invoke((Object)null, algId, device);
         } catch (LinkageError var4) {
         } catch (Exception var5) {
         }
      }

      return JSAFE_SymmetricCipher.getInstance(algId, device);
   }

   class KeyContextMap {
      private KeyContext kcDefault;
      private HashMap keyContexts;

      private KeyContextMap() {
         this.keyContexts = new HashMap(2);
      }

      String getDefaultKeyContext() {
         return this.kcDefault.prefix;
      }

      boolean isKeyContextAvailable(String keyContext) {
         return this.keyContexts.containsKey(keyContext);
      }

      KeyContext getKeyContextFromString(String keyContext) {
         KeyContext kc = (KeyContext)this.keyContexts.get(keyContext);
         if (kc == null) {
            throw new IllegalStateException("KeyContext Unavailable!");
         } else {
            return kc;
         }
      }

      // $FF: synthetic method
      KeyContextMap(Object x1) {
         this();
      }
   }

   class KeyContext {
      String prefix;
      String algorithm;
      int randomLen;
      private byte[] salt;
      private JSAFE_SecretKey secretKey;
      private JSAFE_SecureRandom randomIV;

      private KeyContext() {
         this.randomIV = null;
      }

      private synchronized void initRandomIV() throws NoSuchAlgorithmException {
         if (this.randomIV == null) {
            this.randomIV = (JSAFE_SecureRandom)LegacyEncryptorKey.getRandom();
            this.randomIV.autoseed();
         }

      }

      void getRandomIV(byte[] out, int offset, int len) throws KeyException {
         try {
            this.initRandomIV();
         } catch (NoSuchAlgorithmException var5) {
            throw LegacyEncryptorKey.getKeyException(var5);
         }

         this.randomIV.generateRandomBytes(out, offset, len);
      }

      synchronized JSAFE_SymmetricCipher getEncryptCipher() throws KeyException {
         JSAFE_SymmetricCipher cipher = null;

         try {
            cipher = JSAFE_SymmetricCipher.getInstance(this.algorithm, "Java");
            if (this.salt != null) {
               cipher.setIV(this.salt, 0, this.salt.length);
            }

            cipher.encryptInit(this.secretKey);
            return cipher;
         } catch (Exception var3) {
            throw LegacyEncryptorKey.getKeyException(var3);
         }
      }

      synchronized JSAFE_SymmetricCipher getDecryptCipher() throws KeyException {
         JSAFE_SymmetricCipher cipher = null;

         try {
            cipher = JSAFE_SymmetricCipher.getInstance(this.algorithm, "Java");
            if (this.salt != null) {
               cipher.setIV(this.salt, 0, this.salt.length);
            }

            cipher.decryptInit(this.secretKey);
            return cipher;
         } catch (Exception var3) {
            throw LegacyEncryptorKey.getKeyException(var3);
         }
      }

      // $FF: synthetic method
      KeyContext(Object x1) {
         this();
      }
   }
}
