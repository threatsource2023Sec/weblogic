package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.util.Arrays;

/** @deprecated */
public class ProtocolEncryptionKey extends Control {
   private byte[] subjectPublicKeyInfo;
   ASN1Template asn1TemplateValue;
   private int special = 0;

   /** @deprecated */
   public ProtocolEncryptionKey() {
      this.controlTypeFlag = 5;
      this.theOID = new byte[OID_LIST[5].length];
      System.arraycopy(OID_LIST[5], 0, this.theOID, 0, this.theOID.length);
      this.controlTypeString = "ProtocolEncryptionKey";
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("ProtocolEncryptionKey Encoding is null.");
      } else {
         this.setSubjectPublicKey(var1, var2);
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(JSAFE_PublicKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Public key is null.");
      } else {
         try {
            String var2;
            if (var1.getAlgorithm().compareTo("DSA") == 0) {
               var2 = "DSAPublicKeyX957BER";
            } else {
               var2 = var1.getAlgorithm() + "PublicKeyBER";
            }

            byte[][] var3 = var1.getKeyData(var2);
            this.subjectPublicKeyInfo = var3[0];
         } catch (JSAFE_Exception var4) {
            throw new CRMFException("Could not read the public key. ", var4);
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(byte[] var1, int var2) throws CRMFException {
      if (var1 != null && var2 >= 0) {
         JSAFE_PublicKey var3 = null;

         try {
            var1[var2] = 48;
            var3 = h.a(var1, var2, "Java", theCertJ);
            this.setSubjectPublicKey(var3);
         } catch (JSAFE_Exception var8) {
            throw new CRMFException("Could not read the public key. ", var8);
         } finally {
            if (var3 != null) {
               var3.clearSensitiveData();
            }

         }

      } else {
         throw new CRMFException("Public key encoding is null.");
      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getSubjectPublicKey() throws CRMFException {
      if (this.subjectPublicKeyInfo == null) {
         return null;
      } else {
         try {
            return h.a(this.subjectPublicKeyInfo, 0, "Java", (CertJ)theCertJ);
         } catch (JSAFE_Exception var2) {
            throw new CRMFException("Cannot retrieve the public key: ", var2);
         }
      }
   }

   /** @deprecated */
   public byte[] getSubjectPublicKeyBER() {
      return this.subjectPublicKeyInfo == null ? null : (byte[])((byte[])this.subjectPublicKeyInfo.clone());
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in array is null in PKIPublicationInfo control.");
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode PKIPublicationInfo control.");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            throw new CRMFException("Cannot encode PKIPublicationInfo control.", var5);
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      try {
         this.asn1TemplateValue = null;
         if (this.subjectPublicKeyInfo == null) {
            throw new CRMFException("Protocol Encryption Key value is not set.");
         } else {
            EncodedContainer var1 = new EncodedContainer(77824, true, 0, this.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
            ASN1Container[] var2 = new ASN1Container[]{var1};
            this.asn1TemplateValue = new ASN1Template(var2);
            return this.asn1TemplateValue.derEncodeInit();
         }
      } catch (ASN_Exception var3) {
         throw new CRMFException("Cannot encode ProtocolEncryptionKey control.", var3);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ProtocolEncryptionKey var1 = new ProtocolEncryptionKey();
      if (this.subjectPublicKeyInfo != null) {
         var1.subjectPublicKeyInfo = new byte[this.subjectPublicKeyInfo.length];
         System.arraycopy(this.subjectPublicKeyInfo, 0, var1.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
      }

      var1.special = this.special;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ProtocolEncryptionKey) {
         ProtocolEncryptionKey var2 = (ProtocolEncryptionKey)var1;
         return CertJUtils.byteArraysEqual(this.subjectPublicKeyInfo, var2.subjectPublicKeyInfo);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + Arrays.hashCode(this.subjectPublicKeyInfo);
   }
}
