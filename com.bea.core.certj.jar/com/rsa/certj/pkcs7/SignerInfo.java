package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDList;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.FIPS140Mode;
import com.rsa.certj.FIPS140Role;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_AsymmetricCipher;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_Signature;
import java.io.Serializable;
import java.util.Arrays;

/** @deprecated */
public class SignerInfo implements Serializable, Cloneable {
   private static final int AUTHENT_ATTR_SPECIAL = 8454144;
   private static final int UNAUTHENT_ATTR_SPECIAL = 8454145;
   private int version;
   private X500Name issuer;
   private byte[] serialNumber;
   private byte[] digestOID;
   private X501Attributes authentAttrs;
   private byte[] digestEncryptionOID;
   private X501Attributes unauthentAttrs;
   private byte[] digest;
   private String digestAlgName;
   private String encryptionAlgName;
   private byte[] authenAttrsOrgBer;
   /** @deprecated */
   protected static int special;
   private ASN1Template asn1Template;
   private final c context;

   /** @deprecated */
   public SignerInfo() {
      this(c.a());
   }

   /** @deprecated */
   public SignerInfo(FIPS140Mode var1) {
      this(c.a(var1));
   }

   /** @deprecated */
   public SignerInfo(FIPS140Mode var1, FIPS140Role var2) {
      this(c.a(var1, var2));
   }

   /** @deprecated */
   protected SignerInfo(byte[] var1, int var2, int var3) throws PKCS7Exception {
      this(c.a(), var1, var2, var3);
   }

   /** @deprecated */
   protected SignerInfo(byte[] var1, int var2, int var3, FIPS140Mode var4) throws PKCS7Exception {
      this(c.a(var4), var1, var2, var3);
   }

   /** @deprecated */
   protected SignerInfo(byte[] var1, int var2, int var3, FIPS140Mode var4, FIPS140Role var5) throws PKCS7Exception {
      this(c.a(var4, var5), var1, var2, var3);
   }

   SignerInfo(c var1) {
      this.version = 1;
      this.context = var1;
   }

   SignerInfo(c var1, byte[] var2, int var3, int var4) throws PKCS7Exception {
      this.version = 1;
      this.context = var1;

      try {
         if (var2 == null) {
            throw new PKCS7Exception("Encoding is null.");
         } else {
            SequenceContainer var5 = new SequenceContainer(var4);
            EndContainer var6 = new EndContainer();
            IntegerContainer var7 = new IntegerContainer(0);
            EncodedContainer var8 = new EncodedContainer(12288);
            EncodedContainer var9 = new EncodedContainer(12288);
            EncodedContainer var10 = new EncodedContainer(8466688);
            EncodedContainer var11 = new EncodedContainer(12288);
            OctetStringContainer var12 = new OctetStringContainer(0);
            EncodedContainer var13 = new EncodedContainer(8466689);
            ASN1Container[] var14 = new ASN1Container[]{var5, var7, var8, var9, var10, var11, var12, var13, var6};
            ASN1.berDecode(var2, var3, var14);
            this.version = var7.getValueAsInt();
            SequenceContainer var15 = new SequenceContainer(var4);
            EncodedContainer var16 = new EncodedContainer(12288);
            IntegerContainer var17 = new IntegerContainer(0);
            ASN1Container[] var18 = new ASN1Container[]{var15, var16, var17, var6};
            ASN1.berDecode(var8.data, var8.dataOffset, var18);
            this.issuer = new X500Name(var16.data, var16.dataOffset, 0);
            if (var17.dataLen == 0) {
               throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: Serial Number is null.");
            } else {
               this.serialNumber = new byte[var17.dataLen];
               System.arraycopy(var17.data, var17.dataOffset, this.serialNumber, 0, var17.dataLen);
               if (var9.dataLen == 0) {
                  throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: Digest algorithm is missing.");
               } else {
                  this.digestOID = new byte[var9.dataLen];
                  System.arraycopy(var9.data, var9.dataOffset, this.digestOID, 0, var9.dataLen);
                  if (var10.dataPresent) {
                     this.authenAttrsOrgBer = new byte[var10.dataLen];
                     System.arraycopy(var10.data, var10.dataOffset, this.authenAttrsOrgBer, 0, var10.dataLen);
                     this.authenAttrsOrgBer[0] = 49;
                     this.authentAttrs = new X501Attributes(var10.data, var10.dataOffset, 8454144);
                  }

                  if (var11.dataLen == 0) {
                     throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: Encryption algorithm is missing.");
                  } else {
                     this.digestEncryptionOID = new byte[var11.dataLen];
                     System.arraycopy(var11.data, var11.dataOffset, this.digestEncryptionOID, 0, var11.dataLen);
                     this.checkForDSAWithParamsOID();
                     if (var12.dataLen == 0) {
                        throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: Encrypted digest is missing.");
                     } else {
                        this.digest = new byte[var12.dataLen];
                        System.arraycopy(var12.data, var12.dataOffset, this.digest, 0, var12.dataLen);
                        if (var13.dataPresent) {
                           this.unauthentAttrs = new X501Attributes(var13.data, var13.dataOffset, 8454145);
                        }

                     }
                  }
               }
            }
         }
      } catch (ASN_Exception var19) {
         throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: ", var19);
      } catch (AttributeException var20) {
         throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: ", var20);
      } catch (NameException var21) {
         throw new PKCS7Exception("Cannot decode the BER of the SignerInfo: ", var21);
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
      return this.serialNumber == null ? null : (byte[])this.serialNumber.clone();
   }

   /** @deprecated */
   public byte[] getIssuerAndSerialNumber() throws PKCS7Exception {
      if (this.issuer != null && this.serialNumber != null) {
         try {
            int var1 = this.issuer.getDERLen(0);
            byte[] var2 = new byte[var1];
            var1 = this.issuer.getDEREncoding(var2, 0, 0);
            SequenceContainer var3 = new SequenceContainer(0, true, 0);
            EndContainer var4 = new EndContainer();
            IntegerContainer var5;
            if ((this.serialNumber[0] & 128) >> 7 == 0) {
               var5 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
            } else {
               var5 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
            }

            EncodedContainer var6 = new EncodedContainer(12288, true, 0, var2, 0, var1);
            ASN1Container[] var7 = new ASN1Container[]{var3, var6, var5, var4};
            return ASN1.derEncode(var7);
         } catch (ASN_Exception var8) {
            throw new PKCS7Exception("Cannot encode issuerSerial: ", var8);
         } catch (NameException var9) {
            throw new PKCS7Exception("Cannot encode issuerSerial: ", var9);
         }
      } else {
         throw new PKCS7Exception("Cannot get issuerSerial, not all values set.");
      }
   }

   /** @deprecated */
   public void setDigestAlgorithm(String var1) throws PKCS7Exception {
      this.digestOID = DigestedData.setDigestAlgorithmInternal(this.context, var1);
      this.digestAlgName = var1;
   }

   /** @deprecated */
   public void setDigestAlgorithm(byte[] var1, int var2, int var3) throws PKCS7Exception {
      this.digestOID = DigestedData.setDigestAlgorithmInternal(var1, var2, var3);
   }

   /** @deprecated */
   public byte[] getDigestAlgorithmOID() {
      return this.digestOID;
   }

   /** @deprecated */
   public String getDigestAlgorithmName() throws PKCS7Exception {
      return DigestedData.getDigestAlgorithmNameInternal(this.context, this.digestOID, this.digestAlgName);
   }

   /** @deprecated */
   public void setAuthenticatedAttrs(X501Attributes var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Attributes are null");
      } else {
         try {
            this.authentAttrs = (X501Attributes)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Invalid attributes: ", var3);
         }
      }
   }

   /** @deprecated */
   protected byte[] getAuthenticatedAttrsBer() {
      return this.authenAttrsOrgBer != null ? this.authenAttrsOrgBer : null;
   }

   /** @deprecated */
   public X501Attributes getAuthenticatedAttrs() throws PKCS7Exception {
      if (this.authentAttrs == null) {
         return null;
      } else {
         try {
            return (X501Attributes)this.authentAttrs.clone();
         } catch (CloneNotSupportedException var2) {
            throw new PKCS7Exception("Invalid attributes.");
         }
      }
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(String var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Could not set algorithm OID: name is null.");
      } else {
         this.encryptionAlgName = var1;
         if (var1.indexOf("RSA") != -1) {
            try {
               JSAFE_AsymmetricCipher var2 = h.f(var1, "Java", this.context.b);
               this.digestEncryptionOID = var2.getDERAlgorithmID();
            } catch (JSAFE_Exception var4) {
               throw new PKCS7Exception("Could not set algorithm OID: ", var4);
            }
         } else {
            if (var1.indexOf("DSA") == -1) {
               throw new PKCS7Exception("Could not set Encryption Algorithm, " + var1 + " is invalid algorithm.");
            }

            try {
               JSAFE_Signature var5 = h.b(this.buildSignatureInfo(var1), "Java", this.context.b);
               this.digestEncryptionOID = var5.getDERAlgorithmID();
            } catch (JSAFE_Exception var3) {
               throw new PKCS7Exception("Could not set algorithm OID: ", var3);
            }
         }

      }
   }

   private String buildSignatureInfo(String var1) throws PKCS7Exception {
      if (this.getDigestAlgorithmName() == null) {
         this.setDigestAlgorithm("SHA1");
      }

      return this.getDigestAlgorithmName() + "/" + var1 + "/NoPad";
   }

   /** @deprecated */
   public void setEncryptionAlgorithm(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 != null && var3 > 0) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.digestEncryptionOID = new byte[var3];
            System.arraycopy(var1, var2, this.digestEncryptionOID, 0, var3);
         } else {
            throw new PKCS7Exception("Invalid Encryption Algorithm Identifier.");
         }
      } else {
         throw new PKCS7Exception("Could not set algorithm OID: OID is null");
      }
   }

   /** @deprecated */
   public byte[] getEncryptionAlgorithmOID() {
      return this.digestEncryptionOID == null ? null : (byte[])this.digestEncryptionOID.clone();
   }

   /** @deprecated */
   public String getEncryptionAlgorithmName() throws PKCS7Exception {
      if (this.digestEncryptionOID == null) {
         return null;
      } else if (this.encryptionAlgName != null) {
         return this.encryptionAlgName;
      } else {
         try {
            JSAFE_AsymmetricCipher var4 = h.f(this.digestEncryptionOID, 0, "Java", (FIPS140Context)this.context.b);
            return var4.getEncryptionAlgorithm();
         } catch (JSAFE_Exception var3) {
            try {
               JSAFE_Signature var1 = h.b(this.digestEncryptionOID, 0, "Java", (FIPS140Context)this.context.b);
               return var1.getSignatureAlgorithm();
            } catch (JSAFE_Exception var2) {
               throw new PKCS7Exception("Could not get encryption algorithm name: ", var2);
            }
         }
      }
   }

   private void checkForDSAWithParamsOID() throws PKCS7Exception {
      boolean var1 = true;
      byte var3;
      if (this.digestEncryptionOID[3] == 6) {
         var3 = 3;
      } else {
         var3 = 2;
      }

      if (this.digestEncryptionOID[var3] == 6) {
         byte[] var2 = new byte[this.digestEncryptionOID[var3 + 1]];
         System.arraycopy(this.digestEncryptionOID, var3 + 2, var2, 0, this.digestEncryptionOID[var3 + 1]);
         if (Arrays.equals(var2, OIDList.getOID("DSAX957", 4))) {
            this.setEncryptionAlgorithm("DSA");
         }
      }

   }

   /** @deprecated */
   protected void setEncryptedDigest(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.digest = new byte[var3];
         System.arraycopy(var1, var2, this.digest, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getEncryptedDigest() {
      return this.digest == null ? null : (byte[])this.digest.clone();
   }

   /** @deprecated */
   public void setUnauthenticatedAttrs(X501Attributes var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Null attributes.");
      } else {
         try {
            this.unauthentAttrs = (X501Attributes)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Invalid attributes: ", var3);
         }
      }
   }

   /** @deprecated */
   public X501Attributes getUnauthenticatedAttrs() throws PKCS7Exception {
      if (this.unauthentAttrs == null) {
         return null;
      } else {
         try {
            return (X501Attributes)this.unauthentAttrs.clone();
         } catch (CloneNotSupportedException var2) {
            throw new PKCS7Exception("Invalid attributes: ", var2);
         }
      }
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
         throw new PKCS7Exception("Passed array is null.");
      } else {
         try {
            if (this.asn1Template == null || var3 != special) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new PKCS7Exception("Unable to encode SignerInfo: ", var5);
         }
      }
   }

   private int derEncodeInit() throws PKCS7Exception {
      if (this.issuer != null && this.serialNumber != null) {
         try {
            int var1 = this.issuer.getDERLen(0);
            byte[] var2 = new byte[var1];
            this.issuer.getDEREncoding(var2, 0, 0);
            EncodedContainer var3 = null;
            if (this.authentAttrs != null) {
               var1 = this.authentAttrs.getDERLen(8454144);
               byte[] var4 = new byte[var1];
               var1 = this.authentAttrs.getDEREncoding(var4, 0, 8454144);
               var3 = new EncodedContainer(8466688, true, 0, var4, 0, var1);
            }

            EncodedContainer var23 = null;
            if (this.unauthentAttrs != null) {
               var1 = this.unauthentAttrs.getDERLen(8454145);
               byte[] var5 = new byte[var1];
               var1 = this.unauthentAttrs.getDEREncoding(var5, 0, 8454145);
               var23 = new EncodedContainer(8466689, true, 0, var5, 0, var1);
            }

            SequenceContainer var24 = new SequenceContainer(0, true, 0);
            EndContainer var6 = new EndContainer();
            EncodedContainer var7 = new EncodedContainer(12288, true, 0, var2, 0, var2.length);
            IntegerContainer var8;
            if ((this.serialNumber[0] & 128) >> 7 == 0) {
               var8 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
            } else {
               var8 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
            }

            ASN1Container[] var9 = new ASN1Container[]{var24, var7, var8, var6};
            ASN1Template var10 = new ASN1Template(var9);
            var1 = var10.derEncodeInit();
            byte[] var11 = new byte[var1];
            var10.derEncode(var11, 0);
            SequenceContainer var12 = new SequenceContainer(special, true, 0);
            IntegerContainer var13 = new IntegerContainer(0, true, 0, this.version);
            EncodedContainer var14 = new EncodedContainer(12288, true, 0, var11, 0, var11.length);
            if (this.digestOID == null) {
               throw new PKCS7Exception("DigestAlgorithmIdentifier is not set.");
            } else {
               EncodedContainer var15 = new EncodedContainer(12288, true, 0, this.digestOID, 0, this.digestOID.length);
               if (this.digestEncryptionOID == null) {
                  throw new PKCS7Exception("EncryptionAlgorithmIdentifier is not set.");
               } else {
                  EncodedContainer var16 = new EncodedContainer(12288, true, 0, this.digestEncryptionOID, 0, this.digestEncryptionOID.length);
                  int var17 = 0;
                  if (this.digest != null) {
                     var17 = this.digest.length;
                  }

                  OctetStringContainer var18 = new OctetStringContainer(0, true, 0, this.digest, 0, var17);
                  ASN1Container[] var19;
                  if (var3 != null) {
                     if (var23 != null) {
                        var19 = new ASN1Container[]{var12, var13, var14, var15, var3, var16, var18, var23, var6};
                        this.asn1Template = new ASN1Template(var19);
                     } else {
                        var19 = new ASN1Container[]{var12, var13, var14, var15, var3, var16, var18, var6};
                        this.asn1Template = new ASN1Template(var19);
                     }
                  } else if (var23 != null) {
                     var19 = new ASN1Container[]{var12, var13, var14, var15, var16, var18, var23, var6};
                     this.asn1Template = new ASN1Template(var19);
                  } else {
                     var19 = new ASN1Container[]{var12, var13, var14, var15, var16, var18, var6};
                     this.asn1Template = new ASN1Template(var19);
                  }

                  return this.asn1Template.derEncodeInit();
               }
            }
         } catch (ASN_Exception var20) {
            throw new PKCS7Exception("Cannot encode SignerInfo: ", var20);
         } catch (NameException var21) {
            throw new PKCS7Exception("Cannot encode X500Name: ", var21);
         } catch (AttributeException var22) {
            throw new PKCS7Exception("Cannot encode X501Attributes: ", var22);
         }
      } else {
         throw new PKCS7Exception("Cannot encode SignerInfo: issuerName or SerialNumber is not set.");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof SignerInfo) {
         SignerInfo var2 = (SignerInfo)var1;
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

            if (!CertJUtils.byteArraysEqual(this.serialNumber, var2.serialNumber)) {
               return false;
            } else if (!CertJUtils.byteArraysEqual(this.digestOID, var2.digestOID)) {
               return false;
            } else {
               if (this.authentAttrs != null) {
                  if (!this.authentAttrs.equals(var2.authentAttrs)) {
                     return false;
                  }
               } else if (var2.authentAttrs != null) {
                  return false;
               }

               if (!CertJUtils.byteArraysEqual(this.digestEncryptionOID, var2.digestEncryptionOID)) {
                  return false;
               } else {
                  if (this.unauthentAttrs != null) {
                     if (!this.unauthentAttrs.equals(var2.unauthentAttrs)) {
                        return false;
                     }
                  } else if (var2.unauthentAttrs != null) {
                     return false;
                  }

                  return CertJUtils.byteArraysEqual(this.digest, var2.digest);
               }
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.authentAttrs);
      var2 = var1 * var2 + Arrays.hashCode(this.digest);
      var2 = var1 * var2 + Arrays.hashCode(this.digestEncryptionOID);
      var2 = var1 * var2 + Arrays.hashCode(this.digestOID);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.issuer);
      var2 = var1 * var2 + Arrays.hashCode(this.serialNumber);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.unauthentAttrs);
      var2 = var1 * var2 + this.version;
      return var2;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SignerInfo var1 = new SignerInfo(this.context);
      var1.version = this.version;
      if (this.issuer != null) {
         var1.issuer = (X500Name)this.issuer.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = new byte[this.serialNumber.length];
         System.arraycopy(this.serialNumber, 0, var1.serialNumber, 0, this.serialNumber.length);
      }

      if (this.digestOID != null) {
         var1.digestOID = new byte[this.digestOID.length];
         System.arraycopy(this.digestOID, 0, var1.digestOID, 0, this.digestOID.length);
      }

      if (this.authentAttrs != null) {
         var1.authentAttrs = (X501Attributes)this.authentAttrs.clone();
      }

      if (this.digestEncryptionOID != null) {
         var1.digestEncryptionOID = new byte[this.digestEncryptionOID.length];
         System.arraycopy(this.digestEncryptionOID, 0, var1.digestEncryptionOID, 0, this.digestEncryptionOID.length);
      }

      if (this.unauthentAttrs != null) {
         var1.unauthentAttrs = (X501Attributes)this.unauthentAttrs.clone();
      }

      if (this.digest != null) {
         var1.digest = new byte[this.digest.length];
         System.arraycopy(this.digest, 0, var1.digest, 0, this.digest.length);
      }

      if (this.authenAttrsOrgBer != null) {
         var1.authenAttrsOrgBer = new byte[this.authenAttrsOrgBer.length];
         System.arraycopy(this.authenAttrsOrgBer, 0, var1.authenAttrsOrgBer, 0, this.authenAttrsOrgBer.length);
      }

      try {
         if (this.asn1Template != null) {
            var1.derEncodeInit();
         }

         return var1;
      } catch (PKCS7Exception var3) {
         throw new CloneNotSupportedException("Cannot set ASN1 template:" + var3.getMessage());
      }
   }
}
