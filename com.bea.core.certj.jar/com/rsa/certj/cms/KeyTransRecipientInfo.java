package com.rsa.certj.cms;

import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public final class KeyTransRecipientInfo extends RecipientInfo {
   private X509Certificate a;
   private String b;
   private com.rsa.jsafe.cms.KeyTransRecipientInfo c;

   KeyTransRecipientInfo(com.rsa.jsafe.cms.KeyTransRecipientInfo var1) {
      this.c = var1;
      this.a = null;
      this.b = null;
   }

   KeyTransRecipientInfo(X509Certificate var1, String var2) throws CMSException {
      this.c = null;

      try {
         this.a = var1 == null ? null : (X509Certificate)var1.clone();
      } catch (CloneNotSupportedException var4) {
         throw new CMSException(var4);
      }

      this.b = var2;
   }

   /** @deprecated */
   public X500Name getIssuer() throws CMSException {
      if (this.c != null) {
         return com.rsa.certj.cms.a.a(this.c.getIssuer());
      } else {
         return this.a == null ? null : this.a.getIssuerName();
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      if (this.c != null) {
         return this.c.getSerialNumber().toByteArray();
      } else {
         return this.a == null ? null : this.a.getSerialNumber();
      }
   }

   /** @deprecated */
   public byte[] getSubjectKeyIdentifier() {
      if (this.c != null) {
         return this.c.getSubjectKeyIdentifier();
      } else {
         return this.a == null ? null : this.a.getSubjectUniqueID();
      }
   }

   com.rsa.jsafe.cms.RecipientInfo a(JsafeJCE var1) throws CMSException {
      if (this.c == null) {
         java.security.cert.X509Certificate var2 = com.rsa.certj.cms.a.a(this.a, var1);

         try {
            this.c = com.rsa.jsafe.cms.InfoObjectFactory.newKeyTransRecipientInfo(var2, this.b);
         } catch (com.rsa.jsafe.cms.CMSException var4) {
            throw new CMSException(var4);
         }
      }

      return this.c;
   }
}
