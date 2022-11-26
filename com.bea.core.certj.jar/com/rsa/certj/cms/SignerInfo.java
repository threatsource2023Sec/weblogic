package com.rsa.certj.cms;

import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.cert.Attribute;
import com.rsa.jsafe.provider.JsafeJCE;
import java.math.BigInteger;
import java.security.PrivateKey;

/** @deprecated */
public final class SignerInfo {
   private final JSAFE_PrivateKey a;
   private final X509Certificate b;
   private final String c;
   private final X501Attributes d;
   private final X501Attributes e;
   private com.rsa.jsafe.cms.SignerInfo f;

   SignerInfo(com.rsa.jsafe.cms.SignerInfo var1) {
      this.f = var1;
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = null;
   }

   SignerInfo(JSAFE_PrivateKey var1, X509Certificate var2, String var3, X501Attributes var4, X501Attributes var5) {
      this.f = null;

      try {
         this.a = var1 == null ? null : (JSAFE_PrivateKey)var1.clone();
         this.b = var2 == null ? null : (X509Certificate)var2.clone();
         this.c = var3;
         this.d = var4 == null ? null : (X501Attributes)var4.clone();
         this.e = var5 == null ? null : (X501Attributes)var5.clone();
      } catch (CloneNotSupportedException var7) {
         throw new IllegalArgumentException(var7);
      }
   }

   /** @deprecated */
   public X501Attributes getSignedAttributes() throws CMSException {
      return this.f == null ? new X501Attributes() : com.rsa.certj.cms.a.a(this.f.getSignedAttributes());
   }

   /** @deprecated */
   public X501Attributes getUnsignedAttributes() throws CMSException {
      return this.f == null ? new X501Attributes() : com.rsa.certj.cms.a.a(this.f.getUnsignedAttributes());
   }

   /** @deprecated */
   public X500Name getIssuer() throws CMSException {
      return this.f == null ? null : com.rsa.certj.cms.a.a(this.f.getIssuer());
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      if (this.f == null) {
         return null;
      } else {
         BigInteger var1 = this.f.getSerialNumber();
         return var1 == null ? null : var1.toByteArray();
      }
   }

   /** @deprecated */
   public byte[] getSubjectKeyIdentifier() {
      return this.f == null ? null : this.f.getSubjectKeyIdentifier();
   }

   com.rsa.jsafe.cms.SignerInfo a(JsafeJCE var1) throws CMSException {
      if (this.f == null) {
         java.security.cert.X509Certificate var2 = com.rsa.certj.cms.a.a(this.b, var1);
         PrivateKey var3 = com.rsa.certj.cms.a.a(this.a, var1);
         Attribute[] var4 = com.rsa.certj.cms.a.a(this.d);
         Attribute[] var5 = com.rsa.certj.cms.a.a(this.e);

         try {
            this.f = com.rsa.jsafe.cms.InfoObjectFactory.newSignerInfo(var3, var2, this.c, var4, var5);
         } catch (com.rsa.jsafe.cms.CMSException var7) {
            throw new CMSException(var7);
         }
      }

      return this.f;
   }
}
