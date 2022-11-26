package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.crmf.CRMFException;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.jsafe.JSAFE_PublicKey;

/** @deprecated */
public final class CMPRevokeRequestMessage extends CMPRequestCommon {
   private final X509Certificate[] certsToBeRevoked;
   private X509V3Extensions[] crlEntryExtensionsList;

   /** @deprecated */
   public CMPRevokeRequestMessage(X509Certificate[] var1, X509V3Extensions[] var2) throws InvalidParameterException {
      super(11, (byte[])null);
      if (var1 != null && var1.length != 0) {
         int var3 = var1.length;
         if (var2 != null && var3 != var2.length) {
            throw new InvalidParameterException("CMPRevokeRequestMessage.CMPRevokeRequestMessage: certsToBeRevoked and crlEntryExtensionsList should have the same number of elements.");
         } else {
            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
               if (var1[var4] == null) {
                  throw new InvalidParameterException("CMPRevokeRequestMessage.CMPRevokeRequestMessage: An element of certsToBeRevoked should not be null.");
               }
            }

            if (var2 != null) {
               for(var4 = 0; var4 < var3; ++var4) {
                  X509V3Extensions var5 = var2[var4];
                  if (var5 != null) {
                     this.checkCRLEntryExtensions(var5);
                  }
               }
            }

            this.certsToBeRevoked = var1;
            this.crlEntryExtensionsList = var2;
         }
      } else {
         throw new InvalidParameterException("CMPRevokeRequestMessage.CMPRevokeRequestMessage: certsToBeRevoked should not be empty.");
      }
   }

   /** @deprecated */
   public CMPRevokeRequestMessage(X509Certificate var1, X509V3Extensions var2) throws InvalidParameterException {
      super(11, (byte[])null, (String[])null, (TypeAndValue[])null);
      if (var1 == null) {
         throw new InvalidParameterException("CMPRevokeRequestMessage.CMPRevokeRequestMessage: certToBeRevoked should not be null.");
      } else {
         this.certsToBeRevoked = new X509Certificate[1];
         this.certsToBeRevoked[0] = var1;
         if (var2 != null) {
            this.checkCRLEntryExtensions(var2);
            this.crlEntryExtensionsList = new X509V3Extensions[1];
            this.crlEntryExtensionsList[0] = var2;
         }

      }
   }

   private void checkCRLEntryExtensions(X509V3Extensions var1) throws InvalidParameterException {
      if (var1.getExtensionsType() != 3) {
         throw new InvalidParameterException("CMPRevokeRequestMessage.checkCRLEntryExtensions: extensions should be of type X509V3Extensions.X509_EXT_TYPE_CRL_ENTRY.");
      } else {
         for(int var2 = 0; var2 < var1.getExtensionCount(); ++var2) {
            try {
               X509V3Extension var3 = var1.getExtensionByIndex(var2);
               if (!(var3 instanceof CRLEntryExtension)) {
                  throw new InvalidParameterException("CMPRevokeRequestMessage.checkCRLEntryExtensions: extensions should consists of CRLEntryExtension.");
               }
            } catch (CertificateException var4) {
            }
         }

      }
   }

   /** @deprecated */
   protected byte[] derEncodeBody(CertJ var1) throws CMPException {
      try {
         int var2 = 10485760 | this.getMessageType();
         OfContainer var3 = new OfContainer(var2, true, 0, 12288, new EncodedContainer(12288));

         for(int var4 = 0; var4 < this.certsToBeRevoked.length; ++var4) {
            X509Certificate var5 = this.certsToBeRevoked[var4];
            X509V3Extensions var6 = null;
            if (this.crlEntryExtensionsList != null) {
               var6 = this.crlEntryExtensionsList[var4];
            }

            CertTemplate var7 = this.certToTemplate(var5, var1);

            byte[] var8;
            try {
               var8 = new byte[var7.getDERLen(0)];
               var7.getDEREncoding(var8, 0, 0);
            } catch (CRMFException var15) {
               throw new CMPException("CMPRevokeRequestMessage.derEncode: unable to encode certDetails.", var15);
            }

            EncodedContainer var9 = new EncodedContainer(0, true, 0, var8, 0, var8.length);
            EncodedContainer var10;
            if (var6 == null) {
               var10 = new EncodedContainer(65536, false, 0, (byte[])null, 0, 0);
            } else {
               byte[] var11 = new byte[var6.getDERLen(0)];
               var6.getDEREncoding(var11, 0, 0);
               var10 = new EncodedContainer(65536, true, 0, var11, 0, var11.length);
            }

            ASN1Container[] var20 = new ASN1Container[]{new SequenceContainer(0, true, 0), var9, var10, new EndContainer()};
            ASN1Template var12 = new ASN1Template(var20);

            try {
               byte[] var13 = new byte[var12.derEncodeInit()];
               var12.derEncode(var13, 0);
               var3.addContainer(new EncodedContainer(0, true, 0, var13, 0, var13.length));
            } catch (ASN_Exception var14) {
               throw new CMPException("CMPRevokeRequestMessage.derEncode: encoding RevDetails failed.", var14);
            }
         }

         ASN1Container[] var17 = new ASN1Container[]{var3};
         ASN1Template var18 = new ASN1Template(var17);
         byte[] var19 = new byte[var18.derEncodeInit()];
         var18.derEncode(var19, 0);
         return var19;
      } catch (ASN_Exception var16) {
         throw new CMPException("CMPRevokeRequestMessage.derEncode: encoding RevDetails failed.", var16);
      }
   }

   private CertTemplate certToTemplate(X509Certificate var1, CertJ var2) throws CMPException {
      try {
         CertTemplate var3 = new CertTemplate();
         byte[] var4 = var1.getSerialNumber();
         var3.setSerialNumber(var4, 0, var4.length);
         X500Name var5 = var1.getIssuerName();
         if (var5 != null) {
            var3.setIssuerName(var5);
         }

         var5 = var1.getSubjectName();
         if (var5 != null) {
            var3.setSubjectName(var5);
         }

         JSAFE_PublicKey var6 = var1.getSubjectPublicKey(var2.getDevice());
         if (var6 != null) {
            var3.setSubjectPublicKey(var6);
         }

         return var3;
      } catch (CRMFException var7) {
         throw new CMPException("CMPRevokeRequestMessage.certToTemplate: unable to convert X509Certificate to CertTemplate.", var7);
      } catch (CertificateException var8) {
         throw new CMPException("CMPRevokeRequestMessage.certToTemplate: unable to set public key of X509Certificate.", var8);
      }
   }
}
