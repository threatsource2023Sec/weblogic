package com.bea.core.repackaged.springframework.core.convert;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public abstract class ConversionException extends NestedRuntimeException {
   public ConversionException(String message) {
      super(message);
   }

   public ConversionException(String message, Throwable cause) {
      super(message, cause);
   }
}
