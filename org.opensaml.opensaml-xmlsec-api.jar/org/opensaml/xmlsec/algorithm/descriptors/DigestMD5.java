package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;

public final class DigestMD5 implements DigestAlgorithm {
   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#md5";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.MessageDigest;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return "MD5";
   }
}
