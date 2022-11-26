package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.x.c;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;

/** @deprecated */
public final class OCSPCertID {
   private String hashAlg;
   private byte[] issuerKeyHash;
   private byte[] issuerNameHash;
   private byte[] serial;
   private byte[] encoding;
   private static final int NO_SPECIAL = 0;
   private static final int BOGUS_OPTTAG = 0;
   private static final int BOGUS_OFFSET = 0;
   private static final boolean BOGUS_DATA_PRESENT = true;
   private final c context;

   OCSPCertID(CertJ var1, X509Certificate var2, X509Certificate var3, String var4) throws NameException, NotSupportedException {
      this.context = CertJInternalHelper.context(var1);
      this.hashAlg = var4;

      try {
         byte[] var5 = OCSPutil.extractKeyDER(var2.getSubjectPublicKeyBER(), 0);
         this.issuerKeyHash = OCSPutil.makeDataDigest(var1, var4, var5, 0, var5.length);
      } catch (Exception var9) {
         throw new NotSupportedException(var9);
      }

      byte[] var6 = new byte[var2.getSubjectName().getDERLen(0)];
      var2.getSubjectName().getDEREncoding(var6, 0, 0);

      try {
         this.issuerNameHash = OCSPutil.makeDataDigest(var1, var4, var6, 0, var6.length);
      } catch (Exception var8) {
         throw new NotSupportedException(var8);
      }

      byte[] var7 = var3.getSerialNumber();
      this.serial = new byte[var7.length];
      System.arraycopy(var7, 0, this.serial, 0, var7.length);
   }

   OCSPCertID(CertJ var1, byte[] var2, int var3, int var4) throws NotSupportedException {
      this.context = CertJInternalHelper.context(var1);

      OctetStringContainer var5;
      OctetStringContainer var6;
      EncodedContainer var7;
      try {
         EndContainer var8 = new EndContainer();
         SequenceContainer var9 = new SequenceContainer(0);
         EncodedContainer var10 = new EncodedContainer(65280);
         var5 = new OctetStringContainer(0);
         var6 = new OctetStringContainer(0);
         var7 = new EncodedContainer(65280);
         ASN1Container[] var11 = new ASN1Container[]{var9, var10, var5, var6, var7, var8};
         SequenceContainer var12 = new SequenceContainer(0);
         OIDContainer var13 = new OIDContainer(16777216);
         EncodedContainer var14 = new EncodedContainer(65536);
         ASN1Container[] var15 = new ASN1Container[]{var12, var13, var14, var8};
         ASN1.berDecode(var2, var3, var11);
         ASN1.berDecode(var10.data, var10.dataOffset, var15);
         JSAFE_MessageDigest var16 = h.e(var10.data, var10.dataOffset, "Java", this.context.b);
         this.hashAlg = var16.getAlgorithm();
      } catch (ASN_Exception var17) {
         throw new NotSupportedException(var17);
      } catch (JSAFE_Exception var18) {
         throw new NotSupportedException(var18);
      }

      this.issuerKeyHash = new byte[var6.dataLen];
      System.arraycopy(var6.data, var6.dataOffset, this.issuerKeyHash, 0, var6.dataLen);
      this.issuerNameHash = new byte[var5.dataLen];
      System.arraycopy(var5.data, var5.dataOffset, this.issuerNameHash, 0, var5.dataLen);
      this.serial = new byte[var7.dataLen];
      System.arraycopy(var7.data, var7.dataOffset, this.serial, 0, var7.dataLen);
      this.encoding = new byte[var4];
      System.arraycopy(var2, var3, this.encoding, 0, var4);
   }

   private byte[] getAlgorithmIDDER(String var1) throws NotSupportedException {
      try {
         JSAFE_MessageDigest var2 = h.a(var1, "Java", this.context.b);
         return var2.getDERAlgorithmID();
      } catch (JSAFE_Exception var3) {
         throw new NotSupportedException(var3);
      }
   }

   byte[] encode() throws NotSupportedException {
      if (this.encoding != null) {
         return this.encoding;
      } else {
         byte[] var1 = this.getAlgorithmIDDER(this.hashAlg);

         try {
            SequenceContainer var2 = new SequenceContainer(0, true, 0);
            EncodedContainer var3 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
            OctetStringContainer var4 = new OctetStringContainer(0, true, 0, this.issuerNameHash, 0, this.issuerNameHash.length);
            OctetStringContainer var5 = new OctetStringContainer(0, true, 0, this.issuerKeyHash, 0, this.issuerKeyHash.length);
            IntegerContainer var6 = new IntegerContainer(0, true, 0, this.serial, 0, this.serial.length, true);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var2, var3, var4, var5, var6, var7};
            ASN1Template var9 = new ASN1Template(var8);
            int var10 = var9.derEncodeInit();
            this.encoding = new byte[var10];
            int var11 = var9.derEncode(this.encoding, 0);
            return var10 == var11 ? this.encoding : null;
         } catch (ASN_Exception var12) {
            throw new NotSupportedException(var12);
         }
      }
   }
}
