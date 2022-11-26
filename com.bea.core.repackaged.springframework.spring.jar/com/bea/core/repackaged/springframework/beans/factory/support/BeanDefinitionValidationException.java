package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;

public class BeanDefinitionValidationException extends FatalBeanException {
   public BeanDefinitionValidationException(String msg) {
      super(msg);
   }

   public BeanDefinitionValidationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
