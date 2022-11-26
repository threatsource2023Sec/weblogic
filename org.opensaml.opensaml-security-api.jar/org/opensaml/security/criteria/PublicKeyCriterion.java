package org.opensaml.security.criteria;

import java.security.PublicKey;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class PublicKeyCriterion implements Criterion {
   private PublicKey publicKey;

   public PublicKeyCriterion(@Nonnull PublicKey pubKey) {
      this.setPublicKey(pubKey);
   }

   @Nonnull
   public PublicKey getPublicKey() {
      return this.publicKey;
   }

   public void setPublicKey(@Nonnull PublicKey key) {
      Constraint.isNotNull(key, "Public key criteria value cannot be null");
      this.publicKey = key;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("PublicKeyCriterion [publicKey=");
      builder.append(this.publicKey);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.publicKey.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof PublicKeyCriterion ? this.publicKey.equals(((PublicKeyCriterion)obj).publicKey) : false;
      }
   }
}
