package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.validation.annotation.Validated;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class MethodValidationPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements InitializingBean {
   private Class validatedAnnotationType = Validated.class;
   @Nullable
   private Validator validator;

   public void setValidatedAnnotationType(Class validatedAnnotationType) {
      Assert.notNull(validatedAnnotationType, (String)"'validatedAnnotationType' must not be null");
      this.validatedAnnotationType = validatedAnnotationType;
   }

   public void setValidator(Validator validator) {
      if (validator instanceof LocalValidatorFactoryBean) {
         this.validator = ((LocalValidatorFactoryBean)validator).getValidator();
      } else if (validator instanceof SpringValidatorAdapter) {
         this.validator = (Validator)validator.unwrap(Validator.class);
      } else {
         this.validator = validator;
      }

   }

   public void setValidatorFactory(ValidatorFactory validatorFactory) {
      this.validator = validatorFactory.getValidator();
   }

   public void afterPropertiesSet() {
      Pointcut pointcut = new AnnotationMatchingPointcut(this.validatedAnnotationType, true);
      this.advisor = new DefaultPointcutAdvisor(pointcut, this.createMethodValidationAdvice(this.validator));
   }

   protected Advice createMethodValidationAdvice(@Nullable Validator validator) {
      return validator != null ? new MethodValidationInterceptor(validator) : new MethodValidationInterceptor();
   }
}
