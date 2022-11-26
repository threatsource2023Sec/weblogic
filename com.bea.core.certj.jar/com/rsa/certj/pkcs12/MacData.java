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
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_MAC;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import java.security.SecureRandom;

final class MacData {
   private static final int a = 8;
   private static final int b = 1;

   private MacData() {
   }

   static byte[] a(CertJ var0, byte[] var1, int var2, int var3, String var4, int var5, char[] var6) throws PKCS12Exception {
      JSAFE_MAC var8 = null;
      Object var9 = null;
      byte[] var10 = new byte[8];
      String var11 = var0.getDevice();

      byte[] var32;
      try {
         var8 = h.d(a(var4, var5), var11, var0);
         JSAFE_SecureRandom var7 = var0.getRandomObject();
         var7.generateRandomBytes(var10, 0, 8);
         var8.setSalt(var10, 0, var10.length);
         JSAFE_SecretKey var12 = var8.getBlankKey();
         var12.setPassword(var6, 0, var6.length);
         var8.macInit(var12, (SecureRandom)null);
         var32 = new byte[var8.getMACSize()];
         var8.macUpdate(var1, var2, var3);
         var8.macFinal(var32, 0);
      } catch (JSAFE_Exception var29) {
         throw new PKCS12Exception("MacData.computeMac: JSAFE operation failed.", var29);
      } catch (CertJException var30) {
         throw new PKCS12Exception("MacData.computeMac: ", var30);
      } finally {
         if (var8 != null) {
            var8.clearSensitiveData();
         }

      }

      SequenceContainer var13;
      int var21;
      byte[] var33;
      try {
         var13 = new SequenceContainer(0, true, 0);
         SequenceContainer var14 = new SequenceContainer(0, true, 0);
         EndContainer var15 = new EndContainer();
         OIDContainer var16 = new OIDContainer(0, true, 0, var4, 11);
         EncodedContainer var17 = new EncodedContainer(77824, false, 0, (byte[])null, 0, 0);
         OctetStringContainer var18 = new OctetStringContainer(0, true, 0, var32, 0, var32.length);
         ASN1Container[] var19 = new ASN1Container[]{var13, var14, var16, var17, var15, var18, var15};
         ASN1Template var20 = new ASN1Template(var19);
         var21 = var20.derEncodeInit();
         var33 = new byte[var21];
         var20.derEncode(var33, 0);
      } catch (ASN_Exception var28) {
         throw new PKCS12Exception("MacData.encodeMac: Encoding DigestInfo failed.", var28);
      }

      try {
         var13 = new SequenceContainer(0, true, 0);
         EncodedContainer var34 = new EncodedContainer(12288, true, 0, var33, 0, var33.length);
         OctetStringContainer var35 = new OctetStringContainer(0, true, 0, var10, 0, 8);
         IntegerContainer var36 = new IntegerContainer(131072, var5 != 1, 0, var5);
         EndContainer var37 = new EndContainer();
         ASN1Container[] var38 = new ASN1Container[]{var13, var34, var35, var36, var37};
         ASN1Template var39 = new ASN1Template(var38);
         var21 = var39.derEncodeInit();
         byte[] var40 = new byte[var21];
         var39.derEncode(var40, 0);
         return var40;
      } catch (ASN_Exception var27) {
         throw new PKCS12Exception("MacData.encodeMac: Encoding MacData failed.", var27);
      }
   }

   static void a(CertJ var0, char[] var1, byte[] var2, byte[] var3, int var4, int var5) throws PKCS12Exception {
      if (var0 == null) {
         throw new PKCS12Exception("MacData.MacData: certJ can not be null.");
      } else {
         try {
            SequenceContainer var6 = new SequenceContainer(var5);
            EndContainer var7 = new EndContainer();
            EncodedContainer var8 = new EncodedContainer(12288);
            OctetStringContainer var9 = new OctetStringContainer(0);
            IntegerContainer var10 = new IntegerContainer(131072);
            ASN1Container[] var11 = new ASN1Container[]{var6, var8, var9, var10, var7};
            ASN1.berDecode(var3, var4, var11);
            int var12 = 1;
            if (var10.dataPresent) {
               var12 = var10.getValueAsInt();
            }

            SequenceContainer var13 = new SequenceContainer(0);
            SequenceContainer var14 = new SequenceContainer(0);
            OIDContainer var15 = new OIDContainer(0, 11);
            EncodedContainer var16 = new EncodedContainer(77824);
            OctetStringContainer var17 = new OctetStringContainer(0);
            ASN1Container[] var18 = new ASN1Container[]{var13, var14, var15, var16, var7, var17, var7};
            ASN1.berDecode(var8.data, var8.dataOffset, var18);
            JSAFE_MAC var19 = h.d(a(var15.transformation, var12), var0.getDevice(), var0);
            var19.setSalt(var9.data, var9.dataOffset, var9.dataLen);
            JSAFE_SecretKey var20 = var19.getBlankKey();
            var20.setPassword(var1, 0, var1.length);
            var19.verifyInit(var20, (SecureRandom)null);
            var19.verifyUpdate(var2, 0, var2.length);
            if (!var19.verifyFinal(var17.data, var17.dataOffset, var17.dataLen)) {
               throw new PKCS12Exception("MacData.MacData: MAC Verification failed");
            }
         } catch (ASN_Exception var21) {
            throw new PKCS12Exception("Cannot decode the BER of the MacData.");
         } catch (JSAFE_InvalidUseException var22) {
            throw new PKCS12Exception(var22);
         } catch (JSAFE_UnimplementedException var23) {
            throw new PKCS12Exception(var23);
         } catch (JSAFE_InvalidKeyException var24) {
            throw new PKCS12Exception(var24);
         } catch (JSAFE_InvalidParameterException var25) {
            throw new PKCS12Exception(var25);
         }
      }
   }

   private static String a(String var0, int var1) {
      return "PBE/HMAC/" + var0 + "/PKCS12V1PBE-" + var1 + "-160";
   }
}
