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
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_Signature;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;

/** @deprecated */
public class ProofOfPossession implements Serializable, Cloneable {
   /** @deprecated */
   public static final int RA_VERIFIED_POP = 0;
   /** @deprecated */
   public static final int SIGNATURE_POP = 1;
   /** @deprecated */
   public static final int ENCIPHERMENT_POP = 2;
   /** @deprecated */
   public static final int AGREEMENT_POP = 3;
   private static final int RA_VERIFIED_SPECIAL = 8389888;
   private static final int SIGNATURE_SPECIAL = 8400897;
   private int type;
   private POPOSigningKeyInput poposkInput;
   private POPOPrivKey privPOPKey;
   private byte[] signature;
   private byte[] signatureAlgorithmBER;
   String transformation;
   CertRequest certRequest;
   private CertJ theCertJ;
   /** @deprecated */
   protected JSAFE_PublicKey pubKey;
   /** @deprecated */
   protected JSAFE_PrivateKey privKey;
   private ASN1Template asn1Template;
   private int special;

   /** @deprecated */
   public ProofOfPossession() {
      this.privPOPKey = new POPOPrivKey();
      this.transformation = "";
   }

   /** @deprecated */
   public ProofOfPossession(CertJ var1) {
      this.privPOPKey = new POPOPrivKey();
      this.transformation = "";
      this.theCertJ = var1;
   }

   /** @deprecated */
   public ProofOfPossession(int var1) throws CRMFException {
      this(var1, (CertJ)null);
   }

   /** @deprecated */
   public ProofOfPossession(int var1, CertJ var2) throws CRMFException {
      this.privPOPKey = new POPOPrivKey();
      this.transformation = "";
      if (var1 != 0 && var1 != 1 && var1 != 2 && var1 != 3) {
         throw new CRMFException("This POP is not supported.");
      } else {
         this.type = var1;
         this.theCertJ = var2;
      }
   }

   /** @deprecated */
   public void decodeProofOfPossession(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("ProofOfPossession Encoding is null.");
      } else {
         ChoiceContainer var4 = new ChoiceContainer(var3);
         EndContainer var5 = new EndContainer();
         EncodedContainer var6 = new EncodedContainer(8389888);
         EncodedContainer var7 = new EncodedContainer(8400897);
         EncodedContainer var8 = new EncodedContainer(10551042);
         EncodedContainer var9 = new EncodedContainer(10551043);
         ASN1Container[] var10 = new ASN1Container[]{var4, var6, var7, var8, var9, var5};

         try {
            ASN1.berDecode(var1, var2, var10);
         } catch (ASN_Exception var12) {
            throw new CRMFException(var12);
         }

         if (var6.dataPresent) {
            this.type = 0;
         } else if (var7.dataPresent) {
            this.type = 1;
            this.decodeSignature(var7.data, var7.dataOffset);
         } else if (var8.dataPresent) {
            this.type = 2;
            this.privPOPKey.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
            this.privPOPKey.decodePOPOPrivKey(var8.data, var8.dataOffset, 10485762);
         } else if (var9.dataPresent) {
            this.type = 3;
            this.privPOPKey.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
            this.privPOPKey.decodePOPOPrivKey(var9.data, var9.dataOffset, 10485763);
         }

      }
   }

   private void decodeSignature(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Signature Proof Of Possession is NULL.");
      } else {
         SequenceContainer var3 = new SequenceContainer(8400897);
         EndContainer var4 = new EndContainer();
         EncodedContainer var5 = new EncodedContainer(8466432);
         EncodedContainer var6 = new EncodedContainer(12288);
         BitStringContainer var7 = new BitStringContainer(0);
         ASN1Container[] var8 = new ASN1Container[]{var3, var5, var6, var7, var4};

         try {
            ASN1.berDecode(var1, var2, var8);
         } catch (ASN_Exception var10) {
            throw new CRMFException("Could not BER decode the POP.", var10);
         }

         if (var5.dataPresent) {
            this.poposkInput = new POPOSigningKeyInput(var5.data, var5.dataOffset);
         }

         this.signature = new byte[var7.dataLen];
         System.arraycopy(var7.data, var7.dataOffset, this.signature, 0, var7.dataLen);
         this.setSignatureAlgorithm(var6.data, var6.dataOffset, var6.dataLen);
      }
   }

   /** @deprecated */
   public boolean verifySignature(String var1, SecureRandom var2) throws CRMFException {
      JSAFE_Signature var4 = null;
      if (this.signature == null) {
         throw new CRMFException("Signature is null, cannot verify it.");
      } else {
         JSAFE_PublicKey var3;
         int var5;
         byte[] var6;
         if (this.poposkInput != null) {
            var5 = this.poposkInput.getDERLen();
            if (var5 == 0) {
               throw new CRMFException("Cannot DER-encode poposkInput.");
            }

            var6 = new byte[var5];
            this.poposkInput.getDEREncoding(var6, 0);
            var6[0] = 48;
            var3 = this.poposkInput.getSubjectPublicKey();
         } else {
            if (this.certRequest == null) {
               throw new CRMFException("CertRequest is not set.");
            }

            CertTemplate var7 = this.certRequest.getCertTemplate();
            var3 = var7.getSubjectPublicKey();
            if (var3 == null) {
               throw new CRMFException("Public key is not set in CertRequest; Cannot verify the signature.");
            }

            var5 = this.certRequest.getDERLen(0);
            if (var5 == 0) {
               throw new CRMFException("Cannot DER-encode CertRequest.");
            }

            var6 = new byte[var5];
            this.certRequest.getDEREncoding(var6, 0, 0);
         }

         boolean var13;
         try {
            var4 = h.b(this.transformation, var1, this.theCertJ);
            if (this.theCertJ == null) {
               var4.verifyInit(var3, var2);
            } else {
               var4.verifyInit(var3, (JSAFE_Parameters)null, var2, this.theCertJ.getPKCS11Sessions());
            }

            var4.verifyUpdate(var6, 0, var6.length);
            var13 = var4.verifyFinal(this.signature, 0, this.signature.length);
         } catch (JSAFE_Exception var11) {
            throw new CRMFException("Could not verify the POP's signature: ", var11);
         } finally {
            if (var4 != null) {
               var4.clearSensitiveData();
            }

         }

         return var13;
      }
   }

   /** @deprecated */
   public int getPOPType() {
      return this.type;
   }

   /** @deprecated */
   public POPOSigningKeyInput getPOPOSigningKeyInput() {
      return this.type != 1 ? null : this.poposkInput;
   }

   /** @deprecated */
   public void setPOPOSigningKeyInput(POPOSigningKeyInput var1) throws CRMFException {
      if (this.type != 1) {
         throw new CRMFException("This POP is NOT POPOSigningKey type.");
      } else if (var1 == null) {
         throw new CRMFException("POPOSigningKeyInput object is null.");
      } else {
         this.poposkInput = var1;
      }
   }

   /** @deprecated */
   public String getAlgTransformation() {
      return this.type != 1 ? null : this.transformation;
   }

   /** @deprecated */
   public byte[] getAlgBER() {
      if (this.type == 1 && this.signatureAlgorithmBER != null) {
         byte[] var1 = new byte[this.signatureAlgorithmBER.length];
         System.arraycopy(this.signatureAlgorithmBER, 0, var1, 0, this.signatureAlgorithmBER.length);
         return var1;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public void setSignatureAlgorithm(String var1) throws CRMFException {
      if (this.type != 1) {
         throw new CRMFException("This POP is NOT POPOSigningKey type.");
      } else if (var1 == null) {
         throw new CRMFException("POP Transformation is null.");
      } else {
         this.transformation = var1;

         try {
            this.signatureAlgorithmBER = AlgorithmID.derEncodeAlgID(var1, 1, (byte[])null, 0, 0);
         } catch (ASN_Exception var3) {
            throw new CRMFException("POP Transformation is invalid. ", var3);
         }
      }
   }

   /** @deprecated */
   public void setSignatureAlgorithm(byte[] var1, int var2, int var3) throws CRMFException {
      if (this.type != 1) {
         throw new CRMFException("This POP is NOT POPOSigningKey type.");
      } else if (var1 != null && var3 != 0) {
         try {
            this.signatureAlgorithmBER = new byte[var3];
            System.arraycopy(var1, var2, this.signatureAlgorithmBER, 0, var3);
            this.transformation = AlgorithmID.berDecodeAlgID(var1, var2, 1, (EncodedContainer)null);
            if (this.transformation == null) {
               throw new CRMFException("Unknown Signature Algorithm in POP.");
            }
         } catch (ASN_Exception var5) {
            throw new CRMFException("Cannot set Signature Algorithm in POP.", var5);
         }
      } else {
         throw new CRMFException("POP Algorithm ID is null.");
      }
   }

   /** @deprecated */
   public byte[] getSignature() {
      if (this.type == 1 && this.signature != null) {
         byte[] var1 = new byte[this.signature.length];
         System.arraycopy(this.signature, 0, var1, 0, this.signature.length);
         return var1;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public void setCertRequest(CertRequest var1) throws CRMFException {
      if (this.type != 1) {
         throw new CRMFException("This POP is NOT POPOSigningKey type.");
      } else if (var1 == null) {
         throw new CRMFException("The request in POP is NULL.");
      } else {
         CertTemplate var2 = var1.getCertTemplate();
         if (var2 == null) {
            throw new CRMFException("Invalid CertRequest: CertTemplate is missing.");
         } else if (var2.getSubjectName() != null && var2.getSubjectPublicKey() != null) {
            this.certRequest = var1;
         } else {
            throw new CRMFException("Subject Name and / or Public Key values are missing.");
         }
      }
   }

   /** @deprecated */
   public void signPOP(String var1, JSAFE_PrivateKey var2, SecureRandom var3) throws CRMFException {
      if (this.type != 1) {
         throw new CRMFException("This POP is NOT POPOSigningKey type.");
      } else {
         int var4;
         byte[] var5;
         if (this.certRequest != null) {
            this.poposkInput = null;
            var4 = this.certRequest.getDERLen(0);
            if (var4 == 0) {
               throw new CRMFException("Cannot DER-encode CertRequest in POP.");
            }

            var5 = new byte[var4];
            this.certRequest.getDEREncoding(var5, 0, 0);
         } else {
            if (this.poposkInput == null) {
               throw new CRMFException("Data is not set in poposkInput.");
            }

            var4 = this.poposkInput.getDERLen();
            if (var4 == 0) {
               throw new CRMFException("Cannot DER-encode poposkInput.");
            }

            var5 = new byte[var4];
            this.poposkInput.getDEREncoding(var5, 0);
            var5[0] = 48;
         }

         JSAFE_Signature var6 = null;

         try {
            var6 = h.b(this.transformation, var1, this.theCertJ);
            if (this.theCertJ == null) {
               var6.signInit(var2, var3);
            } else {
               var6.signInit(var2, (JSAFE_Parameters)null, var3, this.theCertJ.getPKCS11Sessions());
            }

            var6.signUpdate(var5, 0, var5.length);
            this.signature = var6.signFinal();
         } catch (JSAFE_Exception var11) {
            throw new CRMFException("Could not sign the POP: ", var11);
         } finally {
            if (var6 != null) {
               var6.clearSensitiveData();
            }

         }

      }
   }

   /** @deprecated */
   public void setEnvironment(CertJ var1, JSAFE_PublicKey var2, JSAFE_PrivateKey var3) {
      this.theCertJ = var1;
      if (var2 != null) {
         this.pubKey = var2;
      }

      if (var3 != null) {
         this.privKey = var3;
      }

   }

   /** @deprecated */
   public void setCertJ(CertJ var1) {
      this.theCertJ = var1;
   }

   /** @deprecated */
   public CertJ getCertJ() {
      return this.theCertJ;
   }

   /** @deprecated */
   public void setKeys(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) {
      if (var1 != null) {
         this.pubKey = var1;
      }

      if (var2 != null) {
         this.privKey = var2;
      }

   }

   /** @deprecated */
   public void setPOPOPrivKey(POPOPrivKey var1) throws CRMFException {
      if (this.type != 2 && this.type != 3) {
         throw new CRMFException("Wrong POP type.");
      } else if (var1 == null) {
         throw new CRMFException("POPOPrivateKey object is null.");
      } else {
         this.privPOPKey = var1;
      }
   }

   /** @deprecated */
   public POPOPrivKey getPOPOPrivKey() throws CRMFException {
      return this.privPOPKey;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         ProofOfPossession var1 = new ProofOfPossession(this.type);
         if (this.poposkInput != null) {
            var1.poposkInput = (POPOSigningKeyInput)this.poposkInput.clone();
         }

         if (this.signature != null) {
            var1.signature = new byte[this.signature.length];
            System.arraycopy(this.signature, 0, var1.signature, 0, this.signature.length);
         }

         if (this.signatureAlgorithmBER != null) {
            var1.signatureAlgorithmBER = new byte[this.signatureAlgorithmBER.length];
            System.arraycopy(this.signatureAlgorithmBER, 0, var1.signatureAlgorithmBER, 0, this.signatureAlgorithmBER.length);
         }

         var1.transformation = this.transformation;
         if (this.certRequest != null) {
            var1.certRequest = (CertRequest)this.certRequest.clone();
         }

         if (this.privPOPKey != null) {
            var1.privPOPKey = (POPOPrivKey)this.privPOPKey.clone();
         }

         var1.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
         return var1;
      } catch (CRMFException var2) {
         throw new CloneNotSupportedException(var2.getMessage());
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof ProofOfPossession) {
         ProofOfPossession var2 = (ProofOfPossession)var1;
         if (this.type != var2.type) {
            return false;
         } else {
            if (this.certRequest != null) {
               if (!this.certRequest.equals(var2.certRequest)) {
                  return false;
               }
            } else if (var2.certRequest != null) {
               return false;
            }

            if (this.poposkInput != null) {
               if (!this.poposkInput.equals(var2.poposkInput)) {
                  return false;
               }
            } else if (var2.poposkInput != null) {
               return false;
            }

            if (this.transformation != null) {
               if (!this.transformation.equals(var2.transformation)) {
                  return false;
               }
            } else if (var2.transformation != null) {
               return false;
            }

            if (!CertJUtils.byteArraysEqual(this.signatureAlgorithmBER, var2.signatureAlgorithmBER)) {
               return false;
            } else if (!CertJUtils.byteArraysEqual(this.signature, var2.signature)) {
               return false;
            } else {
               if (this.privPOPKey != null) {
                  if (!this.privPOPKey.equals(var2.privPOPKey)) {
                     return false;
                  }
               } else if (var2.privPOPKey != null) {
                  return false;
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
      byte var1 = 31;
      int var2 = 1;
      var2 = var1 * var2 + this.type;
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.certRequest);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.poposkInput);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.privPOPKey);
      var2 = var1 * var2 + Arrays.hashCode(this.signature);
      var2 = var1 * var2 + Arrays.hashCode(this.signatureAlgorithmBER);
      var2 = var1 * var2 + CertJInternalHelper.hashCodeValue(this.transformation);
      return var2;
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding of ProofOfPossession.", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      this.special = var1;
      return this.encodeInit();
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      this.special = var3;
      if (var1 == null) {
         throw new CRMFException("Specified array in ProofOfPossession is null.");
      } else if (this.type == 0) {
         var1[0] = -128;
         var1[1] = 0;
         return 2;
      } else {
         try {
            if (this.asn1Template == null) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode ProofOfPossession.", var6);
         }
      }
   }

   private int encodeInit() throws CRMFException {
      if (this.type == 0) {
         return 2;
      } else {
         try {
            boolean var1 = false;
            boolean var2 = false;
            boolean var3 = false;
            int var4 = 0;
            byte[] var5 = null;
            EndContainer var6 = new EndContainer();
            if (this.type == 1) {
               var1 = true;
               SequenceContainer var7 = new SequenceContainer(8400897, true, 0);
               boolean var8 = true;
               int var9 = 0;
               byte[] var10 = null;
               if (this.poposkInput == null) {
                  var8 = false;
               } else {
                  var9 = this.poposkInput.getDERLen();
                  var10 = new byte[var9];
                  var9 = this.poposkInput.getDEREncoding(var10, 0);
               }

               EncodedContainer var11 = new EncodedContainer(8466432, var8, 0, var10, 0, var9);
               if (this.signatureAlgorithmBER == null) {
                  throw new CRMFException("Signature Algorithm ID is not set.");
               }

               EncodedContainer var12 = new EncodedContainer(12288, true, 0, this.signatureAlgorithmBER, 0, this.signatureAlgorithmBER.length);
               if (this.signature == null) {
                  throw new CRMFException("Signature is not set.");
               }

               BitStringContainer var13 = new BitStringContainer(0, true, 0, this.signature, 0, this.signature.length);
               ASN1Container[] var14 = new ASN1Container[]{var7, var11, var12, var13, var6};
               ASN1Template var15 = new ASN1Template(var14);
               var4 = var15.derEncodeInit();
               var5 = new byte[var4];
               var4 = var15.derEncode(var5, 0);
            } else if (this.type == 2) {
               if (this.privPOPKey == null) {
                  throw new CRMFException("POPOPrivKey is not set.");
               }

               var2 = true;
               var4 = this.privPOPKey.getDERLen(10485762);
               var5 = new byte[var4];
               var4 = this.privPOPKey.getDEREncoding(var5, 0, 10485762);
            } else if (this.type == 3) {
               if (this.privPOPKey == null) {
                  throw new CRMFException("POPOPrivKey is not set.");
               }

               var3 = true;
               var4 = this.privPOPKey.getDERLen(10485763);
               var5 = new byte[var4];
               var4 = this.privPOPKey.getDEREncoding(var5, 0, 10485763);
            }

            ChoiceContainer var17 = new ChoiceContainer(this.special, 0);
            EncodedContainer var18 = new EncodedContainer(8400897, var1, 0, var5, 0, var4);
            EncodedContainer var19 = new EncodedContainer(10551042, var2, 0, var5, 0, var4);
            EncodedContainer var20 = new EncodedContainer(10551043, var3, 0, var5, 0, var4);
            ASN1Container[] var21 = new ASN1Container[]{var17, var18, var19, var20, var6};
            this.asn1Template = new ASN1Template(var21);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var16) {
            throw new CRMFException(var16);
         }
      }
   }
}
