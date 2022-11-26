package com.rsa.certj.cms;

import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public final class AuthenticatedDataDecoder extends Decoder {
   AuthenticatedDataDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public boolean verify() throws CMSException {
      try {
         return ((com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a).verify();
      } catch (com.rsa.jsafe.cms.CMSException var2) {
         throw new CMSException(var2);
      }
   }

   /** @deprecated */
   public RecipientInfo[] getRecipientInfos() {
      com.rsa.jsafe.cms.AuthenticatedDataDecoder var1 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getRecipientInfos());
   }

   /** @deprecated */
   public X509Certificate[] getOriginatorCertificates() {
      com.rsa.jsafe.cms.AuthenticatedDataDecoder var1 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getOriginatorCertificates());
   }

   /** @deprecated */
   public X509CRL[] getOriginatorCRLs() {
      com.rsa.jsafe.cms.AuthenticatedDataDecoder var1 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getOriginatorCRLs());
   }

   /** @deprecated */
   public X501Attributes getAuthenticatedAttributes() throws CMSException {
      com.rsa.jsafe.cms.AuthenticatedDataDecoder var1 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;

      try {
         return com.rsa.certj.cms.a.a(var1.getAuthenticatedAttributes());
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }

   /** @deprecated */
   public X501Attributes getUnauthenticatedAttributes() throws CMSException {
      com.rsa.jsafe.cms.AuthenticatedDataDecoder var1 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;

      try {
         return com.rsa.certj.cms.a.a(var1.getUnauthenticatedAttributes());
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }

   /** @deprecated */
   public void decryptAuthenticationKey(RecipientInfo var1, KeyContainer var2) throws CMSException {
      if (var1 == null) {
         throw new CMSException("RecipientInfo cannot be null.");
      } else {
         com.rsa.jsafe.cms.AuthenticatedDataDecoder var3 = (com.rsa.jsafe.cms.AuthenticatedDataDecoder)this.a;
         JsafeJCE var4 = com.rsa.certj.cms.a.a(this.b);

         try {
            var3.decryptAuthenticationKey(var1.a(var4), com.rsa.certj.cms.a.a(var2, var4));
         } catch (com.rsa.jsafe.cms.CMSException var6) {
            throw new CMSException(var6);
         }
      }
   }
}
