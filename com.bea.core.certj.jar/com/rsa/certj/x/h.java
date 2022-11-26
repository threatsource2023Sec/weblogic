package com.rsa.certj.x;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.jsafe.CryptoJ;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_AsymmetricCipher;
import com.rsa.jsafe.JSAFE_IVException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_MAC;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_Recode;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Signature;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import com.rsa.jsafe.JSAFE_UnimplementedException;

public class h {
   private static final boolean a = CryptoJ.isFIPS140Compliant();

   public static JSAFE_PublicKey a(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a(var0, var1, var2, a(var3));
   }

   public static JSAFE_PublicKey a(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var3 != null ? JSAFE_PublicKey.getInstance(var0, var1, var2, var3) : JSAFE_PublicKey.getInstance(var0, var1, var2);
   }

   public static JSAFE_MessageDigest a(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a(var0, var1, a(var2));
   }

   public static JSAFE_MessageDigest a(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_MessageDigest.getInstance(var0, var1, var2) : JSAFE_MessageDigest.getInstance(var0, var1);
   }

   public static JSAFE_Signature b(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return b(var0, var1, a(var2));
   }

   public static JSAFE_Signature b(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return b(var0, var1, var2, a(var3));
   }

   public static JSAFE_Signature b(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_Signature.getInstance(var0, var1, var2) : JSAFE_Signature.getInstance(var0, var1);
   }

   public static JSAFE_Signature b(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var3 != null ? JSAFE_Signature.getInstance(var0, var1, var2, var3) : JSAFE_Signature.getInstance(var0, var1, var2);
   }

   public static JSAFE_SecureRandom a(c var0) {
      return var0.b == null ? (JSAFE_SecureRandom)CryptoJ.getDefaultRandom() : (JSAFE_SecureRandom)CryptoJ.getDefaultRandom(var0.b);
   }

   public static JSAFE_MAC c(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_MAC.getInstance(var0, var1, var2) : JSAFE_MAC.getInstance(var0, var1);
   }

   private static FIPS140Context a(CertJ var0) {
      return var0 == null ? null : CertJInternalHelper.context(var0).b;
   }

   public static JSAFE_SymmetricCipher c(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException, JSAFE_IVException {
      return a && var3 != null ? JSAFE_SymmetricCipher.getInstance(var0, var1, var2, var3) : JSAFE_SymmetricCipher.getInstance(var0, var1, var2);
   }

   public static JSAFE_SymmetricCipher d(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_SymmetricCipher.getInstance(var0, var1, var2) : JSAFE_SymmetricCipher.getInstance(var0, var1);
   }

   public static JSAFE_SymmetricCipher c(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException, JSAFE_IVException {
      return c(var0, var1, var2, a(var3));
   }

   public static JSAFE_PrivateKey d(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException {
      return d(var0, var1, var2, a(var3));
   }

   public static JSAFE_PrivateKey d(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException {
      return a && var3 != null ? JSAFE_PrivateKey.getInstance(var0, var1, var2, var3) : JSAFE_PrivateKey.getInstance(var0, var1, var2);
   }

   public static JSAFE_SymmetricCipher c(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return d(var0, var1, a(var2));
   }

   public static JSAFE_MessageDigest e(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var3 != null ? JSAFE_MessageDigest.getInstance(var0, var1, var2, var3) : JSAFE_MessageDigest.getInstance(var0, var1, var2);
   }

   public static JSAFE_SecretKey e(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_SecretKey.getInstance(var0, var1, var2) : JSAFE_SecretKey.getInstance(var0, var1);
   }

   public static JSAFE_AsymmetricCipher f(byte[] var0, int var1, String var2, FIPS140Context var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var3 != null ? JSAFE_AsymmetricCipher.getInstance(var0, var1, var2, var3) : JSAFE_AsymmetricCipher.getInstance(var0, var1, var2);
   }

   public static JSAFE_AsymmetricCipher f(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_AsymmetricCipher.getInstance(var0, var1, var2) : JSAFE_AsymmetricCipher.getInstance(var0, var1);
   }

   public static JSAFE_Recode g(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_Recode.getInstance(var0, var1, var2) : JSAFE_Recode.getInstance(var0, var1);
   }

   public static JSAFE_PublicKey h(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_PublicKey.getInstance(var0, var1, var2) : JSAFE_PublicKey.getInstance(var0, var1);
   }

   public static JSAFE_PrivateKey i(String var0, String var1, FIPS140Context var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return a && var2 != null ? JSAFE_PrivateKey.getInstance(var0, var1, var2) : JSAFE_PrivateKey.getInstance(var0, var1);
   }

   public static JSAFE_MAC d(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return c(var0, var1, a(var2));
   }

   public static JSAFE_AsymmetricCipher e(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return f(var0, var1, var2, a(var3));
   }

   public static JSAFE_PrivateKey e(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return i(var0, var1, a(var2));
   }

   public static JSAFE_MessageDigest f(byte[] var0, int var1, String var2, CertJ var3) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return e(var0, var1, var2, a(var3));
   }

   public static JSAFE_SecretKey f(String var0, String var1, CertJ var2) throws JSAFE_UnimplementedException, JSAFE_InvalidParameterException {
      return e(var0, var1, a(var2));
   }
}
