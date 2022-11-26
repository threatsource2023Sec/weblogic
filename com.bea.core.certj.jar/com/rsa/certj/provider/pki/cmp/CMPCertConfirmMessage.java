package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import java.util.StringTokenizer;

/** @deprecated */
public final class CMPCertConfirmMessage extends CMPRequestCommon {
   private PKIStatusInfo statusInfo;
   private Certificate certReturned;

   /** @deprecated */
   public CMPCertConfirmMessage(CMPCertResponseCommon var1, PKIStatusInfo var2) throws InvalidParameterException {
      super(24, (byte[])null);
      if (var1 == null) {
         throw new InvalidParameterException("CMPCertConfirmMessage.CMPCertConfirmMessage: response should not be null.");
      } else {
         this.setRecipNonce(var1.getSenderNonce());
         this.statusInfo = var2;
         this.certReturned = var1.getCertificate();
         if (this.certReturned == null) {
            throw new InvalidParameterException("CMPCertConfirmMessage.CMPCertConfirmMessage: response should contain certificate.");
         } else {
            this.setTransactionID(var1.getTransactionID());
         }
      }
   }

   Certificate getCertificateReturned() {
      return this.certReturned;
   }

   /** @deprecated */
   protected byte[] derEncodeBody(CertJ var1) throws CMPException {
      a var2 = new a(CertJInternalHelper.context(var1), this.statusInfo, this.certReturned, var1.getDevice());
      return var2.a(10485760 | this.getMessageType());
   }

   private final class b {
      private PKIStatusInfo b;
      private byte[] c;
      private final c d;

      private b(c var2, PKIStatusInfo var3, Certificate var4, String var5) throws CMPException {
         this.d = var2;
         this.b = var3;
         this.c = this.a(var4, var5);
      }

      private byte[] a() throws CMPException {
         try {
            SequenceContainer var1 = new SequenceContainer(0, true, 0);
            EndContainer var2 = new EndContainer();
            OctetStringContainer var3 = new OctetStringContainer(0, true, 0, this.c, 0, this.c.length);
            EncodedContainer var4;
            if (this.b == null) {
               var4 = new EncodedContainer(65536, false, 0, (byte[])null, 0, 0);
            } else {
               try {
                  int var5 = this.b.getDERLen(65536);
                  byte[] var6 = new byte[var5];
                  this.b.getDEREncoding(var6, 0, 65536);
                  var4 = new EncodedContainer(65536, true, 0, var6, 0, var6.length);
               } catch (PKIException var8) {
                  throw new CMPException("CMPCertConfirmMessage$CertStatus.derEncode: unable to encode StatusInfo");
               }
            }

            ASN1Container[] var10 = new ASN1Container[]{var1, var3, new IntegerContainer(0, true, 0, 0), var4, var2};
            ASN1Template var11 = new ASN1Template(var10);
            byte[] var7 = new byte[var11.derEncodeInit()];
            var11.derEncode(var7, 0);
            return var7;
         } catch (ASN_Exception var9) {
            throw new CMPException("CMPCertConfirmMessage$CertStatus.derEncode: encoding CertStatus failed.", var9);
         }
      }

      private byte[] a(Certificate var1, String var2) throws CMPException {
         if (!(var1 instanceof X509Certificate)) {
            throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: cert has to be an instance of X509Certificate.");
         } else {
            X509Certificate var3 = (X509Certificate)var1;

            String var4;
            try {
               StringTokenizer var5 = new StringTokenizer(var3.getSignatureAlgorithm(), "/");
               if (!var5.hasMoreTokens()) {
                  throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: unable to get signature algorithm from cert.");
               }

               var4 = var5.nextToken();
            } catch (CertificateException var11) {
               throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: unable to get signature algorithm from cert.");
            }

            JSAFE_MessageDigest var12;
            try {
               var12 = h.a(var4, var2, this.d.b);
            } catch (JSAFE_Exception var10) {
               throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: unable to get digest algorithm for " + var4 + ".", var10);
            }

            var12.digestInit();
            byte[] var6 = new byte[var3.getDERLen(0)];

            try {
               var3.getDEREncoding(var6, 0, 0);
            } catch (CertificateException var9) {
               throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: unable to get DER encoding of cert(", var9);
            }

            try {
               var12.digestUpdate(var6, 0, var6.length);
               return var12.digestFinal();
            } catch (JSAFE_Exception var8) {
               throw new CMPException("CMPCertConfirmMessage$CertStatus.createCertHash: unable to compute digest.", var8);
            }
         }
      }

      // $FF: synthetic method
      b(c var2, PKIStatusInfo var3, Certificate var4, String var5, Object var6) throws CMPException {
         this(var2, var3, var4, var5);
      }
   }

   private final class a {
      private b b;

      private a(c var2, PKIStatusInfo var3, Certificate var4, String var5) throws CMPException {
         this.b = CMPCertConfirmMessage.this.new b(var2, var3, var4, var5);
      }

      private byte[] a(int var1) throws CMPException {
         try {
            OfContainer var2 = new OfContainer(var1, 12288, new EncodedContainer(12288));
            byte[] var3 = this.b.a();
            var2.addContainer(new EncodedContainer(0, true, 0, var3, 0, var3.length));
            ASN1Container[] var4 = new ASN1Container[]{var2};
            ASN1Template var5 = new ASN1Template(var4);
            byte[] var6 = new byte[var5.derEncodeInit()];
            var5.derEncode(var6, 0);
            return var6;
         } catch (ASN_Exception var7) {
            throw new CMPException("CMPCertConfirmMessage$CertConfirmContent.derEncode: encoding CertConfirmContent failed.", var7);
         }
      }

      // $FF: synthetic method
      a(c var2, PKIStatusInfo var3, Certificate var4, String var5, Object var6) throws CMPException {
         this(var2, var3, var4, var5);
      }
   }
}
