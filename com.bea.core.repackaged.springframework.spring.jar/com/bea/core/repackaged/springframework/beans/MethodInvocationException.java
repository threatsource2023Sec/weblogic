package com.bea.core.repackaged.springframework.beans;

import java.beans.PropertyChangeEvent;

public class MethodInvocationException extends PropertyAccessException {
   public static final String ERROR_CODE = "methodInvocation";

   public MethodInvocationException(PropertyChangeEvent propertyChangeEvent, Throwable cause) {
      super(propertyChangeEvent, "Property '" + propertyChangeEvent.getPropertyName() + "' threw exception", cause);
   }

   public String getErrorCode() {
      return "methodInvocation";
   }
}
