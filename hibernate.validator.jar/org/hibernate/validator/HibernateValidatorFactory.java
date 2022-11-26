package org.hibernate.validator;

import java.time.Duration;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public interface HibernateValidatorFactory extends ValidatorFactory {
   @Incubating
   ScriptEvaluatorFactory getScriptEvaluatorFactory();

   @Incubating
   Duration getTemporalValidationTolerance();

   HibernateValidatorContext usingContext();
}
