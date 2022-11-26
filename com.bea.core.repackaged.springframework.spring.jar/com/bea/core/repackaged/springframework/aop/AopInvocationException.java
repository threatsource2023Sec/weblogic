package com.bea.core.repackaged.springframework.aop;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public class AopInvocationException extends NestedRuntimeException {
   public AopInvocationException(String msg) {
      super(msg);
   }

   public AopInvocationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
