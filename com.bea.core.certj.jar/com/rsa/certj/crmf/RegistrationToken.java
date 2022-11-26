package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.certj.CertJUtils;
import java.util.Arrays;

/** @deprecated */
public class RegistrationToken extends Control {
   private byte[] value;
   ASN1Template asn1TemplateValue;
   private int special = 0;

   /** @deprecated */
   public RegistrationToken() {
      this.controlTypeFlag = 0;
      this.theOID = new byte[OID_LIST[0].length];
      System.arraycopy(OID_LIST[0], 0, this.theOID, 0, this.theOID.length);
      this.controlTypeString = "RegistrationToken";
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("RegistrationToken encoding is null.");
      } else {
         try {
            UTF8StringContainer var3 = new UTF8StringContainer(this.special);
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            this.value = new byte[var3.dataLen];
            System.arraycopy(var3.data, var3.dataOffset, this.value, 0, var3.dataLen);
         } catch (Exception var5) {
            throw new CRMFException("Cannot decode RegistrationToken control.", var5);
         }
      }
   }

   /** @deprecated */
   public void setValue(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 > 0 && var2 >= 0) {
         this.value = new byte[var3];
         System.arraycopy(var1, var2, this.value, 0, var3);
      } else {
         throw new CRMFException("The RegistrationToken value cannot be null.");
      }
   }

   /** @deprecated */
   public byte[] getValue() {
      if (this.value == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.value.length];
         System.arraycopy(this.value, 0, var1, 0, this.value.length);
         return var1;
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in array is null in RegistrationToken control.");
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode RegistrationToken control.");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var4) {
            throw new CRMFException("Cannot encode RegistrationToken control.", var4);
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      this.asn1TemplateValue = null;

      try {
         UTF8StringContainer var1 = new UTF8StringContainer(this.special, true, 0, this.value, 0, this.value.length);
         ASN1Container[] var2 = new ASN1Container[]{var1};
         this.asn1TemplateValue = new ASN1Template(var2);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var3) {
         throw new CRMFException(var3);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      RegistrationToken var1 = new RegistrationToken();
      if (this.value != null) {
         var1.value = (byte[])((byte[])this.value.clone());
      }

      var1.special = this.special;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof RegistrationToken) {
         RegistrationToken var2 = (RegistrationToken)var1;
         return CertJUtils.byteArraysEqual(this.value, var2.value);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + Arrays.hashCode(this.value);
   }
}
