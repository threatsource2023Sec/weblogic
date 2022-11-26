package com.rsa.certj.provider.path;

import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Vector;

final class PKIXCertPathResult implements CertPathResult {
   private boolean a;
   private String b;
   private Vector c;
   private String d;
   private byte[] e;
   private byte[][] f;
   private final c g;

   PKIXCertPathResult(c var1) {
      this.g = var1;
   }

   public Vector getValidPolicies() {
      return this.c;
   }

   byte[] a() {
      return this.e;
   }

   String b() {
      return this.d;
   }

   byte[][] c() {
      return this.f;
   }

   public String getMessage() {
      return this.b;
   }

   public boolean getValidationResult() {
      return this.a;
   }

   void a(Vector var1) {
      this.c = var1;
   }

   void a(byte[] var1) {
      this.e = var1;
   }

   void a(String var1) {
      this.d = var1;
   }

   void a(byte[][] var1) {
      this.f = var1;
   }

   void b(String var1) {
      this.b = var1;
   }

   public void setValidationResult(boolean var1) {
      this.a = var1;
   }

   public JSAFE_PublicKey getSubjectPublicKey(String var1) throws CertificateException {
      if ("DSA".equals(this.d) && this.f != null) {
         JSAFE_PublicKey var2;
         try {
            var2 = h.h(this.d, var1, this.g.b);
         } catch (JSAFE_Exception var7) {
            throw new CertificateException(var7);
         }

         int var3 = this.f.length;
         byte[][] var4 = new byte[var3 + 1][];
         System.arraycopy(this.f, 0, var4, 0, var3);
         var4[var3] = this.e;

         try {
            var2.setKeyData(var4);
            return var2;
         } catch (JSAFE_InvalidKeyException var6) {
            throw new CertificateException(var6);
         }
      } else {
         return null;
      }
   }
}
