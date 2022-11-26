package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.MACAlgorithm;

public final class HMACSHA1 implements MACAlgorithm {
   @Nonnull
   public String getURI() {
      return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Mac;
   }

   @Nonnull
   public String getJCAAlgorithmID() {
      return "HmacSHA1";
   }

   @Nonnull
   public String getDigest() {
      return "SHA-1";
   }
}
