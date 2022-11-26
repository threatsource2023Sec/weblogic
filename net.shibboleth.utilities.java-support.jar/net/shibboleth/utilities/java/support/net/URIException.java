package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nullable;

public class URIException extends Exception {
   private static final long serialVersionUID = -1461435058482575852L;

   public URIException() {
   }

   public URIException(@Nullable String message) {
      super(message);
   }

   public URIException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public URIException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
