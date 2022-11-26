package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nullable;

public class DataSealerException extends Exception {
   private static final long serialVersionUID = 5366134892878709189L;

   public DataSealerException() {
   }

   public DataSealerException(@Nullable String message) {
      super(message);
   }

   public DataSealerException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public DataSealerException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
