package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTCTimeContainer;
import com.rsa.certj.CertJUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

/** @deprecated */
public class RevokedCertificates implements Serializable, Cloneable {
   private final Vector[] certData = this.createVectorArray(3);
   /** @deprecated */
   protected static int special;
   private boolean timeFlag;
   private ASN1Template asn1Template;

   /** @deprecated */
   public RevokedCertificates() {
   }

   /** @deprecated */
   public RevokedCertificates(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
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
               EncodedContainer var11 = new EncodedContainer(77824);
               IntegerContainer var12 = new IntegerContainer(0);
               EncodedContainer var13 = new EncodedContainer(65280);
               ASN1Container[] var14 = new ASN1Container[]{var9, var12, var13, var11, var10};
               ASN1.berDecode(var8.data, var8.dataOffset, var14);
               byte[] var15 = new byte[var12.dataLen];
               System.arraycopy(var12.data, var12.dataOffset, var15, 0, var12.dataLen);
               this.certData[0].addElement(var15);
               if (var11.dataPresent) {
                  this.certData[2].addElement(new X509V3Extensions(var11.data, var11.dataOffset, 0, 3));
               } else {
                  this.certData[2].addElement((Object)null);
               }

               ChoiceContainer var16 = new ChoiceContainer(0);
               GenTimeContainer var17 = new GenTimeContainer(65536);
               UTCTimeContainer var18 = new UTCTimeContainer(65536);
               ASN1Container[] var19 = new ASN1Container[]{var16, var17, var18, var10};
               ASN1.berDecode(var13.data, var13.dataOffset, var19);
               if (var17.dataPresent) {
                  this.certData[1].addElement(var17.theTime);
               } else if (var18.dataPresent) {
                  this.certData[1].addElement(var18.theTime);
               }
            }

         } catch (Exception var20) {
            throw new CertificateException("Cannot decode the BER of Revoked Certificates.");
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
   public void addRevokedCertificate(byte[] var1, Date var2, X509V3Extensions var3) throws CertificateException {
      if (var1 != null && var2 != null) {
         this.certData[0].addElement(var1);
         this.certData[1].addElement(var2);
         if (var3 != null && var3.getExtensionsType() != 3) {
            throw new CertificateException("Wrong extensions type: should be CRLEntry extensions.");
         } else {
            this.certData[2].addElement(var3);
         }
      } else {
         throw new CertificateException("Values cannot be NULL.");
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber(int var1) throws CertificateException {
      if (var1 >= this.getCertificateCount()) {
         throw new CertificateException("Invalid index");
      } else {
         return (byte[])((byte[])this.certData[0].elementAt(var1));
      }
   }

   /** @deprecated */
   public Date getRevocationDate(int var1) throws CertificateException {
      if (var1 >= this.getCertificateCount()) {
         throw new CertificateException("Invalid index");
      } else {
         return (Date)this.certData[1].elementAt(var1);
      }
   }

   /** @deprecated */
   public X509V3Extensions getExtensions(int var1) throws CertificateException {
      if (var1 >= this.getCertificateCount()) {
         throw new CertificateException("Invalid index");
      } else {
         return (X509V3Extensions)this.certData[2].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getCertificateCount() {
      return this.certData[0].size();
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
      special = var1;
      this.getASN1Template(var1);

      try {
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var3) {
         throw new CertificateException(var3);
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3, boolean var4) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified array is null.");
      } else {
         try {
            this.timeFlag = var4;
            if (this.asn1Template == null || var3 != special) {
               this.getDERLen(var3);
            }

            int var5 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var5;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CertificateException("Unable to encode Revoked Certificates.");
         }
      }
   }

   private void getASN1Template(int var1) throws CertificateException {
      if (this.asn1Template == null || var1 != special) {
         Vector var2 = new Vector();

         try {
            OfContainer var3 = new OfContainer(var1, 12288, new EncodedContainer(12288));
            var2.addElement(var3);

            for(int var4 = 0; var4 < this.certData[0].size(); ++var4) {
               EncodedContainer var5 = this.encodeCert(var4);
               var3.addContainer(var5);
            }
         } catch (ASN_Exception var6) {
            throw new CertificateException("Can't encode RevokedCerts", var6);
         }

         ASN1Container[] var7 = new ASN1Container[var2.size()];
         var2.copyInto(var7);
         this.asn1Template = new ASN1Template(var7);
      }
   }

   private EncodedContainer encodeCert(int var1) throws CertificateException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      EncodedContainer var5 = null;

      try {
         if (this.certData[2].elementAt(var1) != null) {
            int var6 = ((X509V3Extensions)this.certData[2].elementAt(var1)).getDERLen(0);
            byte[] var7 = new byte[var6];
            byte var8 = 0;
            int var9 = ((X509V3Extensions)this.certData[2].elementAt(var1)).getDEREncoding(var7, var8, 0);
            var5 = new EncodedContainer(77824, true, 0, var7, var8, var9);
         }

         byte[] var13 = (byte[])((byte[])this.certData[0].elementAt(var1));
         IntegerContainer var14 = new IntegerContainer(0, true, 0, var13, 0, var13.length, true);
         Date var15 = (Date)this.certData[1].elementAt(var1);
         ASN1Container[] var11;
         ASN1Template var16;
         if (this.timeFlag) {
            GenTimeContainer var10 = new GenTimeContainer(0, true, 0, var15);
            if (var5 != null) {
               var11 = new ASN1Container[]{var3, var14, var10, var5, var4};
               var16 = new ASN1Template(var11);
            } else {
               var11 = new ASN1Container[]{var3, var14, var10, var4};
               var16 = new ASN1Template(var11);
            }
         } else {
            UTCTimeContainer var17 = new UTCTimeContainer(0, true, 0, var15);
            if (var5 != null) {
               var11 = new ASN1Container[]{var3, var14, var17, var5, var4};
               var16 = new ASN1Template(var11);
            } else {
               var11 = new ASN1Container[]{var3, var14, var17, var4};
               var16 = new ASN1Template(var11);
            }
         }

         var16.derEncodeInit();
         int var18 = var16.derEncodeInit();
         byte[] var19 = new byte[var18];
         var18 = var16.derEncode(var19, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var19, 0, var18);
         return var2;
      } catch (ASN_Exception var12) {
         throw new CertificateException(" Can't encode RevokedCertificates");
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof RevokedCertificates) {
         RevokedCertificates var2 = (RevokedCertificates)var1;
         int var3 = this.certData[0].size();
         if (var3 != var2.certData[0].size()) {
            return false;
         } else {
            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
               byte[] var5 = (byte[])((byte[])this.certData[0].elementAt(var4));
               byte[] var6 = (byte[])((byte[])var2.certData[0].elementAt(var4));
               if (!CertJUtils.byteArraysEqual(var5, var6)) {
                  return false;
               }
            }

            var3 = this.certData[1].size();
            if (var3 != var2.certData[1].size()) {
               return false;
            } else {
               for(var4 = 0; var4 < var3; ++var4) {
                  Date var7 = (Date)this.certData[1].elementAt(var4);
                  Date var9 = (Date)var2.certData[1].elementAt(var4);
                  if (!var7.equals(var9)) {
                     return false;
                  }
               }

               var3 = this.certData[2].size();
               if (var3 != var2.certData[2].size()) {
                  return false;
               } else {
                  for(var4 = 0; var4 < var3; ++var4) {
                     X509V3Extensions var8 = (X509V3Extensions)this.certData[2].elementAt(var4);
                     X509V3Extensions var10 = (X509V3Extensions)var2.certData[2].elementAt(var4);
                     if (!var8.equals(var10)) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;

      int var2;
      for(var2 = 0; var2 < this.certData[0].size(); ++var2) {
         byte[] var3 = (byte[])((byte[])this.certData[0].elementAt(var2));
         var1 ^= Arrays.hashCode(var3);
      }

      for(var2 = 0; var2 < this.certData[1].size(); ++var2) {
         Date var4 = (Date)this.certData[1].elementAt(var2);
         var1 ^= var4.hashCode();
      }

      for(var2 = 0; var2 < this.certData[2].size(); ++var2) {
         X509V3Extensions var5 = (X509V3Extensions)this.certData[2].elementAt(var2);
         var1 ^= var5.hashCode();
      }

      return var1;
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      RevokedCertificates var1 = new RevokedCertificates();

      for(int var2 = 0; var2 < this.certData.length; ++var2) {
         for(int var3 = 0; var3 < this.certData[var2].size(); ++var3) {
            var1.certData[var2].addElement(this.certData[var2].elementAt(var3));
         }
      }

      try {
         if (this.asn1Template != null) {
            var1.getASN1Template(special);
         }

         return var1;
      } catch (CertificateException var4) {
         throw new CloneNotSupportedException("Cannot get ASN1 Template");
      }
   }
}
