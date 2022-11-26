package net.shibboleth.utilities.java.support.security;

import java.security.KeyException;
import javax.annotation.Nullable;

public class KeyNotFoundException extends KeyException {
   private static final long serialVersionUID = 310860860022348748L;

   public KeyNotFoundException() {
   }

   public KeyNotFoundException(@Nullable String message) {
      super(message);
   }

   public KeyNotFoundException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public KeyNotFoundException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
