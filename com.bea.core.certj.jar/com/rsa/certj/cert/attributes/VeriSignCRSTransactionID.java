package com.rsa.certj.cert.attributes;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.PrintStringContainer;
import com.rsa.asn1.SetContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.CompatibilityType;
import com.rsa.certj.cert.AttributeException;
import java.math.BigInteger;
import java.util.Arrays;

/** @deprecated */
public class VeriSignCRSTransactionID extends X501Attribute {
   private byte[] id;
   private String idString;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignCRSTransactionID() {
      super(11, "VeriSignCRSTransactionID");
   }

   /** @deprecated */
   public VeriSignCRSTransactionID(byte[] var1, int var2, int var3) {
      this();
      this.setTransactionID(var1, var2, var3);
   }

   private String byteArrayToString(byte[] var1) {
      BigInteger var2 = new BigInteger(var1);
      return var2.toString();
   }

   private byte[] stringToByteArray(String var1) {
      try {
         BigInteger var2 = new BigInteger(var1, 10);
         return var2.toByteArray();
      } catch (NumberFormatException var3) {
         if (!CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_SCEP)) {
            throw var3;
         } else {
            return null;
         }
      }
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
            IntegerContainer var5 = new IntegerContainer(65536);
            PrintStringContainer var6 = new PrintStringContainer(65536);
            ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
            ASN1.berDecode(var1, var2, var7);
            if (var5.dataPresent) {
               if (var5.dataLen > 0) {
                  this.id = new byte[var5.dataLen];
                  System.arraycopy(var5.data, var5.dataOffset, this.id, 0, var5.dataLen);
                  this.idString = this.byteArrayToString(this.id);
               }
            } else {
               if (!var6.dataPresent) {
                  throw new AttributeException("Unexpected encoding.");
               }

               String var8 = var6.getValueAsString();
               byte[] var9 = new byte[var6.dataLen];
               System.arraycopy(var6.data, var6.dataOffset, var9, 0, var6.dataLen);
               this.id = this.stringToByteArray(var8);
               this.idString = var8;
            }

         } catch (ASN_Exception var10) {
            throw new AttributeException("Could not BER decode VeriSignCRSTransactionID.");
         }
      }
   }

   /** @deprecated */
   public void setTransactionID(byte[] var1, int var2, int var3) {
      this.reset();
      if (var1 != null && var3 != 0) {
         this.id = new byte[var3];
         System.arraycopy(var1, var2, this.id, 0, var3);
         this.idString = this.byteArrayToString(this.id);
      }

   }

   /** @deprecated */
   public byte[] getTransactionID() {
      return this.id == null ? null : (byte[])((byte[])this.id.clone());
   }

   /** @deprecated */
   public void setTransactionIDString(String var1) {
      this.reset();
      if (var1 != null) {
         this.id = this.stringToByteArray(var1);
         this.idString = var1;
      }

   }

   /** @deprecated */
   public String getTransactionIDString() {
      return this.idString;
   }

   /** @deprecated */
   protected int derEncodeValueInit() {
      this.asn1TemplateValue = null;
      SetContainer var1 = new SetContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      ASN1Container[] var3 = new ASN1Container[]{var1, null, var2};

      try {
         if (CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_SCEP)) {
            String var4;
            if (this.idString != null && this.idString.length() != 0) {
               var4 = this.idString;
            } else {
               var4 = "0";
            }

            PrintStringContainer var5 = new PrintStringContainer(0, true, 0, var4);
            var3[1] = var5;
         } else {
            int var7 = 0;
            if (this.id != null) {
               var7 = this.id.length;
            }

            IntegerContainer var8 = new IntegerContainer(0, true, 0, this.id, 0, var7, true);
            var3[1] = var8;
         }

         this.asn1TemplateValue = new ASN1Template(var3);
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
      VeriSignCRSTransactionID var1 = new VeriSignCRSTransactionID();
      if (this.id != null) {
         var1.id = (byte[])((byte[])this.id.clone());
      }

      var1.idString = this.idString;
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof VeriSignCRSTransactionID) {
         VeriSignCRSTransactionID var2 = (VeriSignCRSTransactionID)var1;
         if (!CertJ.isCompatibilityTypeSet(CompatibilityType.CERTJ_COMPATIBILITY_SCEP)) {
            return CertJUtils.byteArraysEqual(var2.id, this.id);
         } else {
            return this.idString == var2.idString || this.idString != null && this.idString.equals(var2.idString);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = super.hashCode();
      int var3;
      if (this.id == null && this.idString != null) {
         var3 = this.idString.hashCode();
      } else {
         var3 = Arrays.hashCode(this.id);
      }

      var2 = var1 * var2 + var3;
      return var2;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.id = null;
      this.idString = null;
      this.asn1TemplateValue = null;
   }
}
