package org.hibernate.validator.engine;

import javax.validation.ConstraintViolation;

public interface HibernateConstraintViolation extends ConstraintViolation {
   Object getDynamicPayload(Class var1);
}
