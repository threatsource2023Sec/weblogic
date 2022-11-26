package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;

@SupportedValidationTarget({ValidationTarget.PARAMETERS})
public class ParameterScriptAssertValidator extends AbstractScriptAssertValidator {
   public void initialize(ConstraintDescriptor constraintDescriptor, HibernateConstraintValidatorInitializationContext initializationContext) {
      ParameterScriptAssert constraintAnnotation = (ParameterScriptAssert)constraintDescriptor.getAnnotation();
      this.validateParameters(constraintAnnotation);
      this.initialize(constraintAnnotation.lang(), constraintAnnotation.script(), initializationContext);
   }

   public boolean isValid(Object[] arguments, ConstraintValidatorContext constraintValidatorContext) {
      if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
         ((HibernateConstraintValidatorContext)constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class)).addMessageParameter("script", this.escapedScript);
      }

      List parameterNames = ((ConstraintValidatorContextImpl)constraintValidatorContext).getMethodParameterNames();
      Map bindings = this.getBindings(arguments, parameterNames);
      return this.scriptAssertContext.evaluateScriptAssertExpression(bindings);
   }

   private Map getBindings(Object[] arguments, List parameterNames) {
      Map bindings = CollectionHelper.newHashMap();

      for(int i = 0; i < arguments.length; ++i) {
         bindings.put(parameterNames.get(i), arguments[i]);
      }

      return bindings;
   }

   private void validateParameters(ParameterScriptAssert constraintAnnotation) {
      Contracts.assertNotEmpty(constraintAnnotation.script(), Messages.MESSAGES.parameterMustNotBeEmpty("script"));
      Contracts.assertNotEmpty(constraintAnnotation.lang(), Messages.MESSAGES.parameterMustNotBeEmpty("lang"));
   }
}
