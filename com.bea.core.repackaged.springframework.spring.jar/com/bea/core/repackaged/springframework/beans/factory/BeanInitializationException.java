package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;

public class BeanInitializationException extends FatalBeanException {
   public BeanInitializationException(String msg) {
      super(msg);
   }

   public BeanInitializationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
