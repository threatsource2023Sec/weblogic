package org.apache.xml.security.algorithms.implementations;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.SignatureAlgorithmSpi;
import org.apache.xml.security.signature.XMLSignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SignatureBaseRSA extends SignatureAlgorithmSpi {
   private static final Logger LOG = LoggerFactory.getLogger(SignatureBaseRSA.class);
   private Signature signatureAlgorithm;

   public abstract String engineGetURI();

   public SignatureBaseRSA() throws XMLSignatureException {
      String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
      LOG.debug("Created SignatureRSA using {}", algorithmID);
      String provider = JCEMapper.getProviderId();

      Object[] exArgs;
      try {
         if (provider == null) {
            this.signatureAlgorithm = Signature.getInstance(algorithmID);
         } else {
            this.signatureAlgorithm = Signature.getInstance(algorithmID, provider);
         }

      } catch (NoSuchAlgorithmException var5) {
         exArgs = new Object[]{algorithmID, var5.getLocalizedMessage()};
         throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
      } catch (NoSuchProviderException var6) {
         exArgs = new Object[]{algorithmID, var6.getLocalizedMessage()};
         throw new XMLSignatureException("algorithms.NoSuchAlgorithm", exArgs);
      }
   }

   protected void engineSetParameter(AlgorithmParameterSpec params) throws XMLSignatureException {
      try {
         this.signatureAlgorithm.setParameter(params);
      } catch (InvalidAlgorithmParameterException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected boolean engineVerify(byte[] signature) throws XMLSignatureException {
      try {
         return this.signatureAlgorithm.verify(signature);
      } catch (SignatureException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineInitVerify(Key publicKey) throws XMLSignatureException {
      if (!(publicKey instanceof PublicKey)) {
         String supplied = null;
         if (publicKey != null) {
            supplied = publicKey.getClass().getName();
         }

         String needed = PublicKey.class.getName();
         Object[] exArgs = new Object[]{supplied, needed};
         throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
      } else {
         try {
            this.signatureAlgorithm.initVerify((PublicKey)publicKey);
         } catch (InvalidKeyException var6) {
            Signature sig = this.signatureAlgorithm;

            try {
               this.signatureAlgorithm = Signature.getInstance(this.signatureAlgorithm.getAlgorithm());
            } catch (Exception var5) {
               LOG.debug("Exception when reinstantiating Signature: {}", var5);
               this.signatureAlgorithm = sig;
            }

            throw new XMLSignatureException(var6);
         }
      }
   }

   protected byte[] engineSign() throws XMLSignatureException {
      try {
         return this.signatureAlgorithm.sign();
      } catch (SignatureException var2) {
         throw new XMLSignatureException(var2);
      }
   }

   protected void engineInitSign(Key privateKey, SecureRandom secureRandom) throws XMLSignatureException {
      if (!(privateKey instanceof PrivateKey)) {
         String supplied = null;
         if (privateKey != null) {
            supplied = privateKey.getClass().getName();
         }

         String needed = PrivateKey.class.getName();
         Object[] exArgs = new Object[]{supplied, needed};
         throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", exArgs);
      } else {
         try {
            if (secureRandom == null) {
               this.signatureAlgorithm.initSign((PrivateKey)privateKey);
            } else {
               this.signatureAlgorithm.initSign((PrivateKey)privateKey, secureRandom);
            }

         } catch (InvalidKeyException var6) {
            throw new XMLSignatureException(var6);
         }
      }
   }

   protected void engineInitSign(Key privateKey) throws XMLSignatureException {
      this.engineInitSign(privateKey, (SecureRandom)null);
   }

   protected void engineUpdate(byte[] input) throws XMLSignatureException {
      try {
         this.signatureAlgorithm.update(input);
      } catch (SignatureException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineUpdate(byte input) throws XMLSignatureException {
      try {
         this.signatureAlgorithm.update(input);
      } catch (SignatureException var3) {
         throw new XMLSignatureException(var3);
      }
   }

   protected void engineUpdate(byte[] buf, int offset, int len) throws XMLSignatureException {
      try {
         this.signatureAlgorithm.update(buf, offset, len);
      } catch (SignatureException var5) {
         throw new XMLSignatureException(var5);
      }
   }

   protected String engineGetJCEAlgorithmString() {
      return this.signatureAlgorithm.getAlgorithm();
   }

   protected String engineGetJCEProviderName() {
      return this.signatureAlgorithm.getProvider().getName();
   }

   protected void engineSetHMACOutputLength(int HMACOutputLength) throws XMLSignatureException {
      throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
   }

   protected void engineInitSign(Key signingKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSignatureException {
      throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnRSA");
   }

   public static class SignatureRSASHA3_512MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA3_512MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha3-512-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA3_384MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA3_384MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha3-384-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA3_256MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA3_256MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha3-256-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA3_224MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA3_224MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha3-224-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA512MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA512MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA384MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA384MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA256MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA256MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA224MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA224MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1";
      }
   }

   public static class SignatureRSASHA1MGF1 extends SignatureBaseRSA {
      public SignatureRSASHA1MGF1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1";
      }
   }

   public static class SignatureRSAMD5 extends SignatureBaseRSA {
      public SignatureRSAMD5() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";
      }
   }

   public static class SignatureRSARIPEMD160 extends SignatureBaseRSA {
      public SignatureRSARIPEMD160() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160";
      }
   }

   public static class SignatureRSASHA512 extends SignatureBaseRSA {
      public SignatureRSASHA512() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
      }
   }

   public static class SignatureRSASHA384 extends SignatureBaseRSA {
      public SignatureRSASHA384() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
      }
   }

   public static class SignatureRSASHA256 extends SignatureBaseRSA {
      public SignatureRSASHA256() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
      }
   }

   public static class SignatureRSASHA224 extends SignatureBaseRSA {
      public SignatureRSASHA224() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha224";
      }
   }

   public static class SignatureRSASHA1 extends SignatureBaseRSA {
      public SignatureRSASHA1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
      }
   }
}
