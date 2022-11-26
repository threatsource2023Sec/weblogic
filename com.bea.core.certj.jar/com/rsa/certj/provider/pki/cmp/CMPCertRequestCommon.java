package com.rsa.certj.provider.pki.cmp;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.crmf.CRMFException;
import com.rsa.certj.crmf.CertReqMessages;
import com.rsa.certj.crmf.CertRequest;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.certj.crmf.Control;
import com.rsa.certj.crmf.Controls;
import com.rsa.certj.crmf.POPOPrivKey;
import com.rsa.certj.crmf.POPOSigningKeyInput;
import com.rsa.certj.crmf.ProofOfPossession;
import com.rsa.certj.crmf.RegInfo;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import java.util.Date;

/** @deprecated */
public abstract class CMPCertRequestCommon extends CMPRequestCommon {
   /** @deprecated */
   protected Controls controls;
   /** @deprecated */
   protected CertTemplate certTemplate;
   private CertRequest certRequest;
   private ProofOfPossession pop;

   /** @deprecated */
   protected CMPCertRequestCommon(int var1, CertTemplate var2, Controls var3) throws InvalidParameterException {
      super(var1, (byte[])null);
      if (var2 == null) {
         throw new InvalidParameterException("CMPCertRequestCommon.CMPCertRequestCommon: certTemplate should not be null.");
      } else {
         this.certTemplate = var2;
         this.controls = var3;
      }
   }

   /** @deprecated */
   protected CMPCertRequestCommon(int var1, CertTemplate var2, Control var3) throws InvalidParameterException {
      super(var1, (byte[])null);
      if (var2 == null) {
         throw new InvalidParameterException("CMPCertRequestCommon.CMPCertRequestCommon: certTemplate should not be null.");
      } else {
         this.certTemplate = var2;
         if (var3 != null) {
            try {
               Controls var4 = new Controls();
               var4.addControl(var3);
               this.controls = var4;
            } catch (CRMFException var5) {
               throw new InvalidParameterException("CMPCertRequestCommon:CMPCertRequestCommon: creation of Controls object failed.", var5);
            }
         }

      }
   }

   /** @deprecated */
   public Certificate getCertificateTemplate() {
      try {
         X509Certificate var1 = new X509Certificate();
         byte[] var2 = this.certTemplate.getSerialNumber();
         if (var2 != null && var2.length > 0) {
            var1.setSerialNumber(var2, 0, var2.length);
         }

         X500Name var3 = this.certTemplate.getIssuerName();
         if (var3 != null) {
            var1.setIssuerName(var3);
         }

         Date var4 = this.certTemplate.getStartDate();
         Date var5 = this.certTemplate.getEndDate();
         if (var4 != null && var5 != null) {
            var1.setValidity(var4, var5);
         }

         var3 = this.certTemplate.getSubjectName();
         if (var3 != null) {
            var1.setSubjectName(var3);
         }

         JSAFE_PublicKey var6 = this.certTemplate.getSubjectPublicKey();
         if (var6 != null) {
            var1.setSubjectPublicKey(var6);
         }

         byte[] var7 = this.certTemplate.getIssuerUniqueID();
         if (var7 != null) {
            var1.setIssuerUniqueID(var7, 0, var7.length);
         }

         var7 = this.certTemplate.getSubjectUniqueID();
         if (var7 != null) {
            var1.setSubjectUniqueID(var7, 0, var7.length);
         }

         X509V3Extensions var8 = this.certTemplate.getExtensions();
         if (var8 != null) {
            var1.setExtensions(var8);
         }

         return var1;
      } catch (Exception var9) {
         return null;
      }
   }

   /** @deprecated */
   protected CertTemplate getCertTemplate() {
      return this.certTemplate;
   }

   /** @deprecated */
   protected byte[] derEncodeBody(CertJ var1) throws CMPException {
      int var2 = this.getMessageType();

      try {
         if (this.certTemplate.getSubjectPublicKey() != null && this.pop == null) {
            throw new CMPException("CMPCertRequestCommon.derEncode: pop has not been set. Use generateProofOfPossession method to create pop.");
         } else {
            RegInfo var3 = CMP.convertRegInfo(this.getRegInfo());
            if (this.certRequest == null) {
               this.certRequest = this.createCertRequest();
            }

            CertReqMessages var4 = new CertReqMessages(this.certRequest, this.pop, var3);
            int var5 = var4.getDERLen(10485760 | var2);
            byte[] var6 = new byte[var5];
            var4.getDEREncoding(var6, 0, 10485760 | var2);
            return var6;
         }
      } catch (CRMFException var7) {
         throw new CMPException("CMPCertRequestCommon.derEncode: unable to encode CertReqMessages(", var7);
      }
   }

   /** @deprecated */
   protected void setPop(CMPPOPGenerationInfoSignature var1, JSAFE_PrivateKey var2, CertJ var3) throws CMPException {
      JSAFE_SecureRandom var4;
      try {
         var4 = var3.getRandomObject();
      } catch (CertJException var10) {
         throw new CMPException("CMPCertRequestCommon.setPop: unable to get a registered random service(", var10);
      }

      try {
         this.pop = new ProofOfPossession(1, var3);
         this.pop.setSignatureAlgorithm(var1.getSignatureAlgorithm());
         X500Name var5 = this.certTemplate.getSubjectName();
         if (var5 == null) {
            POPOSigningKeyInput var6 = new POPOSigningKeyInput();
            var6.setSubjectPublicKey(this.certTemplate.getSubjectPublicKey());
            if (var1.authBySenderName()) {
               GeneralName var7 = var1.getSender();
               var6.setSenderName(var7);
            } else {
               char[] var11 = var1.getSharedSecret();
               var6.setSharedSecret(var11, 0, var11.length);
               byte[] var8 = var1.getSalt();
               var6.setSalt(var8, 0, var8.length);
               var6.setIterationCount(var1.getIterationCount());
            }

            this.pop.setPOPOSigningKeyInput(var6);
         } else {
            this.pop.setCertRequest(this.createCertRequest());
         }

         this.pop.signPOP(var3.getDevice(), var2, var4);
      } catch (CRMFException var9) {
         throw new CMPException("CMPCertRequestCommon.setPop: ", var9);
      }
   }

   /** @deprecated */
   protected void setPop(int var1, CMPPOPGenerationInfoNonSignature var2) throws CMPException {
      try {
         this.pop = new ProofOfPossession(var1);
         POPOPrivKey var3 = new POPOPrivKey();
         int var4 = var2.getMethod();
         switch (var4) {
            case 2:
               var3.setType(1);
               var3.setSubsequentMessage(0);
               this.pop.setPOPOPrivKey(var3);
               return;
            default:
               throw new CMPException("CMPCertRequestCommon.setPop: unsupported POP method(" + var4 + ").");
         }
      } catch (CRMFException var5) {
         throw new CMPException("CMPCertRequestCommon.setPop: ", var5);
      }
   }

   /** @deprecated */
   protected void setPop(int var1) throws CMPException {
      try {
         this.pop = new ProofOfPossession(var1);
      } catch (CRMFException var3) {
         throw new CMPException("CMPCertRequestCommon.setPop: ", var3);
      }
   }

   /** @deprecated */
   protected Controls getControls() {
      return this.controls;
   }

   private CertRequest createCertRequest() throws CMPException {
      try {
         return new CertRequest(0, this.certTemplate, this.controls);
      } catch (CRMFException var2) {
         throw new CMPException("CMPCertRequestCommon.createCertRequest: unable to instantiate CertRequest(", var2);
      }
   }
}
