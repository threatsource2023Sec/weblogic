package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nullable;

public class DataExpiredException extends DataSealerException {
   private static final long serialVersionUID = -4345061831894801408L;

   public DataExpiredException() {
   }

   public DataExpiredException(@Nullable String message) {
      super(message);
   }

   public DataExpiredException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public DataExpiredException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
