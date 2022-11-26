package com.rsa.certj.cert;

import com.rsa.asn1.OIDList;
import com.rsa.certj.CertJ;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_Session;
import com.rsa.jsafe.JSAFE_Signature;
import java.io.Serializable;
import java.security.SecureRandom;

/** @deprecated */
public abstract class CertRequest implements Serializable, Cloneable {
   private static final String DEFAULT_DEVICE = "Java";
   /** @deprecated */
   public static final int RSA_WITH_SHA1_PKCS = 0;
   /** @deprecated */
   public static final int RSA_WITH_SHA1_ISO_OIW = 1;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X930 = 2;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X957 = 3;
   /** @deprecated */
   protected byte[] subjectPublicKeyInfo;
   /** @deprecated */
   protected byte[] signatureAlgorithmBER;
   /** @deprecated */
   protected int signatureAlgorithmFormat = -1;
   /** @deprecated */
   protected byte[] signature;
   /** @deprecated */
   protected boolean signedByUs;
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
      return "Java";
   }

   /** @deprecated */
   public String[] getDeviceList() throws CertificateException {
      return new String[]{"Java"};
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
   public void setSubjectPublicKey(JSAFE_PublicKey var1) throws CertificateException {
      this.clearSignature();
      if (var1 == null) {
         throw new CertificateException("Public key is null.");
      } else {
         try {
            String var2;
            if (this.signatureAlgorithmFormat == 3 && var1.getAlgorithm().compareTo("DSA") == 0) {
               var2 = "DSAPublicKeyX957BER";
            } else {
               var2 = var1.getAlgorithm() + "PublicKeyBER";
            }

            byte[][] var3 = var1.getKeyData(var2);
            this.subjectPublicKeyInfo = var3[0];
         } catch (JSAFE_Exception var4) {
            throw new CertificateException("Could not read the public key.");
         }
      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getSubjectPublicKey(String var1) throws CertificateException {
      if (this.subjectPublicKeyInfo == null) {
         throw new CertificateException("Object not set with public key.");
      } else if (var1 == null) {
         throw new CertificateException("Device is null.");
      } else {
         try {
            return h.a(this.subjectPublicKeyInfo, 0, var1, (CertJ)this.theCertJ);
         } catch (JSAFE_Exception var3) {
            throw new CertificateException("Cannot retrieve the public key: ", var3);
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(byte[] var1, int var2) throws CertificateException {
      this.clearSignature();
      if (var1 == null) {
         throw new CertificateException("Public key encoding is null.");
      } else {
         JSAFE_PublicKey var3 = null;

         try {
            var3 = h.a(var1, var2, "Java", this.theCertJ);
            this.setSubjectPublicKey(var3);
         } catch (JSAFE_Exception var8) {
            throw new CertificateException("Could not read the public key.");
         } finally {
            if (var3 != null) {
               var3.clearSensitiveData();
            }

         }

      }
   }

   /** @deprecated */
   public abstract void signCertRequest(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException;

   /** @deprecated */
   public abstract boolean verifyCertRequestSignature(String var1, SecureRandom var2) throws CertificateException;

   /** @deprecated */
   protected byte[] performSignature(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4, byte[] var5, int var6, int var7) throws CertificateException {
      if (var1 != null && var2 != null && var3 != null && var4 != null && var5 != null) {
         JSAFE_Signature var8 = null;

         byte[] var9;
         try {
            this.signedByUs = true;
            var8 = h.b(var1, var2, this.theCertJ);
            if (this.theCertJ == null) {
               var8.signInit(var3, (JSAFE_Parameters)null, var4, (JSAFE_Session[])null);
            } else {
               var8.signInit(var3, (JSAFE_Parameters)null, var4, this.theCertJ.getPKCS11Sessions());
            }

            this.signatureAlgorithmBER = var8.getDERAlgorithmID(this.getSignatureFormat(), false);
            var8.signUpdate(var5, var6, var7);
            var9 = var8.signFinal();
         } catch (JSAFE_Exception var13) {
            this.signedByUs = false;
            throw new CertificateException("Could not sign the request: ", var13);
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
   protected boolean performSignatureVerification(String var1, SecureRandom var2, byte[] var3, int var4, int var5, byte[] var6, int var7, int var8) throws CertificateException {
      if (var1 != null && var2 != null && var3 != null && var6 != null) {
         JSAFE_PublicKey var9 = null;
         JSAFE_Signature var10 = null;

         boolean var11;
         try {
            var9 = h.a(this.subjectPublicKeyInfo, 0, var1, (CertJ)this.theCertJ);
            var10 = h.b(this.signatureAlgorithmBER, 0, var1, (CertJ)this.theCertJ);
            if (this.theCertJ == null) {
               var10.verifyInit(var9, (JSAFE_Parameters)null, var2, (JSAFE_Session[])null);
            } else {
               var10.verifyInit(var9, (JSAFE_Parameters)null, var2, this.theCertJ.getPKCS11Sessions());
            }

            var10.verifyUpdate(var3, var4, var5);
            var11 = var10.verifyFinal(var6, var7, var8);
         } catch (JSAFE_Exception var15) {
            throw new CertificateException("Could not verify the request: ", var15);
         } finally {
            if (var9 != null) {
               var9.clearSensitiveData();
            }

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
      this.signatureAlgorithmBER = null;
      this.signedByUs = false;
   }

   /** @deprecated */
   protected void clearComponents() {
      this.clearSignature();
      this.subjectPublicKeyInfo = null;
   }
}
