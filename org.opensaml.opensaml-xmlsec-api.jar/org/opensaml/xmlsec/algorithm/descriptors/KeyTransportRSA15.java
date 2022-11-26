package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.KeyTransportAlgorithm;

public final class KeyTransportRSA15 implements KeyTransportAlgorithm {
   @Nonnull
   @NotEmpty
   public String getKey() {
      return "RSA";
   }

   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.KeyTransport;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
   }

   @Nonnull
   @NotEmpty
   public String getCipherMode() {
      return "ECB";
   }

   @Nonnull
   @NotEmpty
   public String getPadding() {
      return "PKCS1Padding";
   }
}
