package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SymmetricKeyWrapAlgorithm;

public final class SymmetricKeyWrapDESede implements SymmetricKeyWrapAlgorithm {
   @Nonnull
   @NotEmpty
   public String getKey() {
      return "DESede";
   }

   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.SymmetricKeyWrap;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return "DESedeWrap";
   }

   @Nonnull
   public Integer getKeyLength() {
      return 192;
   }
}
