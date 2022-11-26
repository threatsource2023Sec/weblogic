package org.opensaml.saml.criterion;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class EntityRoleCriterion implements Criterion {
   @Nonnull
   private final QName role;

   public EntityRoleCriterion(@Nonnull QName samlRole) {
      this.role = (QName)Constraint.isNotNull(samlRole, "SAML role cannot be null");
   }

   @Nonnull
   public QName getRole() {
      return this.role;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EntityRoleCriterion [role=");
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
         return obj instanceof EntityRoleCriterion ? this.role.equals(((EntityRoleCriterion)obj).role) : false;
      }
   }
}
