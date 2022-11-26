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
public class MessageDigest extends X501Attribute {
   private byte[] digest;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public MessageDigest() {
      super(15, "MessageDigest");
   }

   /** @deprecated */
   public MessageDigest(byte[] var1, int var2, int var3) {
      this();
      this.setMessageDigest(var1, var2, var3);
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
            throw new AttributeException("Could not BER decode MessageDigest.");
         }

         if (var5.dataLen != 0) {
            this.digest = new byte[var5.dataLen];
            System.arraycopy(var5.data, var5.dataOffset, this.digest, 0, var5.dataLen);
         }

      }
   }

   /** @deprecated */
   public byte[] getMessageDigest() {
      if (this.digest == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.digest.length];
         System.arraycopy(this.digest, 0, var1, 0, this.digest.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setMessageDigest(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.digest = new byte[var3];
         System.arraycopy(var1, var2, this.digest, 0, var3);
      }

   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      int var1 = 0;
      if (this.digest != null) {
         var1 = this.digest.length;
      }

      try {
         EndContainer var2 = new EndContainer();
         SetContainer var3 = new SetContainer(0, true, 0);
         OctetStringContainer var4 = new OctetStringContainer(0, true, 0, this.digest, 0, var1);
         ASN1Container[] var5 = new ASN1Container[]{var3, var4, var2};
         this.asn1TemplateValue = new ASN1Template(var5);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
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
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      MessageDigest var1 = new MessageDigest();
      if (this.digest != null) {
         var1.digest = (byte[])((byte[])this.digest.clone());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof MessageDigest) {
         MessageDigest var2 = (MessageDigest)var1;
         return CertJUtils.byteArraysEqual(var2.digest, this.digest);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + Arrays.hashCode(this.digest);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.asn1TemplateValue = null;
      if (this.digest != null) {
         for(int var1 = 0; var1 < this.digest.length; ++var1) {
            this.digest[var1] = 0;
         }

         this.digest = null;
      }

   }
}
