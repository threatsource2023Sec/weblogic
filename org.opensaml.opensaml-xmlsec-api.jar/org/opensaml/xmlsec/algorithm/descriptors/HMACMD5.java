package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.MACAlgorithm;

public final class HMACMD5 implements MACAlgorithm {
   @Nonnull
   @NotEmpty
   public String getURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
   }

   @Nonnull
   public AlgorithmDescriptor.AlgorithmType getType() {
      return AlgorithmDescriptor.AlgorithmType.Mac;
   }

   @Nonnull
   @NotEmpty
   public String getJCAAlgorithmID() {
      return "HmacMD5";
   }

   @Nonnull
   @NotEmpty
   public String getDigest() {
      return "MD5";
   }
}
