package org.hibernate.validator;

import java.time.Duration;
import java.util.Set;
import javax.validation.Configuration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public interface HibernateValidatorConfiguration extends Configuration {
   String FAIL_FAST = "hibernate.validator.fail_fast";
   String ALLOW_PARAMETER_CONSTRAINT_OVERRIDE = "hibernate.validator.allow_parameter_constraint_override";
   String ALLOW_MULTIPLE_CASCADED_VALIDATION_ON_RESULT = "hibernate.validator.allow_multiple_cascaded_validation_on_result";
   String ALLOW_PARALLEL_METHODS_DEFINE_PARAMETER_CONSTRAINTS = "hibernate.validator.allow_parallel_method_parameter_constraint";
   /** @deprecated */
   @Deprecated
   String CONSTRAINT_MAPPING_CONTRIBUTOR = "hibernate.validator.constraint_mapping_contributor";
   String CONSTRAINT_MAPPING_CONTRIBUTORS = "hibernate.validator.constraint_mapping_contributors";
   String ENABLE_TRAVERSABLE_RESOLVER_RESULT_CACHE = "hibernate.validator.enable_traversable_resolver_result_cache";
   @Incubating
   String SCRIPT_EVALUATOR_FACTORY_CLASSNAME = "hibernate.validator.script_evaluator_factory";
   @Incubating
   String TEMPORAL_VALIDATION_TOLERANCE = "hibernate.validator.temporal_validation_tolerance";

   ResourceBundleLocator getDefaultResourceBundleLocator();

   ConstraintMapping createConstraintMapping();

   @Incubating
   Set getDefaultValueExtractors();

   HibernateValidatorConfiguration addMapping(ConstraintMapping var1);

   HibernateValidatorConfiguration failFast(boolean var1);

   HibernateValidatorConfiguration externalClassLoader(ClassLoader var1);

   HibernateValidatorConfiguration allowOverridingMethodAlterParameterConstraint(boolean var1);

   HibernateValidatorConfiguration allowMultipleCascadedValidationOnReturnValues(boolean var1);

   HibernateValidatorConfiguration allowParallelMethodsDefineParameterConstraints(boolean var1);

   HibernateValidatorConfiguration enableTraversableResolverResultCache(boolean var1);

   @Incubating
   HibernateValidatorConfiguration scriptEvaluatorFactory(ScriptEvaluatorFactory var1);

   @Incubating
   HibernateValidatorConfiguration temporalValidationTolerance(Duration var1);

   @Incubating
   HibernateValidatorConfiguration constraintValidatorPayload(Object var1);
}
