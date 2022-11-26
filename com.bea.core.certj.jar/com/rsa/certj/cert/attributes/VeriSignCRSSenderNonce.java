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
public class VeriSignCRSSenderNonce extends X501Attribute {
   private byte[] nonce;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignCRSSenderNonce() {
      super(9, "VeriSignCRSSenderNonce");
   }

   /** @deprecated */
   public VeriSignCRSSenderNonce(byte[] var1, int var2, int var3) {
      this();
      this.setSenderNonce(var1, var2, var3);
   }

   /** @deprecated */
   protected void decodeValue(byte[] var1, int var2) throws AttributeException {
      if (var1 == null) {
         throw new AttributeException("Encoding is null.");
      } else {
         this.reset();

         try {
            SetContainer var3 = new SetContainer(0);
            EndContainer var4 = new EndContainer();
            OctetStringContainer var5 = new OctetStringContainer(0);
            ASN1Container[] var6 = new ASN1Container[]{var3, var5, var4};
            ASN1.berDecode(var1, var2, var6);
            if (var5.dataLen > 0) {
               this.nonce = new byte[var5.dataLen];
               System.arraycopy(var5.data, var5.dataOffset, this.nonce, 0, var5.dataLen);
            }

         } catch (ASN_Exception var7) {
            throw new AttributeException("Could not BER decode VeriSignCRSSenderNonce.");
         }
      }
   }

   /** @deprecated */
   public void setSenderNonce(byte[] var1, int var2, int var3) {
      this.reset();
      if (var1 != null && var3 != 0) {
         this.nonce = new byte[var3];
         System.arraycopy(var1, var2, this.nonce, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getSenderNonce() {
      return this.nonce == null ? null : (byte[])((byte[])this.nonce.clone());
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      int var1 = 0;
      if (this.nonce != null) {
         var1 = this.nonce.length;
      }

      try {
         SetContainer var2 = new SetContainer(0, true, 0);
         OctetStringContainer var3 = new OctetStringContainer(0, true, 0, this.nonce, 0, var1);
         EndContainer var4 = new EndContainer();
         ASN1Container[] var5 = new ASN1Container[]{var2, var3, var4};
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
      VeriSignCRSSenderNonce var1 = new VeriSignCRSSenderNonce();
      if (this.nonce != null) {
         var1.nonce = (byte[])((byte[])this.nonce.clone());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof VeriSignCRSSenderNonce) {
         VeriSignCRSSenderNonce var2 = (VeriSignCRSSenderNonce)var1;
         return CertJUtils.byteArraysEqual(var2.nonce, this.nonce);
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      var2 = var1 * var2 + Arrays.hashCode(this.nonce);
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.nonce = null;
      this.asn1TemplateValue = null;
   }
}
