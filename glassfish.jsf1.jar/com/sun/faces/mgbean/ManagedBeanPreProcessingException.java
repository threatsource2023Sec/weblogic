package com.sun.faces.mgbean;

import javax.faces.FacesException;

public class ManagedBeanPreProcessingException extends FacesException {
   private Type type;

   public ManagedBeanPreProcessingException() {
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
   }

   public ManagedBeanPreProcessingException(Type type) {
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
      this.type = type;
   }

   public ManagedBeanPreProcessingException(String message) {
      super(message);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
   }

   public ManagedBeanPreProcessingException(String message, Type type) {
      super(message);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
      this.type = type;
   }

   public ManagedBeanPreProcessingException(Throwable t) {
      super(t);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
   }

   public ManagedBeanPreProcessingException(Throwable t, Type type) {
      super(t);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
      this.type = type;
   }

   public ManagedBeanPreProcessingException(String message, Throwable t) {
      super(message, t);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
   }

   public ManagedBeanPreProcessingException(String message, Throwable t, Type type) {
      super(message, t);
      this.type = ManagedBeanPreProcessingException.Type.CHECKED;
      this.type = type;
   }

   public Type getType() {
      return this.type;
   }

   public static enum Type {
      CHECKED,
      UNCHECKED;
   }
}
