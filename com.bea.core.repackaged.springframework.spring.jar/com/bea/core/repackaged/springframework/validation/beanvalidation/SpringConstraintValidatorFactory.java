package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

public class SpringConstraintValidatorFactory implements ConstraintValidatorFactory {
   private final AutowireCapableBeanFactory beanFactory;

   public SpringConstraintValidatorFactory(AutowireCapableBeanFactory beanFactory) {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      this.beanFactory = beanFactory;
   }

   public ConstraintValidator getInstance(Class key) {
      return (ConstraintValidator)this.beanFactory.createBean(key);
   }

   public void releaseInstance(ConstraintValidator instance) {
      this.beanFactory.destroyBean(instance);
   }
}
