package org.hibernate.validator.constraintvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.Incubating;

@Incubating
public interface HibernateConstraintValidator extends ConstraintValidator {
   default void initialize(ConstraintDescriptor constraintDescriptor, HibernateConstraintValidatorInitializationContext initializationContext) {
   }
}
