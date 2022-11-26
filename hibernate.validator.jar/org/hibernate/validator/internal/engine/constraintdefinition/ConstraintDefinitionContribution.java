package org.hibernate.validator.internal.engine.constraintdefinition;

import java.util.List;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ConstraintDefinitionContribution {
   private final Class constraintType;
   private final List validatorDescriptors;
   private final boolean includeExisting;

   public ConstraintDefinitionContribution(Class constraintType, List validatorDescriptors, boolean includeExisting) {
      this.constraintType = constraintType;
      this.validatorDescriptors = CollectionHelper.toImmutableList(validatorDescriptors);
      this.includeExisting = includeExisting;
   }

   public Class getConstraintType() {
      return this.constraintType;
   }

   public List getValidatorDescriptors() {
      return this.validatorDescriptors;
   }

   public boolean includeExisting() {
      return this.includeExisting;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConstraintDefinitionContribution that = (ConstraintDefinitionContribution)o;
         if (!this.constraintType.equals(that.constraintType)) {
            return false;
         } else {
            return this.validatorDescriptors.equals(that.validatorDescriptors);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.constraintType.hashCode();
      result = 31 * result + this.validatorDescriptors.hashCode();
      return result;
   }

   public String toString() {
      return "ConstraintDefinitionContribution{constraintType=" + this.constraintType + ", validatorDescriptors=" + this.validatorDescriptors + ", includeExisting=" + this.includeExisting + '}';
   }
}
