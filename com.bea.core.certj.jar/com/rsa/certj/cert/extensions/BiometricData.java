package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.AlgorithmID;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.NameException;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class BiometricData implements Serializable, Cloneable {
   private byte[] dataHash;
   private String dataUri;
   private int biometricType = -1;
   private byte[] dataID;
   private byte[] hashAlgorithmBER;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;

   /** @deprecated */
   public BiometricData(byte[] var1, int var2, int var3) throws NameException {
      if (var1 != null && var2 >= 0) {
         try {
            SequenceContainer var4 = new SequenceContainer(var3);
            EncodedContainer var5 = new EncodedContainer(12288);
            OctetStringContainer var6 = new OctetStringContainer(0);
            EndContainer var7 = new EndContainer();
            ChoiceContainer var8 = new ChoiceContainer(0);
            IntegerContainer var9 = new IntegerContainer(0);
            OIDContainer var10 = new OIDContainer(16777216);
            IA5StringContainer var11 = new IA5StringContainer(65536);
            ASN1Container[] var12 = new ASN1Container[]{var4, var8, var9, var10, var7, var5, var6, var11, var7};
            ASN1.berDecode(var1, var2, var12);
            if (var9.dataPresent) {
               this.biometricType = var9.getValueAsInt();
            } else {
               this.dataID = new byte[var10.dataLen];
               System.arraycopy(var10.data, var10.dataOffset, this.dataID, 0, var10.dataLen);
            }

            this.hashAlgorithmBER = new byte[var5.dataLen];
            System.arraycopy(var5.data, var5.dataOffset, this.hashAlgorithmBER, 0, var5.dataLen);
            if (AlgorithmID.berDecodeAlgID(this.hashAlgorithmBER, 0, 11, (EncodedContainer)null) == null) {
               throw new NameException("Unknown or invalid hash algorithm.");
            } else {
               this.dataHash = new byte[var6.dataLen];
               System.arraycopy(var6.data, var6.dataOffset, this.dataHash, 0, var6.dataLen);
               this.dataUri = var11.getValueAsString();
            }
         } catch (ASN_Exception var13) {
            throw new NameException("Could not BER decode the Biometric Data info.", var13);
         }
      } else {
         throw new NameException("Encoding is null.");
      }
   }

   /** @deprecated */
   public BiometricData() {
   }

   /** @deprecated */
   public void setDataHash(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 > 0 && var2 >= 0 && var1.length >= var3 + var2) {
         this.dataHash = new byte[var3];
         System.arraycopy(var1, var2, this.dataHash, 0, var3);
      } else {
         this.dataHash = null;
      }

   }

   /** @deprecated */
   public byte[] getDataHash() {
      if (this.dataHash == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.dataHash.length];
         System.arraycopy(this.dataHash, 0, var1, 0, this.dataHash.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setDataURI(String var1) {
      this.dataUri = var1;
   }

   /** @deprecated */
   public String getDataURI() {
      return this.dataUri;
   }

   /** @deprecated */
   public void setBiometricType(int var1) {
      this.biometricType = var1;
      this.dataID = null;
   }

   /** @deprecated */
   public int getBiometricType() {
      return this.biometricType;
   }

   /** @deprecated */
   public void setDataID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 > 0 && var2 >= 0 && var1.length >= var3 + var2) {
         this.dataID = new byte[var3];
         System.arraycopy(var1, var2, this.dataID, 0, var3);
         this.biometricType = -1;
      } else {
         this.dataID = null;
      }

   }

   /** @deprecated */
   public byte[] getDataID() {
      if (this.dataID == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.dataID.length];
         System.arraycopy(this.dataID, 0, var1, 0, this.dataID.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setHashAlgorithm(String var1) throws NameException {
      try {
         this.hashAlgorithmBER = AlgorithmID.derEncodeAlgID(var1, 11, (byte[])null, 0, 0);
      } catch (ASN_Exception var3) {
         throw new NameException("Invalid hash algorithm. ", var3);
      }
   }

   /** @deprecated */
   public String getHashAlgorithm() throws NameException {
      try {
         return AlgorithmID.berDecodeAlgID(this.hashAlgorithmBER, 0, 11, (EncodedContainer)null);
      } catch (ASN_Exception var2) {
         throw new NameException("Invalid hash algorithm. ", var2);
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws NameException {
      if (var0 == null) {
         throw new NameException("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new NameException("Unable to determine length of the BER. ", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws NameException {
      if (this.dataHash != null && this.hashAlgorithmBER != null && (this.dataID != null || this.biometricType != -1)) {
         this.special = var1;
         return this.derEncodeInit();
      } else {
         throw new NameException("Biometric data is not set.");
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws NameException {
      if (var1 == null) {
         throw new NameException("Specified array is null.");
      } else {
         try {
            int var4;
            if (this.asn1Template == null || var3 != this.special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new NameException("Cannot encode BiometricData.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new NameException("Unable to encode BiometricData.", var5);
         }
      }
   }

   private int derEncodeInit() throws NameException {
      try {
         SequenceContainer var1 = new SequenceContainer(this.special);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, this.hashAlgorithmBER, 0, this.hashAlgorithmBER.length);
         OctetStringContainer var3 = new OctetStringContainer(0, true, 0, this.dataHash, 0, this.dataHash.length);
         ChoiceContainer var4 = new ChoiceContainer(0);
         EndContainer var5 = new EndContainer();
         Object var6;
         if (this.biometricType != -1) {
            var6 = new IntegerContainer(0, true, 0, this.biometricType);
         } else {
            if (this.dataID == null) {
               throw new NameException("Type of Biometric Data is not set.");
            }

            var6 = new OIDContainer(16777216, true, 0, this.dataID, 0, this.dataID.length);
         }

         IA5StringContainer var7;
         if (this.dataUri != null) {
            var7 = new IA5StringContainer(65536, true, 0, this.dataUri);
         } else {
            var7 = new IA5StringContainer(65536, false, 0, (String)null);
         }

         ASN1Container[] var8 = new ASN1Container[]{var1, var4, (ASN1Container)var6, var5, var2, var3, var7, var5};
         this.asn1Template = new ASN1Template(var8);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var9) {
         throw new NameException("cannot create the DER encoding. ", var9);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BiometricData) {
         BiometricData var2 = (BiometricData)var1;
         if (this.dataUri != null) {
            if (!this.dataUri.equals(var2.dataUri)) {
               return false;
            }
         } else if (var2.dataUri != null) {
            return false;
         }

         if (!CertJUtils.byteArraysEqual(this.dataHash, var2.dataHash)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.dataID, var2.dataID)) {
            return false;
         } else if (!CertJUtils.byteArraysEqual(this.hashAlgorithmBER, var2.hashAlgorithmBER)) {
            return false;
         } else {
            return this.biometricType == var2.biometricType;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      try {
         int var1 = this.getDERLen(0);
         if (var1 == 0) {
            return 0;
         } else {
            byte[] var2 = new byte[var1];
            this.getDEREncoding(var2, 0, 0);
            return Arrays.hashCode(var2);
         }
      } catch (NameException var3) {
         return 0;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      BiometricData var1 = new BiometricData();
      if (this.dataHash != null) {
         var1.dataHash = new byte[this.dataHash.length];
         System.arraycopy(this.dataHash, 0, var1.dataHash, 0, this.dataHash.length);
      }

      if (this.dataID != null) {
         var1.dataID = new byte[this.dataID.length];
         System.arraycopy(this.dataID, 0, var1.dataID, 0, this.dataID.length);
      }

      if (this.hashAlgorithmBER != null) {
         var1.hashAlgorithmBER = new byte[this.hashAlgorithmBER.length];
         System.arraycopy(this.hashAlgorithmBER, 0, var1.hashAlgorithmBER, 0, this.hashAlgorithmBER.length);
      }

      var1.biometricType = this.biometricType;
      var1.special = this.biometricType;
      var1.dataUri = this.dataUri;

      try {
         if (this.asn1Template != null) {
            var1.derEncodeInit();
         }

         return var1;
      } catch (NameException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }
}
