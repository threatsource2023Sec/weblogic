package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureDSASHA1 implements SignatureAlgorithm {
   @Nonnull
   @NotEmpty
   public String getKey() {
      return "DSA";
   }

   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Signature;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return "SHA1withDSA";
   }

   @Nonnull
   @NotEmpty
   public String getDigest() {
      return "SHA-1";
   }
}
