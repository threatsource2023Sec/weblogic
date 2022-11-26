package com.rsa.certj.pkcs12;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_IVException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.io.Serializable;
import java.util.Vector;

final class SafeContents implements Serializable, Cloneable {
   private SafeObjects a;
   private DatabaseService b;
   private static final int c = 1;
   private static final int d = 2;
   private static final int e = 3;
   private static final int f = 4;
   private static final int g = 5;
   private static final int h = 6;
   private static final byte[] i = new byte[]{42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 1};
   private static final byte[] j = new byte[]{42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 2};
   private static final byte[] k = new byte[]{42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 3};
   private static final byte[] l = new byte[]{42, -122, 72, -122, -9, 13, 1, 12, 10, 1, 4};
   private static final byte[] m = new byte[]{42, -122, 72, -122, -9, 13, 1, 9, 22, 1};
   private static final byte[] n = new byte[]{42, -122, 72, -122, -9, 13, 1, 9, 23, 1};
   private static final byte[] o = new byte[]{49, 0};
   private static final int p = 8;

   SafeContents() {
   }

   SafeContents(CertJ var1, DatabaseService var2, SafeObjects var3, char[] var4, byte[] var5, int var6, int var7) throws PKCS12Exception {
      this.a = var3;
      this.b = var2;
      this.a(var1, var4, var5, var7);
   }

   SafeContents(SafeObjects var1) throws PKCS12Exception {
      this.a = var1;
   }

   byte[] a(CertJ var1, String var2, char[] var3, int var4) throws PKCS12Exception {
      ASN1Template[] var5 = this.b(var1, var2, var3, var4);
      if (var5 != null && var5.length != 0) {
         int var6 = 0;

         try {
            for(int var7 = 0; var7 < var5.length; ++var7) {
               var6 += var5[var7].derEncodeInit();
            }

            byte var14 = 48;
            int var8 = ASN1Lengths.getTagLen(var14);
            int var9 = ASN1Lengths.getLengthLen(var6) + var8;
            byte[] var10 = new byte[var9 + var6];
            ASN1Lengths.writeTag(var10, 0, var14);
            ASN1Lengths.writeLength(var10, var8, var6);
            int var11 = var9;

            for(int var12 = 0; var12 < var5.length; ++var12) {
               var11 += var5[var12].derEncode(var10, var11);
            }

            return var10;
         } catch (ASN_Exception var13) {
            throw new PKCS12Exception("SafeContents.derEncode: Encoding a bag failed.", var13);
         }
      } else {
         throw new PKCS12Exception("SafeContents.derEncode: No bag to be exported.");
      }
   }

   private void a(CertJ var1, char[] var2, byte[] var3, int var4) throws PKCS12Exception {
      try {
         OfContainer var5 = new OfContainer(var4, 12288, new EncodedContainer(12288));
         ASN1Container[] var6 = new ASN1Container[]{var5};
         ASN1.berDecode(var3, 0, var6);

         for(int var7 = 0; var7 < var5.getContainerCount(); ++var7) {
            this.b(var1, var2, var5.containerAt(var7).data, var5.containerAt(var7).dataOffset);
         }

      } catch (ASN_Exception var8) {
         throw new PKCS12Exception("Cannot decode the BER of the SafeContents.");
      }
   }

   private void b(CertJ var1, char[] var2, byte[] var3, int var4) throws PKCS12Exception {
      try {
         SequenceContainer var5 = new SequenceContainer(0);
         EndContainer var6 = new EndContainer();
         OIDContainer var7 = new OIDContainer(16777216);
         EncodedContainer var8 = new EncodedContainer(10616576);
         EncodedContainer var9 = new EncodedContainer(78080, true, 0, (byte[])null, 0, 0);
         ASN1Container[] var10 = new ASN1Container[]{var5, var7, var8, var9, var6};
         ASN1.berDecode(var3, var4, var10);
         X501Attributes var11 = null;
         int var12;
         if (var9.dataPresent && var9.dataLen > 2) {
            var11 = new X501Attributes(var9.data, var9.dataOffset, 0);

            for(var12 = 0; var12 < var11.getAttributeCount(); ++var12) {
               var11.getAttributeByIndex(var12);
            }
         }

         var12 = var7.data[var7.dataOffset + var7.dataLen - 1] & -1;
         int var13 = 1;
         var13 += ASN1Lengths.determineLengthLen(var8.data, var8.dataOffset + 1);
         int var14 = var8.dataOffset + var13;
         switch (var12) {
            case 1:
               this.a.b.addElement(var11);
               this.b(var1, var8.data, var14);
               break;
            case 2:
               this.a.b.addElement(var11);
               this.a(var2, var1, var8.data, var14);
               break;
            case 3:
               this.a.a.addElement(var11);
               this.a(var8.data, var14);
               break;
            case 4:
               this.a.c.addElement(var11);
               this.a(var1, var8.data, var14);
               break;
            case 5:
               throw new PKCS12Exception("Secret Bag is not implemented yet");
            case 6:
               this.a(var1, var2, var8.data, 10551040);
               break;
            default:
               throw new PKCS12Exception("Illegal BagType found");
         }

      } catch (AttributeException var15) {
         throw new PKCS12Exception(var15);
      } catch (ASN_Exception var16) {
         throw new PKCS12Exception("Cannot decode the BER of the SafeContents.");
      }
   }

   private void a(byte[] var1, int var2) throws PKCS12Exception {
      try {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         OIDContainer var5 = new OIDContainer(16777216);
         EncodedContainer var6 = new EncodedContainer(10616576);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};
         ASN1.berDecode(var1, var2, var7);
         OctetStringContainer var8 = new OctetStringContainer(0);
         ASN1Container[] var9 = new ASN1Container[]{var8};
         int var10 = 1;
         var10 += ASN1Lengths.determineLengthLen(var6.data, var6.dataOffset + 1);
         ASN1.berDecode(var6.data, var6.dataOffset + var10, var9);
         X509Certificate var11 = new X509Certificate(var8.data, var8.dataOffset, 0);
         this.a.a().addElement(var11);
         if (this.b != null) {
            this.b.insertCertificate(var11);
         }

      } catch (NoServiceException var12) {
         throw new PKCS12Exception(var12);
      } catch (DatabaseException var13) {
         throw new PKCS12Exception(var13);
      } catch (CertificateException var14) {
         throw new PKCS12Exception(var14);
      } catch (ASN_Exception var15) {
         throw new PKCS12Exception("Cannot decode the BER of the CertBag.");
      }
   }

   private void a(CertJ var1, byte[] var2, int var3) throws PKCS12Exception {
      try {
         SequenceContainer var4 = new SequenceContainer(0);
         EndContainer var5 = new EndContainer();
         OIDContainer var6 = new OIDContainer(16777216);
         EncodedContainer var7 = new EncodedContainer(10616576);
         ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
         ASN1.berDecode(var2, var3, var8);
         OctetStringContainer var9 = new OctetStringContainer(0, true, 0, (byte[])null, 0, 0);
         ASN1Container[] var10 = new ASN1Container[]{var9};
         int var11 = 1;
         var11 += ASN1Lengths.determineLengthLen(var7.data, var7.dataOffset + 1);
         ASN1.berDecode(var7.data, var7.dataOffset + var11, var10);
         X509CRL var12 = new X509CRL(var9.data, var9.dataOffset, 0);
         this.a.b().addElement(var12);
         DatabaseService var13 = (DatabaseService)var1.bindServices(1);
         if (var13 != null) {
            var13.insertCRL(var12);
         }

      } catch (InvalidParameterException var14) {
         throw new PKCS12Exception(var14);
      } catch (ProviderManagementException var15) {
         throw new PKCS12Exception(var15);
      } catch (NoServiceException var16) {
         throw new PKCS12Exception(var16);
      } catch (DatabaseException var17) {
         throw new PKCS12Exception(var17);
      } catch (CertificateException var18) {
         throw new PKCS12Exception(var18);
      } catch (ASN_Exception var19) {
         throw new PKCS12Exception("Cannot decode the BER of the CertBag.");
      }
   }

   private void a(char[] var1, CertJ var2, byte[] var3, int var4) throws PKCS12Exception {
      try {
         JSAFE_SymmetricCipher var5 = com.rsa.certj.x.h.c(var3, var4, var2.getDevice(), var2);
         JSAFE_SecretKey var6 = var5.getBlankKey();
         var6.setPassword(var1, 0, var1.length);
         byte[] var7 = a(var3, var4, var5, var6);
         JSAFE_PrivateKey var8 = JSAFE_PrivateKey.getInstance(var7, 0, "Java");
         this.a.c().addElement(var8);
      } catch (JSAFE_InvalidParameterException var9) {
         throw new PKCS12Exception(var9);
      } catch (JSAFE_UnimplementedException var10) {
         throw new PKCS12Exception(var10);
      } catch (JSAFE_IVException var11) {
         throw new PKCS12Exception(var11);
      }
   }

   private static byte[] a(byte[] var0, int var1, JSAFE_SymmetricCipher var2, JSAFE_SecretKey var3) throws PKCS12Exception {
      int[] var4 = b(var0, var1);
      int var5 = var4[0];
      int var6 = var4[1];

      try {
         var2.decryptInit(var3);
         byte[] var7 = var2.decryptUpdate(var0, var5, var6);
         byte[] var8 = var2.decryptFinal();
         byte[] var9 = new byte[var7.length + var8.length];
         System.arraycopy(var7, 0, var9, 0, var7.length);
         System.arraycopy(var8, 0, var9, var7.length, var8.length);
         return var9;
      } catch (JSAFE_Exception var10) {
         throw new PKCS12Exception(var10);
      }
   }

   private static int[] b(byte[] var0, int var1) throws PKCS12Exception {
      SequenceContainer var2 = new SequenceContainer(0);
      EndContainer var3 = new EndContainer();
      EncodedContainer var4 = new EncodedContainer(12288);
      OctetStringContainer var5 = new OctetStringContainer(0);
      ASN1Container[] var6 = new ASN1Container[]{var2, var4, var5, var3};

      try {
         ASN1.berDecode(var0, var1, var6);
      } catch (ASN_Exception var8) {
         throw new PKCS12Exception("Cannot build the PKCS #8 encrypted key. (" + var8.getMessage() + ")");
      }

      return new int[]{var5.dataOffset, var5.dataLen};
   }

   private void b(CertJ var1, byte[] var2, int var3) throws PKCS12Exception {
      try {
         JSAFE_PrivateKey var4 = com.rsa.certj.x.h.d(var2, var3, var1.getDevice(), var1);
         this.a.c().addElement(var4);
      } catch (JSAFE_Exception var5) {
         throw new PKCS12Exception(var5);
      }
   }

   private ASN1Template[] b(CertJ var1, String var2, char[] var3, int var4) throws PKCS12Exception {
      Vector var5 = this.a.a();
      Vector var6 = this.a.b();
      Vector var7 = this.a.c();
      Vector var8 = this.a.g();
      Vector var9 = this.a.a;
      Vector var10 = this.a.c;
      Vector var11 = this.a.b;
      int var12 = var5.size();
      int var13 = var6.size();
      int var14 = var7.size();
      int var15 = var9.size();
      int var16 = var10.size();
      int var17 = var11.size();
      int var18 = var8.size();
      ASN1Template[] var19 = new ASN1Template[var12 + var13 + var14];

      int var20;
      X501Attributes var21;
      for(var20 = 0; var20 < var12; ++var20) {
         var21 = null;
         if (var15 > var20) {
            var21 = (X501Attributes)var9.elementAt(var20);
         }

         var19[var20] = this.a(k, this.a((Certificate)var5.elementAt(var20)), var21);
      }

      for(var20 = 0; var20 < var13; ++var20) {
         var21 = null;
         if (var16 > var20) {
            var21 = (X501Attributes)var10.elementAt(var20);
         }

         var19[var12 + var20] = this.a(l, this.a((CRL)var6.elementAt(var20)), var21);
      }

      byte[] var26 = var4 == 2 ? j : i;

      for(int var27 = 0; var27 < var14; ++var27) {
         X501Attributes var22 = null;
         if (var17 > var27) {
            var22 = (X501Attributes)var11.elementAt(var27);
         }

         JSAFE_PrivateKey var23 = (JSAFE_PrivateKey)var7.elementAt(var27);
         String var24 = null;
         if (var18 > var27) {
            var24 = (String)var8.elementAt(var27);
         }

         byte[] var25 = var4 == 2 ? this.a(var1, var23, var2, var3, var24) : this.a(var23, var24);
         var19[var12 + var13 + var27] = this.a(var26, var25, var22);
      }

      return var19;
   }

   private ASN1Template a(byte[] var1, byte[] var2, X501Attributes var3) throws PKCS12Exception {
      byte[] var4 = this.a(var3);

      try {
         SequenceContainer var5 = new SequenceContainer(0, true, 0);
         EndContainer var6 = new EndContainer();
         OIDContainer var7 = new OIDContainer(16777216, true, 0, var1, 0, var1.length);
         EncodedContainer var8 = new EncodedContainer(0, true, 0, var2, 0, var2.length);
         EncodedContainer var9 = new EncodedContainer(0, true, 0, var4, 0, var4.length);
         ASN1Container[] var10 = new ASN1Container[]{var5, var7, var8, var9, var6};
         return new ASN1Template(var10);
      } catch (ASN_Exception var11) {
         throw new PKCS12Exception("SafeContents.createSafeBagTemplate: ", var11);
      }
   }

   private byte[] a(Certificate var1) throws PKCS12Exception {
      if (!(var1 instanceof X509Certificate)) {
         throw new PKCS12Exception("SafeContents.encodeCertBag: Unknown certificate type.");
      } else {
         X509Certificate var2 = (X509Certificate)var1;
         int var3 = var2.getDERLen(0);

         try {
            SequenceContainer var4 = new SequenceContainer(10485760, true, 0);
            EndContainer var5 = new EndContainer();
            OIDContainer var6 = new OIDContainer(16777216, true, 0, m, 0, m.length);
            OctetStringContainer var7 = new OctetStringContainer(10551296, true, 0, (byte[])null, 0, var3);
            ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
            ASN1Template var9 = new ASN1Template(var8);
            int var10 = var9.derEncodeInit();
            byte[] var11 = new byte[var10];
            int var12 = var9.derEncode(var11, 0);
            var2.getDEREncoding(var11, var12, 0);
            return var11;
         } catch (ASN_Exception var13) {
            throw new PKCS12Exception("SafeContents.encodeCertBag: DER encoding of CertBag failed.", var13);
         } catch (CertificateException var14) {
            throw new PKCS12Exception("SafeContents.encodeCertBag: DER encoding of X509Certificate failed.", var14);
         }
      }
   }

   private byte[] a(CRL var1) throws PKCS12Exception {
      if (!(var1 instanceof X509CRL)) {
         throw new PKCS12Exception("SafeContents.encodeCRLBag: Unknown CRL type.");
      } else {
         X509CRL var2 = (X509CRL)var1;
         int var3 = var2.getDERLen(0);

         try {
            SequenceContainer var4 = new SequenceContainer(10485760, true, 0);
            EndContainer var5 = new EndContainer();
            OIDContainer var6 = new OIDContainer(16777216, true, 0, n, 0, n.length);
            OctetStringContainer var7 = new OctetStringContainer(10551296, true, 0, (byte[])null, 0, var3);
            ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
            ASN1Template var9 = new ASN1Template(var8);
            int var10 = var9.derEncodeInit();
            byte[] var11 = new byte[var10];
            int var12 = var9.derEncode(var11, 0);
            var2.getDEREncoding(var11, var12, 0);
            return var11;
         } catch (ASN_Exception var13) {
            throw new PKCS12Exception("SafeContents.encodeCRLBag: DER encoding of CRLBag failed.", var13);
         } catch (CertificateException var14) {
            throw new PKCS12Exception("SafeContents.encodeCRLBag: DER encoding of X509CRL failed.", var14);
         }
      }
   }

   private byte[] a(JSAFE_PrivateKey var1, String var2) throws PKCS12Exception {
      byte[] var3 = null;
      if (var2 == null) {
         String[] var4 = var1.getSupportedGetFormats();
         String var5 = null;

         for(int var6 = 0; var6 < var4.length; ++var6) {
            if (var4[var6].endsWith("X957BER")) {
               var5 = var4[var6];
               break;
            }

            if (var5 == null && var4[var6].endsWith("BER")) {
               var5 = var4[var6];
            }
         }

         if (var5 != null) {
            try {
               var3 = var1.getKeyData(var5)[0];
            } catch (JSAFE_Exception var8) {
               throw new PKCS12Exception("SafeContents.encodeKeyBag: getKeyData failed.", var8);
            }
         }
      } else {
         if (!var2.endsWith("BER")) {
            throw new PKCS12Exception("SafeContents.encodeKeyBag: getKeyData failed(Wrong key format).");
         }

         try {
            var3 = var1.getKeyData(var2)[0];
         } catch (JSAFE_Exception var7) {
            throw new PKCS12Exception("SafeContents.encodeKeyBag: getKeyData failed.", var7);
         }
      }

      if (var3 == null) {
         throw new PKCS12Exception("SafeContents.encodeKeyBag: No BER format found for private key.");
      } else {
         return this.a(var3);
      }
   }

   private byte[] a(CertJ var1, JSAFE_PrivateKey var2, String var3, char[] var4, String var5) throws PKCS12Exception {
      JSAFE_SymmetricCipher var6 = null;

      byte[] var10;
      try {
         var6 = com.rsa.certj.x.h.c(var3, var1.getDevice(), var1);
         JSAFE_SecureRandom var7 = var1.getRandomObject();
         byte[] var8 = new byte[8];
         var7.generateRandomBytes(var8, 0, 8);
         var6.setSalt(var8, 0, 8);
         JSAFE_SecretKey var9 = var6.getBlankKey();
         var9.setPassword(var4, 0, var4.length);
         var6.encryptInit(var9);
         var10 = this.a(var6.wrapPrivateKey(var2, true, var5));
      } catch (JSAFE_Exception var16) {
         throw new PKCS12Exception("SafeContents.encodeShroudedKeyBag: Key wrapping failed.", var16);
      } catch (NoServiceException var17) {
         throw new PKCS12Exception("SafeContents.encodeShroudedKeyBag: Random provider not found.", var17);
      } catch (RandomException var18) {
         throw new PKCS12Exception("SafeContents.encodeShroudedKeyBag: Random provider failed.", var18);
      } finally {
         if (var6 != null) {
            var6.clearSensitiveData();
         }

      }

      return var10;
   }

   private byte[] a(byte[] var1) throws PKCS12Exception {
      int var2 = 10485760;
      byte var3 = 1;
      int var4 = var1.length;

      try {
         int var5 = ASN1Lengths.getLengthLen(var4) + var3;
         byte[] var6 = new byte[var5 + var4];
         ASN1Lengths.writeTag(var6, 0, var2);
         ASN1Lengths.writeLength(var6, var3, var4);
         System.arraycopy(var1, 0, var6, var5, var4);
         return var6;
      } catch (ASN_Exception var7) {
         throw new PKCS12Exception("SafeContents.addContextExplicitHeader: ", var7);
      }
   }

   private byte[] a(X501Attributes var1) throws PKCS12Exception {
      if (var1 != null && var1.getAttributeCount() != 0) {
         try {
            byte[] var2 = new byte[var1.getDERLen(0)];
            var1.getDEREncoding(var2, 0, 0);
            return var2;
         } catch (AttributeException var3) {
            throw new PKCS12Exception("SafeContents.encodeAttributes: DER encoding of X509Attributes failed.", var3);
         }
      } else {
         return o;
      }
   }
}
