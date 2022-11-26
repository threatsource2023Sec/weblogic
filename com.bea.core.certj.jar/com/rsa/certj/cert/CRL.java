package com.rsa.certj.cert;

import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OIDList;
import com.rsa.certj.CertJ;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_Signature;
import java.io.Serializable;
import java.security.SecureRandom;

/** @deprecated */
public abstract class CRL implements Serializable, Cloneable {
   /** @deprecated */
   public static final int RSA_WITH_SHA1_PKCS = 0;
   /** @deprecated */
   public static final int RSA_WITH_SHA1_ISO_OIW = 1;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X930 = 2;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X957 = 3;
   /** @deprecated */
   protected byte[] signatureAlgorithmBER;
   /** @deprecated */
   protected int signatureAlgorithmFormat = -1;
   /** @deprecated */
   protected byte[] signature;
   /** @deprecated */
   protected String theDevice;
   /** @deprecated */
   protected String[] theDeviceList;
   private CertJ theCertJ;

   /** @deprecated */
   public final void setCertJ(CertJ var1) {
      this.theCertJ = var1;
   }

   /** @deprecated */
   public final CertJ getCertJ() {
      return this.theCertJ;
   }

   /** @deprecated */
   public String getSignatureAlgorithm() throws CertificateException {
      if (this.signatureAlgorithmBER == null) {
         throw new CertificateException("Object not set with signature algorithm.");
      } else {
         return OIDList.getTrans(this.signatureAlgorithmBER, 0, this.signatureAlgorithmBER.length, 1);
      }
   }

   /** @deprecated */
   public byte[] getSignatureAlgorithmDER() throws CertificateException {
      if (this.signatureAlgorithmBER == null) {
         throw new CertificateException("Object not set with signature algorithm.");
      } else {
         return (byte[])((byte[])this.signatureAlgorithmBER.clone());
      }
   }

   /** @deprecated */
   public abstract byte[] getSignature() throws CertificateException;

   /** @deprecated */
   public String getDevice() throws CertificateException {
      if (this.theDevice == null) {
         throw new CertificateException("Object not set with a device.");
      } else {
         return this.theDevice;
      }
   }

   /** @deprecated */
   public String[] getDeviceList() throws CertificateException {
      if (this.theDeviceList == null) {
         throw new CertificateException("Object not set with a device.");
      } else {
         String[] var1 = new String[this.theDeviceList.length];
         System.arraycopy(this.theDeviceList, 0, var1, 0, this.theDeviceList.length);
         return var1;
      }
   }

   /** @deprecated */
   public void setSignatureStandard(int var1) {
      this.signatureAlgorithmFormat = var1;
   }

   /** @deprecated */
   public int getSignatureStandard() {
      return this.signatureAlgorithmFormat;
   }

   /** @deprecated */
   public String getSignatureFormat() {
      switch (this.signatureAlgorithmFormat) {
         case 0:
            return "RSAWithSHA1PKCS";
         case 1:
            return "RSAWithSHA1ISO_OIW";
         case 2:
            return "DSAWithSHA1X930";
         case 3:
            return "DSAWithSHA1X957";
         default:
            return null;
      }
   }

   /** @deprecated */
   public abstract void signCRL(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException;

   /** @deprecated */
   public void signCRL(byte[] var1, int var2, String var3, JSAFE_PrivateKey var4, SecureRandom var5) throws CertificateException {
      if (var1 != null && var3 != null && var4 != null) {
         try {
            int var6 = ASN1Lengths.determineLengthLen(var1, var2 + 1) + ASN1Lengths.determineLength(var1, var2 + 1);
            String var7 = OIDList.getTrans(var1, var2, var6, 1);
            this.signCRL(var7, var3, var4, var5);
         } catch (ASN_Exception var8) {
            throw new CertificateException("Cannot sign cert:", var8);
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public abstract boolean verifyCRLSignature(String var1, JSAFE_PublicKey var2, SecureRandom var3) throws CertificateException;

   /** @deprecated */
   public boolean verifyCRLSignature(String var1, byte[] var2, int var3, SecureRandom var4) throws CertificateException {
      if (var1 != null && var2 != null) {
         try {
            JSAFE_PublicKey var5 = h.a(var2, var3, var1, this.theCertJ);
            return this.verifyCRLSignature(var1, var5, var4);
         } catch (JSAFE_Exception var6) {
            throw new CertificateException("Cannot verify: ", var6);
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public boolean verifyCRLSignature(String var1, Certificate var2, SecureRandom var3) throws CertificateException {
      if (var1 != null && var2 != null) {
         JSAFE_PublicKey var4 = var2.getSubjectPublicKey(var1);
         return this.verifyCRLSignature(var1, var4, var3);
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   protected byte[] performSignature(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4, byte[] var5, int var6, int var7) throws CertificateException {
      if (var1 != null && var2 != null && var3 != null && var5 != null) {
         JSAFE_Signature var8 = null;

         byte[] var9;
         try {
            var8 = h.b(var1, var2, this.theCertJ);
            if (this.theCertJ == null) {
               var8.signInit(var3, var4);
            } else {
               var8.signInit(var3, (JSAFE_Parameters)null, var4, this.theCertJ.getPKCS11Sessions());
            }

            var8.signUpdate(var5, var6, var7);
            var9 = var8.signFinal();
         } catch (JSAFE_Exception var13) {
            throw new CertificateException("Could not sign the CRL: ", var13);
         } finally {
            if (var8 != null) {
               var8.clearSensitiveData();
            }

         }

         return var9;
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   protected boolean performSignatureVerification(String var1, JSAFE_PublicKey var2, SecureRandom var3, byte[] var4, int var5, int var6, byte[] var7, int var8, int var9) throws CertificateException {
      if (var1 != null && var2 != null && var4 != null && var7 != null) {
         JSAFE_Signature var10 = null;

         boolean var11;
         try {
            var10 = h.b(this.signatureAlgorithmBER, 0, var1, (CertJ)this.theCertJ);
            if (this.theCertJ == null) {
               var10.verifyInit(var2, var3);
            } else {
               var10.verifyInit(var2, (JSAFE_Parameters)null, var3, this.theCertJ.getPKCS11Sessions());
            }

            var10.verifyUpdate(var4, var5, var6);
            var11 = var10.verifyFinal(var7, var8, var9);
         } catch (JSAFE_Exception var15) {
            throw new CertificateException("Could not verify the CRL signature: ", var15);
         } finally {
            if (var10 != null) {
               var10.clearSensitiveData();
            }

         }

         return var11;
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   protected void clearSignature() {
      this.signature = null;
      this.theDevice = null;
      this.theDeviceList = null;
   }

   /** @deprecated */
   protected void clearComponents() {
      this.clearSignature();
      this.signatureAlgorithmBER = null;
   }
}
