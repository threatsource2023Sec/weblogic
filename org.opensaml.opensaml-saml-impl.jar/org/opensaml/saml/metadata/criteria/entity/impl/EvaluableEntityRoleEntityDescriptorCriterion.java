package org.opensaml.saml.metadata.criteria.entity.impl;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.metadata.criteria.entity.EvaluableEntityDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EvaluableEntityRoleEntityDescriptorCriterion implements EvaluableEntityDescriptorCriterion {
   private QName role;

   public EvaluableEntityRoleEntityDescriptorCriterion(EntityRoleCriterion criterion) {
      Constraint.isNotNull(criterion, "EntityRoleCriterion was null");
      this.role = (QName)Constraint.isNotNull(criterion.getRole(), "Criterion role QName was null");
   }

   public EvaluableEntityRoleEntityDescriptorCriterion(QName entityRole) {
      this.role = (QName)Constraint.isNotNull(entityRole, "Entity Role QName was null");
   }

   public boolean apply(EntityDescriptor entityDescriptor) {
      if (entityDescriptor == null) {
         return false;
      } else {
         return !entityDescriptor.getRoleDescriptors(this.role).isEmpty();
      }
   }

   public int hashCode() {
      return this.role.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof EvaluableEntityRoleEntityDescriptorCriterion ? Objects.equals(this.role, ((EvaluableEntityRoleEntityDescriptorCriterion)other).role) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("role", this.role).toString();
   }
}
