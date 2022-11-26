package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface AlgorithmDescriptor {
   @Nonnull
   @NotEmpty
   String getURI();

   @Nonnull
   AlgorithmType getType();

   @Nonnull
   @NotEmpty
   String getJCAAlgorithmID();

   public static enum AlgorithmType {
      BlockEncryption,
      Mac,
      MessageDigest,
      KeyAgreement,
      KeyTransport,
      Signature,
      SymmetricKeyWrap;
   }
}
