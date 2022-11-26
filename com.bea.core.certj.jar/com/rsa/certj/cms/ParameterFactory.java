package com.rsa.certj.cms;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import java.util.Date;

/** @deprecated */
public final class ParameterFactory {
   /** @deprecated */
   public static final String MAC_HMAC_SHA1 = "HMACSHA1";
   /** @deprecated */
   public static final String MAC_HMAC_SHA224 = "HMACSHA224";
   /** @deprecated */
   public static final String MAC_HMAC_SHA256 = "HMACSHA256";
   /** @deprecated */
   public static final String MAC_HMAC_SHA384 = "HMACSHA384";
   /** @deprecated */
   public static final String MAC_HMAC_SHA512 = "HMACSHA512";
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
   /** @deprecated */
   public static final String ENCRYPTION_ALG_AES_CBC_PKCS5PAD = "AES/CBC/PKCS5Pad";
   /** @deprecated */
   public static final String ENCRYPTION_ALG_DES_CBC_PKCS5PAD = "DES/CBC/PKCS5Pad";
   /** @deprecated */
   public static final String ENCRYPTION_ALG_DESEDE_CBC_PKCS5PAD = "DESede/CBC/PKCS5Pad";
   /** @deprecated */
   public static final String ENCRYPTION_ALG_RC2_CBC_PKCS5PAD = "RC2/CBC/PKCS5Pad";

   private ParameterFactory() {
   }

   /** @deprecated */
   public static CMSParameters newAuthenticatedDataParameters(RecipientInfo[] var0, String var1) throws CMSException {
      return newAuthenticatedDataParameters(var0, var1, 0, (DatabaseService)null, (X501Attributes)null, (X501Attributes)null, (JSAFE_SecureRandom)null);
   }

   /** @deprecated */
   public static CMSParameters newAuthenticatedDataParameters(RecipientInfo[] var0, String var1, int var2, DatabaseService var3, X501Attributes var4, X501Attributes var5, JSAFE_SecureRandom var6) throws CMSException {
      return new CMSParameters.a(var0, var1, var2, var3, var4, var5, var6);
   }

   /** @deprecated */
   public static CMSParameters newDigestedDataParameters(String var0) {
      return new CMSParameters.b(var0);
   }

   /** @deprecated */
   public static CMSParameters newEncryptedDataParameters(String var0, JSAFE_SecretKey var1) throws CMSException {
      return newEncryptedDataParameters(var0, var1, (X501Attributes)null, (JSAFE_SecureRandom)null);
   }

   /** @deprecated */
   public static CMSParameters newEncryptedDataParameters(String var0, JSAFE_SecretKey var1, X501Attributes var2, JSAFE_SecureRandom var3) throws CMSException {
      return new CMSParameters.c(var0, var1, var2, var3);
   }

   /** @deprecated */
   public static CMSParameters newEnvelopedDataParameters(RecipientInfo[] var0, String var1) throws CMSException {
      return newEnvelopedDataParameters(var0, var1, 0, (DatabaseService)null, (X501Attributes)null, (JSAFE_SecureRandom)null);
   }

   /** @deprecated */
   public static CMSParameters newEnvelopedDataParameters(RecipientInfo[] var0, String var1, int var2, DatabaseService var3, X501Attributes var4, JSAFE_SecureRandom var5) throws CMSException {
      return new CMSParameters.d(var0, var1, var2, var3, var4, var5);
   }

   /** @deprecated */
   public static CMSParameters newSignedDataParameters(SignerInfo[] var0, DatabaseService var1) throws CMSException {
      return newSignedDataParameters(var0, var1, (JSAFE_SecureRandom)null);
   }

   /** @deprecated */
   public static CMSParameters newSignedDataParameters(SignerInfo[] var0, DatabaseService var1, JSAFE_SecureRandom var2) throws CMSException {
      return new CMSParameters.e(var0, var1, var2);
   }

   /** @deprecated */
   public static CMSParameters newTimeStampTokenParameters(String var0, String var1, byte[] var2, byte[] var3, Date var4) throws CMSException {
      return newTimeStampTokenParameters(var0, var1, var2, var3, var4, (Accuracy)null, false, (byte[])null, (GeneralName)null, (X509V3Extensions)null);
   }

   /** @deprecated */
   public static CMSParameters newTimeStampTokenParameters(String var0, String var1, byte[] var2, byte[] var3, Date var4, Accuracy var5, boolean var6, byte[] var7, GeneralName var8, X509V3Extensions var9) throws CMSException {
      return new CMSParameters.f(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }
}
