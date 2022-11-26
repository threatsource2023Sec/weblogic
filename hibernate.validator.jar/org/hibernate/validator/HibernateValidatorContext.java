package org.hibernate.validator;

import java.time.Duration;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorContext;
import javax.validation.valueextraction.ValueExtractor;

public interface HibernateValidatorContext extends ValidatorContext {
   HibernateValidatorContext messageInterpolator(MessageInterpolator var1);

   HibernateValidatorContext traversableResolver(TraversableResolver var1);

   HibernateValidatorContext constraintValidatorFactory(ConstraintValidatorFactory var1);

   HibernateValidatorContext parameterNameProvider(ParameterNameProvider var1);

   HibernateValidatorContext clockProvider(ClockProvider var1);

   HibernateValidatorContext addValueExtractor(ValueExtractor var1);

   HibernateValidatorContext failFast(boolean var1);

   HibernateValidatorContext allowOverridingMethodAlterParameterConstraint(boolean var1);

   HibernateValidatorContext allowMultipleCascadedValidationOnReturnValues(boolean var1);

   HibernateValidatorContext allowParallelMethodsDefineParameterConstraints(boolean var1);

   HibernateValidatorContext enableTraversableResolverResultCache(boolean var1);

   @Incubating
   HibernateValidatorContext temporalValidationTolerance(Duration var1);

   @Incubating
   HibernateValidatorContext constraintValidatorPayload(Object var1);
}
