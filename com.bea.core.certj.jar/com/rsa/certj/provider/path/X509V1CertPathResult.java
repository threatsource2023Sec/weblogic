package com.rsa.certj.provider.path;

import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Vector;

final class X509V1CertPathResult implements CertPathResult {
   private boolean a;
   private String b;
   private X509Certificate c;

   public Vector getValidPolicies() {
      return null;
   }

   public String getMessage() {
      return this.b;
   }

   public boolean getValidationResult() {
      return this.a;
   }

   void a(String var1) {
      this.b = var1;
   }

   void a(X509Certificate var1) {
      this.c = var1;
   }

   public void setValidationResult(boolean var1) {
      this.a = var1;
   }

   public JSAFE_PublicKey getSubjectPublicKey(String var1) throws CertificateException {
      return this.c == null ? null : this.c.getSubjectPublicKey(var1);
   }
}
