package org.opensaml.core.config;

import javax.annotation.Nullable;

public class InitializationException extends Exception {
   private static final long serialVersionUID = 4498093523448059017L;

   public InitializationException() {
   }

   public InitializationException(@Nullable String msg) {
      super(msg);
   }

   public InitializationException(@Nullable Throwable cause) {
      super(cause);
   }

   public InitializationException(@Nullable String message, @Nullable Throwable cause) {
      super(message, cause);
   }
}
