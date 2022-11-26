package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public class EjbAccessException extends NestedRuntimeException {
   public EjbAccessException(String msg) {
      super(msg);
   }

   public EjbAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
