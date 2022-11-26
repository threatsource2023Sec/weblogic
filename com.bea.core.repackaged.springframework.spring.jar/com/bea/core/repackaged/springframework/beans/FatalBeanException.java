package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class FatalBeanException extends BeansException {
   public FatalBeanException(String msg) {
      super(msg);
   }

   public FatalBeanException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
