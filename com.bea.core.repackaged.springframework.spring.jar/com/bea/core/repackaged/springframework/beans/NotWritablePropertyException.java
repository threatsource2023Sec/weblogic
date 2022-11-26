package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class NotWritablePropertyException extends InvalidPropertyException {
   @Nullable
   private final String[] possibleMatches;

   public NotWritablePropertyException(Class beanClass, String propertyName) {
      super(beanClass, propertyName, "Bean property '" + propertyName + "' is not writable or has an invalid setter method: Does the return type of the getter match the parameter type of the setter?");
      this.possibleMatches = null;
   }

   public NotWritablePropertyException(Class beanClass, String propertyName, String msg) {
      super(beanClass, propertyName, msg);
      this.possibleMatches = null;
   }

   public NotWritablePropertyException(Class beanClass, String propertyName, String msg, Throwable cause) {
      super(beanClass, propertyName, msg, cause);
      this.possibleMatches = null;
   }

   public NotWritablePropertyException(Class beanClass, String propertyName, String msg, String[] possibleMatches) {
      super(beanClass, propertyName, msg);
      this.possibleMatches = possibleMatches;
   }

   @Nullable
   public String[] getPossibleMatches() {
      return this.possibleMatches;
   }
}
