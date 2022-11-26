package org.opensaml.security.x509;

import java.util.Arrays;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.apache.commons.codec.binary.Hex;

public final class X509DigestCriterion implements Criterion {
   private String algorithm;
   private byte[] x509digest;

   public X509DigestCriterion(@Nonnull String alg, @Nonnull byte[] digest) {
      this.setAlgorithm(alg);
      this.setDigest(digest);
   }

   @Nonnull
   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(@Nonnull String alg) {
      String trimmed = StringSupport.trimOrNull(alg);
      Constraint.isNotNull(trimmed, "Certificate digest algorithm cannot be null or empty");
      this.algorithm = trimmed;
   }

   @Nonnull
   public byte[] getDigest() {
      return this.x509digest;
   }

   public void setDigest(@Nonnull byte[] digest) {
      if (digest != null && digest.length != 0) {
         this.x509digest = digest;
      } else {
         throw new IllegalArgumentException("Certificate digest criteria value cannot be null or empty");
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("X509DigestCriterion [algorithm=");
      builder.append(this.algorithm);
      builder.append(", digest=");
      builder.append(Hex.encodeHexString(this.x509digest));
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.algorithm.hashCode();
      result = 37 * result + this.x509digest.hashCode();
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof X509DigestCriterion)) {
         return false;
      } else {
         X509DigestCriterion other = (X509DigestCriterion)obj;
         return this.algorithm.equals(other.algorithm) && Arrays.equals(this.x509digest, other.x509digest);
      }
   }
}
