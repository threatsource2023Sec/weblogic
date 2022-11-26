package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.CertificateException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class PolicyQualifiers implements Serializable, Cloneable {
   private Vector[] policy = this.createVectorArray(2);
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public PolicyQualifiers() {
   }

   /** @deprecated */
   public PolicyQualifiers(byte[] var1, int var2, int var3) throws CertificateException {
      this.special = var3;
      if (var1 == null) {
         throw new CertificateException("Data is missing.");
      } else {
         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               SequenceContainer var9 = new SequenceContainer(0);
               EndContainer var10 = new EndContainer();
               EncodedContainer var11 = new EncodedContainer(65280);
               OIDContainer var12 = new OIDContainer(0);
               ASN1Container[] var13 = new ASN1Container[]{var9, var12, var11, var10};
               ASN1.berDecode(var8.data, var8.dataOffset, var13);
               byte[] var14;
               if (var12.dataLen != 0) {
                  var14 = new byte[var12.dataLen];
                  System.arraycopy(var12.data, var12.dataOffset, var14, 0, var12.dataLen);
                  this.policy[0].addElement(var14);
               }

               var14 = new byte[var11.dataLen];
               System.arraycopy(var11.data, var11.dataOffset, var14, 0, var11.dataLen);
               this.policy[1].addElement(var14);
            }

         } catch (Exception var15) {
            throw new CertificateException("Cannot decode the BER of PolicyQualifiers.");
         }
      }
   }

   /** @deprecated */
   public void addPolicyQualifier(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) throws CertificateException {
      if (var1 != null && var4 != null) {
         byte[] var7 = new byte[var3];
         System.arraycopy(var1, var2, var7, 0, var3);
         this.policy[0].addElement(var7);
         byte[] var8 = new byte[var6];
         System.arraycopy(var4, var5, var8, 0, var6);
         this.policy[1].addElement(var8);
      } else {
         throw new CertificateException(" Null data in setPolicyQualifierInfo");
      }
   }

   /** @deprecated */
   public byte[] getPolicyQualifierId(int var1) throws CertificateException {
      if (this.policy[0].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (byte[])this.policy[0].elementAt(var1);
      }
   }

   /** @deprecated */
   public byte[] getQualifier(int var1) throws CertificateException {
      if (this.policy[1].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (byte[])this.policy[1].elementAt(var1);
      }
   }

   private Vector[] createVectorArray(int var1) {
      Vector[] var2 = new Vector[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = new Vector();
      }

      return var2;
   }

   /** @deprecated */
   public int getQualifiersCount() {
      return this.policy[0].size();
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CertificateException("Unable to determine length of the BER");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CertificateException {
      this.special = var1;
      return this.derEncodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified array is null.");
      } else {
         try {
            if (this.asn1Template == null || var3 != this.special) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new CertificateException("Unable to encode PolicyQualifiers");
         }
      }
   }

   private int derEncodeInit() {
      if (this.asn1Template != null) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.policy[0].size(); ++var3) {
               EncodedContainer var4 = this.encodeQualifier(var3);
               var2.addContainer(var4);
            }

            ASN1Container[] var7 = new ASN1Container[var1.size()];
            var1.copyInto(var7);
            this.asn1Template = new ASN1Template(var7);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var5) {
            return 0;
         } catch (CertificateException var6) {
            return 0;
         }
      }
   }

   private EncodedContainer encodeQualifier(int var1) throws CertificateException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      byte[] var5 = (byte[])this.policy[0].elementAt(var1);
      byte[] var6 = (byte[])this.policy[1].elementAt(var1);

      try {
         EncodedContainer var7 = new EncodedContainer(65280, true, 0, var6, 0, var6.length);
         OIDContainer var8 = new OIDContainer(16777216, true, 0, var5, 0, var5.length);
         ASN1Container[] var9 = new ASN1Container[]{var3, var8, var7, var4};
         ASN1Template var10 = new ASN1Template(var9);
         int var11 = var10.derEncodeInit();
         byte[] var12 = new byte[var11];
         var11 = var10.derEncode(var12, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var12, 0, var11);
         return var2;
      } catch (ASN_Exception var13) {
         throw new CertificateException(" Can't encode PolicyQualifier");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof PolicyQualifiers) {
         PolicyQualifiers var2 = (PolicyQualifiers)var1;
         int var3 = this.policy[0].size();
         if (var3 != var2.policy[0].size()) {
            return false;
         } else {
            int var4;
            byte[] var5;
            byte[] var6;
            for(var4 = 0; var4 < var3; ++var4) {
               var5 = (byte[])((byte[])this.policy[0].elementAt(var4));
               var6 = (byte[])((byte[])var2.policy[0].elementAt(var4));
               if (!CertJUtils.byteArraysEqual(var5, var6)) {
                  return false;
               }
            }

            var3 = this.policy[1].size();
            if (var3 != var2.policy[1].size()) {
               return false;
            } else {
               for(var4 = 0; var4 < var3; ++var4) {
                  var5 = (byte[])this.policy[1].elementAt(var4);
                  var6 = (byte[])var2.policy[1].elementAt(var4);
                  if (!CertJUtils.byteArraysEqual(var5, var6)) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;
      int var2 = this.policy[0].size();

      int var3;
      byte[] var4;
      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (byte[])this.policy[0].elementAt(var3);
         var1 ^= Arrays.hashCode(var4);
      }

      var1 *= 17;
      var2 = this.policy[1].size();

      for(var3 = 0; var3 < var2; ++var3) {
         var4 = (byte[])this.policy[1].elementAt(var3);
         var1 ^= Arrays.hashCode(var4);
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      PolicyQualifiers var1 = new PolicyQualifiers();

      for(int var2 = 0; var2 < this.policy.length; ++var2) {
         for(int var3 = 0; var3 < this.policy[var2].size(); ++var3) {
            var1.policy[var2].addElement(this.policy[var2].elementAt(var3));
         }
      }

      var1.special = this.special;
      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}
