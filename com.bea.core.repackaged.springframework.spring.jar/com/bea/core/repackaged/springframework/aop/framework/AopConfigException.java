package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public class AopConfigException extends NestedRuntimeException {
   public AopConfigException(String msg) {
      super(msg);
   }

   public AopConfigException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
