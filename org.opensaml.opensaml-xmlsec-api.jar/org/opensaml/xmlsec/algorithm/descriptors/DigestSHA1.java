package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;

public final class DigestSHA1 implements DigestAlgorithm {
   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2000/09/xmldsig#sha1";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.MessageDigest;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "SHA-1";
   }
}
