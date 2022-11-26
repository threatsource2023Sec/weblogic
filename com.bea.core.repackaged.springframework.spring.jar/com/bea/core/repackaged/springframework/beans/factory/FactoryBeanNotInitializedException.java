package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;

public class FactoryBeanNotInitializedException extends FatalBeanException {
   public FactoryBeanNotInitializedException() {
      super("FactoryBean is not fully initialized yet");
   }

   public FactoryBeanNotInitializedException(String msg) {
      super(msg);
   }
}
