package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class KeyLengthCriterion implements Criterion {
   private Integer keyLength;

   public KeyLengthCriterion(@Nonnull Integer length) {
      this.setKeyLength(length);
   }

   @Nonnull
   public Integer getKeyLength() {
      return this.keyLength;
   }

   public void setKeyLength(@Nonnull Integer length) {
      Constraint.isNotNull(length, "Key length criteria value cannot be null");
      this.keyLength = length;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("KeyLengthCriterion [keyLength=");
      builder.append(this.keyLength);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.keyLength.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof KeyLengthCriterion ? this.keyLength.equals(((KeyLengthCriterion)obj).keyLength) : false;
      }
   }
}
