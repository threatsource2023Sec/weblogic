package org.hibernate.validator.cdi.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.internal.util.Contracts;

public class InjectingConstraintValidatorFactory implements ConstraintValidatorFactory {
   private final Map constraintValidatorMap = Collections.synchronizedMap(new IdentityHashMap());
   private final BeanManager beanManager;

   @Inject
   public InjectingConstraintValidatorFactory(BeanManager beanManager) {
      Contracts.assertNotNull(beanManager, "The BeanManager cannot be null");
      this.beanManager = beanManager;
   }

   public ConstraintValidator getInstance(Class key) {
      DestructibleBeanInstance destructibleBeanInstance = new DestructibleBeanInstance(this.beanManager, key);
      this.constraintValidatorMap.put(destructibleBeanInstance.getInstance(), destructibleBeanInstance);
      return (ConstraintValidator)destructibleBeanInstance.getInstance();
   }

   public void releaseInstance(ConstraintValidator instance) {
      DestructibleBeanInstance destructibleBeanInstance = (DestructibleBeanInstance)this.constraintValidatorMap.remove(instance);
      if (destructibleBeanInstance != null) {
         destructibleBeanInstance.destroy();
      }

   }
}
