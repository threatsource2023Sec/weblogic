package org.hibernate.validator.internal.engine.constraintvalidation;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {
   public final ConstraintValidator getInstance(Class key) {
      return (ConstraintValidator)this.run(NewInstance.action(key, "ConstraintValidator"));
   }

   public void releaseInstance(ConstraintValidator instance) {
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
