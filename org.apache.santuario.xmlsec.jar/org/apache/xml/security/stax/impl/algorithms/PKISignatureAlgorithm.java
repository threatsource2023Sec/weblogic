package org.apache.xml.security.stax.impl.algorithms;

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
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.JavaUtils;

public class PKISignatureAlgorithm implements SignatureAlgorithm {
   private final String jceName;
   private final Signature signature;

   public PKISignatureAlgorithm(String jceName, String jceProvider) throws NoSuchProviderException, NoSuchAlgorithmException {
      this.jceName = jceName;
      if (jceProvider != null) {
         this.signature = Signature.getInstance(this.jceName, jceProvider);
      } else {
         this.signature = Signature.getInstance(this.jceName);
      }

   }

   public void engineUpdate(byte[] input) throws XMLSecurityException {
      try {
         this.signature.update(input);
      } catch (SignatureException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public void engineUpdate(byte input) throws XMLSecurityException {
      try {
         this.signature.update(input);
      } catch (SignatureException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public void engineUpdate(byte[] buf, int offset, int len) throws XMLSecurityException {
      try {
         this.signature.update(buf, offset, len);
      } catch (SignatureException var5) {
         throw new XMLSecurityException(var5);
      }
   }

   public void engineInitSign(Key signingKey) throws XMLSecurityException {
      try {
         this.signature.initSign((PrivateKey)signingKey);
      } catch (InvalidKeyException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public void engineInitSign(Key signingKey, SecureRandom secureRandom) throws XMLSecurityException {
      try {
         this.signature.initSign((PrivateKey)signingKey, secureRandom);
      } catch (InvalidKeyException var4) {
         throw new XMLSecurityException(var4);
      }
   }

   public void engineInitSign(Key signingKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSecurityException {
      try {
         this.signature.initSign((PrivateKey)signingKey);
      } catch (InvalidKeyException var4) {
         throw new XMLSecurityException(var4);
      }
   }

   public byte[] engineSign() throws XMLSecurityException {
      try {
         byte[] jcebytes = this.signature.sign();
         if (this.jceName.contains("ECDSA")) {
            return org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertASN1toXMLDSIG(jcebytes);
         } else {
            return this.jceName.contains("DSA") ? JavaUtils.convertDsaASN1toXMLDSIG(jcebytes, 20) : jcebytes;
         }
      } catch (SignatureException var2) {
         throw new XMLSecurityException(var2);
      } catch (IOException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public void engineInitVerify(Key verificationKey) throws XMLSecurityException {
      try {
         this.signature.initVerify((PublicKey)verificationKey);
      } catch (InvalidKeyException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public boolean engineVerify(byte[] signature) throws XMLSecurityException {
      try {
         byte[] jcebytes = signature;
         if (this.jceName.contains("ECDSA")) {
            jcebytes = org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertXMLDSIGtoASN1(signature);
         } else if (this.jceName.contains("DSA")) {
            jcebytes = JavaUtils.convertDsaXMLDSIGtoASN1(signature, 20);
         }

         return this.signature.verify(jcebytes);
      } catch (SignatureException var3) {
         throw new XMLSecurityException(var3);
      } catch (IOException var4) {
         throw new XMLSecurityException(var4);
      }
   }

   public void engineSetParameter(AlgorithmParameterSpec params) throws XMLSecurityException {
      try {
         this.signature.setParameter(params);
      } catch (InvalidAlgorithmParameterException var3) {
         throw new XMLSecurityException(var3);
      }
   }
}
