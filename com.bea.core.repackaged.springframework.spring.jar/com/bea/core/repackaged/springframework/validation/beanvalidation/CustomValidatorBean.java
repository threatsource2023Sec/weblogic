package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public class CustomValidatorBean extends SpringValidatorAdapter implements Validator, InitializingBean {
   @Nullable
   private ValidatorFactory validatorFactory;
   @Nullable
   private MessageInterpolator messageInterpolator;
   @Nullable
   private TraversableResolver traversableResolver;

   public void setValidatorFactory(ValidatorFactory validatorFactory) {
      this.validatorFactory = validatorFactory;
   }

   public void setMessageInterpolator(MessageInterpolator messageInterpolator) {
      this.messageInterpolator = messageInterpolator;
   }

   public void setTraversableResolver(TraversableResolver traversableResolver) {
      this.traversableResolver = traversableResolver;
   }

   public void afterPropertiesSet() {
      if (this.validatorFactory == null) {
         this.validatorFactory = Validation.buildDefaultValidatorFactory();
      }

      ValidatorContext validatorContext = this.validatorFactory.usingContext();
      MessageInterpolator targetInterpolator = this.messageInterpolator;
      if (targetInterpolator == null) {
         targetInterpolator = this.validatorFactory.getMessageInterpolator();
      }

      validatorContext.messageInterpolator(new LocaleContextMessageInterpolator(targetInterpolator));
      if (this.traversableResolver != null) {
         validatorContext.traversableResolver(this.traversableResolver);
      }

      this.setTargetValidator(validatorContext.getValidator());
   }
}
