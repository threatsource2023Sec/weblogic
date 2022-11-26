package com.rsa.certj.cms;

import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import java.util.Date;

/** @deprecated */
public final class InfoObjectFactory {
   /** @deprecated */
   public static final String ENCRYPTION_RSA = "RSA";
   /** @deprecated */
   public static final String KDF_PBKDF2WITHSHA1 = "PBKDF2/SHA1/PKCS5V2PBE";
   /** @deprecated */
   public static final String KDF_PBKDF2WITHSHA224 = "PBKDF2/SHA224/PKCS5V2PBE";
   /** @deprecated */
   public static final String KDF_PBKDF2WITHSHA256 = "PBKDF2/SHA256/PKCS5V2PBE";
   /** @deprecated */
   public static final String KDF_PBKDF2WITHSHA384 = "PBKDF2/SHA384/PKCS5V2PBE";
   /** @deprecated */
   public static final String KDF_PBKDF2WITHSHA512 = "PBKDF2/SHA512/PKCS5V2PBE";
   /** @deprecated */
   public static final String KEYAGREE_ECDH = "ECDH";
   /** @deprecated */
   public static final String KEYAGREE_DH = "DH";
   /** @deprecated */
   public static final String DIGEST_SHA1 = "SHA1";
   /** @deprecated */
   public static final String DIGEST_SHA224 = "SHA224";
   /** @deprecated */
   public static final String DIGEST_SHA256 = "SHA256";
   /** @deprecated */
   public static final String DIGEST_SHA384 = "SHA384";
   /** @deprecated */
   public static final String DIGEST_SHA512 = "SHA512";

   private InfoObjectFactory() {
   }

   /** @deprecated */
   public static KeyTransRecipientInfo newKeyTransRecipientInfo(X509Certificate var0, String var1) throws CMSException {
      return new KeyTransRecipientInfo(var0, var1);
   }

   /** @deprecated */
   public static PasswordRecipientInfo newPasswordRecipientInfo(char[] var0) throws CMSException {
      return new PasswordRecipientInfo(var0, (String)null, 100000);
   }

   /** @deprecated */
   public static PasswordRecipientInfo newPasswordRecipientInfo(char[] var0, String var1, int var2) throws CMSException {
      return new PasswordRecipientInfo(var0, var1, var2);
   }

   /** @deprecated */
   public static KekRecipientInfo newKekRecipientInfo(byte[] var0, JSAFE_SecretKey var1) throws CMSException {
      return newKekRecipientInfo(var0, var1, (Date)null);
   }

   /** @deprecated */
   public static KekRecipientInfo newKekRecipientInfo(byte[] var0, JSAFE_SecretKey var1, Date var2) throws CMSException {
      return new KekRecipientInfo(var0, var1, var2, (String)null, (byte[])null);
   }

   /** @deprecated */
   public static KekRecipientInfo newKekRecipientInfo(byte[] var0, JSAFE_SecretKey var1, Date var2, String var3, byte[] var4) throws CMSException {
      return new KekRecipientInfo(var0, var1, var2, var3, var4);
   }

   /** @deprecated */
   public static KeyAgreeRecipientInfo newKeyAgreeRecipientInfo(X509Certificate var0, JSAFE_PrivateKey var1, X509Certificate var2) throws CMSException {
      return new KeyAgreeRecipientInfo(var0, (JSAFE_PublicKey)null, var1, var2);
   }

   /** @deprecated */
   public static KeyAgreeRecipientInfo newKeyAgreeRecipientInfo(JSAFE_PublicKey var0, JSAFE_PrivateKey var1, X509Certificate var2) throws CMSException {
      return new KeyAgreeRecipientInfo((X509Certificate)null, var0, var1, var2);
   }

   /** @deprecated */
   public static SignerInfo newSignerInfo(JSAFE_PrivateKey var0, X509Certificate var1, String var2) throws CMSException {
      return newSignerInfo(var0, var1, var2, (X501Attributes)null, (X501Attributes)null);
   }

   /** @deprecated */
   public static SignerInfo newSignerInfo(JSAFE_PrivateKey var0, X509Certificate var1, String var2, X501Attributes var3, X501Attributes var4) throws CMSException {
      return new SignerInfo(var0, var1, var2, var3, var4);
   }
}
