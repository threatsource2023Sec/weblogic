package com.rsa.certj.cert;

import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.AlgorithmID;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OIDList;
import com.rsa.certj.CertJ;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_Session;
import com.rsa.jsafe.JSAFE_Signature;
import java.io.Serializable;
import java.security.SecureRandom;

/** @deprecated */
public abstract class Certificate implements Serializable, Cloneable {
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
   protected JSAFE_PublicKey subjectPublicKey;
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
      try {
         if (this.signatureAlgorithmBER == null) {
            throw new CertificateException("Object not set with signature algorithm.");
         } else {
            return AlgorithmID.berDecodeAlgID(this.signatureAlgorithmBER, 0, 1, (EncodedContainer)null);
         }
      } catch (ASN_Exception var2) {
         throw new CertificateException("Invalid Signature Algorithm.", var2);
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
   public String getSignatureFormat(String var1) {
      if (var1 == null) {
         return null;
      } else {
         switch (this.signatureAlgorithmFormat) {
            case 0:
               if (var1.equals("SHA1/RSA/PKCS1Block01Pad")) {
                  return "RSAWithSHA1PKCS";
               }

               return null;
            case 1:
               if (var1.equals("SHA1/RSA/PKCS1Block01Pad")) {
                  return "RSAWithSHA1ISO_OIW";
               }

               return null;
            case 2:
               if (!var1.equals("SHA1/DSA") && !var1.equals("SHA1/DSA/NoPad")) {
                  return null;
               }

               return "DSAWithSHA1X930";
            case 3:
               if (!var1.equals("SHA1/DSA") && !var1.equals("SHA1/DSA/NoPad")) {
                  return null;
               }

               return "DSAWithSHA1X957";
            default:
               return var1.equals("SHA1/DSA") ? "SHA1/DSA/NoPad" : null;
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(JSAFE_PublicKey var1) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Public key is null.");
      } else {
         this.clearSignature();

         try {
            this.subjectPublicKey = (JSAFE_PublicKey)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException(var3);
         }

         try {
            byte[] var2;
            if (this.signatureAlgorithmFormat == 3 && var1.getAlgorithm().compareTo("DSA") == 0) {
               var2 = var1.getKeyData("DSAPublicKeyX957BER")[0];
            } else {
               var2 = var1.getKeyData(var1.getAlgorithm() + "PublicKeyBER")[0];
            }

            this.subjectPublicKeyInfo = var2;
         } catch (JSAFE_Exception var4) {
            throw new CertificateException("Could not read the public key.");
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Public key encoding is null.");
      } else {
         try {
            String var3;
            if (this.theCertJ != null) {
               var3 = this.theCertJ.getDevice();
            } else {
               var3 = "Java";
            }

            this.setSubjectPublicKey(h.a(var1, var2, var3, this.theCertJ));
         } catch (JSAFE_Exception var4) {
            throw new CertificateException("Could not read the public key.");
         }
      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getSubjectPublicKey(String var1) throws CertificateException {
      if (this.subjectPublicKey == null) {
         throw new CertificateException("Object not set with public key.");
      } else {
         try {
            return (JSAFE_PublicKey)this.subjectPublicKey.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException(var3);
         }
      }
   }

   /** @deprecated */
   public byte[] getSubjectPublicKeyBER() throws CertificateException {
      if (this.subjectPublicKeyInfo == null) {
         throw new CertificateException("Object not set with public key.");
      } else {
         return (byte[])((byte[])this.subjectPublicKeyInfo.clone());
      }
   }

   /** @deprecated */
   public byte[] getUniqueID() {
      try {
         JSAFE_PublicKey var1 = this.getSubjectPublicKey("Java");
         byte[][] var2 = var1.getKeyData();
         JSAFE_MessageDigest var3 = h.a("MD5", "Java", this.theCertJ);
         var3.digestInit();

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.digestUpdate(var2[var4], 0, var2[var4].length);
         }

         return var3.digestFinal();
      } catch (JSAFE_Exception var5) {
      } catch (CertificateException var6) {
      }

      return null;
   }

   /** @deprecated */
   public abstract void signCertificate(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException;

   /** @deprecated */
   public void signCertificate(byte[] var1, int var2, String var3, JSAFE_PrivateKey var4, SecureRandom var5) throws CertificateException {
      if (var1 != null && var3 != null && var4 != null) {
         try {
            int var6 = 1 + ASN1Lengths.determineLengthLen(var1, var2 + 1) + ASN1Lengths.determineLength(var1, var2 + 1);
            String var7 = OIDList.getTrans(var1, var2, var6, 1);
            this.signCertificate(var7, var3, var4, var5);
         } catch (ASN_Exception var8) {
            throw new CertificateException("Cannot sign cert:", var8);
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public abstract boolean verifyCertificateSignature(String var1, JSAFE_PublicKey var2, SecureRandom var3) throws CertificateException;

   /** @deprecated */
   public boolean verifyCertificateSignature(String var1, byte[] var2, int var3, SecureRandom var4) throws CertificateException {
      if (var1 != null && var2 != null) {
         try {
            JSAFE_PublicKey var5 = h.a(var2, var3, var1, this.theCertJ);
            return this.verifyCertificateSignature(var1, var5, var4);
         } catch (JSAFE_Exception var6) {
            throw new CertificateException("Cannot verify: ", var6);
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public boolean verifyCertificateSignature(String var1, Certificate var2, SecureRandom var3) throws CertificateException {
      if (var1 != null && var2 != null) {
         JSAFE_PublicKey var4 = var2.getSubjectPublicKey(var1);
         return this.verifyCertificateSignature(var1, var4, var3);
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
               var8.signInit(var3, (JSAFE_Parameters)null, var4, (JSAFE_Session[])null);
            } else {
               var8.signInit(var3, (JSAFE_Parameters)null, var4, this.theCertJ.getPKCS11Sessions());
            }

            var8.signUpdate(var5, var6, var7);
            var9 = var8.signFinal();
         } catch (JSAFE_Exception var13) {
            throw new CertificateException("Could not sign the certificate: ", var13);
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
               var10.verifyInit(var2, (JSAFE_Parameters)null, var3, (JSAFE_Session[])null);
            } else {
               var10.verifyInit(var2, (JSAFE_Parameters)null, var3, this.theCertJ.getPKCS11Sessions());
            }

            var10.verifyUpdate(var4, var5, var6);
            var11 = var10.verifyFinal(var7, var8, var9);
         } catch (JSAFE_Exception var15) {
            throw new CertificateException("Could not verify the certificate: ", var15);
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
   }

   /** @deprecated */
   protected void clearComponents() {
      this.clearSignature();
      this.signatureAlgorithmBER = null;
      this.subjectPublicKeyInfo = null;
      if (this.subjectPublicKey != null) {
         this.subjectPublicKey.clearSensitiveData();
      }

      this.subjectPublicKey = null;
   }
}
