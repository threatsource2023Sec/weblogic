package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.AttributeException;
import java.util.Arrays;

/** @deprecated */
public class LocalKeyID extends X501Attribute {
   private byte[] localKeyID;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public LocalKeyID() {
      super(4, "LocalKeyID");
   }

   /** @deprecated */
   public LocalKeyID(byte[] var1, int var2, int var3) {
      this();
      this.setLocalKeyID(var1, var2, var3);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();
         SetContainer var3 = new SetContainer(0);
         EndContainer var4 = new EndContainer();
         OctetStringContainer var5 = new OctetStringContainer(0);
         ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};

         try {
            ASN1.berDecode(var1, var2, var6);
         } catch (ASN_Exception var8) {
            throw new AttributeException("Could not BER decode LocalKeyID.");
         }

         this.localKeyID = new byte[var5.dataLen];
         System.arraycopy(var5.data, var5.dataOffset, this.localKeyID, 0, var5.dataLen);
      }
   }

   /** @deprecated */
   public byte[] getLocalKeyID() {
      if (this.localKeyID == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.localKeyID.length];
         System.arraycopy(this.localKeyID, 0, var1, 0, this.localKeyID.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setLocalKeyID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.localKeyID = new byte[var3];
         System.arraycopy(var1, var2, this.localKeyID, 0, var3);
      }

   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      if (this.localKeyID == null) {
         return 0;
      } else {
         try {
            EndContainer var1 = new EndContainer();
            SetContainer var2 = new SetContainer(0, true, 0);
            OctetStringContainer var3 = new OctetStringContainer(0, true, 0, this.localKeyID, 0, this.localKeyID.length);
            ASN1Container[] var4 = new ASN1Container[]{var2, var3, var1};
            this.asn1TemplateValue = new ASN1Template(var4);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var5) {
            return 0;
         }
      }
   }

   /** @deprecated */
   protected int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      LocalKeyID var1 = new LocalKeyID();
      if (this.localKeyID != null) {
         var1.localKeyID = (byte[])((byte[])this.localKeyID.clone());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof LocalKeyID) {
         LocalKeyID var2 = (LocalKeyID)var1;
         return CertJUtils.byteArraysEqual(var2.localKeyID, this.localKeyID);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + Arrays.hashCode(this.localKeyID);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.asn1TemplateValue = null;
      if (this.localKeyID != null) {
         for(int var1 = 0; var1 < this.localKeyID.length; ++var1) {
            this.localKeyID[var1] = 0;
         }

         this.localKeyID = null;
      }

   }
}
