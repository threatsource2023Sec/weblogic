package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.KeyTransportAlgorithm;

public final class KeyTransportRSAOAEPMGF1P implements KeyTransportAlgorithm {
   @Nonnull
   public String getKey() {
      return "RSA";
   }

   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.KeyTransport;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
   }

   @Nonnull
   public String getCipherMode() {
      return "ECB";
   }

   @Nonnull
   public String getPadding() {
      return "OAEPPadding";
   }
}
