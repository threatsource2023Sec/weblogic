package com.bea.core.repackaged.springframework.core.serializer.support;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public class SerializationFailedException extends NestedRuntimeException {
   public SerializationFailedException(String message) {
      super(message);
   }

   public SerializationFailedException(String message, Throwable cause) {
      super(message, cause);
   }
}
