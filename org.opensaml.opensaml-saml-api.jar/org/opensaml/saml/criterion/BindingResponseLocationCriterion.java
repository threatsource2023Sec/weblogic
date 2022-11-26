package org.opensaml.saml.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class BindingResponseLocationCriterion implements Criterion {
   @Nonnull
   @NotEmpty
   private final String location;

   public BindingResponseLocationCriterion(@Nonnull @NotEmpty String responseLocationUri) {
      this.location = (String)Constraint.isNotNull(StringSupport.trimOrNull(responseLocationUri), "Response location cannot be null or empty");
   }

   @Nonnull
   @NotEmpty
   public String getLocation() {
      return this.location;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("BindingResponseLocation [location=");
      builder.append(this.location);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.location.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof BindingResponseLocationCriterion ? this.location.equals(((BindingResponseLocationCriterion)obj).location) : false;
      }
   }
}
