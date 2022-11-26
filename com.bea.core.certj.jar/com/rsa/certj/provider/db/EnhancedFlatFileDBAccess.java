package com.rsa.certj.provider.db;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_KeyWrapCipher;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class EnhancedFlatFileDBAccess {
   static final String CERT_DIR = "certs";
   static final String CRL_DIR = "crls";
   static final String PRIV_DIR = "privs";
   static final String PUB_DIR = "pubs";
   static final String CERT_TYPE = "cer";
   static final String CRL_TYPE = "crl";
   static final String PRV_TYPE = "prv";
   static final String PUB_TYPE = "pub";
   private File certDir;
   private File crlDir;
   private File privDir;
   private File pubDir;
   private static final int KEY_LENGTH_BITS = 256;
   private static final String DRBG_ALGORITHM = "CTRDRBG";
   private static final String DRBG_KEY_LENGTH_BITS = "128";
   private static final String PERSONALISATION_STRING = "0";
   private static final String PBKDF_ALGORITHM = "PBKDF2/SHA256/PKCS5V2PBE-1000";
   private static final String KEYWRAP_ALGORITHM = "AESKeyWrapRFC5649";
   private static final int MAX_FILE_PREFIX = 999999;
   File path;
   protected char[] passphrase;
   private final byte[] salt;
   private JSAFE_SecretKey secretKey;
   int references = 0;
   private final ReentrantReadWriteLock certReadWriteLock = new ReentrantReadWriteLock();
   private final ReentrantReadWriteLock crlReadWriteLock = new ReentrantReadWriteLock();
   private final ReentrantReadWriteLock keyReadWriteLock = new ReentrantReadWriteLock();

   EnhancedFlatFileDBAccess(File var1, char[] var2, byte[] var3) throws DatabaseException {
      this.path = var1;
      this.passphrase = var2;
      this.salt = var3;
      this.setupStores();
   }

   private void setupStores() throws DatabaseException {
      if (!this.path.exists() && !this.path.mkdirs()) {
         throw new DatabaseException("Error: Could not create base directory " + this.path.getPath());
      } else {
         this.certDir = setupComponentDirectory(this.path, "certs");
         this.crlDir = setupComponentDirectory(this.path, "crls");
         this.privDir = setupComponentDirectory(this.path, "privs");
         this.pubDir = setupComponentDirectory(this.path, "pubs");
      }
   }

   private static File setupComponentDirectory(File var0, String var1) throws DatabaseException {
      File var2 = new File(var0, var1);
      if (var2.exists()) {
         if (!var2.isDirectory()) {
            if (!var2.delete()) {
               throw new DatabaseException("Error deleting previous file at this location");
            }

            if (!var2.mkdir()) {
               throw new DatabaseException("Error creating " + var1 + " directory");
            }
         }
      } else if (!var2.mkdir()) {
         throw new DatabaseException("Error creating " + var1 + " directory");
      }

      return var2;
   }

   /** @deprecated */
   protected List collectAllFiles(File var1, String var2) {
      a var3 = new a(var2);
      File[] var4 = var1.listFiles(var3);
      return (List)(var4 != null ? Arrays.asList(var4) : new ArrayList(0));
   }

   private List collectMatchingFiles(File var1, String var2, String var3) {
      if (var2 == null) {
         return this.collectAllFiles(var1, var3);
      } else {
         a var4 = new a(var2, var3);
         File[] var5 = var1.listFiles(var4);
         return (List)(var5 != null ? Arrays.asList(var5) : new ArrayList(0));
      }
   }

   static X509Certificate loadCertFromFile(File var0) throws DatabaseException {
      try {
         byte[] var1 = loadBinaryFromFile(var0);
         return new X509Certificate(var1, 0, 0);
      } catch (CertificateException var2) {
         throw new DatabaseException("Error: unable to instantiate an X509Certificate object.", var2);
      }
   }

   static X509CRL loadCRLFromFile(File var0) throws DatabaseException {
      try {
         byte[] var1 = loadBinaryFromFile(var0);
         return new X509CRL(var1, 0, 0);
      } catch (CertificateException var2) {
         throw new DatabaseException("Error: unable to instantiate an X509CRL object.", var2);
      }
   }

   private static byte[] loadBinaryFromFile(File var0) throws DatabaseException {
      FileInputStream var1 = null;

      byte[] var5;
      try {
         var1 = new FileInputStream(var0);
         int var2 = (int)var0.length();
         byte[] var3 = new byte[var2];
         int var4 = var1.read(var3);
         if (var4 != var2) {
            throw new DatabaseException("Error: contents read from " + var0 + " are of wrong length.");
         }

         var5 = var3;
      } catch (IOException var14) {
         throw new DatabaseException("Error: IO operation failed.", var14);
      } finally {
         try {
            if (var1 != null) {
               var1.close();
            }
         } catch (IOException var13) {
         }

      }

      return var5;
   }

   void insertCertificate(X509Certificate var1, CertJ var2) throws DatabaseException {
      X500Name var3;
      byte[] var4;
      String var5;
      byte[] var6;
      try {
         var3 = var1.getIssuerName();
         var4 = var1.getSerialNumber();
         var5 = this.makeFileName(var3, var4, var2);
         var6 = new byte[var1.getDERLen(0)];
         var1.getDEREncoding(var6, 0, 0);
      } catch (CertificateException var11) {
         throw new DatabaseException("Error: X509Certificate operation failed.", var11);
      }

      this.certReadWriteLock.writeLock().lock();

      try {
         File var7 = (File)this.findCert(var5, var3, var4, true);
         if (var7 != null) {
            return;
         }

         var7 = this.findNewFileName(this.certDir, var5, "cer");
         this.writeToFile(var7, var6);
      } finally {
         this.certReadWriteLock.writeLock().unlock();
      }

   }

   int selectCertificate(X500Name var1, byte[] var2, Vector var3, CertJ var4) throws DatabaseException {
      this.certReadWriteLock.readLock().lock();

      Certificate var5;
      try {
         var5 = (Certificate)this.findCert(this.makeFileName(var1, var2, var4), var1, var2, false);
      } finally {
         this.certReadWriteLock.readLock().unlock();
      }

      if (var5 == null) {
         return 0;
      } else {
         if (!var3.contains(var5)) {
            var3.addElement(var5);
         }

         return 1;
      }
   }

   int selectCertificate(X500Name var1, Vector var2) throws DatabaseException {
      int var3 = 0;
      this.certReadWriteLock.readLock().lock();

      try {
         List var4 = this.collectAllFiles(this.certDir, "cer");

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            X509Certificate var6 = loadCertFromFile((File)var4.get(var5));
            X500Name var7 = var6.getSubjectName();
            if (var1.equals(var7)) {
               ++var3;
               if (!var2.contains(var6)) {
                  var2.addElement(var6);
               }
            }
         }
      } finally {
         this.certReadWriteLock.readLock().unlock();
      }

      return var3;
   }

   int selectCertificate(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
      int var4 = 0;
      this.certReadWriteLock.readLock().lock();

      try {
         List var5 = this.collectAllFiles(this.certDir, "cer");

         for(int var6 = 0; var6 < var5.size(); ++var6) {
            X509Certificate var7 = loadCertFromFile((File)var5.get(var6));
            if (var7 != null) {
               X500Name var8 = var7.getSubjectName();
               if ((var1 == null || var8.contains(var1)) && CertJUtils.compareExtensions(var2, var7.getExtensions())) {
                  if (!var3.contains(var7)) {
                     var3.addElement(var7);
                  }

                  ++var4;
               }
            }
         }
      } finally {
         this.certReadWriteLock.readLock().unlock();
      }

      return var4;
   }

   void deleteCertificate(X500Name var1, byte[] var2, CertJ var3) throws DatabaseException {
      if (var1 != null && var2 != null) {
         this.certReadWriteLock.writeLock().lock();

         try {
            File var4 = (File)this.findCert(this.makeFileName(var1, var2, var3), var1, var2, true);
            if (var4 != null) {
               var4.delete();
            }
         } finally {
            this.certReadWriteLock.writeLock().unlock();
         }

      } else {
         throw new DatabaseException("Error: neither issuerName nor serialNumber is null.");
      }
   }

   private Object findCert(String var1, X500Name var2, byte[] var3, boolean var4) throws DatabaseException {
      List var5 = this.collectMatchingFiles(this.certDir, var1, "cer");

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         File var7 = (File)var5.get(var6);
         X509Certificate var8 = loadCertFromFile(var7);
         if (var8 != null) {
            if (var2.equals(var8.getIssuerName()) && CertJUtils.byteArraysEqual(var3, var8.getSerialNumber()) && var4) {
               return var7;
            }

            return var8;
         }
      }

      return null;
   }

   List allCerts() {
      this.certReadWriteLock.readLock().lock();

      List var1;
      try {
         var1 = this.collectAllFiles(this.certDir, "cer");
      } finally {
         this.certReadWriteLock.readLock().unlock();
      }

      return var1;
   }

   void insertCRL(X509CRL var1, CertJ var2) throws DatabaseException {
      X500Name var3;
      Date var4;
      String var5;
      byte[] var6;
      try {
         var3 = var1.getIssuerName();
         var4 = var1.getThisUpdate();
         var5 = this.makeFileName(var3, var2);
         var6 = new byte[var1.getDERLen(0)];
         var1.getDEREncoding(var6, 0, 0);
      } catch (CertificateException var11) {
         throw new DatabaseException("Error: X509CRL operation failed.", var11);
      }

      this.crlReadWriteLock.writeLock().lock();

      try {
         File var7 = this.findCRLFile(var5, var3, var4);
         if (var7 == null) {
            var7 = this.findNewFileName(this.crlDir, var5, "crl");
            this.writeToFile(var7, var6);
            return;
         }
      } finally {
         this.crlReadWriteLock.writeLock().unlock();
      }

   }

   int selectCRL(X500Name var1, Date var2, Vector var3, CertJ var4) throws DatabaseException {
      X509CRL var5 = null;
      this.crlReadWriteLock.readLock().lock();

      try {
         List var6 = this.collectMatchingFiles(this.crlDir, this.makeFileName(var1, var4), "crl");
         Date var7 = new Date(0L);

         for(int var10 = 0; var10 < var6.size(); ++var10) {
            X509CRL var8 = loadCRLFromFile((File)var6.get(var10));
            if (var1.equals(var8.getIssuerName())) {
               Date var9 = var8.getThisUpdate();
               if (!var9.after(var2) && var9.after(var7)) {
                  var7 = var9;
                  var5 = var8;
               }
            }
         }
      } finally {
         this.crlReadWriteLock.readLock().unlock();
      }

      if (var5 == null) {
         return 0;
      } else {
         if (!var3.contains(var5)) {
            var3.addElement(var5);
         }

         return 1;
      }
   }

   void deleteCRL(X500Name var1, Date var2, CertJ var3) throws DatabaseException {
      if (var1 != null && var2 != null) {
         this.crlReadWriteLock.writeLock().lock();

         try {
            File var4 = this.findCRLFile(this.makeFileName(var1, var3), var1, var2);
            if (var4 == null) {
               return;
            }

            var4.delete();
         } finally {
            this.crlReadWriteLock.writeLock().unlock();
         }

      } else {
         throw new DatabaseException("Error: neither issuerName nor lastUpdate should be null.");
      }
   }

   private File findCRLFile(String var1, X500Name var2, Date var3) throws DatabaseException {
      List var4 = this.collectMatchingFiles(this.crlDir, var1, "crl");

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         File var6 = (File)var4.get(var5);
         X509CRL var7 = loadCRLFromFile((File)var4.get(var5));
         if (var7 != null && var2.equals(var7.getIssuerName()) && var3.equals(var7.getThisUpdate())) {
            return var6;
         }
      }

      return null;
   }

   List allCRLs() {
      this.crlReadWriteLock.readLock().lock();

      List var1;
      try {
         var1 = this.collectAllFiles(this.crlDir, "crl");
      } finally {
         this.crlReadWriteLock.readLock().unlock();
      }

      return var1;
   }

   private void deriveKey(CertJ var1) throws DatabaseException {
      try {
         JSAFE_SecureRandom var2 = (JSAFE_SecureRandom)JSAFE_SecureRandom.getInstance("CTRDRBG-128-0", "Java");
         SecureRandom var3 = new SecureRandom();
         var2.setSeed(var3.generateSeed(20));
         JSAFE_SecretKey var4 = JSAFE_SecretKey.getInstance("PBKDF2/SHA256/PKCS5V2PBE-1000", var1.getDevice());
         var4.setPassword(this.passphrase, 0, this.passphrase.length);
         var4.setSalt(this.salt, 0, this.salt.length);
         var4.generateInit(new int[]{256}, var2);
         var4.generate();
         this.secretKey = JSAFE_SecretKey.getInstance("AES256", var1.getDevice());
         byte[] var5 = var4.getSecretKeyData();
         this.secretKey.setSecretKeyData(var5, 0, var5.length);
      } catch (Exception var6) {
         throw new DatabaseException("Could not derive key encryption key: ", var6);
      }
   }

   private static JSAFE_PublicKey loadPublicKeyFromFile(File var0, CertJ var1) throws DatabaseException {
      try {
         byte[] var2 = loadBinaryFromFile(var0);
         return h.a(var2, 0, var1.getDevice(), (CertJ)var1);
      } catch (JSAFE_Exception var3) {
         throw new DatabaseException("Error: unable to instantiate a public key object.", var3);
      }
   }

   JSAFE_PrivateKey loadPrivateKeyFromFile(File var1, CertJ var2) throws DatabaseException {
      return this.decryptPrivateKey(loadBinaryFromFile(var1), var2);
   }

   /** @deprecated */
   protected synchronized byte[] encryptPrivateKey(JSAFE_PrivateKey var1, CertJ var2) throws DatabaseException {
      JSAFE_KeyWrapCipher var3 = null;

      byte[] var4;
      try {
         if (this.secretKey == null) {
            this.deriveKey(var2);
         }

         var3 = JSAFE_KeyWrapCipher.getInstance("AESKeyWrapRFC5649", var2.getDevice());
         var3.encryptInit(this.secretKey);
         var4 = var3.wrapPrivateKey(var1);
      } catch (JSAFE_Exception var8) {
         throw new DatabaseException("Error: symmetric cipher operation failed.", var8);
      } finally {
         if (var3 != null) {
            var3.clearSensitiveData();
         }

      }

      return var4;
   }

   /** @deprecated */
   protected JSAFE_PrivateKey decryptPrivateKey(byte[] var1, CertJ var2) throws DatabaseException {
      JSAFE_KeyWrapCipher var3 = null;

      JSAFE_PrivateKey var4;
      try {
         if (this.secretKey == null) {
            this.deriveKey(var2);
         }

         var3 = JSAFE_KeyWrapCipher.getInstance("AESKeyWrapRFC5649", var2.getDevice());
         var3.decryptInit(this.secretKey);
         var4 = var3.unwrapPrivateKey(var1, 0, var1.length);
      } catch (JSAFE_Exception var8) {
         throw new DatabaseException("Error: symmetric cipher operation failed.", var8);
      } finally {
         if (var3 != null) {
            var3.clearSensitiveData();
         }

      }

      return var4;
   }

   void insertKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2, CertJ var3) throws DatabaseException {
      String var4;
      byte[] var5;
      byte[][] var6;
      try {
         var4 = this.makeFileName(var1);
         var5 = this.encryptPrivateKey(var2, var3);
         var6 = var1.getKeyData(var1.getAlgorithm() + "PublicKeyBER");
      } catch (JSAFE_Exception var11) {
         throw new DatabaseException("Error: publicKey getKeyData failed.", var11);
      }

      this.keyReadWriteLock.writeLock().lock();

      try {
         File var7 = (File)this.findKey(var4, var1, true, var3);
         if (var7 != null) {
            return;
         }

         var7 = this.findNewFileName(this.privDir, var4, "prv");
         this.writeToFile(var7, var5);
         this.writeToFile(new File(this.pubDir, this.matchingPublicKeyFileName(var7.getName())), var6[0]);
      } finally {
         this.keyReadWriteLock.writeLock().unlock();
      }

   }

   JSAFE_PrivateKey selectPrivateKey(JSAFE_PublicKey var1, CertJ var2) throws DatabaseException {
      this.keyReadWriteLock.readLock().lock();

      JSAFE_PrivateKey var3;
      try {
         var3 = (JSAFE_PrivateKey)this.findKey(this.makeFileName(var1), var1, false, var2);
      } finally {
         this.keyReadWriteLock.readLock().unlock();
      }

      return var3;
   }

   void deleteKey(JSAFE_PublicKey var1, CertJ var2) throws DatabaseException {
      try {
         var1.getKeyData(var1.getAlgorithm() + "PublicKeyBER");
      } catch (JSAFE_UnimplementedException var13) {
      }

      this.keyReadWriteLock.writeLock().lock();

      try {
         List var3 = this.collectMatchingFiles(this.pubDir, this.makeFileName(var1), "pub");

         for(int var4 = 0; var4 < var3.size(); ++var4) {
            File var5 = (File)var3.get(var4);
            JSAFE_PublicKey var6 = loadPublicKeyFromFile(var5, var2);
            if (var6.getAlgorithm().equals(var1.getAlgorithm())) {
               try {
                  var6.getKeyData(var6.getAlgorithm() + "PublicKeyBER");
               } catch (JSAFE_UnimplementedException var12) {
               }
            }

            if (var1.equals(var6)) {
               File var7 = new File(this.privDir, this.matchingPrivateKeyFileName(var5.getName()));
               var5.delete();
               var7.delete();
               return;
            }
         }

      } finally {
         this.keyReadWriteLock.writeLock().unlock();
      }
   }

   List allKeys() {
      this.keyReadWriteLock.readLock().lock();

      List var1;
      try {
         var1 = this.collectAllFiles(this.privDir, "prv");
      } finally {
         this.keyReadWriteLock.readLock().unlock();
      }

      return var1;
   }

   private Object findKey(String var1, JSAFE_PublicKey var2, boolean var3, CertJ var4) throws DatabaseException {
      try {
         var2.getKeyData(var2.getAlgorithm() + "PublicKeyBER");
      } catch (JSAFE_UnimplementedException var11) {
      }

      List var5 = this.collectMatchingFiles(this.pubDir, var1, "pub");

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         File var7 = (File)var5.get(var6);
         JSAFE_PublicKey var8 = loadPublicKeyFromFile(var7, var4);
         if (var8.getAlgorithm().equals(var2.getAlgorithm())) {
            try {
               var8.getKeyData(var8.getAlgorithm() + "PublicKeyBER");
            } catch (JSAFE_UnimplementedException var10) {
            }
         }

         if (var2.equals(var8)) {
            File var9 = new File(this.privDir, this.matchingPrivateKeyFileName(var7.getName()));
            if (var3) {
               return var9;
            }

            return this.loadPrivateKeyFromFile(var9, var4);
         }
      }

      return null;
   }

   /** @deprecated */
   protected File findNewFileName(File var1, String var2, String var3) throws DatabaseException {
      int var4 = 0;

      while(var4 != 999999) {
         File var5 = new File(var1, var4 + "_" + var2 + "." + var3);
         ++var4;
         if (!var5.exists()) {
            return var5;
         }
      }

      throw new DatabaseException("Database in " + var1.toString() + " is full.");
   }

   private void writeToFile(File var1, byte[] var2) throws DatabaseException {
      FileOutputStream var3 = null;

      try {
         var3 = new FileOutputStream(var1);
         var3.write(var2);
         var3.flush();
      } catch (IOException var12) {
         throw new DatabaseException("Error writing to file.");
      } finally {
         try {
            if (var3 != null) {
               var3.close();
            }
         } catch (IOException var11) {
         }

      }

   }

   private String matchingPublicKeyFileName(String var1) {
      return var1.substring(0, var1.length() - "prv".length()) + "pub";
   }

   private String matchingPrivateKeyFileName(String var1) {
      return var1.substring(0, var1.length() - "pub".length()) + "prv";
   }

   /** @deprecated */
   protected String makeFileName(X500Name var1, CertJ var2) throws DatabaseException {
      try {
         int var3 = var1.getDERLen(0);
         byte[] var4 = new byte[var3];
         var1.getDEREncoding(var4, 0, 0);
         int var5 = CertJUtils.bytesToHashCode(var4);
         var5 |= Integer.MIN_VALUE;
         return Integer.toHexString(var5);
      } catch (NameException var6) {
         throw new DatabaseException("Error: X500Name operation failed.", var6);
      }
   }

   /** @deprecated */
   protected String makeFileName(X500Name var1, byte[] var2, CertJ var3) throws DatabaseException {
      try {
         int var4 = var1.getDERLen(0);
         byte[] var5 = new byte[var4];
         var1.getDEREncoding(var5, 0, 0);
         int var6 = CertJUtils.bytesToHashCode(var5);
         var6 ^= CertJUtils.bytesToHashCode(var2);
         var6 |= Integer.MIN_VALUE;
         return Integer.toHexString(var6);
      } catch (NameException var7) {
         throw new DatabaseException("Error: X500Name operation failed.", var7);
      }
   }

   /** @deprecated */
   protected String makeFileName(JSAFE_PublicKey var1) throws DatabaseException {
      int var2 = 0;
      if (var1.getAlgorithm().equals("EC") && var1.getDevice().equals("Native")) {
         try {
            byte[] var3 = var1.getKeyData("ECPublicKeyBER")[0];
            var1 = JSAFE_PublicKey.getInstance(var3, 0, "Java");
         } catch (JSAFE_UnimplementedException var5) {
            throw new DatabaseException(var5);
         } catch (JSAFE_InvalidParameterException var6) {
            throw new DatabaseException(var6);
         }
      }

      byte[][] var7 = var1.getKeyData();

      for(int var4 = 0; var4 < var7.length; ++var4) {
         if (var7[var4] != null) {
            var2 ^= CertJUtils.bytesToHashCode(var7[var4]);
         }
      }

      var2 |= Integer.MIN_VALUE;
      return Integer.toHexString(var2);
   }

   private static class a implements FilenameFilter {
      private final String a;

      public a(String var1, String var2) {
         this.a = var1 + "." + var2;
      }

      public a(String var1) {
         this.a = "." + var1;
      }

      public boolean accept(File var1, String var2) {
         return var2.endsWith(this.a);
      }
   }
}
