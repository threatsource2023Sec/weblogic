package org.opensaml.saml.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public final class RoleDescriptorCriterion implements Criterion {
   @Nonnull
   private final RoleDescriptor role;

   public RoleDescriptorCriterion(@Nonnull RoleDescriptor samlRole) {
      this.role = (RoleDescriptor)Constraint.isNotNull(samlRole, "SAML role cannot be null");
   }

   @Nonnull
   public RoleDescriptor getRole() {
      return this.role;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("RoleDescriptorCriterion [role=");
      builder.append(this.role);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.role.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof RoleDescriptorCriterion ? this.role.equals(((RoleDescriptorCriterion)obj).role) : false;
      }
   }
}
