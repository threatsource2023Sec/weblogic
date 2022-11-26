package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.parsing.Problem;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;

final class BeanMethod extends ConfigurationMethod {
   public BeanMethod(MethodMetadata metadata, ConfigurationClass configurationClass) {
      super(metadata, configurationClass);
   }

   public void validate(ProblemReporter problemReporter) {
      if (!this.getMetadata().isStatic()) {
         if (this.configurationClass.getMetadata().isAnnotated(Configuration.class.getName()) && !this.getMetadata().isOverridable()) {
            problemReporter.error(new NonOverridableMethodError());
         }

      }
   }

   private class NonOverridableMethodError extends Problem {
      public NonOverridableMethodError() {
         super(String.format("@Bean method '%s' must not be private or final; change the method's modifiers to continue", BeanMethod.this.getMetadata().getMethodName()), BeanMethod.this.getResourceLocation());
      }
   }
}
