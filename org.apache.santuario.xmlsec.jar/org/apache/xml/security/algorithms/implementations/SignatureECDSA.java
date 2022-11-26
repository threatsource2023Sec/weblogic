package org.apache.xml.security.algorithms.implementations;

import java.io.IOException;
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
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SignatureECDSA extends SignatureAlgorithmSpi {
   private static final Logger LOG = LoggerFactory.getLogger(SignatureECDSA.class);
   private Signature signatureAlgorithm;

   public abstract String engineGetURI();

   public static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
      return ECDSAUtils.convertASN1toXMLDSIG(asn1Bytes);
   }

   public static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
      return ECDSAUtils.convertXMLDSIGtoASN1(xmldsigBytes);
   }

   public SignatureECDSA() throws XMLSignatureException {
      String algorithmID = JCEMapper.translateURItoJCEID(this.engineGetURI());
      LOG.debug("Created SignatureECDSA using {}", algorithmID);
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
         byte[] jcebytes = convertXMLDSIGtoASN1(signature);
         if (LOG.isDebugEnabled()) {
            LOG.debug("Called ECDSA.verify() on " + XMLUtils.encodeToString(signature));
         }

         return this.signatureAlgorithm.verify(jcebytes);
      } catch (SignatureException var3) {
         throw new XMLSignatureException(var3);
      } catch (IOException var4) {
         throw new XMLSignatureException(var4);
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
         byte[] jcebytes = this.signatureAlgorithm.sign();
         return convertASN1toXMLDSIG(jcebytes);
      } catch (SignatureException var2) {
         throw new XMLSignatureException(var2);
      } catch (IOException var3) {
         throw new XMLSignatureException(var3);
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

   public static class SignatureECDSARIPEMD160 extends SignatureECDSA {
      public SignatureECDSARIPEMD160() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160";
      }
   }

   public static class SignatureECDSASHA512 extends SignatureECDSA {
      public SignatureECDSASHA512() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
      }
   }

   public static class SignatureECDSASHA384 extends SignatureECDSA {
      public SignatureECDSASHA384() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384";
      }
   }

   public static class SignatureECDSASHA256 extends SignatureECDSA {
      public SignatureECDSASHA256() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256";
      }
   }

   public static class SignatureECDSASHA224 extends SignatureECDSA {
      public SignatureECDSASHA224() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224";
      }
   }

   public static class SignatureECDSASHA1 extends SignatureECDSA {
      public SignatureECDSASHA1() throws XMLSignatureException {
      }

      public String engineGetURI() {
         return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
      }
   }
}
