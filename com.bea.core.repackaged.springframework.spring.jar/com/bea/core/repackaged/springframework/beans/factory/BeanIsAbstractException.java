package com.bea.core.repackaged.springframework.beans.factory;

public class BeanIsAbstractException extends BeanCreationException {
   public BeanIsAbstractException(String beanName) {
      super(beanName, "Bean definition is abstract");
   }
}
