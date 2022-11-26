package com.rsa.certj.pkcs12;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.pkcs7.ContentInfo;
import com.rsa.certj.pkcs7.Data;
import com.rsa.certj.pkcs7.EncryptedData;
import com.rsa.certj.pkcs7.PKCS7Exception;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_IVException;
import com.rsa.jsafe.JSAFE_InputException;
import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_PaddingException;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.io.Serializable;
import java.util.Vector;

final class PFX implements Serializable, Cloneable {
   private int a = 3;
   private SafeObjects b;
   private static final int c = 8;

   PFX(CertJ var1, DatabaseService var2, char[] var3, char[] var4, byte[] var5, int var6, int var7) throws PKCS12Exception {
      try {
         SequenceContainer var8 = new SequenceContainer(var7);
         EndContainer var9 = new EndContainer();
         IntegerContainer var10 = new IntegerContainer(0);
         EncodedContainer var11 = new EncodedContainer(12288);
         EncodedContainer var12 = new EncodedContainer(77824);
         ASN1Container[] var13 = new ASN1Container[]{var8, var10, var11, var12, var9};
         ASN1.berDecode(var5, var6, var13);
         this.a = var10.getValueAsInt();
         if (var4 == null) {
            var4 = new char[var3.length];
            System.arraycopy(var3, 0, var4, 0, var3.length);
         }

         byte[] var14 = this.a(var4, var1, var11.data, var11.dataOffset, var11.dataLen);
         if (var12.dataPresent) {
            MacData.a(var1, var3, var14, var12.data, var12.dataOffset, 0);
         }

         OfContainer var15 = new OfContainer(0, 12288, new EncodedContainer(65280));
         ASN1Container[] var16 = new ASN1Container[]{var15};
         ASN1.berDecode(var14, 0, var16);
         this.b = new SafeObjects();
         int var17 = 0;

         while(var17 < var15.getContainerCount()) {
            ASN1Container var18 = var15.containerAt(var17);
            byte[] var19 = this.a(var4, var1, var18.data, var18.dataOffset, var18.dataLen);
            int var20 = ContentInfo.getMessageType(var18.data, var18.dataOffset, var18.dataLen);
            switch (var20) {
               case 1:
               case 6:
                  new SafeContents(var1, var2, this.b, var4, var19, 0, 0);
                  ++var17;
                  break;
               case 3:
                  throw new PKCS12Exception("enveloped data is not implemented yet");
               default:
                  throw new PKCS12Exception("Illegal contentType found");
            }
         }

         if (var2 != null) {
            Vector var27 = this.b.c();
            Vector var28 = this.b.b;

            for(int var29 = 0; var29 < var27.size(); ++var29) {
               JSAFE_PrivateKey var30 = (JSAFE_PrivateKey)var27.elementAt(var29);
               X501Attributes var21 = (X501Attributes)var28.elementAt(var29);
               if (var21 != null) {
                  X509Certificate var22 = this.b.a(var21);
                  if (var22 != null) {
                     var2.insertPrivateKeyByCertificate(var22, var30);
                  }
               }
            }
         }

      } catch (NoServiceException var23) {
         throw new PKCS12Exception(var23);
      } catch (DatabaseException var24) {
         throw new PKCS12Exception(var24);
      } catch (ASN_Exception var25) {
         throw new PKCS12Exception("Cannot decode the BER of the PFX.");
      } catch (PKCS7Exception var26) {
         throw new PKCS12Exception("PFX.PFX: Illegal contentType found.", var26);
      }
   }

   PFX(Certificate[] var1, CRL[] var2, JSAFE_PrivateKey[] var3, X501Attributes[] var4, X501Attributes[] var5, X501Attributes[] var6, String[] var7) throws InvalidParameterException {
      this.b = new SafeObjects(var1, var2, var3, var4, var5, var6, var7);
   }

   private byte[] a(char[] var1, CertJ var2, byte[] var3, int var4, int var5) throws PKCS12Exception {
      try {
         int var6 = ContentInfo.getMessageType(var3, var4, var5);
         switch (var6) {
            case 1:
               Data var7 = new Data();
               var7.readInit(var3, var4, var5);
               return var7.getData();
            case 3:
               throw new PKCS12Exception("Enveloped Data is not implemented yet");
            case 6:
               return this.a(var2, var1, var3, var4);
            default:
               throw new PKCS12Exception("Illegal contentType found");
         }
      } catch (PKCS7Exception var8) {
         throw new PKCS12Exception(var8);
      }
   }

   private byte[] a(CertJ var1, char[] var2, byte[] var3, int var4) throws PKCS12Exception {
      try {
         SequenceContainer var5 = new SequenceContainer(0);
         EndContainer var6 = new EndContainer();
         OIDContainer var7 = new OIDContainer(16777216);
         EncodedContainer var8 = new EncodedContainer(10616576);
         ASN1Container[] var9 = new ASN1Container[]{var5, var7, var8, var6};
         ASN1.berDecode(var3, var4, var9);
         SequenceContainer var10 = new SequenceContainer(10551296);
         EndContainer var11 = new EndContainer();
         IntegerContainer var12 = new IntegerContainer(0);
         EncodedContainer var13 = new EncodedContainer(12288);
         ASN1Container[] var14 = new ASN1Container[]{var10, var12, var13, var11};
         ASN1.berDecode(var8.data, var8.dataOffset, var14);
         SequenceContainer var15 = new SequenceContainer(0);
         EndContainer var16 = new EndContainer();
         OIDContainer var17 = new OIDContainer(16777216);
         EncodedContainer var18 = new EncodedContainer(12288);
         OctetStringContainer var19 = new OctetStringContainer(8454144);
         ASN1Container[] var20 = new ASN1Container[]{var15, var17, var18, var19, var16};
         ASN1.berDecode(var13.data, var13.dataOffset, var20);
         SequenceContainer var21 = new SequenceContainer(0);
         EndContainer var22 = new EndContainer();
         OIDContainer var23 = new OIDContainer(0);
         EncodedContainer var24 = new EncodedContainer(130816);
         ASN1Container[] var25 = new ASN1Container[]{var21, var23, var24, var22};
         ASN1.berDecode(var18.data, var18.dataOffset, var25);
         SequenceContainer var26 = new SequenceContainer(0);
         EndContainer var27 = new EndContainer();
         OctetStringContainer var28 = new OctetStringContainer(0);
         IntegerContainer var29 = new IntegerContainer(0);
         ASN1Container[] var30 = new ASN1Container[]{var26, var28, var29, var27};
         ASN1.berDecode(var24.data, var24.dataOffset, var30);
         JSAFE_SymmetricCipher var31 = h.c(var18.data, var18.dataOffset, var1.getDevice(), var1);
         var31.setSalt(var28.data, var28.dataOffset, var28.dataLen);
         JSAFE_SecretKey var32 = var31.getBlankKey();
         var32.setPassword(var2, 0, var2.length);
         var31.decryptInit(var32);
         byte[] var33 = new byte[var19.dataLen];
         int var34 = var31.decryptUpdate(var19.data, var19.dataOffset, var19.dataLen, var33, 0);
         var31.decryptFinal(var33, var34);
         return var33;
      } catch (JSAFE_InputException var35) {
         throw new PKCS12Exception(var35);
      } catch (JSAFE_PaddingException var36) {
         throw new PKCS12Exception(var36);
      } catch (JSAFE_InvalidParameterException var37) {
         throw new PKCS12Exception(var37);
      } catch (JSAFE_UnimplementedException var38) {
         throw new PKCS12Exception(var38);
      } catch (JSAFE_InvalidUseException var39) {
         throw new PKCS12Exception(var39);
      } catch (JSAFE_InvalidKeyException var40) {
         throw new PKCS12Exception(var40);
      } catch (JSAFE_IVException var41) {
         throw new PKCS12Exception(var41);
      } catch (ASN_Exception var42) {
         throw new PKCS12Exception(var42);
      }
   }

   void a(int var1) {
      this.a = var1;
   }

   int a() {
      return this.a;
   }

   SafeObjects b() {
      return this.b;
   }

   byte[] a(CertJ var1, char[] var2, char[] var3, String var4, String var5, int var6, int var7) throws PKCS12Exception {
      if (var3 == null) {
         var3 = new char[var2.length];
         System.arraycopy(var2, 0, var3, 0, var2.length);
      }

      SafeContents var8 = new SafeContents(this.b);
      byte[] var9 = var8.a(var1, var4, var3, var7);
      Data var11 = new Data();

      byte[] var12;
      byte[] var13;
      SequenceContainer var32;
      EndContainer var34;
      try {
         var11.setContent(var9, 0, var9.length);
         Object var10 = var11;
         if (var7 == 1) {
            try {
               JSAFE_SecureRandom var15 = var1.getRandomObject();
               byte[] var16 = new byte[8];
               var15.generateRandomBytes(var16, 0, 8);
               EncryptedData var17 = new EncryptedData(var1, (CertPathCtx)null);
               var17.setVersionNumber(0);
               var17.setSalt(var16, 0, var16.length);
               var17.setEncryptionAlgorithm(var4);
               var17.setPassword(var3, 0, var3.length);
               var17.setContentInfo(var11);
               var10 = var17;
               h.c(var4, var1.getDevice(), var1);
            } catch (JSAFE_Exception var27) {
               throw new PKCS12Exception("PFX.derEncode: ", var27);
            } catch (CertJException var28) {
               throw new PKCS12Exception("PFX.derEncode: No random provider error.", var28);
            }
         }

         int var30 = ((ContentInfo)var10).getContentInfoDERLen();

         try {
            var32 = new SequenceContainer(0, true, 0);
            var34 = new EndContainer();
            EncodedContainer var18 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var30);
            ASN1Container[] var19 = new ASN1Container[]{var32, var18, var34};
            ASN1Template var20 = new ASN1Template(var19);
            var13 = new byte[var20.derEncodeInit()];
            int var21 = var20.derEncode(var13, 0);
            ((ContentInfo)var10).writeMessage(var13, var21);
         } catch (ASN_Exception var26) {
            throw new PKCS12Exception("PFX.derEncode: Encoding of authenticated failed.", var26);
         }

         Data var33 = new Data();
         var33.setContent(var13, 0, var13.length);
         int var14 = var33.getContentInfoDERLen();
         var12 = new byte[var14];
         var33.writeMessage(var12, 0);
      } catch (PKCS7Exception var29) {
         throw new PKCS12Exception("PFX.derEncode: ContentInfo encoding failed.", var29);
      }

      byte[] var31 = MacData.a(var1, var13, 0, var13.length, var5, var6, var2);

      try {
         var32 = new SequenceContainer(0, true, 0);
         var34 = new EndContainer();
         IntegerContainer var35 = new IntegerContainer(0, true, 0, this.a);
         EncodedContainer var36 = new EncodedContainer(12288, true, 0, var12, 0, var12.length);
         EncodedContainer var37 = new EncodedContainer(77824, true, 0, var31, 0, var31.length);
         ASN1Container[] var38 = new ASN1Container[]{var32, var35, var36, var37, var34};
         ASN1Template var22 = new ASN1Template(var38);
         int var24 = var22.derEncodeInit();
         byte[] var23 = new byte[var24];
         var22.derEncode(var23, 0);
         return var23;
      } catch (ASN_Exception var25) {
         throw new PKCS12Exception("PFX.derEncode: Encoding PFX failed.", var25);
      }
   }
}
