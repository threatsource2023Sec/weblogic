package com.bea.core.repackaged.springframework.cglib.proxy;

import com.bea.core.repackaged.springframework.cglib.core.CodeGenerationException;

public class UndeclaredThrowableException extends CodeGenerationException {
   public UndeclaredThrowableException(Throwable t) {
      super(t);
   }

   public Throwable getUndeclaredThrowable() {
      return this.getCause();
   }
}
