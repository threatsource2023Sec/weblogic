package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureDSASHA256 implements SignatureAlgorithm {
   @Nonnull
   @NotEmpty
   public String getKey() {
      return "DSA";
   }

   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2009/xmldsig11#dsa-sha256";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Signature;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return "SHA256withDSA";
   }

   @Nonnull
   @NotEmpty
   public String getDigest() {
      return "SHA-256";
   }
}
