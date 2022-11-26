package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SymmetricKeyWrapAlgorithm;

public final class SymmetricKeyWrapAES128 implements SymmetricKeyWrapAlgorithm {
   @Nonnull
   public String getKey() {
      return "AES";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#kw-aes128";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.SymmetricKeyWrap;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "AESWrap";
   }

   @Nonnull
   public Integer getKeyLength() {
      return 128;
   }
}
