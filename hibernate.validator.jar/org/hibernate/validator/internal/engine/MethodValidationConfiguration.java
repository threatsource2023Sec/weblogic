package org.hibernate.validator.internal.engine;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;
import org.hibernate.validator.internal.metadata.aggregated.rule.ParallelMethodsMustNotDefineGroupConversionForCascadedReturnValue;
import org.hibernate.validator.internal.metadata.aggregated.rule.ParallelMethodsMustNotDefineParameterConstraints;
import org.hibernate.validator.internal.metadata.aggregated.rule.ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine;
import org.hibernate.validator.internal.metadata.aggregated.rule.VoidMethodsMustNotBeReturnValueConstrained;
import org.hibernate.validator.internal.util.CollectionHelper;

public class MethodValidationConfiguration {
   private boolean allowOverridingMethodAlterParameterConstraint;
   private boolean allowMultipleCascadedValidationOnReturnValues;
   private boolean allowParallelMethodsDefineParameterConstraints;
   private Set configuredRuleSet;

   private MethodValidationConfiguration(boolean allowOverridingMethodAlterParameterConstraint, boolean allowMultipleCascadedValidationOnReturnValues, boolean allowParallelMethodsDefineParameterConstraints) {
      this.allowOverridingMethodAlterParameterConstraint = false;
      this.allowMultipleCascadedValidationOnReturnValues = false;
      this.allowParallelMethodsDefineParameterConstraints = false;
      this.allowOverridingMethodAlterParameterConstraint = allowOverridingMethodAlterParameterConstraint;
      this.allowMultipleCascadedValidationOnReturnValues = allowMultipleCascadedValidationOnReturnValues;
      this.allowParallelMethodsDefineParameterConstraints = allowParallelMethodsDefineParameterConstraints;
      this.configuredRuleSet = buildConfiguredRuleSet(allowOverridingMethodAlterParameterConstraint, allowMultipleCascadedValidationOnReturnValues, allowParallelMethodsDefineParameterConstraints);
   }

   public boolean isAllowOverridingMethodAlterParameterConstraint() {
      return this.allowOverridingMethodAlterParameterConstraint;
   }

   public boolean isAllowMultipleCascadedValidationOnReturnValues() {
      return this.allowMultipleCascadedValidationOnReturnValues;
   }

   public boolean isAllowParallelMethodsDefineParameterConstraints() {
      return this.allowParallelMethodsDefineParameterConstraints;
   }

   public Set getConfiguredRuleSet() {
      return this.configuredRuleSet;
   }

   private static Set buildConfiguredRuleSet(boolean allowOverridingMethodAlterParameterConstraint, boolean allowMultipleCascadedValidationOnReturnValues, boolean allowParallelMethodsDefineParameterConstraints) {
      HashSet result = CollectionHelper.newHashSet(5);
      if (!allowOverridingMethodAlterParameterConstraint) {
         result.add(new OverridingMethodMustNotAlterParameterConstraints());
      }

      if (!allowParallelMethodsDefineParameterConstraints) {
         result.add(new ParallelMethodsMustNotDefineParameterConstraints());
      }

      result.add(new VoidMethodsMustNotBeReturnValueConstrained());
      if (!allowMultipleCascadedValidationOnReturnValues) {
         result.add(new ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine());
      }

      result.add(new ParallelMethodsMustNotDefineGroupConversionForCascadedReturnValue());
      return CollectionHelper.toImmutableSet(result);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.allowMultipleCascadedValidationOnReturnValues ? 1231 : 1237);
      result = 31 * result + (this.allowOverridingMethodAlterParameterConstraint ? 1231 : 1237);
      result = 31 * result + (this.allowParallelMethodsDefineParameterConstraints ? 1231 : 1237);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         MethodValidationConfiguration other = (MethodValidationConfiguration)obj;
         if (this.allowMultipleCascadedValidationOnReturnValues != other.allowMultipleCascadedValidationOnReturnValues) {
            return false;
         } else if (this.allowOverridingMethodAlterParameterConstraint != other.allowOverridingMethodAlterParameterConstraint) {
            return false;
         } else {
            return this.allowParallelMethodsDefineParameterConstraints == other.allowParallelMethodsDefineParameterConstraints;
         }
      }
   }

   // $FF: synthetic method
   MethodValidationConfiguration(boolean x0, boolean x1, boolean x2, Object x3) {
      this(x0, x1, x2);
   }

   public static class Builder {
      private boolean allowOverridingMethodAlterParameterConstraint = false;
      private boolean allowMultipleCascadedValidationOnReturnValues = false;
      private boolean allowParallelMethodsDefineParameterConstraints = false;

      public Builder() {
      }

      public Builder(MethodValidationConfiguration template) {
         this.allowOverridingMethodAlterParameterConstraint = template.allowOverridingMethodAlterParameterConstraint;
         this.allowMultipleCascadedValidationOnReturnValues = template.allowMultipleCascadedValidationOnReturnValues;
         this.allowParallelMethodsDefineParameterConstraints = template.allowParallelMethodsDefineParameterConstraints;
      }

      public Builder allowOverridingMethodAlterParameterConstraint(boolean allow) {
         this.allowOverridingMethodAlterParameterConstraint = allow;
         return this;
      }

      public Builder allowMultipleCascadedValidationOnReturnValues(boolean allow) {
         this.allowMultipleCascadedValidationOnReturnValues = allow;
         return this;
      }

      public Builder allowParallelMethodsDefineParameterConstraints(boolean allow) {
         this.allowParallelMethodsDefineParameterConstraints = allow;
         return this;
      }

      public boolean isAllowOverridingMethodAlterParameterConstraint() {
         return this.allowOverridingMethodAlterParameterConstraint;
      }

      public boolean isAllowMultipleCascadedValidationOnReturnValues() {
         return this.allowMultipleCascadedValidationOnReturnValues;
      }

      public boolean isAllowParallelMethodsDefineParameterConstraints() {
         return this.allowParallelMethodsDefineParameterConstraints;
      }

      public MethodValidationConfiguration build() {
         return new MethodValidationConfiguration(this.allowOverridingMethodAlterParameterConstraint, this.allowMultipleCascadedValidationOnReturnValues, this.allowParallelMethodsDefineParameterConstraints);
      }
   }
}
