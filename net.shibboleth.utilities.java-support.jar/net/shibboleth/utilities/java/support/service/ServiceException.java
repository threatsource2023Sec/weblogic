package net.shibboleth.utilities.java.support.service;

import javax.annotation.Nullable;

public class ServiceException extends RuntimeException {
   private static final long serialVersionUID = 5875639786427807960L;

   public ServiceException() {
   }

   public ServiceException(@Nullable String message) {
      super(message);
   }

   public ServiceException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public ServiceException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
