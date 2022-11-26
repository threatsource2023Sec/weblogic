package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import javax.validation.ValidationException;

public class OptionalValidatorFactoryBean extends LocalValidatorFactoryBean {
   public void afterPropertiesSet() {
      try {
         super.afterPropertiesSet();
      } catch (ValidationException var2) {
         LogFactory.getLog(this.getClass()).debug("Failed to set up a Bean Validation provider", var2);
      }

   }
}
