package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.MACAlgorithm;

public final class HMACSHA512 implements MACAlgorithm {
   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Mac;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "HmacSHA512";
   }

   @Nonnull
   public String getDigest() {
      return "SHA-512";
   }
}
