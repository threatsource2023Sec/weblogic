package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class KeyNameCriterion implements Criterion {
   private String keyName;

   public KeyNameCriterion(@Nonnull String name) {
      this.setKeyName(name);
   }

   @Nonnull
   public String getKeyName() {
      return this.keyName;
   }

   public void setKeyName(@Nonnull String name) {
      String trimmed = StringSupport.trimOrNull(name);
      Constraint.isNotNull(trimmed, "Key name criteria value cannot be null or empty");
      this.keyName = trimmed;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("KeyNameCriterion [keyName=");
      builder.append(this.keyName);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.keyName.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof KeyNameCriterion ? this.keyName.equals(((KeyNameCriterion)obj).keyName) : false;
      }
   }
}
