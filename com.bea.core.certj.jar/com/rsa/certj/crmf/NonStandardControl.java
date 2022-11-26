package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.certj.CertJUtils;
import java.util.Arrays;

/** @deprecated */
public class NonStandardControl extends Control {
   private byte[] valueDER;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public NonStandardControl() {
      this.controlTypeFlag = 6;
      this.controlTypeString = "NonStandardControl";
   }

   /** @deprecated */
   public NonStandardControl(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) {
      this.controlTypeFlag = 6;
      if (var1 != null && var3 != 0) {
         this.theOID = new byte[var3];
         System.arraycopy(var1, var2, this.theOID, 0, var3);
      }

      if (var4 != null && var6 != 0) {
         this.valueDER = new byte[var6];
         System.arraycopy(var4, var5, this.valueDER, 0, var6);
      }

      this.controlTypeString = "NonStandardControl";
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Non Standard Control encoding is null.");
      } else {
         this.valueDER = new byte[var1.length - var2];
         System.arraycopy(var1, var2, this.valueDER, 0, var1.length - var2);
      }
   }

   /** @deprecated */
   public void setOID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.theOID = new byte[var3];
         System.arraycopy(var1, var2, this.theOID, 0, var3);
      }

   }

   /** @deprecated */
   public void setValue(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.valueDER = new byte[var3];
         System.arraycopy(var1, var2, this.valueDER, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getValue() {
      return this.valueDER == null ? null : (byte[])((byte[])this.valueDER.clone());
   }

   /** @deprecated */
   protected int derEncodeValueInit() throws CRMFException {
      this.asn1TemplateValue = null;
      if (this.valueDER != null && this.theOID != null) {
         try {
            EncodedContainer var1 = new EncodedContainer(65280, true, 0, this.valueDER, 0, this.valueDER.length);
            ASN1Container[] var2 = new ASN1Container[]{var1};
            this.asn1TemplateValue = new ASN1Template(var2);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var3) {
            throw new CRMFException("Cannot encode Non Standard Control. ", var3);
         }
      } else {
         throw new CRMFException("Values are not set in Non Standard Control.");
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         throw new CRMFException("Cannot encode Non Standard Control. ");
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            throw new CRMFException("Cannot encode Non Standard Control. ", var5);
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      NonStandardControl var1 = new NonStandardControl();
      if (this.valueDER != null) {
         var1.valueDER = (byte[])((byte[])this.valueDER.clone());
      }

      if (this.theOID != null) {
         var1.theOID = (byte[])((byte[])this.theOID.clone());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof NonStandardControl) {
         NonStandardControl var2 = (NonStandardControl)var1;
         if (!CertJUtils.byteArraysEqual(this.valueDER, var2.valueDER)) {
            return false;
         } else {
            return CertJUtils.byteArraysEqual(this.theOID, var2.theOID);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.valueDER);
      var2 = var1 * var2 + Arrays.hashCode(this.theOID);
      return var2;
   }
}
