package com.rsa.certj.cms;

import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public final class EnvelopedDataDecoder extends Decoder {
   EnvelopedDataDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public RecipientInfo[] getRecipientInfos() {
      com.rsa.jsafe.cms.EnvelopedDataDecoder var1 = (com.rsa.jsafe.cms.EnvelopedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getRecipientInfos());
   }

   /** @deprecated */
   public X509Certificate[] getOriginatorCertificates() {
      com.rsa.jsafe.cms.EnvelopedDataDecoder var1 = (com.rsa.jsafe.cms.EnvelopedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getOriginatorCertificates());
   }

   /** @deprecated */
   public X509CRL[] getOriginatorCRLs() {
      com.rsa.jsafe.cms.EnvelopedDataDecoder var1 = (com.rsa.jsafe.cms.EnvelopedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getOriginatorCRLs());
   }

   /** @deprecated */
   public X501Attributes getUnprotectedAttributes() throws CMSException {
      com.rsa.jsafe.cms.EnvelopedDataDecoder var1 = (com.rsa.jsafe.cms.EnvelopedDataDecoder)this.a;

      try {
         return com.rsa.certj.cms.a.a(var1.getUnprotectedAttributes());
      } catch (com.rsa.jsafe.cms.CMSException var3) {
         throw new CMSException(var3);
      }
   }

   /** @deprecated */
   public void decryptContentEncryptionKey(RecipientInfo var1, KeyContainer var2) throws CMSException {
      if (var1 == null) {
         throw new CMSException("RecipientInfo cannot be null.");
      } else {
         com.rsa.jsafe.cms.EnvelopedDataDecoder var3 = (com.rsa.jsafe.cms.EnvelopedDataDecoder)this.a;
         JsafeJCE var4 = com.rsa.certj.cms.a.a(this.b);

         try {
            var3.decryptContentEncryptionKey(var1.a(var4), com.rsa.certj.cms.a.a(var2, var4));
         } catch (com.rsa.jsafe.cms.CMSException var6) {
            throw new CMSException(var6);
         }
      }
   }
}
