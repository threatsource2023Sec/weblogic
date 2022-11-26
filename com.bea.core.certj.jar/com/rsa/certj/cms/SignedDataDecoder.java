package com.rsa.certj.cms;

import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;
import java.security.cert.CertStore;

/** @deprecated */
public final class SignedDataDecoder extends Decoder {
   SignedDataDecoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      super(var1, var2);
   }

   /** @deprecated */
   public X509Certificate[] getCertificates() {
      com.rsa.jsafe.cms.SignedDataDecoder var1 = (com.rsa.jsafe.cms.SignedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getCertificates());
   }

   /** @deprecated */
   public X509CRL[] getCRLs() {
      com.rsa.jsafe.cms.SignedDataDecoder var1 = (com.rsa.jsafe.cms.SignedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getCRLs());
   }

   /** @deprecated */
   public SignerInfo[] getSignerInfos() {
      com.rsa.jsafe.cms.SignedDataDecoder var1 = (com.rsa.jsafe.cms.SignedDataDecoder)this.a;
      return com.rsa.certj.cms.a.a(var1.getSignerInfos());
   }

   /** @deprecated */
   public boolean verify(SignerInfo var1, CertPathCtx var2) throws CMSException {
      com.rsa.jsafe.cms.SignedDataDecoder var3 = (com.rsa.jsafe.cms.SignedDataDecoder)this.a;
      JsafeJCE var4 = com.rsa.certj.cms.a.a(this.b);
      CertStore var5 = null;
      if (var2 != null) {
         var5 = com.rsa.certj.cms.a.a(var2.getDatabase(), var4);
      }

      com.rsa.jsafe.cms.SignerInfo var6 = null;
      if (var1 != null) {
         var6 = var1.a(var4);
      }

      if (var2 != null && var2.getTrustedCerts() != null && var2.getTrustedCerts().length != 0) {
         CertStore var7 = com.rsa.certj.cms.a.a(var2.getTrustedCerts(), var4);
         boolean var8 = var2.isFlagRaised(4);

         try {
            return var3.verify(var6, var7, var5, var8);
         } catch (com.rsa.jsafe.cms.CMSException var10) {
            throw new CMSException(var10);
         }
      } else {
         try {
            return var3.verify(var6, var5);
         } catch (com.rsa.jsafe.cms.CMSException var11) {
            throw new CMSException(var11);
         }
      }
   }
}
