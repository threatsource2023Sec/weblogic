package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_UnimplementedException;

/** @deprecated */
public class OCSPResponderInternal extends OCSPResponder {
   private static final int NO_SPECIAL = 0;
   private byte[] taggedSignerNameDER;

   /** @deprecated */
   protected OCSPResponderInternal(CertJ var1, OCSPResponder var2) throws InvalidParameterException, NameException, CertificateException {
      super(var2.getProfile(), var2.getFlags(), var2.getDestList(), var2.getProxyList(), var2.getRequestControl(), var2.getResponderCert(), var2.getResponderCACerts(), var2.getDatabase(), var2.getTimeTolerance());
      X509Certificate var3 = null;
      OCSPRequestControl var4 = var2.getRequestControl();
      if (var4 != null) {
         var3 = var4.getSignerCert();
      }

      if (var3 != null) {
         X500Name var5 = var3.getSubjectName();
         this.taggedSignerNameDER = new byte[var5.getDERLen(0)];
         var5.getDEREncoding(this.taggedSignerNameDER, 0, 0);
         String var6 = var1.getDevice();
         JSAFE_PublicKey var7 = var3.getSubjectPublicKey(var6);

         byte[][] var8;
         try {
            var8 = var7.getKeyData("RSAPublicKeyBER");
         } catch (JSAFE_UnimplementedException var12) {
            try {
               var8 = var7.getKeyData("DSAPublicKeyBER");
            } catch (JSAFE_UnimplementedException var11) {
               throw new CertificateException("Cannot get key BER");
            }
         }

         if (var8.length != 1) {
            throw new CertificateException("#keys>1");
         }
      }

   }

   /** @deprecated */
   protected String getSignatureAlgorithm() {
      return super.getRequestControl().getSignatureAlgorithm();
   }
}
