package com.bea.core.repackaged.springframework.beans;

public class NullValueInNestedPathException extends InvalidPropertyException {
   public NullValueInNestedPathException(Class beanClass, String propertyName) {
      super(beanClass, propertyName, "Value of nested property '" + propertyName + "' is null");
   }

   public NullValueInNestedPathException(Class beanClass, String propertyName, String msg) {
      super(beanClass, propertyName, msg);
   }

   public NullValueInNestedPathException(Class beanClass, String propertyName, String msg, Throwable cause) {
      super(beanClass, propertyName, msg, cause);
   }
}
