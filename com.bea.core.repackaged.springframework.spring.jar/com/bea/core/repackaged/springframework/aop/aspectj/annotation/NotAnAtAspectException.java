package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;

public class NotAnAtAspectException extends AopConfigException {
   private final Class nonAspectClass;

   public NotAnAtAspectException(Class nonAspectClass) {
      super(nonAspectClass.getName() + " is not an @AspectJ aspect");
      this.nonAspectClass = nonAspectClass;
   }

   public Class getNonAspectClass() {
      return this.nonAspectClass;
   }
}
