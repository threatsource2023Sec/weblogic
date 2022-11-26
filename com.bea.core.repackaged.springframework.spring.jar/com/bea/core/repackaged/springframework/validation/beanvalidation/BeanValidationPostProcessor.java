package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.aop.framework.AopProxyUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidationPostProcessor implements BeanPostProcessor, InitializingBean {
   @Nullable
   private Validator validator;
   private boolean afterInitialization = false;

   public void setValidator(Validator validator) {
      this.validator = validator;
   }

   public void setValidatorFactory(ValidatorFactory validatorFactory) {
      this.validator = validatorFactory.getValidator();
   }

   public void setAfterInitialization(boolean afterInitialization) {
      this.afterInitialization = afterInitialization;
   }

   public void afterPropertiesSet() {
      if (this.validator == null) {
         this.validator = Validation.buildDefaultValidatorFactory().getValidator();
      }

   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      if (!this.afterInitialization) {
         this.doValidate(bean);
      }

      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      if (this.afterInitialization) {
         this.doValidate(bean);
      }

      return bean;
   }

   protected void doValidate(Object bean) {
      Assert.state(this.validator != null, "No Validator set");
      Object objectToValidate = AopProxyUtils.getSingletonTarget(bean);
      if (objectToValidate == null) {
         objectToValidate = bean;
      }

      Set result = this.validator.validate(objectToValidate, new Class[0]);
      if (!result.isEmpty()) {
         StringBuilder sb = new StringBuilder("Bean state is invalid: ");
         Iterator it = result.iterator();

         while(it.hasNext()) {
            ConstraintViolation violation = (ConstraintViolation)it.next();
            sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
            if (it.hasNext()) {
               sb.append("; ");
            }
         }

         throw new BeanInitializationException(sb.toString());
      }
   }
}
