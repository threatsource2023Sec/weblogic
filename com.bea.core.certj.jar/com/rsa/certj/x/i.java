package com.rsa.certj.x;

import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.jcp.SuiteBChecker;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.provider.JsafeJCE;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertPathValidatorException;
import java.security.spec.X509EncodedKeySpec;

public class i {
   public static boolean a(X509Certificate var0, JSAFE_PublicKey var1, c var2) throws CertPathException {
      try {
         byte[] var3 = new byte[var0.getDERLen(0)];

         java.security.cert.X509Certificate var4;
         try {
            var0.getDEREncoding(var3, 0, 0);
            var4 = d.a(var3, var2);
         } catch (Exception var6) {
            throw new CertPathException("Unable to parse certificate", var6);
         }

         return var1 == null ? SuiteBChecker.isSuiteBCompliant(var4) : SuiteBChecker.isSuiteBCompliant(var4, a(var1));
      } catch (CertPathValidatorException var7) {
         throw new CertPathException("Unable to check for SuiteB Compliance", var7);
      }
   }

   public static boolean a(X509CRL var0) throws CertStatusException {
      c var2 = c.a();
      byte[] var4 = new byte[var0.getDERLen(0)];

      java.security.cert.X509CRL var3;
      try {
         var0.getDEREncoding(var4, 0, 0);
         var3 = d.b(var4, var2);
      } catch (Exception var6) {
         throw new CertStatusException("Unable to parse CRL", var6);
      }

      boolean var1 = SuiteBChecker.isSuiteBCompliant(var3, (PublicKey)null);
      return var1;
   }

   private static PublicKey a(JSAFE_PublicKey var0) throws CertPathException {
      String[] var1 = var0.getSupportedGetFormats();
      String var2 = null;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if ("RSAPublicKeyBER".equals(var1[var3]) || "DSAPublicKeyBER".equals(var1[var3]) || "ECPublicKeyBER".equals(var1[var3])) {
            var2 = var1[var3];
            break;
         }
      }

      try {
         byte[][] var8 = var0.getKeyData(var2);
         KeyFactory var4 = KeyFactory.getInstance(var0.getAlgorithm(), new JsafeJCE());
         PublicKey var5 = var4.generatePublic(new X509EncodedKeySpec(var8[0]));
         return var5;
      } catch (Exception var7) {
         throw new CertPathException("Unable to parse JSAFE_PublicKey for SuiteB compliance checks.", var7);
      }
   }
}
