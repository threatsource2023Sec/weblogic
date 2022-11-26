package com.rsa.certj.provider.db;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.File;

/** @deprecated */
public class FlatFileDBAccess extends EnhancedFlatFileDBAccess {
   private static final String PBE_ALGORITHM = "PBE/SHA1/RC4/PKCS12V1PBE-5-128";
   private static final String DIGEST_TYPE = "MD5";
   private int baseNameLen;
   private int prefixLen;

   FlatFileDBAccess(File var1, char[] var2, int var3, int var4) throws DatabaseException {
      super(var1, var2, (byte[])null);
      this.baseNameLen = var3;
      this.prefixLen = var4;
   }

   private static String hexEncode(byte var0) {
      char[] var1 = new char[2];
      int var2 = var0 & 15;
      if (var2 < 0) {
         var2 += 16;
      }

      int var3 = var0 >> 4;
      if (var3 < 0) {
         var3 += 16;
      }

      if (var2 < 10) {
         var1[1] = (char)(48 + var2);
      } else {
         var1[1] = (char)(65 + (var2 - 10));
      }

      if (var3 < 10) {
         var1[0] = (char)(48 + var3);
      } else {
         var1[0] = (char)(65 + (var3 - 10));
      }

      return new String(var1);
   }

   /** @deprecated */
   protected byte[] encryptPrivateKey(JSAFE_PrivateKey var1, CertJ var2) throws DatabaseException {
      JSAFE_SymmetricCipher var3 = null;

      byte[] var6;
      try {
         var3 = h.c("PBE/SHA1/RC4/PKCS12V1PBE-5-128", var2.getDevice(), var2);
         JSAFE_SecureRandom var4 = var2.getRandomObject();
         var3.generateSalt(var4);
         JSAFE_SecretKey var5 = var3.getBlankKey();
         var5.setPassword(this.passphrase, 0, this.passphrase.length);
         var3.encryptInit(var5, var4);
         var6 = var3.wrapPrivateKey(var1, true);
      } catch (JSAFE_Exception var12) {
         throw new DatabaseException("Error: symmetric cipher operation failed.", var12);
      } catch (NoServiceException var13) {
         throw new DatabaseException("Error: random provider is not available in certJ.", var13);
      } catch (RandomException var14) {
         throw new DatabaseException("Error: random number generation failed.", var14);
      } finally {
         if (var3 != null) {
            var3.clearSensitiveData();
         }

      }

      return var6;
   }

   /** @deprecated */
   protected JSAFE_PrivateKey decryptPrivateKey(byte[] var1, CertJ var2) throws DatabaseException {
      JSAFE_SymmetricCipher var3 = null;

      JSAFE_PrivateKey var6;
      try {
         var3 = h.c(var1, 0, var2.getDevice(), (CertJ)var2);
         JSAFE_SecretKey var4 = var3.getBlankKey();
         var4.setPassword(this.passphrase, 0, this.passphrase.length);
         byte[] var5 = unwrapBER(var1, 0, var3, var4);
         var6 = JSAFE_PrivateKey.getInstance(var5, 0, var2.getDevice());
      } catch (JSAFE_Exception var10) {
         throw new DatabaseException("Error: symmetric cipher operation failed.", var10);
      } finally {
         if (var3 != null) {
            var3.clearSensitiveData();
         }

      }

      return var6;
   }

   private static byte[] unwrapBER(byte[] var0, int var1, JSAFE_SymmetricCipher var2, JSAFE_SecretKey var3) throws DatabaseException {
      int[] var4 = getEncryptedKeyInfo(var0, var1);
      int var5 = var4[0];
      int var6 = var4[1];

      try {
         var2.decryptInit(var3);
         byte[] var7 = var2.decryptUpdate(var0, var5, var6);
         byte[] var8 = var2.decryptFinal();
         byte[] var9 = new byte[var7.length + var8.length];
         System.arraycopy(var7, 0, var9, 0, var7.length);
         System.arraycopy(var8, 0, var9, var7.length, var8.length);
         return var9;
      } catch (JSAFE_Exception var10) {
         throw new DatabaseException(var10);
      }
   }

   private static int[] getEncryptedKeyInfo(byte[] var0, int var1) throws DatabaseException {
      SequenceContainer var2 = new SequenceContainer(0);
      EndContainer var3 = new EndContainer();
      EncodedContainer var4 = new EncodedContainer(12288);
      OctetStringContainer var5 = new OctetStringContainer(0);
      ASN1Container[] var6 = new ASN1Container[]{var2, var4, var5, var3};

      try {
         ASN1.berDecode(var0, var1, var6);
      } catch (ASN_Exception var8) {
         throw new DatabaseException("Cannot build the PKCS #8 encrypted key. (" + var8.getMessage() + ")");
      }

      return new int[]{var5.dataOffset, var5.dataLen};
   }

   /** @deprecated */
   protected File findNewFileName(File var1, String var2, String var3) throws DatabaseException {
      int var4 = 0;
      int var5 = 10 * (this.prefixLen + 1);
      char[] var6 = new char[this.prefixLen];

      File var7;
      do {
         if (var4 == var5) {
            throw new DatabaseException("Error: Database in " + var1.toString() + " is full.");
         }

         String var8 = "" + var4;

         int var9;
         for(var9 = 0; var9 < this.prefixLen - var8.length(); ++var9) {
            var6[var9] = '0';
         }

         for(var9 = 0; var9 < var8.length(); ++var9) {
            var6[this.prefixLen - var8.length() + var9] = var8.charAt(var9);
         }

         var7 = new File(var1, new String(var6) + var2 + "." + var3);
         ++var4;
      } while(var7.exists());

      return var7;
   }

   /** @deprecated */
   protected String makeFileName(X500Name var1, byte[] var2, CertJ var3) throws DatabaseException {
      try {
         byte[][] var4 = new byte[2][];
         int var5 = var1.getDERLen(0);
         var4[0] = var2;
         var4[1] = new byte[var5];
         var1.getDEREncoding(var4[1], 0, 0);
         JSAFE_MessageDigest var6 = h.a("MD5", var3.getDevice(), var3);
         var6.digestInit();

         for(int var7 = 0; var7 < var4.length; ++var7) {
            var6.digestUpdate(var4[var7], 0, var4[var7].length);
         }

         byte[] var10 = var6.digestFinal();
         return this.truncateByteArray(var10);
      } catch (NameException var8) {
         throw new DatabaseException("Error: X500Name operation failed.", var8);
      } catch (JSAFE_Exception var9) {
         throw new DatabaseException("Error: digest operation failed.", var9);
      }
   }

   /** @deprecated */
   protected String makeFileName(X500Name var1, CertJ var2) throws DatabaseException {
      try {
         int var3 = var1.getDERLen(0);
         byte[] var4 = new byte[var3];
         var1.getDEREncoding(var4, 0, 0);
         JSAFE_MessageDigest var5 = h.a("MD5", var2.getDevice(), var2);
         var5.digestInit();
         var5.digestUpdate(var4, 0, var4.length);
         byte[] var6 = var5.digestFinal();
         return this.truncateByteArray(var6);
      } catch (NameException var7) {
         throw new DatabaseException("Error: X500Name operation failed.", var7);
      } catch (JSAFE_Exception var8) {
         throw new DatabaseException("Error: digest operation failed.", var8);
      }
   }

   /** @deprecated */
   protected String makeFileName(JSAFE_PublicKey var1) {
      int var2 = this.baseNameLen / 2;
      byte[][] var3 = var1.getKeyData();
      byte[] var4 = var3[0];
      int var6;
      if (var4.length < var2) {
         byte[] var5 = var4;
         var4 = new byte[var2];
         System.arraycopy(var5, 0, var4, 0, var5.length);

         for(var6 = var5.length; var6 < var2; ++var6) {
            var4[var6] = 0;
         }
      }

      String var8 = "";
      var6 = var4.length - var2;

      for(int var7 = 0; var7 < var2; ++var7) {
         var8 = var8 + hexEncode(var4[var6 + var7]);
      }

      return var8;
   }

   private synchronized String truncateByteArray(byte[] var1) {
      int var2 = this.baseNameLen / 2;
      int var3 = var1.length;
      int var4 = var3 - var2;
      byte[] var5 = new byte[var2];
      System.arraycopy(var1, var4, var5, 0, var2);
      StringBuffer var6 = new StringBuffer();

      for(int var7 = 0; var7 < var2; ++var7) {
         var6.append(hexEncode(var5[var7]));
      }

      return var6.toString();
   }
}
