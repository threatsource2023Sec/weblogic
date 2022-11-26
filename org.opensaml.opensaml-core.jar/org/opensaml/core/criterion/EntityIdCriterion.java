package org.opensaml.core.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class EntityIdCriterion implements Criterion {
   @Nonnull
   @NotEmpty
   private final String id;

   public EntityIdCriterion(@Nonnull @NotEmpty String entityId) {
      this.id = (String)Constraint.isNotNull(StringSupport.trimOrNull(entityId), "Entity ID cannot be null or empty");
   }

   @Nonnull
   @NotEmpty
   public String getEntityId() {
      return this.id;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EntityIdCriterion [id=");
      builder.append(this.id);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EntityIdCriterion ? this.id.equals(((EntityIdCriterion)obj).id) : false;
      }
   }
}
