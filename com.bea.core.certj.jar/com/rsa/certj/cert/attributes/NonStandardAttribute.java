package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.AttributeException;
import java.util.Arrays;

/** @deprecated */
public class NonStandardAttribute extends X501Attribute {
   private byte[] valueDER;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public NonStandardAttribute() {
      super(23, "NonStandardAttribute");
   }

   /** @deprecated */
   public NonStandardAttribute(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) {
      this();
      this.setOID(var1, var2, var3);
      this.setValue(var4, var5, var6);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();

         try {
            OfContainer var3 = new OfContainer(0, 12544, new EncodedContainer(65280));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            if (var3.getContainerCount() == 0) {
               this.valueDER = new byte[0];
            } else {
               EncodedContainer var5 = (EncodedContainer)var3.containerAt(0);
               this.valueDER = new byte[var5.dataLen];
               System.arraycopy(var5.data, var5.dataOffset, this.valueDER, 0, var5.dataLen);
            }
         } catch (ASN_Exception var6) {
            throw new AttributeException("Could not BER decode the non-standard attribute.");
         }
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
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.valueDER != null && this.theOID != null) {
         try {
            EndContainer var1 = new EndContainer();
            SetContainer var2 = new SetContainer(0, true, 0);
            EncodedContainer var3 = new EncodedContainer(65280, true, 0, this.valueDER, 0, this.valueDER.length);
            ASN1Container[] var4 = new ASN1Container[]{var2, var3, var1};
            this.asn1TemplateValue = new ASN1Template(var4);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var5) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         boolean var3 = false;

         try {
            int var6 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var6;
         } catch (ASN_Exception var5) {
            this.asn1TemplateValue = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      NonStandardAttribute var1 = new NonStandardAttribute();
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
      if (var1 != null && var1 instanceof NonStandardAttribute) {
         NonStandardAttribute var2 = (NonStandardAttribute)var1;
         return !CertJUtils.byteArraysEqual(var2.valueDER, this.valueDER) ? false : CertJUtils.byteArraysEqual(var2.theOID, this.theOID);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + Arrays.hashCode(this.valueDER);
      var2 = var1 * var2 + Arrays.hashCode(this.theOID);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.valueDER = null;
      this.asn1TemplateValue = null;
   }
}
