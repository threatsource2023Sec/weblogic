package org.hibernate.validator.constraintvalidation;

import java.time.Duration;
import javax.validation.ClockProvider;
import org.hibernate.validator.Incubating;
import org.hibernate.validator.spi.scripting.ScriptEvaluator;

@Incubating
public interface HibernateConstraintValidatorInitializationContext {
   ScriptEvaluator getScriptEvaluatorForLanguage(String var1);

   ClockProvider getClockProvider();

   @Incubating
   Duration getTemporalValidationTolerance();
}
