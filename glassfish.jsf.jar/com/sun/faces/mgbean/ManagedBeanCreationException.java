package com.sun.faces.mgbean;

import javax.faces.FacesException;

public class ManagedBeanCreationException extends FacesException {
   private static final long serialVersionUID = -641675334387524657L;

   public ManagedBeanCreationException() {
   }

   public ManagedBeanCreationException(String message) {
      super(message);
   }

   public ManagedBeanCreationException(Throwable t) {
      super(t);
   }

   public ManagedBeanCreationException(String message, Throwable t) {
      super(message, t);
   }
}
