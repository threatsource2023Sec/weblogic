package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.SubjectAltName;
import com.rsa.certj.cert.extensions.SubjectKeyID;
import com.rsa.jsafe.JSAFE_SecureRandom;
import java.util.Date;

class PKIHeader {
   private static final int m = 16;
   private static final int n = 16;
   /** @deprecated */
   protected int a = -1;
   /** @deprecated */
   protected GeneralName b;
   /** @deprecated */
   protected GeneralName c;
   /** @deprecated */
   protected Date d;
   /** @deprecated */
   protected a e;
   /** @deprecated */
   protected a f;
   /** @deprecated */
   protected a g;
   /** @deprecated */
   protected a h;
   /** @deprecated */
   protected a i;
   /** @deprecated */
   protected a j;
   /** @deprecated */
   protected String[] k;
   /** @deprecated */
   protected TypeAndValue[] l;

   /** @deprecated */
   protected PKIHeader(byte[] var1, int var2) throws CMPException {
      IntegerContainer var3;
      EncodedContainer var4;
      EncodedContainer var5;
      GenTimeContainer var6;
      EncodedContainer var7;
      OctetStringContainer var8;
      OctetStringContainer var9;
      OctetStringContainer var10;
      OctetStringContainer var11;
      OctetStringContainer var12;
      OfContainer var13;
      OfContainer var14;
      try {
         EndContainer var15 = new EndContainer();
         SequenceContainer var16 = new SequenceContainer(0);
         var3 = new IntegerContainer(0);
         var4 = new EncodedContainer(65280);
         var5 = new EncodedContainer(65280);
         var6 = new GenTimeContainer(10551296);
         var7 = new EncodedContainer(10563585);
         var8 = new OctetStringContainer(10551298);
         var9 = new OctetStringContainer(10551299);
         var10 = new OctetStringContainer(10551300);
         var11 = new OctetStringContainer(10551301);
         var12 = new OctetStringContainer(10551302);
         var13 = new OfContainer(10551303, 12288, new EncodedContainer(3072));
         var14 = new OfContainer(10551304, 12288, new EncodedContainer(12288));
         ASN1Container[] var17 = new ASN1Container[]{var16, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15};
         ASN1.berDecode(var1, var2, var17);
      } catch (ASN_Exception var22) {
         throw new CMPException("PKIHeader.PKIHeader: decoding PKIHeader failed.", var22);
      }

      try {
         this.a = var3.getValueAsInt();
      } catch (ASN_Exception var21) {
         throw new CMPException("PKIHeader.PKIHeader: unable to get PKIHeader.pvo as int.", var21);
      }

      this.b = this.a((ASN1Container)var4, (String)"sender");
      this.c = this.a((ASN1Container)var5, (String)"recipient");
      if (var6.dataPresent) {
         this.d = var6.theTime;
      }

      this.e = this.a((ASN1Container)var7);
      this.f = this.a((ASN1Container)var8);
      this.g = this.a((ASN1Container)var9);
      this.h = this.a((ASN1Container)var10);
      this.i = this.a((ASN1Container)var11);
      this.j = this.a((ASN1Container)var12);
      int var24;
      int var25;
      ASN1Container var26;
      if (var13.dataPresent) {
         var24 = var13.getContainerCount();
         this.k = new String[var24];

         for(var25 = 0; var25 < var24; ++var25) {
            try {
               var26 = var13.containerAt(var25);
               UTF8StringContainer var18 = new UTF8StringContainer(0);
               ASN1Container[] var19 = new ASN1Container[]{var18};
               ASN1.berDecode(var26.data, var26.dataOffset, var19);
               if (var18.dataPresent && var18.dataLen != 0) {
                  this.k[var25] = new String(var18.data, var18.dataOffset, var18.dataLen);
               } else {
                  this.k[var25] = null;
               }
            } catch (ASN_Exception var23) {
               throw new CMPException("PKIHeader.PKIHeader: unable to extract and decode a freeText string.", var23);
            }
         }
      }

      if (var14.dataPresent) {
         var24 = var14.getContainerCount();
         this.l = new TypeAndValue[var24];

         for(var25 = 0; var25 < var24; ++var25) {
            try {
               var26 = var14.containerAt(var25);
               this.l[var25] = new TypeAndValue(var26.data, var26.dataOffset, 0);
            } catch (ASN_Exception var20) {
               throw new CMPException("PKIHeader.PKIHeader: unable to get an element of generalInfo.", var20);
            }
         }
      }

   }

   /** @deprecated */
   protected PKIHeader(CMPRequestCommon var1, CMPProtectInfo var2, byte[] var3, JSAFE_SecureRandom var4) throws CMPException {
      int var5 = var1.getVersion();
      if (var5 > 0 && var5 != 2) {
         throw new CMPException("PKIHeader.PKIHeader: CMP version provided (" + var1.getVersion() + ") does not match the supported version (" + 2 + ").");
      } else {
         this.a = 2;
         this.a(var2);
         this.d = var1.getMessageTime();
         if (var3 != null) {
            this.e = new a(var3);
         }

         this.g = null;
         this.a(var1, var4);
         this.h = new a(var1.getTransactionID());
         this.i = new a(this.a((JSAFE_SecureRandom)var4, 16));
         if (var1.getRecipNonce() != null) {
            this.j = new a(var1.getRecipNonce());
         }

         this.k = var1.getFreeText();
         this.l = var1.getGeneralInfo();
      }
   }

   private void a(CMPProtectInfo var1) throws CMPException {
      if (var1 != null) {
         X509Certificate var2;
         if (var1.pbmProtected()) {
            this.f = new a(var1.getKeyID());
         } else {
            var2 = var1.getSenderCert();
            this.b = this.a(var2);
            this.f = this.b(var2);
         }

         var2 = var1.getRecipCert();
         if (var2 != null) {
            this.c = this.a(var2);
            this.g = this.b(var2);
         }

      }
   }

   private GeneralName a(X509Certificate var1) throws CMPException {
      GeneralName var2 = null;
      X500Name var3 = var1.getSubjectName();
      if (var3 != null && var3.getRDNCount() != 0) {
         try {
            var2 = new GeneralName();
            var2.setGeneralName(var3, 5);
         } catch (NameException var8) {
            throw new CMPException("PKIHeader.getSubjectName: unable to create a GeneralName.", var8);
         }
      } else {
         X509V3Extensions var4 = var1.getExtensions();
         if (var4 != null) {
            SubjectAltName var5;
            try {
               var5 = (SubjectAltName)var4.getExtensionByType(17);
            } catch (CertificateException var10) {
               throw new CMPException("PKIHeader.getSubjectName: unable to extract SubjectAltName extension.", var10);
            }

            if (var5 != null) {
               try {
                  GeneralNames var6 = var5.getGeneralNames();
                  if (var6 != null) {
                     int var7 = var6.getNameCount();
                     if (var7 > 0) {
                        var2 = var6.getGeneralName(0);
                     }
                  }
               } catch (NameException var9) {
                  throw new CMPException("PKIHeader.getSubjectName: unable to extract GeneralName from SubjectAltName.", var9);
               }
            }
         }
      }

      if (var2 == null) {
         var2 = this.h();
      }

      return var2;
   }

   private a b(X509Certificate var1) throws CMPException {
      X509V3Extensions var2 = var1.getExtensions();
      if (var2 != null) {
         try {
            SubjectKeyID var3 = (SubjectKeyID)var2.getExtensionByType(14);
            if (var3 != null) {
               return new a(var3.getKeyID());
            }
         } catch (CertificateException var4) {
            throw new CMPException("PKIHeader.getSubjectKID: unable to extract Subject Key ID extension.", var4);
         }
      }

      return null;
   }

   /** @deprecated */
   protected byte[] a() throws CMPException {
      if (this.b == null) {
         this.b = this.h();
      }

      if (this.c == null) {
         this.c = this.h();
      }

      byte[] var1;
      try {
         var1 = new byte[this.b.getDERLen(0)];
         this.b.getDEREncoding(var1, 0, 0);
      } catch (NameException var25) {
         throw new CMPException("PKIHeader.derEncode: unable to encode sender.", var25);
      }

      byte[] var2;
      try {
         var2 = new byte[this.c.getDERLen(0)];
         this.c.getDEREncoding(var2, 0, 0);
      } catch (NameException var24) {
         throw new CMPException("PKIHeader.derEncode:  unable to encode recipient.", var24);
      }

      try {
         EndContainer var3 = new EndContainer();
         SequenceContainer var4 = new SequenceContainer(0, true, 0);
         IntegerContainer var5 = new IntegerContainer(0, true, 0, this.a);
         EncodedContainer var6 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
         EncodedContainer var7 = new EncodedContainer(0, true, 0, var2, 0, var2.length);
         GenTimeContainer var8;
         if (this.d == null) {
            var8 = new GenTimeContainer(65536, false, 0, (Date)null);
         } else {
            var8 = new GenTimeContainer(10551296, true, 0, this.d);
         }

         EncodedContainer var9;
         if (this.e == null) {
            var9 = new EncodedContainer(65536, false, 0, (byte[])null, 0, 0);
         } else {
            var9 = new EncodedContainer(0, true, 0, this.e.b, this.e.c, this.e.d);
         }

         ASN1Container var10 = this.a((a)this.f, 2);
         ASN1Container var11 = this.a((a)this.g, 3);
         ASN1Container var12 = this.a((a)this.h, 4);
         ASN1Container var13 = this.a((a)this.i, 5);
         ASN1Container var14 = this.a((a)this.j, 6);
         OfContainer var15;
         if (this.k != null) {
            var15 = new OfContainer(10551303, true, 0, 12288, new EncodedContainer(3072));

            for(int var16 = 0; var16 < this.k.length; ++var16) {
               byte[] var17 = this.k[var16].getBytes();

               try {
                  var15.addContainer(new UTF8StringContainer(0, true, 0, var17, 0, var17.length));
               } catch (ASN_Exception var23) {
                  throw new CMPException("PKIHeader.derEncode: unable to add an element to freeText.", var23);
               }
            }
         } else {
            var15 = new OfContainer(65536, false, 0, 0, (ASN1Container)null);
         }

         byte[] var19;
         OfContainer var27;
         if (this.l != null) {
            var27 = new OfContainer(10551304, true, 0, 12288, new EncodedContainer(12288));

            for(int var28 = 0; var28 < this.l.length; ++var28) {
               TypeAndValue var18 = this.l[var28];
               var19 = new byte[var18.getDERLen(0)];
               var18.getDEREncoding(var19, 0, 0);
               EncodedContainer var20 = new EncodedContainer(0, true, 0, var19, 0, var19.length);

               try {
                  var27.addContainer(var20);
               } catch (ASN_Exception var22) {
                  throw new CMPException("PKIHeader.derEncode: unable to add an element to generalInfo.", var22);
               }
            }
         } else {
            var27 = new OfContainer(65536, false, 0, 0, (ASN1Container)null);
         }

         ASN1Container[] var29 = new ASN1Container[]{var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var27, var3};
         ASN1Template var30 = new ASN1Template(var29);
         var19 = new byte[var30.derEncodeInit()];
         var30.derEncode(var19, 0);
         return var19;
      } catch (ASN_Exception var26) {
         throw new CMPException("PKIHeader.derEncode: encoding CMP PKIHeader failed.", var26);
      }
   }

   /** @deprecated */
   protected byte[] b() {
      return this.a(this.f);
   }

   /** @deprecated */
   protected byte[] c() {
      return this.a(this.g);
   }

   /** @deprecated */
   protected byte[] d() {
      return this.a(this.i);
   }

   /** @deprecated */
   protected byte[] e() {
      return this.a(this.j);
   }

   /** @deprecated */
   protected byte[] f() {
      return this.a(this.h);
   }

   /** @deprecated */
   protected byte[] g() {
      return this.a(this.e);
   }

   private byte[] a(a var1) {
      return var1 == null ? null : var1.a();
   }

   private void a(CMPRequestCommon var1, JSAFE_SecureRandom var2) {
      byte[] var3 = var1.getTransactionID();
      if (var3 == null) {
         var3 = this.a((JSAFE_SecureRandom)var2, 16);
         var1.setTransactionID(var3);
      }
   }

   private byte[] a(JSAFE_SecureRandom var1, int var2) {
      byte[] var3 = new byte[var2];

      do {
         var1.generateRandomBytes(var3, 0, var3.length);
      } while((var3[0] & 128) != 0);

      return var3;
   }

   private GeneralName h() throws CMPException {
      GeneralName var1 = new GeneralName();

      try {
         var1.setGeneralName(new X500Name(), 5);
         return var1;
      } catch (NameException var3) {
         throw new CMPException("PKIHeader.createEmptyName.", var3);
      }
   }

   private GeneralName a(ASN1Container var1, String var2) throws CMPException {
      if (var1.dataPresent && var1.dataLen != 0) {
         try {
            return new GeneralName(var1.data, var1.dataOffset, 0);
         } catch (NameException var4) {
            throw new CMPException("PKIHeader.decodeGeneralName: unable to decode a GeneralName in the " + var2 + " field.", var4);
         }
      } else {
         throw new CMPException("PKIHeader.decodeGeneralName: missing required field(" + var2 + ").");
      }
   }

   private ASN1Container a(a var1, int var2) throws ASN_Exception {
      return (ASN1Container)(var1 != null && var1.d != 0 ? new OctetStringContainer(10551296 | var2, true, 0, var1.b, var1.c, var1.d) : new EncodedContainer(65536, false, 0, (byte[])null, 0, 0));
   }

   private a a(ASN1Container var1) {
      return !var1.dataPresent ? null : new a(var1.data, var1.dataOffset, var1.dataLen);
   }

   private final class a {
      private byte[] b;
      private int c;
      private int d;

      private a(byte[] var2) {
         this.b = var2;
         this.c = 0;
         this.d = var2.length;
      }

      private a(byte[] var2, int var3, int var4) {
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }

      private byte[] a() {
         if (this.c != 0 && this.b.length != this.d) {
            byte[] var1 = new byte[this.d];
            System.arraycopy(this.b, this.c, var1, 0, this.d);
            return var1;
         } else {
            return this.b;
         }
      }

      // $FF: synthetic method
      a(byte[] var2, Object var3) {
         this(var2);
      }

      // $FF: synthetic method
      a(byte[] var2, int var3, int var4, Object var5) {
         this(var2, var3, var4);
      }
   }
}
