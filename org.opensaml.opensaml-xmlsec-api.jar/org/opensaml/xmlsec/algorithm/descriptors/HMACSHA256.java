package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.MACAlgorithm;

public final class HMACSHA256 implements MACAlgorithm {
   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Mac;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "HmacSHA256";
   }

   @Nonnull
   public String getDigest() {
      return "SHA-256";
   }
}
