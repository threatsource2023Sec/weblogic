package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;

public final class DigestRIPEMD160 implements DigestAlgorithm {
   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#ripemd160";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.MessageDigest;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "RIPEMD160";
   }
}
