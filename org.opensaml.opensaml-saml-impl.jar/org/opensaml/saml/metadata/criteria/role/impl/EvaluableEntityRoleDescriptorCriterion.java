package org.opensaml.saml.metadata.criteria.role.impl;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.metadata.criteria.role.EvaluableRoleDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public class EvaluableEntityRoleDescriptorCriterion implements EvaluableRoleDescriptorCriterion {
   @Nonnull
   @NotEmpty
   private QName role;

   public EvaluableEntityRoleDescriptorCriterion(@Nonnull EntityRoleCriterion criterion) {
      Constraint.isNotNull(criterion, "EntityRoleCriterion was null");
      this.role = (QName)Constraint.isNotNull(criterion.getRole(), "Criterion entity role was null");
   }

   public EvaluableEntityRoleDescriptorCriterion(@Nonnull QName entityRole) {
      this.role = (QName)Constraint.isNotNull(entityRole, "Entity role QName was null");
   }

   public boolean apply(RoleDescriptor input) {
      if (input == null) {
         return false;
      } else {
         QName schemaType = input.getSchemaType();
         return schemaType != null && Objects.equals(this.role, schemaType) ? true : Objects.equals(this.role, input.getElementQName());
      }
   }

   public int hashCode() {
      return this.role.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof EvaluableEntityRoleDescriptorCriterion ? Objects.equals(this.role, ((EvaluableEntityRoleDescriptorCriterion)other).role) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("role", this.role).toString();
   }
}
