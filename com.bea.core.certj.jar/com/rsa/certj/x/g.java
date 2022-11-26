package com.rsa.certj.x;

import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.jcp.EVChecker;
import java.security.cert.CertificateException;
import java.util.List;

public class g {
   public static boolean a(X509Certificate[] var0, List var1, int var2, c var3) throws CertPathException {
      java.security.cert.X509Certificate[] var5 = new java.security.cert.X509Certificate[var0.length];

      try {
         try {
            for(int var6 = 0; var6 < var0.length; ++var6) {
               byte[] var7 = new byte[var0[var6].getDERLen(0)];
               var0[var6].getDEREncoding(var7, 0, 0);
               var5[var6] = d.a(var7, var3);
            }
         } catch (Exception var8) {
            throw new CertPathException("Unable to parse certificates", var8);
         }

         boolean var4 = EVChecker.isEVCompliant(var5, var1, var2);
         return var4;
      } catch (CertificateException var9) {
         throw new CertPathException("Unable to check for EV Compliance", var9);
      }
   }

   public static int a() {
      return EVChecker.getDefaultCompatibilityMask();
   }
}
