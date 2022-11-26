package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureECDSASHA512 implements SignatureAlgorithm {
   @Nonnull
   public String getKey() {
      return "EC";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Signature;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "SHA512withECDSA";
   }

   @Nonnull
   public String getDigest() {
      return "SHA-512";
   }
}
