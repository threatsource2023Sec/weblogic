package org.apache.xml.security.stax.impl.algorithms;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Mac;
import org.apache.xml.security.exceptions.XMLSecurityException;

public class HMACSignatureAlgorithm implements SignatureAlgorithm {
   private Mac mac;

   public HMACSignatureAlgorithm(String jceName, String jceProvider) throws NoSuchProviderException, NoSuchAlgorithmException {
      if (jceProvider != null) {
         this.mac = Mac.getInstance(jceName, jceProvider);
      } else {
         this.mac = Mac.getInstance(jceName);
      }

   }

   public void engineUpdate(byte[] input) throws XMLSecurityException {
      this.mac.update(input);
   }

   public void engineUpdate(byte input) throws XMLSecurityException {
      this.mac.update(input);
   }

   public void engineUpdate(byte[] buf, int offset, int len) throws XMLSecurityException {
      this.mac.update(buf, offset, len);
   }

   public void engineInitSign(Key signingKey) throws XMLSecurityException {
      try {
         this.mac.init(signingKey);
      } catch (InvalidKeyException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public void engineInitSign(Key signingKey, SecureRandom secureRandom) throws XMLSecurityException {
      try {
         this.mac.init(signingKey);
      } catch (InvalidKeyException var4) {
         throw new XMLSecurityException(var4);
      }
   }

   public void engineInitSign(Key signingKey, AlgorithmParameterSpec algorithmParameterSpec) throws XMLSecurityException {
      try {
         this.mac.init(signingKey, algorithmParameterSpec);
      } catch (InvalidKeyException var4) {
         throw new XMLSecurityException(var4);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new XMLSecurityException(var5);
      }
   }

   public byte[] engineSign() throws XMLSecurityException {
      return this.mac.doFinal();
   }

   public void engineInitVerify(Key verificationKey) throws XMLSecurityException {
      try {
         this.mac.init(verificationKey);
      } catch (InvalidKeyException var3) {
         throw new XMLSecurityException(var3);
      }
   }

   public boolean engineVerify(byte[] signature) throws XMLSecurityException {
      byte[] completeResult = this.mac.doFinal();
      return MessageDigest.isEqual(completeResult, signature);
   }

   public void engineSetParameter(AlgorithmParameterSpec params) throws XMLSecurityException {
   }
}
