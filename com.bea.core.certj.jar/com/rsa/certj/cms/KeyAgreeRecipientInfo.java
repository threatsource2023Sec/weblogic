package com.rsa.certj.cms;

import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.provider.JsafeJCE;
import java.security.PrivateKey;
import java.security.PublicKey;

/** @deprecated */
public final class KeyAgreeRecipientInfo extends RecipientInfo {
   private final X509Certificate a;
   private final JSAFE_PublicKey b;
   private final JSAFE_PrivateKey c;
   private final X509Certificate d;
   private com.rsa.jsafe.cms.KeyAgreeRecipientInfo e;

   KeyAgreeRecipientInfo(com.rsa.jsafe.cms.KeyAgreeRecipientInfo var1) {
      this.e = var1;
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
   }

   KeyAgreeRecipientInfo(X509Certificate var1, JSAFE_PublicKey var2, JSAFE_PrivateKey var3, X509Certificate var4) {
      try {
         this.a = (X509Certificate)((X509Certificate)(var1 == null ? null : var1.clone()));
         this.b = (JSAFE_PublicKey)((JSAFE_PublicKey)(var2 == null ? null : var2.clone()));
         this.c = (JSAFE_PrivateKey)((JSAFE_PrivateKey)(var3 == null ? null : var3.clone()));
         this.d = (X509Certificate)((X509Certificate)(var4 == null ? null : var4.clone()));
      } catch (CloneNotSupportedException var6) {
         throw new IllegalArgumentException(var6);
      }
   }

   /** @deprecated */
   public X500Name getOrigIssuer() throws CMSException {
      if (this.e != null) {
         return com.rsa.certj.cms.a.a(this.e.getOrigIssuer());
      } else {
         return this.a == null ? null : this.a.getIssuerName();
      }
   }

   /** @deprecated */
   public byte[] getOrigSerialNumber() {
      if (this.e != null) {
         return this.e.getOrigSerialNumber().toByteArray();
      } else {
         return this.a == null ? null : this.a.getSerialNumber();
      }
   }

   /** @deprecated */
   public byte[] getOrigSubjectKeyIdentifier() {
      if (this.e != null) {
         return this.e.getOrigSubjectKeyIdentifier();
      } else {
         return this.a == null ? null : this.a.getSubjectUniqueID();
      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getOrigPublicKey() throws CMSException {
      if (this.e != null) {
         return com.rsa.certj.cms.a.a(this.e.getOrigPublicKey());
      } else if (this.b != null) {
         try {
            return (JSAFE_PublicKey)this.b.clone();
         } catch (CloneNotSupportedException var2) {
            throw new IllegalArgumentException(var2);
         }
      } else if (this.a != null) {
         try {
            return this.a.getSubjectPublicKey("Java");
         } catch (CertificateException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   /** @deprecated */
   public X500Name getRecipientIssuer() throws CMSException {
      if (this.e != null) {
         return com.rsa.certj.cms.a.a(this.e.getRecipientIssuer());
      } else {
         return this.d == null ? null : this.d.getIssuerName();
      }
   }

   /** @deprecated */
   public byte[] getRecipientSerialNumber() {
      if (this.e != null) {
         return this.e.getRecipientSerialNumber().toByteArray();
      } else {
         return this.d == null ? null : this.d.getSerialNumber();
      }
   }

   /** @deprecated */
   public byte[] getRecipientSubjectKeyIdentifier() {
      if (this.e != null) {
         return this.e.getRecipientSubjectKeyIdentifier();
      } else {
         return this.d == null ? null : this.d.getSubjectUniqueID();
      }
   }

   com.rsa.jsafe.cms.RecipientInfo a(JsafeJCE var1) throws CMSException {
      if (this.e == null) {
         java.security.cert.X509Certificate var2 = com.rsa.certj.cms.a.a(this.a, var1);
         java.security.cert.X509Certificate var3 = com.rsa.certj.cms.a.a(this.d, var1);
         PublicKey var4 = com.rsa.certj.cms.a.a(this.b, var1);
         PrivateKey var5 = com.rsa.certj.cms.a.a(this.c, var1);

         try {
            if (var4 == null) {
               this.e = com.rsa.jsafe.cms.InfoObjectFactory.newKeyAgreeRecipientInfo(var2, var5, var3);
            } else {
               this.e = com.rsa.jsafe.cms.InfoObjectFactory.newKeyAgreeRecipientInfo(var4, var5, var3);
            }
         } catch (com.rsa.jsafe.cms.CMSException var7) {
            throw new CMSException(var7);
         }
      }

      return this.e;
   }
}
