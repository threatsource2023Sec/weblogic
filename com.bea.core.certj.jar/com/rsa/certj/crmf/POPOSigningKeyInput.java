package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.AlgorithmID;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MAC;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;

/** @deprecated */
public class POPOSigningKeyInput implements Serializable, Cloneable {
   private ASN1Template asn1Template;
   private byte[] salt;
   private int count;
   private GeneralName sender;
   private byte[] subjectPublicKeyInfo;
   private byte[] macValue;
   private char[] secret;
   byte[] passwordBasedMAC = new byte[]{42, -122, 72, -122, -10, 125, 7, 66, 13};
   byte[] hmacSHA1 = new byte[]{43, 6, 1, 5, 5, 8, 1, 2};

   /** @deprecated */
   public POPOSigningKeyInput() {
   }

   /** @deprecated */
   public POPOSigningKeyInput(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("POPOSigningKeyInput Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(8454144);
         ChoiceContainer var4 = new ChoiceContainer(0);
         EncodedContainer var5 = new EncodedContainer(10551040);
         EncodedContainer var6 = new EncodedContainer(12288);
         EncodedContainer var7 = new EncodedContainer(12288);
         EndContainer var8 = new EndContainer();
         ASN1Container[] var9 = new ASN1Container[]{var3, var4, var5, var6, var8, var7, var8};

         try {
            ASN1.berDecode(var1, var2, var9);
         } catch (ASN_Exception var11) {
            throw new CRMFException("FIRST", var11);
         }

         try {
            if (var5.dataPresent) {
               this.sender = new GeneralName(var5.data, var5.dataOffset, 10485760);
            }
         } catch (NameException var12) {
            throw new CRMFException("NAME ", var12);
         }

         if (var6.dataPresent) {
            this.setPKMACValue(var6.data, var6.dataOffset);
         }

         this.setSubjectPublicKey(var7.data, var7.dataOffset);
      }
   }

   private void setPKMACValue(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("PKMACValue Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EncodedContainer var4 = new EncodedContainer(12288);
         BitStringContainer var5 = new BitStringContainer(0);
         EndContainer var6 = new EndContainer();
         ASN1Container[] var7 = new ASN1Container[]{var3, var4, var5, var6};

         try {
            ASN1.berDecode(var1, var2, var7);
         } catch (ASN_Exception var9) {
            throw new CRMFException("Could not BER decode the PKMACValue.", var9);
         }

         this.setAlgorithmID(var4.data, var4.dataOffset);
         this.macValue = new byte[var5.dataLen];
         System.arraycopy(var5.data, var5.dataOffset, this.macValue, 0, var5.dataLen);
      }
   }

   /** @deprecated */
   public boolean verifyPKMACValue() throws CRMFException {
      if (this.secret == null) {
         throw new CRMFException("Secret value is not set in POPOSigningKeyInput.");
      } else if (this.subjectPublicKeyInfo == null) {
         throw new CRMFException("Public Key is not set in POPOSigningKeyInput.");
      } else {
         try {
            String var1 = "PBE/HMAC/SHA1/PKIXPBE-" + this.count;
            JSAFE_MAC var2 = h.d(var1, "Java", (CertJ)null);
            var2.setSalt(this.salt, 0, this.salt.length);
            JSAFE_SecretKey var3 = var2.getBlankKey();
            var3.setPassword(this.secret, 0, this.secret.length);
            var2.verifyInit(var3, (SecureRandom)null);
            var2.verifyUpdate(this.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
            return var2.verifyFinal(this.macValue, 0, this.macValue.length);
         } catch (JSAFE_Exception var4) {
            throw new CRMFException(var4);
         }
      }
   }

   private void setAlgorithmID(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Password-Based MAC algorithm ID Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         EncodedContainer var5 = new EncodedContainer(12288);
         OIDContainer var6 = new OIDContainer(0);
         ASN1Container[] var7 = new ASN1Container[]{var3, var6, var5, var4};

         try {
            ASN1.berDecode(var1, var2, var7);
         } catch (ASN_Exception var21) {
            throw new CRMFException("Could not BER decode the PKMAC algID.", var21);
         }

         if (this.passwordBasedMAC.length != var6.dataLen) {
            throw new CRMFException("Wrong OID for Password-Based MAC.");
         } else {
            for(int var8 = 0; var8 < var6.dataLen; ++var8) {
               if (this.passwordBasedMAC[var8] != var6.data[var8 + var6.dataOffset]) {
                  throw new CRMFException("Wrong OID for Password-Based MAC.");
               }
            }

            SequenceContainer var23 = new SequenceContainer(0);
            OctetStringContainer var9 = new OctetStringContainer(0);
            EncodedContainer var10 = new EncodedContainer(12288);
            IntegerContainer var11 = new IntegerContainer(0);
            EncodedContainer var12 = new EncodedContainer(12288);
            ASN1Container[] var13 = new ASN1Container[]{var23, var9, var10, var11, var12, var4};

            try {
               ASN1.berDecode(var5.data, var5.dataOffset, var13);
            } catch (ASN_Exception var20) {
               throw new CRMFException(var20);
            }

            this.salt = new byte[var9.dataLen];
            System.arraycopy(var9.data, var9.dataOffset, this.salt, 0, var9.dataLen);

            try {
               this.count = var11.getValueAsInt();
               String var14 = AlgorithmID.berDecodeAlgID(var10.data, var10.dataOffset, 11, (EncodedContainer)null);
               if (var14 == null) {
                  throw new CRMFException("Cannot recognize the digest algorithm.");
               }

               if (!var14.equals("SHA1")) {
                  throw new CRMFException("Digest algorithm SHA1 was expected.");
               }
            } catch (ASN_Exception var22) {
               throw new CRMFException(var22);
            }

            try {
               SequenceContainer var24 = new SequenceContainer(0);
               EncodedContainer var15 = new EncodedContainer(65280);
               OIDContainer var16 = new OIDContainer(0);
               ASN1Container[] var17 = new ASN1Container[]{var24, var16, var15, var4};
               ASN1.berDecode(var12.data, var12.dataOffset, var17);
               if (var16.dataLen != this.hmacSHA1.length) {
                  throw new CRMFException("Wrong HMAC OID.");
               } else {
                  for(int var18 = 0; var18 < this.hmacSHA1.length; ++var18) {
                     if (var16.data[var18 + var16.dataOffset] != this.hmacSHA1[var18]) {
                        throw new CRMFException("Wrong HMAC OID.");
                     }
                  }

               }
            } catch (ASN_Exception var19) {
               throw new CRMFException("Could not BER decode the HMAC algID.", var19);
            }
         }
      }
   }

   /** @deprecated */
   public void setSharedSecret(char[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.secret = new char[var1.length];
         System.arraycopy(var1, var2, this.secret, 0, var3);
      } else {
         throw new CRMFException("Shared secret is null in PBMParameter.");
      }
   }

   /** @deprecated */
   public void setSalt(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.salt = new byte[var3];
         System.arraycopy(var1, var2, this.salt, 0, var3);
      } else {
         throw new CRMFException("The Salt value is null in PBMParameter.");
      }
   }

   /** @deprecated */
   public byte[] getSalt() {
      if (this.salt == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.salt.length];
         System.arraycopy(this.salt, 0, var1, 0, this.salt.length);
         return var1;
      }
   }

   /** @deprecated */
   public int getIterationCount() {
      return this.count;
   }

   /** @deprecated */
   public void setIterationCount(int var1) {
      this.count = var1;
   }

   /** @deprecated */
   public GeneralName getSenderName() throws CRMFException {
      if (this.sender == null) {
         return null;
      } else {
         try {
            return (GeneralName)this.sender.clone();
         } catch (CloneNotSupportedException var2) {
            throw new CRMFException(var2);
         }
      }
   }

   /** @deprecated */
   public void setSenderName(GeneralName var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in SenderName is null.");
      } else {
         try {
            this.sender = (GeneralName)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException(var3);
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(JSAFE_PublicKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Public key is null in POPOSigningKeyInput.");
      } else {
         try {
            String var2;
            if (var1.getAlgorithm().compareTo("DSA") == 0) {
               var2 = "DSAPublicKeyX957BER";
            } else {
               var2 = var1.getAlgorithm() + "PublicKeyBER";
            }

            byte[][] var3 = var1.getKeyData(var2);
            this.subjectPublicKeyInfo = var3[0];
         } catch (JSAFE_Exception var4) {
            throw new CRMFException("Could not read the public key in POPOSigningKeyInput. ", var4);
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Public key encoding is null in POPOSigningKeyInput.");
      } else {
         JSAFE_PublicKey var3 = null;

         try {
            var1[var2] = 48;
            var3 = h.a(var1, var2, "Java", (CertJ)null);
            this.setSubjectPublicKey(var3);
         } catch (JSAFE_Exception var8) {
            throw new CRMFException("Could not read the public key in POPOSigningKeyInput. ", var8);
         } finally {
            if (var3 != null) {
               var3.clearSensitiveData();
            }

         }

      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getSubjectPublicKey() throws CRMFException {
      if (this.subjectPublicKeyInfo == null) {
         return null;
      } else {
         try {
            return h.a(this.subjectPublicKeyInfo, 0, "Java", (CertJ)((CertJ)null));
         } catch (JSAFE_Exception var2) {
            throw new CRMFException("Cannot retrieve the public key in POPOSigningKeyInput: ", var2);
         }
      }
   }

   /** @deprecated */
   public byte[] getSubjectPublicKeyBER() {
      return this.subjectPublicKeyInfo == null ? null : (byte[])((byte[])this.subjectPublicKeyInfo.clone());
   }

   /** @deprecated */
   public byte[] getPKMACValue() {
      if (this.macValue == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.macValue.length];
         System.arraycopy(this.macValue, 0, var1, 0, this.macValue.length);
         return var1;
      }
   }

   private void computeMacValue() throws CRMFException {
      if (this.secret == null) {
         throw new CRMFException("Shared Secret is not set in PKMAC value.");
      } else if (this.salt == null) {
         throw new CRMFException("Salt value is not set in PKMAC value.");
      } else if (this.subjectPublicKeyInfo == null) {
         throw new CRMFException("Public Key is not set in PKMAC value.");
      } else if (this.count == 0) {
         throw new CRMFException("Iteration count is not set in PKMAC value.");
      } else {
         try {
            String var1 = "PBE/HMAC/SHA1/PKIXPBE-" + this.count;
            JSAFE_MAC var2 = h.d(var1, "Java", (CertJ)null);
            var2.setSalt(this.salt, 0, this.salt.length);
            JSAFE_SecretKey var3 = var2.getBlankKey();
            var3.setPassword(this.secret, 0, this.secret.length);
            var2.macInit(var3, (SecureRandom)null);
            var2.macUpdate(this.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
            this.macValue = var2.macFinal();
            var2.clearSensitiveData();
         } catch (JSAFE_Exception var4) {
            throw new CRMFException(var4);
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      POPOSigningKeyInput var1 = new POPOSigningKeyInput();
      var1.count = this.count;
      if (this.salt != null) {
         var1.salt = new byte[this.salt.length];
         System.arraycopy(this.salt, 0, var1.salt, 0, this.salt.length);
      }

      if (this.sender != null) {
         var1.sender = (GeneralName)this.sender.clone();
      }

      if (this.subjectPublicKeyInfo != null) {
         var1.subjectPublicKeyInfo = new byte[this.subjectPublicKeyInfo.length];
         System.arraycopy(this.subjectPublicKeyInfo, 0, var1.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
      }

      if (this.macValue != null) {
         var1.macValue = new byte[this.macValue.length];
         System.arraycopy(this.macValue, 0, var1.macValue, 0, this.macValue.length);
      }

      if (this.secret != null) {
         var1.secret = new char[this.secret.length];
         System.arraycopy(this.secret, 0, var1.secret, 0, this.secret.length);
      }

      try {
         if (this.asn1Template != null) {
            var1.encodeInit();
         }

         return var1;
      } catch (CRMFException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof POPOSigningKeyInput) {
         POPOSigningKeyInput var2 = (POPOSigningKeyInput)var1;

         try {
            int var3 = this.getDERLen();
            int var4 = var2.getDERLen();
            if (var3 != var4) {
               return false;
            } else if (var3 != 0 && var4 != 0) {
               byte[] var5 = new byte[var3];
               byte[] var6 = new byte[var4];
               var3 = this.getDEREncoding(var5, 0);
               var4 = var2.getDEREncoding(var6, 0);
               if (var3 != var4) {
                  return false;
               } else {
                  for(int var7 = 0; var7 < var3; ++var7) {
                     if (var5[var7] != var6[var7]) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         } catch (CRMFException var8) {
            return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      byte var1 = 31;
      int var2 = 1;

      try {
         int var3 = this.getDERLen();
         byte[] var4 = new byte[var3];
         this.getDEREncoding(var4, 0);
         var2 = var1 * var2 + Arrays.hashCode(var4);
         return var2;
      } catch (CRMFException var5) {
         return 0;
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("POPOSigningKeyInput Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen() throws CRMFException {
      return this.encodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified POPOSigningKeyInput array is null.");
      } else {
         try {
            if (this.asn1Template == null) {
               this.getDERLen();
            }

            int var3 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode POPOSigningKeyInput.", var5);
         }
      }
   }

   private int encodeInit() throws CRMFException {
      try {
         boolean var1 = true;
         boolean var2 = true;
         byte[] var3 = null;
         int var4 = 0;
         byte[] var5 = null;
         int var6 = 0;
         if (this.subjectPublicKeyInfo == null) {
            throw new CRMFException("Public key is not set.");
         } else {
            EncodedContainer var11;
            if (this.sender != null) {
               var2 = false;
               var4 = this.sender.getDERLen(10485760);
               var3 = new byte[var4];
               var4 = this.sender.getDEREncoding(var3, 0, 10485760);
            } else {
               var1 = false;
               if (this.macValue == null) {
                  this.computeMacValue();
               }

               byte[] var7 = new byte[this.hmacSHA1.length + 6];
               var7[0] = 48;
               var7[1] = (byte)(this.hmacSHA1.length + 4);
               var7[2] = 6;
               var7[3] = (byte)this.hmacSHA1.length;
               System.arraycopy(this.hmacSHA1, 0, var7, 4, this.hmacSHA1.length);
               var7[this.hmacSHA1.length + 4] = 5;
               var7[this.hmacSHA1.length + 5] = 0;
               byte[] var8 = AlgorithmID.derEncodeAlgID("SHA1", 11, (byte[])null, 0, 0);
               SequenceContainer var9 = new SequenceContainer(0, true, 0);
               OctetStringContainer var10 = new OctetStringContainer(0, true, 0, this.salt, 0, this.salt.length);
               var11 = new EncodedContainer(12288, true, 0, var8, 0, var8.length);
               IntegerContainer var12 = new IntegerContainer(0, true, 0, this.count);
               EncodedContainer var13 = new EncodedContainer(12288, true, 0, var7, 0, var7.length);
               EndContainer var14 = new EndContainer();
               ASN1Container[] var15 = new ASN1Container[]{var9, var10, var11, var12, var13, var14};
               ASN1Template var16 = new ASN1Template(var15);
               int var17 = var16.derEncodeInit();
               byte[] var18 = new byte[var17];
               var17 = var16.derEncode(var18, 0);
               byte[] var19 = new byte[this.passwordBasedMAC.length + var17 + 4];
               var19[0] = 48;
               var19[1] = (byte)(this.passwordBasedMAC.length + var17 + 2);
               var19[2] = 6;
               var19[3] = (byte)this.passwordBasedMAC.length;
               System.arraycopy(this.passwordBasedMAC, 0, var19, 4, this.passwordBasedMAC.length);
               System.arraycopy(var18, 0, var19, this.passwordBasedMAC.length + 4, var18.length);
               SequenceContainer var20 = new SequenceContainer(0, true, 0);
               EncodedContainer var21 = new EncodedContainer(12288, true, 0, var19, 0, var19.length);
               BitStringContainer var22 = new BitStringContainer(0, true, 0, this.macValue, 0, this.macValue.length);
               ASN1Container[] var23 = new ASN1Container[]{var20, var21, var22, var14};
               ASN1Template var24 = new ASN1Template(var23);
               var6 = var24.derEncodeInit();
               var5 = new byte[var6];
               var6 = var24.derEncode(var5, 0);
            }

            SequenceContainer var27 = new SequenceContainer(8454144, true, 0);
            EndContainer var28 = new EndContainer();
            ChoiceContainer var29 = new ChoiceContainer(0, 0);
            EncodedContainer var30 = new EncodedContainer(10485760, var1, 0, var3, 0, var4);
            var11 = new EncodedContainer(12288, var2, 0, var5, 0, var6);
            EncodedContainer var31 = new EncodedContainer(12288, true, 0, this.subjectPublicKeyInfo, 0, this.subjectPublicKeyInfo.length);
            ASN1Container[] var32 = new ASN1Container[]{var27, var29, var30, var11, var28, var31, var28};
            this.asn1Template = new ASN1Template(var32);
            return this.asn1Template.derEncodeInit();
         }
      } catch (ASN_Exception var25) {
         throw new CRMFException("Unable to encode POPOSigningKeyInput.", var25);
      } catch (NameException var26) {
         throw new CRMFException("Unable to encode POPOSigningKeyInput.", var26);
      }
   }
}
