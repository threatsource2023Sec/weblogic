package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class CertReqMessages implements Serializable, Cloneable {
   private Vector[] messages = this.createVectorArray(3);
   private int special;
   private ASN1Template asn1Template;
   private CertPathCtx theCertPathCtx;
   private CertJ theCertJ;
   private JSAFE_PublicKey pubKey;
   private JSAFE_PrivateKey privKey;

   /** @deprecated */
   public CertReqMessages() {
   }

   /** @deprecated */
   public CertReqMessages(CertRequest var1, ProofOfPossession var2, RegInfo var3) throws CRMFException {
      this.addCertReqMsg(var1, var2, var3);
   }

   /** @deprecated */
   public void addCertReqMsg(CertRequest var1, ProofOfPossession var2, RegInfo var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Cert Request is NULL.");
      } else {
         this.messages[0].addElement(var1);
         if (var2 != null) {
            this.messages[1].addElement(var2);
         } else {
            this.messages[1].addElement((Object)null);
         }

         if (var3 != null) {
            this.messages[2].addElement(var3);
         } else {
            this.messages[2].addElement((Object)null);
         }

      }
   }

   /** @deprecated */
   public void decodeCertReqMessages(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("CertReqMessages Encoding is null.");
      } else {
         this.special = var3;

         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               SequenceContainer var9 = new SequenceContainer(0);
               EndContainer var10 = new EndContainer();
               EncodedContainer var11 = new EncodedContainer(12288);
               ChoiceContainer var12 = new ChoiceContainer(65536);
               EncodedContainer var13 = new EncodedContainer(8455424);
               EncodedContainer var14 = new EncodedContainer(8466433);
               EncodedContainer var15 = new EncodedContainer(10616578);
               EncodedContainer var16 = new EncodedContainer(10616579);
               EncodedContainer var17 = new EncodedContainer(77824);
               ASN1Container[] var18 = new ASN1Container[]{var9, var11, var12, var13, var14, var15, var16, var10, var17, var10};
               ASN1.berDecode(var8.data, var8.dataOffset, var18);
               CertRequest var19 = new CertRequest();
               var19.setEnvironment(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);
               var19.decodeCertRequest(var11.data, var11.dataOffset, 0);
               this.messages[0].addElement(var19);
               ProofOfPossession var20;
               if (var13.dataPresent) {
                  var20 = new ProofOfPossession(0);
                  var20.decodeProofOfPossession(var13.data, var13.dataOffset, 65536);
                  this.messages[1].addElement(var20);
               } else if (var14.dataPresent) {
                  var20 = new ProofOfPossession(1);
                  CertTemplate var21 = var19.getCertTemplate();
                  if (var21 == null) {
                     throw new CRMFException("Invalid CertRequest: CertTemplate is missing.");
                  }

                  if (var21.getSubjectName() != null && var21.getSubjectPublicKey() != null) {
                     var20.setCertRequest(var19);
                  }

                  var20.decodeProofOfPossession(var14.data, var14.dataOffset, 65536);
                  this.messages[1].addElement(var20);
               } else if (var15.dataPresent) {
                  var20 = new ProofOfPossession(2);
                  var20.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
                  var20.decodeProofOfPossession(var15.data, var15.dataOffset, 65536);
                  this.messages[1].addElement(var20);
               } else if (var16.dataPresent) {
                  var20 = new ProofOfPossession();
                  var20.setEnvironment(this.theCertJ, this.pubKey, this.privKey);
                  var20.decodeProofOfPossession(var16.data, var16.dataOffset, 65536);
                  this.messages[1].addElement(var20);
               } else {
                  this.messages[1].addElement((Object)null);
               }

               if (var17.dataPresent) {
                  this.messages[2].addElement(new RegInfo(var17.data, var17.dataOffset, 0));
               } else {
                  this.messages[2].addElement((Object)null);
               }
            }

         } catch (ASN_Exception var22) {
            throw new CRMFException("Could not BER decode the cert request messages. ", var22);
         }
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
   public void setEnvironment(CertJ var1, CertPathCtx var2, JSAFE_PublicKey var3, JSAFE_PrivateKey var4) {
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
      if (var3 != null) {
         this.pubKey = var3;
      }

      if (var4 != null) {
         this.privKey = var4;
      }

   }

   /** @deprecated */
   public CertRequest getCertRequest(int var1) throws CRMFException {
      if (var1 >= 0 && var1 < this.messages[0].size()) {
         return this.messages[0].elementAt(var1) == null ? null : (CertRequest)this.messages[0].elementAt(var1);
      } else {
         throw new CRMFException("Invalid index for CertRequest.");
      }
   }

   /** @deprecated */
   public ProofOfPossession getPOP(int var1) throws CRMFException {
      if (var1 >= 0 && var1 < this.messages[1].size()) {
         return this.messages[1].elementAt(var1) == null ? null : (ProofOfPossession)this.messages[1].elementAt(var1);
      } else {
         throw new CRMFException("Invalid index for ProofOfPossession.");
      }
   }

   /** @deprecated */
   public RegInfo getRegInfo(int var1) throws CRMFException {
      if (var1 >= 0 && var1 < this.messages[2].size()) {
         return this.messages[2].elementAt(var1) == null ? null : (RegInfo)this.messages[2].elementAt(var1);
      } else {
         throw new CRMFException("Invalid index for RegInfo.");
      }
   }

   /** @deprecated */
   public int getCertReqMsgCount() {
      return this.messages[0].size();
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 != null && var0.length > var1) {
         if (var0[var1] == 0 && var0[var1 + 1] == 0) {
            return var1 + 2;
         } else {
            try {
               return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
            } catch (ASN_Exception var3) {
               throw new CRMFException("Unable to determine length of the BER", var3);
            }
         }
      } else {
         throw new CRMFException("Illegal CertReqMessages encoding values.");
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.encodeInit(var1);
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified CertReqMessages array is null.");
      } else {
         try {
            if (this.asn1Template == null || this.special != var3) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode CertReqMessages.", var6);
         }
      }
   }

   private int encodeInit(int var1) throws CRMFException {
      this.special = var1;
      Vector var2 = new Vector();

      try {
         OfContainer var3 = new OfContainer(var1, true, 0, 12288, new EncodedContainer(12288));
         var2.addElement(var3);
         int var4 = this.messages[0].size();

         for(int var5 = 0; var5 < var4; ++var5) {
            int var6 = ((CertRequest)this.messages[0].elementAt(var5)).getDERLen(0);
            byte[] var7 = new byte[var6];
            var6 = ((CertRequest)this.messages[0].elementAt(var5)).getDEREncoding(var7, 0, 0);
            boolean var8 = false;
            int var9 = 0;
            byte[] var10 = null;
            int var11 = -1;
            if (this.messages[1].elementAt(var5) != null) {
               var8 = true;
               ProofOfPossession var12 = (ProofOfPossession)this.messages[1].elementAt(var5);
               var9 = var12.getDERLen(65536);
               var10 = new byte[var9];
               var9 = var12.getDEREncoding(var10, 0, 65536);
               var11 = var12.getPOPType();
            }

            boolean var26 = false;
            int var13 = 0;
            byte[] var14 = null;
            if (this.messages[2].elementAt(var5) != null) {
               var26 = true;
               RegInfo var15 = (RegInfo)this.messages[2].elementAt(var5);
               var13 = var15.getDERLen(0);
               var14 = new byte[var13];
               var13 = var15.getDEREncoding(var14, 0, 0);
            }

            SequenceContainer var27 = new SequenceContainer(0, true, 0);
            EndContainer var16 = new EndContainer();
            EncodedContainer var17 = new EncodedContainer(12288, true, 0, var7, 0, var6);
            EncodedContainer var18;
            if (var11 == 0) {
               var18 = new EncodedContainer(8389888, var8, 0, var10, 0, var9);
            } else if (var11 == 1) {
               var18 = new EncodedContainer(8400897, var8, 0, var10, 0, var9);
            } else if (var11 == 2) {
               var18 = new EncodedContainer(10498050, var8, 0, var10, 0, var9);
            } else if (var11 == 3) {
               var18 = new EncodedContainer(10498051, var8, 0, var10, 0, var9);
            } else {
               var18 = new EncodedContainer(65536, false, 0, (byte[])null, 0, 0);
            }

            EncodedContainer var19 = new EncodedContainer(77824, var26, 0, var14, 0, var13);
            ASN1Container[] var20 = new ASN1Container[]{var27, var17, var18, var19, var16};
            this.asn1Template = new ASN1Template(var20);
            int var21 = this.asn1Template.derEncodeInit();
            byte[] var22 = new byte[var21];
            var21 = this.asn1Template.derEncode(var22, 0);
            EncodedContainer var23 = new EncodedContainer(0, true, 0, var22, 0, var21);
            var3.addContainer(var23);
         }

         ASN1Container[] var25 = new ASN1Container[var2.size()];
         var2.copyInto(var25);
         this.asn1Template = new ASN1Template(var25);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var24) {
         throw new CRMFException(var24);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof CertReqMessages) {
         CertReqMessages var2 = (CertReqMessages)var1;
         int var3 = this.messages.length;
         int var4 = var2.messages.length;
         if (var3 != var4) {
            return false;
         } else {
            for(int var5 = 0; var5 < var3; ++var5) {
               int var6 = this.messages[var5].size();
               int var7 = var2.messages[var5].size();
               if (var6 != var7) {
                  return false;
               }

               for(int var8 = 0; var8 < var6; ++var8) {
                  if (this.messages[var5].elementAt(var8) == null) {
                     if (var2.messages[var5].elementAt(var8) != null) {
                        return false;
                     }
                  } else {
                     if (var2.messages[var5].elementAt(var8) == null) {
                        return false;
                     }

                     if (!this.messages[var5].elementAt(var8).equals(var2.messages[var5].elementAt(var8))) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + Arrays.hashCode(this.messages);
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CertReqMessages var1 = new CertReqMessages();
      var1.setEnvironment(this.theCertJ, this.theCertPathCtx, this.pubKey, this.privKey);

      for(int var2 = 0; var2 < this.messages.length; ++var2) {
         for(int var3 = 0; var3 < this.messages[var2].size(); ++var3) {
            var1.messages[var2].addElement(this.messages[var2].elementAt(var3));
         }
      }

      var1.special = this.special;

      try {
         if (this.asn1Template != null) {
            var1.encodeInit(this.special);
         }

         return var1;
      } catch (CRMFException var4) {
         throw new CloneNotSupportedException(var4.getMessage());
      }
   }
}
