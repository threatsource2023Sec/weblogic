package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureRSASHA256 implements SignatureAlgorithm {
   @Nonnull
   public String getKey() {
      return "RSA";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Signature;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "SHA256withRSA";
   }

   @Nonnull
   public String getDigest() {
      return "SHA-256";
   }
}
