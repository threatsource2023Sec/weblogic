package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.FIPS140Mode;
import com.rsa.certj.FIPS140Role;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_AsymmetricCipher;
import com.rsa.jsafe.JSAFE_Exception;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class RecipientInfo implements Serializable, Cloneable {
   private int version;
   private X500Name issuer;
   private byte[] serialNumber;
   private byte[] keyEncryptionOID;
   private byte[] encryptedKey;
   private String algorithmName;
   /** @deprecated */
   protected static int special;
   private ASN1Template asn1Template;
   private final c context;

   /** @deprecated */
   public RecipientInfo() {
      this(c.a());
   }

   /** @deprecated */
   public RecipientInfo(FIPS140Mode var1) {
      this(c.a(var1));
   }

   /** @deprecated */
   public RecipientInfo(FIPS140Mode var1, FIPS140Role var2) {
      this(c.a(var1, var2));
   }

   /** @deprecated */
   protected RecipientInfo(byte[] var1, int var2, int var3) throws PKCS7Exception {
      this(c.a(), var1, var2, var3);
   }

   /** @deprecated */
   protected RecipientInfo(byte[] var1, int var2, int var3, FIPS140Mode var4) throws PKCS7Exception {
      this(c.a(var4), var1, var2, var3);
   }

   /** @deprecated */
   protected RecipientInfo(byte[] var1, int var2, int var3, FIPS140Mode var4, FIPS140Role var5) throws PKCS7Exception {
      this(c.a(var4, var5), var1, var2, var3);
   }

   RecipientInfo(c var1) {
      this.version = 0;
      this.context = var1;
   }

   RecipientInfo(c var1, byte[] var2, int var3, int var4) throws PKCS7Exception {
      this.version = 0;
      this.context = var1;
      if (var2 == null) {
         throw new PKCS7Exception("Cannot create RecipientInfo: encoding is null.");
      } else {
         try {
            SequenceContainer var5 = new SequenceContainer(var4);
            EndContainer var6 = new EndContainer();
            IntegerContainer var7 = new IntegerContainer(0);
            EncodedContainer var8 = new EncodedContainer(12288);
            EncodedContainer var9 = new EncodedContainer(12288);
            OctetStringContainer var10 = new OctetStringContainer(0);
            ASN1Container[] var11 = new ASN1Container[]{var5, var7, var8, var9, var10, var6};
            ASN1.berDecode(var2, var3, var11);
            this.version = var7.getValueAsInt();
            SequenceContainer var12 = new SequenceContainer(var4);
            EncodedContainer var13 = new EncodedContainer(12288);
            IntegerContainer var14 = new IntegerContainer(0);
            ASN1Container[] var15 = new ASN1Container[]{var12, var13, var14, var6};
            ASN1.berDecode(var8.data, var8.dataOffset, var15);
            this.issuer = new X500Name(var13.data, var13.dataOffset, 0);
            this.serialNumber = new byte[var14.dataLen];
            System.arraycopy(var14.data, var14.dataOffset, this.serialNumber, 0, var14.dataLen);
            this.keyEncryptionOID = new byte[var9.dataLen];
            System.arraycopy(var9.data, var9.dataOffset, this.keyEncryptionOID, 0, var9.dataLen);
            this.encryptedKey = new byte[var10.dataLen];
            System.arraycopy(var10.data, var10.dataOffset, this.encryptedKey, 0, var10.dataLen);
         } catch (ASN_Exception var16) {
            throw new PKCS7Exception("Cannot decode the BER of the RecipientInfo.");
         } catch (NameException var17) {
            throw new PKCS7Exception("Cannot decode the BER of the RecipientInfo.");
         }
      }
   }

   /** @deprecated */
   public void setVersionNumber(int var1) {
      this.version = var1;
   }

   /** @deprecated */
   public int getVersionNumber() {
      return this.version;
   }

   /** @deprecated */
   public void setIssuerAndSerialNumber(X500Name var1, byte[] var2, int var3, int var4) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Issuer name is null.");
      } else {
         try {
            this.issuer = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var6) {
            throw new PKCS7Exception("Invalid name.");
         }

         if (var2 != null && var4 > 0) {
            if (var3 >= 0 && var3 + var4 <= var2.length) {
               this.serialNumber = new byte[var4];
               System.arraycopy(var2, var3, this.serialNumber, 0, var4);
            } else {
               throw new PKCS7Exception("Invalid SerialNumber");
            }
         } else {
            throw new PKCS7Exception("SerialNumber is null.");
         }
      }
   }

   /** @deprecated */
   public X500Name getIssuerName() throws PKCS7Exception {
      if (this.issuer == null) {
         return null;
      } else {
         try {
            return (X500Name)this.issuer.clone();
         } catch (CloneNotSupportedException var2) {
            throw new PKCS7Exception("Invalid attributes.");
         }
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      return this.serialNumber == null ? null : (byte[])((byte[])this.serialNumber.clone());
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(String var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Could not set algorithm OID: name is null.");
      } else {
         this.algorithmName = var1;

         try {
            JSAFE_AsymmetricCipher var2 = h.f(var1, "Java", this.context.b);
            this.keyEncryptionOID = var2.getDERAlgorithmID();
         } catch (JSAFE_Exception var3) {
            throw new PKCS7Exception("Could not set algorithm OID: ", var3);
         }
      }
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.keyEncryptionOID = new byte[var3];
            System.arraycopy(var1, var2, this.keyEncryptionOID, 0, var3);
         } else {
            throw new PKCS7Exception("Invalid Encryption Algorithm Identifier.");
         }
      } else {
         throw new PKCS7Exception("Could not set algorithm OID: OID is null");
      }
   }

   /** @deprecated */
   public byte[] getEncryptionAlgorithmOID() {
      return this.keyEncryptionOID == null ? null : (byte[])((byte[])this.keyEncryptionOID.clone());
   }

   /** @deprecated */
   public String getEncryptionAlgorithmName() throws PKCS7Exception {
      if (this.keyEncryptionOID == null) {
         return null;
      } else if (this.algorithmName != null) {
         return this.algorithmName;
      } else {
         try {
            JSAFE_AsymmetricCipher var1 = h.f(this.keyEncryptionOID, 0, "Java", (FIPS140Context)this.context.b);
            return var1.getEncryptionAlgorithm();
         } catch (JSAFE_Exception var2) {
            throw new PKCS7Exception("Could not get algorithm OID: ", var2);
         }
      }
   }

   /** @deprecated */
   protected void setEncryptedKey(byte[] var1, int var2, int var3) {
      this.encryptedKey = new byte[var3];
      System.arraycopy(var1, var2, this.encryptedKey, 0, var3);
   }

   /** @deprecated */
   public byte[] getEncryptedKey() {
      return this.encryptedKey == null ? null : (byte[])((byte[])this.encryptedKey.clone());
   }

   /** @deprecated */
   protected static int getNextBEROffset(byte[] var0, int var1) throws PKCS7Exception {
      if (var0 == null) {
         throw new PKCS7Exception("Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new PKCS7Exception("Unable to determine length of the BER: ", var3);
         }
      }
   }

   /** @deprecated */
   protected int getDERLen(int var1) throws PKCS7Exception {
      special = var1;
      return this.derEncodeInit();
   }

   /** @deprecated */
   protected int getDEREncoding(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Specified array is null.");
      } else {
         try {
            int var4;
            if (this.asn1Template == null || var3 != special) {
               var4 = this.getDERLen(var3);
               if (var4 == 0) {
                  throw new PKCS7Exception("Unable to encode RecipientInfo.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new PKCS7Exception("Unable to encode RecipientInfo: ", var5);
         }
      }
   }

   private int derEncodeInit() {
      try {
         if (this.issuer == null) {
            return 0;
         } else {
            int var1 = this.issuer.getDERLen(0);
            if (var1 == 0) {
               return 0;
            } else {
               byte[] var2 = new byte[var1];
               this.issuer.getDEREncoding(var2, 0, 0);
               SequenceContainer var3 = new SequenceContainer(0, true, 0);
               EndContainer var4 = new EndContainer();
               EncodedContainer var5 = new EncodedContainer(12288, true, 0, var2, 0, var2.length);
               if (this.serialNumber == null) {
                  return 0;
               } else {
                  IntegerContainer var6;
                  if ((this.serialNumber[0] & 128) >> 7 == 0) {
                     var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
                  } else {
                     var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
                  }

                  ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
                  ASN1Template var8 = new ASN1Template(var7);
                  var1 = var8.derEncodeInit();
                  byte[] var9 = new byte[var1];
                  var8.derEncode(var9, 0);
                  if (this.keyEncryptionOID == null) {
                     return 0;
                  } else if (this.encryptedKey == null) {
                     return 0;
                  } else {
                     SequenceContainer var10 = new SequenceContainer(special, true, 0);
                     IntegerContainer var11 = new IntegerContainer(0, true, 0, this.version);
                     EncodedContainer var12 = new EncodedContainer(12288, true, 0, var9, 0, var9.length);
                     EncodedContainer var13 = new EncodedContainer(12288, true, 0, this.keyEncryptionOID, 0, this.keyEncryptionOID.length);
                     OctetStringContainer var14 = new OctetStringContainer(0, true, 0, this.encryptedKey, 0, this.encryptedKey.length);
                     ASN1Container[] var15 = new ASN1Container[]{var10, var11, var12, var13, var14, var4};
                     this.asn1Template = new ASN1Template(var15);
                     return this.asn1Template.derEncodeInit();
                  }
               }
            }
         }
      } catch (ASN_Exception var16) {
         return 0;
      } catch (NameException var17) {
         return 0;
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof RecipientInfo) {
         RecipientInfo var2 = (RecipientInfo)var1;
         if (this.version != var2.version) {
            return false;
         } else {
            if (this.issuer != null) {
               if (!this.issuer.equals(var2.issuer)) {
                  return false;
               }
            } else if (var2.issuer != null) {
               return false;
            }

            return CertJUtils.byteArraysEqual(this.serialNumber, var2.serialNumber) && CertJUtils.byteArraysEqual(this.keyEncryptionOID, var2.keyEncryptionOID) && CertJUtils.byteArraysEqual(this.encryptedKey, var2.encryptedKey);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + Arrays.hashCode(this.encryptedKey);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.issuer);
      var2 = var1 * var2 + Arrays.hashCode(this.keyEncryptionOID);
      var2 = var1 * var2 + Arrays.hashCode(this.serialNumber);
      var2 = var1 * var2 + this.version;
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      RecipientInfo var1 = new RecipientInfo(this.context);
      var1.version = this.version;
      if (this.issuer != null) {
         var1.issuer = (X500Name)this.issuer.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = new byte[this.serialNumber.length];
         System.arraycopy(this.serialNumber, 0, var1.serialNumber, 0, this.serialNumber.length);
      }

      if (this.keyEncryptionOID != null) {
         var1.keyEncryptionOID = new byte[this.keyEncryptionOID.length];
         System.arraycopy(this.keyEncryptionOID, 0, var1.keyEncryptionOID, 0, this.keyEncryptionOID.length);
      }

      if (this.encryptedKey != null) {
         var1.encryptedKey = new byte[this.encryptedKey.length];
         System.arraycopy(this.encryptedKey, 0, var1.encryptedKey, 0, this.encryptedKey.length);
      }

      if (this.asn1Template != null) {
         var1.derEncodeInit();
      }

      return var1;
   }
}
