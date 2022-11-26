package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;

/** @deprecated */
public class CertRequest implements Serializable, Cloneable {
   private int reqId;
   private CertTemplate template;
   private Controls controls;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;
   private CertPathCtx theCertPathCtx;
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;

   /** @deprecated */
   public CertRequest() {
   }

   /** @deprecated */
   public void decodeCertRequest(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("CertRequest Encoding is null.");
      } else {
         this.special = var3;
         SequenceContainer var4 = new SequenceContainer(var3);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12288);
         EncodedContainer var8 = new EncodedContainer(77824);
         ASN1Container[] var9 = new ASN1Container[]{var4, var6, var7, var8, var5};

         try {
            ASN1.berDecode(var1, var2, var9);
            this.reqId = var6.getValueAsInt();
            this.template = new CertTemplate(var7.data, var7.dataOffset, 0);
            if (var8.dataPresent) {
               this.controls = new Controls();
               this.controls.setEnvironment(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
               this.controls.decodeControls(var8.data, var8.dataOffset, 0);
            }

         } catch (ASN_Exception var11) {
            throw new CRMFException("Could not BER decode the cert request info.", var11);
         }
      }
   }

   /** @deprecated */
   public CertRequest(int var1, CertTemplate var2, Controls var3) throws CRMFException {
      this.reqId = var1;
      if (var2 == null) {
         throw new CRMFException("Cert Template is null.");
      } else {
         try {
            this.template = (CertTemplate)var2.clone();
            if (var3 != null) {
               this.controls = (Controls)var3.clone();
            }

         } catch (CloneNotSupportedException var5) {
            throw new CRMFException("Unable to create CertRequest object", var5);
         }
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.", var3);
         }
      }
   }

   /** @deprecated */
   public void setRequestID(int var1) {
      this.reqId = var1;
   }

   /** @deprecated */
   public int getRequestID() {
      return this.reqId;
   }

   /** @deprecated */
   public void setCertTemplate(CertTemplate var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Cert Template is NULL.");
      } else {
         try {
            this.template = (CertTemplate)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Unable to set CertTemplate", var3);
         }
      }
   }

   /** @deprecated */
   public CertTemplate getCertTemplate() throws CRMFException {
      if (this.template == null) {
         return null;
      } else {
         try {
            return (CertTemplate)this.template.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Unable to get CertTemplate", var2);
         }
      }
   }

   /** @deprecated */
   public void setControls(Controls var1) throws CRMFException {
      try {
         if (var1 != null) {
            this.controls = (Controls)var1.clone();
         }

      } catch (CloneNotSupportedException var3) {
         throw new CRMFException("Unable to set Controls", var3);
      }
   }

   /** @deprecated */
   public Controls getControls() throws CRMFException {
      if (this.controls == null) {
         return null;
      } else {
         try {
            return (Controls)this.controls.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException("Unable to get Controls", var2);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.encodeInit(var1);
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified array is null.");
      } else {
         try {
            if (this.asn1Template == null || this.special != var3) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode CertRequest.", var6);
         }
      }
   }

   private int encodeInit(int var1) throws CRMFException {
      try {
         this.special = var1;
         SequenceContainer var2 = new SequenceContainer(var1, true, 0);
         EndContainer var3 = new EndContainer();
         IntegerContainer var4 = new IntegerContainer(0, true, 0, this.reqId);
         if (this.template == null) {
            throw new CRMFException("Cert Template is not set.");
         } else {
            int var5 = this.template.getDERLen(0);
            byte[] var6 = new byte[var5];
            var5 = this.template.getDEREncoding(var6, 0, 0);
            EncodedContainer var7 = new EncodedContainer(0, true, 0, var6, 0, var5);
            boolean var8 = false;
            int var9 = 0;
            byte[] var10 = null;
            if (this.controls != null) {
               var8 = true;
               var9 = this.controls.getDERLen(65536);
               var10 = new byte[var9];
               var9 = this.controls.getDEREncoding(var10, 0, 0);
            }

            EncodedContainer var11 = new EncodedContainer(65536, var8, 0, var10, 0, var9);
            ASN1Container[] var12 = new ASN1Container[]{var2, var4, var7, var11, var3};
            this.asn1Template = new ASN1Template(var12);
            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var13) {
         throw new CRMFException(var13);
      }
   }

   /** @deprecated */
   public void setEnvironment(CertJ var1, CertPathCtx var2, JSAFE_PublicKey var3, JSAFE_PrivateKey var4) {
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
      if (var3 != null) {
         this.pubKey = var3;
      }

      if (var4 != null) {
         this.privKey = var4;
      }

   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof CertRequest) {
         CertRequest var2 = (CertRequest)var1;
         if (this.special != var2.special) {
            return false;
         } else if (this.reqId != var2.reqId) {
            return false;
         } else {
            if (this.template != null) {
               if (!this.template.equals(var2.template)) {
                  return false;
               }
            } else if (var2.template != null) {
               return false;
            }

            if (this.controls != null) {
               if (!this.controls.equals(var2.controls)) {
                  return false;
               }
            } else if (var2.controls != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.controls);
      var2 = var1 * var2 + this.reqId;
      var2 = var1 * var2 + this.special;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.template);
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CertRequest var1 = new CertRequest();
      var1.setEnvironment(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
      var1.special = this.special;
      var1.reqId = this.reqId;
      if (this.template != null) {
         var1.template = (CertTemplate)this.template.clone();
      }

      if (this.controls != null) {
         var1.controls = (Controls)this.controls.clone();
      }

      try {
         if (this.asn1Template != null) {
            var1.encodeInit(this.special);
         }

         return var1;
      } catch (CRMFException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }
}
