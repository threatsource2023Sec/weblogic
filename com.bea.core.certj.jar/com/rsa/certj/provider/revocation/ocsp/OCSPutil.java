package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.AuthorityInfoAccess;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_MessageDigest;
import java.util.Vector;

/** @deprecated */
public final class OCSPutil {
   private OCSPutil() {
   }

   /** @deprecated */
   public static String getAIALocation(X509Certificate var0) {
      X509V3Extensions var1 = var0.getExtensions();
      if (var1 == null) {
         return null;
      } else {
         try {
            AuthorityInfoAccess var2 = (AuthorityInfoAccess)var1.getExtensionByType(100);
            if (var2 == null) {
               return null;
            }

            for(int var3 = 0; var3 < var2.getAccessDescriptionCount(); ++var3) {
               byte[] var4 = var2.getAccessMethod(var3);
               if (CertJUtils.byteArraysEqual(var4, AuthorityInfoAccess.ID_AD_OCSP)) {
                  GeneralName var5 = var2.getAccessLocation(var3);
                  if (var5.getGeneralNameType() == 7) {
                     return (String)var5.getGeneralName();
                  }
               }
            }
         } catch (CertificateException var6) {
         }

         return null;
      }
   }

   /** @deprecated */
   public static byte[] makeDataDigest(CertJ var0, String var1, byte[] var2, int var3, int var4) throws InvalidParameterException {
      Object var5 = null;

      try {
         JSAFE_MessageDigest var6 = h.a(var1, var0.getDevice(), var0);
         byte[] var8 = new byte[var6.getDigestSize()];
         var6.digestInit();
         var6.digestUpdate(var2, var3, var4);
         var6.digestFinal(var8, 0);
         return var8;
      } catch (Exception var7) {
         throw new InvalidParameterException("makeDataDigest:" + var7.toString());
      }
   }

   /** @deprecated */
   public static byte[] extractKeyDER(byte[] var0, int var1) throws ASN_Exception {
      EndContainer var2 = new EndContainer();
      SequenceContainer var3 = new SequenceContainer(0);
      EncodedContainer var4 = new EncodedContainer(65280);
      BitStringContainer var5 = new BitStringContainer(0);
      ASN1Container[] var6 = new ASN1Container[]{var3, var4, var5, var2};
      ASN1.berDecode(var0, var1, var6);
      byte[] var7 = new byte[var5.dataLen];
      System.arraycopy(var5.data, var5.dataOffset, var7, 0, var5.dataLen);
      return var7;
   }

   /** @deprecated */
   public static int selectCertificateByKeyHash(CertJ var0, DatabaseService var1, byte[] var2, int var3, int var4, Vector var5) throws DatabaseException, NoServiceException, CertificateException, InvalidParameterException {
      int var6 = 0;
      boolean var7 = true;

      while(var1.hasMoreCertificates()) {
         Certificate var8;
         if (var7) {
            var8 = var1.firstCertificate();
            var7 = false;
         } else {
            var8 = var1.nextCertificate();
         }

         try {
            byte[] var9 = extractKeyDER(var8.getSubjectPublicKeyBER(), 0);
            byte[] var10 = makeDataDigest(var0, "SHA1", var9, 0, var9.length);
            if (CertJUtils.byteArraysEqual(var10, 0, var10.length, var2, var3, var4)) {
               var5.addElement(var8);
               ++var6;
            }
         } catch (Exception var11) {
         }
      }

      return var6;
   }
}
